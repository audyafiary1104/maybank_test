package com.maybank.test.adapter;

import com.maybank.test.dto.request.SearchDto;
import com.maybank.test.dto.response.GithubResponse;
import com.maybank.test.exception.CustomException;
import com.maybank.test.util.constant.GlobalConstant;
import com.maybank.test.util.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GithubClient {
    @Autowired
    private RestTemplate restTemplate;

    public GithubResponse searchUsers(SearchDto searchDto) {
           if (searchDto.getSize().isBlank() || searchDto.getSize().isEmpty())
                searchDto.setSize(GlobalConstant.DEFAULT_SIZE_PAGE);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(GlobalConstant.DEFAULT_URI)
                .queryParam("q", searchDto.getSearch())
                .queryParam("page",searchDto.getPage())
                .queryParam("order",searchDto.getOrder())
                .queryParam("sort",searchDto.getSort())
                .queryParam("per_page", searchDto.getSize());
        ResponseEntity<GithubResponse> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(),GithubResponse.class);
        GithubResponse response = responseEntity.getBody();
        if (response.getTotalCount() <= 0)
            throw new CustomException(ResponseCode.RESPONSE_NO_RECORD,ResponseCode.NO_RECORD_MESSAGE);
        return response;
    }
}
