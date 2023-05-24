package com.maybank.test.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GithubResponse{
	@JsonProperty("total_count")
	private Integer totalCount;
	@JsonProperty("incomplete_results")
	private Boolean incompleteResults;
	@JsonProperty("items")
	private List<GithubUserDTO> items;
}