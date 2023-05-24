package com.maybank.test.config;

import com.maybank.test.exception.CustomException;
import com.maybank.test.util.constant.GlobalConstant;
import com.maybank.test.util.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;


@Configuration
public class HttpClientConfig {
    @Value("${api.github.token}")
    private String authorizationHeader;
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setInterceptors(Collections.singletonList(loggingInterceptor()));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    private ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", GlobalConstant.CONTENT_TYPE);
            headers.set("Authorization", "Bearer " + authorizationHeader);
            headers.set("X-GitHub-Api-Version", GlobalConstant.GITHUB_API_VERSION);
            logRequest(request,body);
            ClientHttpResponse response = execution.execute(request, body);
            logResponse(response);
            return response;
        };
    }
    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        String requestBody = new String(body, StandardCharsets.UTF_8);
        UriComponents components = UriComponentsBuilder.fromUriString(request.getURI().toString()).build();
        String pageParam = components.getQueryParams().getFirst("per_page");
        int page = Integer.parseInt(pageParam);
        if (page > 100)
            throw new CustomException(ResponseCode.RESPONSE_MAX_PAG,ResponseCode.MAX_PAGE_MESSAGE);
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)){
            throw new CustomException(ResponseCode.RESPONSE_VALIDATION,ResponseCode.VALIDATION_MESSAGE);
        }
    }

    @Bean
    public URI defaultEndpoint() throws URISyntaxException {
        return new URI(GlobalConstant.DEFAULT_URI);
    }

}
