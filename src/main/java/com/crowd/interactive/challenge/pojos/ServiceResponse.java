package com.crowd.interactive.challenge.pojos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    private String message;
    private String timestamp;
    private Object data;
    private HttpStatus status;

    public ServiceResponse(String message) {
	this.message = message;
	this.timestamp = LocalDateTime.now().toString();
    }

    public ServiceResponse(HttpStatus status) {
	this.status = status;
    }
}
