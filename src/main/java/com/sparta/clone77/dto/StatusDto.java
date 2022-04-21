package com.sparta.clone77.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@NoArgsConstructor
public class StatusDto {

    private boolean status = true;
    private String http = String.valueOf(HttpStatus.OK);
    private String message;

    public StatusDto(String message) { this.message = message; }

}
