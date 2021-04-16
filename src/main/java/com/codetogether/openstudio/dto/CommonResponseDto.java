package com.codetogether.openstudio.dto;

import lombok.Getter;

@Getter
public class CommonResponseDto {
    private Boolean result;
    private String message;

    public CommonResponseDto(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
