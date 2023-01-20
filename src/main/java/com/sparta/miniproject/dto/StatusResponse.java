package com.sparta.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponse {
    private String message;
    private int statusCode;

    public StatusResponse(String massage, int statusCode) {
        this.message = massage;
        this.statusCode = statusCode;
    }


}