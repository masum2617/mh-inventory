package com.mh.inventory.exception;

import com.mh.inventory.common.commonresponse.ErrorInfo;
import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.enums.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        Response res = new Response();
        res.setError(ErrorInfo.of(ErrorCode.RESOURCE_NOT_FOUND.getCode(), ErrorCode.RESOURCE_NOT_FOUND.getDefaultMessage()));

        return res;
    }
}
