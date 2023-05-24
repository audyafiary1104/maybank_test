package com.maybank.test.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.GithubResponse;
import com.maybank.test.exception.CustomException;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.services.JasperService;
import com.maybank.test.util.constant.ResponseCode;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperServiceImpl implements JasperService {
    @Value("${report.template.path}")
    private String templatePath;

    @Value("${report.output.path}")
    private String outputPath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public String generateReport(SearchDto searchDto, GithubResponse githubResponse) {
        try {
            Resource reportResource = resourceLoader.getResource(templatePath);
            File reportFile = reportResource.getFile();
            ObjectMapper objectMapper = new ObjectMapper();

            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());
            String json = objectMapper.writeValueAsString(githubResponse.getItems());
            List<Object> dataList = objectMapper.readValue(json, new TypeReference<List<Object>>() {});
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            String pagination = searchDto.getPage() + "/" + githubResponse.getTotalCount() / Integer.parseInt(searchDto.getSize());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("query", searchDto.getSearch());
            parameters.put("Pagination", pagination);
            parameters.put("total_row", searchDto.getSize());
            parameters.put("total_count", githubResponse.getTotalCount());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            String uploadDir = StringUtils.cleanPath(outputPath);
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String timestamp = now.format(formatter);
            String outputFilePath = uploadPath.resolve(timestamp + ".pdf").toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

            return timestamp ;
        } catch (JRException | IOException e) {
            throw new CustomException(ResponseCode.RESPONSE_JASPER_ERROR, ResponseCode.JASPER_ERROR_MESSAGE);
        }
    }

    @Override
    public Resource downloadReport(String id)   {
        String uploadDir = StringUtils.cleanPath(outputPath);
        String uploadPath = Paths.get(uploadDir, id ).toString();
        File reportFile = new File(uploadPath);
        if (!reportFile.exists() || !reportFile.isFile()) {
            throw new CustomException(ResponseCode.RESPONSE_FILE_NOTFOUND,ResponseCode.FILE_NOT_FOUND_MESSAGE);
        }
        Resource resource = null;
        try {
            resource = new UrlResource(reportFile.toURI());
        } catch (MalformedURLException e) {
            throw new CustomException(ResponseCode.RESPONSE_FILE_INVALID,ResponseCode.FILE_INVALID_URL);

        }

        if (!resource.exists() || !resource.isReadable()) {
            throw new CustomException(ResponseCode.RESPONSE_FILE_NOT_READ,ResponseCode.FILE_NOT_READ_MESSAGE);
        }

        return resource;
    }
}
