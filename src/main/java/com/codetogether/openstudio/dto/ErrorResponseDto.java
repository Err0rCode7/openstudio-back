package com.codetogether.openstudio.dto;

import lombok.RequiredArgsConstructor;

public class ErrorResponseDto extends CommonResponseDto {
    public ErrorResponseDto(Boolean result, String message) {
        super(result, message);
    }
}
