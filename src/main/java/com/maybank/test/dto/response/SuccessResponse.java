package com.maybank.test.dto.response;

import com.maybank.test.util.constant.ResponseCode;
import lombok.Data;

@Data
public class SuccessResponse<T> {
    private T result;
    private String message = ResponseCode.SUCCESS;
}
