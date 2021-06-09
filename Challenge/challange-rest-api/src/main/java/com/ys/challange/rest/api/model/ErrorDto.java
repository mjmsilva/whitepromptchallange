package com.ys.challange.rest.api.model;

import lombok.Data;

@Data
public class ErrorDto {

    private String timestamp;
    private String status;
    private String errorMessage;
    private String errorDescription;
}
