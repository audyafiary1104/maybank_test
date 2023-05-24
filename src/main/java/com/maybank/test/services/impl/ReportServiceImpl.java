package com.maybank.test.services.impl;
import com.maybank.test.adapter.GithubClient;
import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.GithubResponse;
import com.maybank.test.dto.response.SuccessResponse;
import com.maybank.test.exception.CustomException;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import com.maybank.test.repository.HistoryExportRepository;
import com.maybank.test.repository.ReportRepository;
import com.maybank.test.services.JasperService;
import com.maybank.test.services.ReportService;
import com.maybank.test.util.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    GithubClient githubClient;
    @Autowired
    JasperService jasperService;
    @Autowired
    HistoryExportRepository historyExportRepository;
    @Autowired
    ReportRepository reportRepository;
    @Override
    public Resource downloadReport(String reportId)  {
        Optional<Report> optionalReport = reportRepository.findByFilename(reportId+ ".pdf");
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Resource filePath = jasperService.downloadReport(report.getFilename());
            HistoryExport historyExport = new HistoryExport();
            historyExport.setReport(report);
            historyExport.setExportedAt(LocalDateTime.now());
            report.getHistoryExports().add(historyExport);
            reportRepository.save(report);
            return filePath;
        } else {
            throw new CustomException(ResponseCode.RESPONSE_NO_RECORD,ResponseCode.NO_RECORD_MESSAGE);
        }


    }

    @Override
    public SuccessResponse<List<HistoryExport>> historyReport(Long reportId) {
        SuccessResponse<List<HistoryExport>> successResponse = new SuccessResponse<>();
        if(reportId != null){
            Optional<Report> optionalReport = reportRepository.findById(reportId);
            if (optionalReport.isPresent()) {
                 Report report = optionalReport.get();
                List<HistoryExport> list = historyExportRepository.findByReport(report);
                successResponse.setResult(list);
            } else {
                throw new CustomException(ResponseCode.RESPONSE_NO_RECORD,ResponseCode.NO_RECORD_MESSAGE);
            }
        }else {
            List<HistoryExport> list = historyExportRepository.findAll();
            successResponse.setResult(list);
        }
        return successResponse;
    }

    @Override
    public SuccessResponse<List<Report>> ListReport(Long reportId) {
        SuccessResponse<List<Report>> successResponse = new SuccessResponse<>();
        if(reportId != null){
            Optional<Report> optionalReport = reportRepository.findById(reportId);
            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();
                List<Report> list = new ArrayList<>();
                list.add(report);
                successResponse.setResult(list);
            } else {
                throw new CustomException(ResponseCode.RESPONSE_NO_RECORD,ResponseCode.NO_RECORD_MESSAGE);
            }
        }else {
            List<Report> list = reportRepository.findAll();
            successResponse.setResult(list);
        }
        return successResponse;
    }

    @Override
    public SuccessResponse<Report> searchUsers(SearchDto searchDto) throws URISyntaxException {
        GithubResponse githubResponse =  githubClient.searchUsers(searchDto);
        String filePath = jasperService.generateReport(searchDto,githubResponse);
        Report report = new Report();
        report.setFilename(filePath+ ".pdf");
        report.setDownloadUrl("/report/download/"+filePath);
        report.setCreatedAt(LocalDateTime.now());
        reportRepository.save(report);
        SuccessResponse<Report> successResponse = new SuccessResponse<>();
        successResponse.setResult(report);
        return successResponse;
    }

}
