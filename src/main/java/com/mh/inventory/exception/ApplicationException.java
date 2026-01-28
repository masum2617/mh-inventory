package com.mh.inventory.exception;

import com.mh.inventory.enums.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Base exception class for all application-specific exceptions.
 * This class provides a standardized way to create exceptions with error codes.
 */
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Creates a new ApplicationException with the specified error code.
     *
     * @param errorCode The error code
     */
    public ApplicationException(ErrorCode errorCode) {
        this(errorCode, errorCode.getDefaultMessage(), errorCode.getHttpStatus());
    }

    /**
     * Creates a new ApplicationException with the specified error code and custom message.
     *
     * @param errorCode The error code
     * @param message   The custom error message
     */
    public ApplicationException(ErrorCode errorCode, String message) {
        this(errorCode, message, errorCode.getHttpStatus());
    }

    /**
     * Creates a new ApplicationException with the specified error code, custom message, and HTTP status.
     *
     * @param errorCode  The error code
     * @param message    The custom error message
     * @param httpStatus The HTTP status
     */
    public ApplicationException(ErrorCode errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Gets the error code.
     *
     * @return The error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the error message.
     *
     * @return The error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets the HTTP status.
     *
     * @return The HTTP status
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
