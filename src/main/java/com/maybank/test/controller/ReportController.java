package com.maybank.test.controller;

import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.SuccessResponse;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import com.maybank.test.services.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
       @Autowired
       ReportServiceImpl reportService;


    @GetMapping("/")
    public ResponseEntity<SuccessResponse<Report>> search(SearchDto searchDto) throws URISyntaxException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.searchUsers(searchDto));
    }
    @GetMapping("/get-all")
    public ResponseEntity<SuccessResponse<List<Report>>> listReport(@RequestParam(value = "report_id",required = false) Long reportID) throws URISyntaxException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.ListReport(reportID));
    }
    @GetMapping("/download/{reportId}")
    public ResponseEntity<Resource> download(@PathVariable("reportId") String id) throws IOException {
        Resource resource = reportService.downloadReport(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping("/history-export")
    public ResponseEntity<SuccessResponse<List<HistoryExport>>> HistoryDownload(@RequestParam(value = "REPORT_ID",required = false) Long reportID) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.historyReport(reportID));
    }
}
