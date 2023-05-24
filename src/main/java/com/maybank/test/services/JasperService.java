package com.maybank.test.services;

import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.GithubResponse;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface JasperService {
  String generateReport(SearchDto searchDto, GithubResponse githubResponse) throws URISyntaxException;
  Resource downloadReport(String id) ;

}
