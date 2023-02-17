package com.seerbit.transstats.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResponseCodes {
    REQUEST_SUCCESSFUL("00", "Request Successful."),
    RESOURCE_NOT_FOUND("05", "Resource not found."),
    RESOURCE_ALREADY_EXIST("06", "Resource already exist."),
    BAD_INPUT_PARAM("07", "Bad input params."),
    INVALID_DATE_FORMAT("77","Invalid date format"),
    IO_EXCEPTION("88","I/O Error, Please try again"),
    INTERNAL_SERVER("99","Internal Server Error");

    private String code;
    private String message;
}
