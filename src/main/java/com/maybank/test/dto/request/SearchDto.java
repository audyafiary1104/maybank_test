package com.maybank.test.dto.request;

import com.maybank.test.util.constant.GlobalConstant;
import lombok.Data;
import lombok.Value;

@Data
public class SearchDto {

    private String search;
    private String sort  = "repositories";
    private String order = "desc";
    private String page = GlobalConstant.DEFAULT_PAGE;
    private String size = GlobalConstant.DEFAULT_SIZE_PAGE;
}
