package com.maybank.test.util.constant;

public class ResponseCode {
    public static final String RESPONSE_VALIDATION = "VALIDATION_ERROR";
    public static final String VALIDATION_MESSAGE = "Search parameter is required";
    public static final String SUCCESS = "SUCCESS";

    public static final String RESPONSE_NO_RECORD = "DATA_NO_RECORD";
    public static final String NO_RECORD_MESSAGE = "No records found";
    public static final String RESPONSE_MAX_PAG = "PAGE_OVER_SIZE";
    public static final String MAX_PAGE_MESSAGE = "Page size exceeds the maximum limit";
    public static final String RESPONSE_JASPER_ERROR = "GENERATE_PDF_ERROR";
    public static final String JASPER_ERROR_MESSAGE = "Error generating PDF";
    public static final String RESPONSE_FILE_NOTFOUND = "FILE_NOT_FOUND";
    public static final String FILE_NOT_FOUND_MESSAGE = "File not found";
    public static final String RESPONSE_FILE_NOT_READ = "FILE_PERMISSION";
    public static final String FILE_NOT_READ_MESSAGE = "Cannot read the file";
    public static final String RESPONSE_FILE_INVALID = "FILE_INVALID_URL";
    public static final String FILE_INVALID_URL = "Invalid file URL";

    public static final String RESPONSE_UNAUTHORIZED = "UNAUTHORIZED_ACCESS";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized access: Please ensure your github token credentials are correct";


}
