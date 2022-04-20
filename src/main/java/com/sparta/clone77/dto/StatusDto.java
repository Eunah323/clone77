package com.sparta.clone77.dto;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class StatusDto {

    private boolean status = true;
    private String http = String.valueOf(HttpStatus.OK);
    private String msg;

    public StatusDto(String msg) { this.msg = msg; }

}
