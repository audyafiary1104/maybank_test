package com.maybank.test.services;

import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.GithubResponse;
import com.maybank.test.dto.response.SuccessResponse;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public interface ReportService {
     Resource downloadReport(String id) throws IOException;
     SuccessResponse<List<HistoryExport>> historyReport(Long reportId);
     SuccessResponse<List<Report>> ListReport(Long reportId);
     SuccessResponse<Report> searchUsers(SearchDto searchDto) throws URISyntaxException;
}
