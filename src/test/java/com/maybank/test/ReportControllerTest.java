package com.maybank.test;

import com.maybank.test.controller.ReportController;
import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.SuccessResponse;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import com.maybank.test.services.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
 class ReportControllerTest {

    @Mock
    private ReportServiceImpl reportService;

    @InjectMocks
    private ReportController reportController;

    @Test
    public void testSearch() throws URISyntaxException {
        // Prepare test data
        SearchDto searchDto = new SearchDto();

        // Mock the service method
        SuccessResponse<Report> response = new SuccessResponse<>();
        when(reportService.searchUsers(searchDto)).thenReturn(response);

        // Perform the API call
        ResponseEntity<SuccessResponse<Report>> result = reportController.search(searchDto);

        // Verify the response
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());

        // Verify the service method was called
        verify(reportService).searchUsers(searchDto);
    }

    @Test
    public void testListReport() throws URISyntaxException {
        // Prepare test data
        Long reportId = 1L;

        // Mock the service method
        SuccessResponse<List<Report>> response = new SuccessResponse<>();
        when(reportService.ListReport(reportId)).thenReturn(response);

        // Perform the API call
        ResponseEntity<SuccessResponse<List<Report>>> result = reportController.listReport(reportId);

        // Verify the response
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());

        // Verify the service method was called
        verify(reportService).ListReport(reportId);
    }

    @Test
    public void testDownload() throws IOException {
        // Prepare test data
        String id = "123";

        // Mock the service method
        Resource resource = new ByteArrayResource(new byte[0]);
        when(reportService.downloadReport(id)).thenReturn(resource);

        // Perform the API call
        ResponseEntity<Resource> result = reportController.download(id);

        // Verify the response
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resource, result.getBody());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, result.getHeaders().getContentType());

        // Verify the service method was called
        verify(reportService).downloadReport(id);
    }

    @Test
    public void testHistoryDownload() {
        // Prepare test data
        Long reportId = 1L;

        // Mock the service method
        SuccessResponse<List<HistoryExport>> response = new SuccessResponse<>();
        when(reportService.historyReport(reportId)).thenReturn(response);

        // Perform the API call
        ResponseEntity<SuccessResponse<List<HistoryExport>>> result = reportController.HistoryDownload(reportId);

        // Verify the response
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());

        // Verify the service method was called
        verify(reportService).historyReport(reportId);
    }
}
