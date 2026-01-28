package com.mh.inventory.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Centralized registry of all error codes used in the application.
 *
 */
@Getter
public enum ErrorCode {

    // ========== Authentication & Authorization Errors (1000-1999) ==========
    AUTHENTICATION_FAILED("AUTH_1000", "Authentication failed", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("AUTH_1001", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    ACCOUNT_EXPIRED("AUTH_1002", "Account has expired", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("AUTH_1003", "Account locked due to 5 or more incorrect password attempts. Please contact the administrator to unlock your account.", HttpStatus.UNAUTHORIZED),
    CREDENTIALS_EXPIRED("AUTH_1004", "Credentials have expired", HttpStatus.UNAUTHORIZED),
    ACCOUNT_DISABLED("AUTH_1005", "Account is disabled", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH_1005", "JWT token has expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH_1006", "Invalid JWT token", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_FOUND("AUTH_1007", "JWT token not found", HttpStatus.UNAUTHORIZED),
    TOKEN_REVOKED("AUTH_1008", "JWT token has been revoked", HttpStatus.UNAUTHORIZED),
    INSUFFICIENT_PERMISSIONS("AUTH_1009", "Insufficient permissions to access this resource", HttpStatus.FORBIDDEN),
    ACCESS_DENIED("AUTH_1010", "Access denied", HttpStatus.FORBIDDEN),
    PASSWORD_NOT_MATCHED("AUTH_1011", "Password does not match", HttpStatus.BAD_REQUEST),
    FIRST_LOGIN_REQUIRED("AUTH_1012", "You must register before logging in", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED("AUTH_1013", "User is not authorized to perform this action", HttpStatus.UNAUTHORIZED),

    // ========== Validation Errors (2000-2999) ==========
    VALIDATION_ERROR("VAL_2000", "Validation failed", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_PARAMETER("VAL_2001", "Invalid request parameter", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD("VAL_2002", "Missing required field", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT("VAL_2003", "Invalid format", HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION("VAL_2004", "Constraint violation", HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_NOT_VALID_ERROR("VAL_2005", "Method argument is not valid", HttpStatus.BAD_REQUEST),

    // ========== Resource Errors (3000-3999) ==========
    RESOURCE_NOT_FOUND("RES_3000", "Resource not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("RES_3001", "User not found", HttpStatus.NOT_FOUND),
    BAD_CREDENTIAL("RES_3002", "Invalid credentials", HttpStatus.UNAUTHORIZED),
    RESOURCE_ALREADY_EXISTS("RES_3100", "Resource already exists", HttpStatus.CONFLICT),
    USER_ALREADY_EXISTS("RES_3101", "User already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("RES_3102", "Email already exists", HttpStatus.CONFLICT),
    MOBILE_NUMBER_ALREADY_EXISTS("RES_3103", "Mobile number already exists", HttpStatus.CONFLICT),
    LOGIN_ID_ALREADY_EXISTS("RES_3104", "Login ID already exists", HttpStatus.CONFLICT),
    DOMAIN_ALREADY_EXISTS("RES_3105", "Domain name already exists", HttpStatus.CONFLICT),

    // ========== Business Logic Errors (4000-4999) ==========
    OPERATION_FAILED("BUS_4000", "Operation failed", HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_EXPIRED("BUS_4002", "Verification code has expired", HttpStatus.BAD_REQUEST),
    INVALID_VERIFICATION_CODE("BUS_4003", "Invalid verification code", HttpStatus.BAD_REQUEST),
    DATA_NOT_MATCHED("BUS_4004", "Data does not match", HttpStatus.BAD_REQUEST),

    // ========== File Handling Errors (5000-5999) ==========
    FILE_UPLOAD_ERROR("FILE_5000", "File upload failed", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND("FILE_5001", "File not found", HttpStatus.NOT_FOUND),
    INVALID_FILE_TYPE("FILE_5002", "Invalid file type", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE("FILE_5003", "File size exceeds the maximum allowed limit", HttpStatus.BAD_REQUEST),

    // ========== External Service Errors (6000-6999) ==========
    EXTERNAL_SERVICE_ERROR("EXT_6000", "External service error", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_TIMEOUT("EXT_6001", "External service timeout", HttpStatus.GATEWAY_TIMEOUT),
    EXTERNAL_SERVICE_UNAVAILABLE("EXT_6002", "External service unavailable", HttpStatus.SERVICE_UNAVAILABLE),

    // ========== System Errors (9000-9999) ==========
    INTERNAL_SERVER_ERROR("SYS_9000", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("SYS_9001", "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    DATABASE_ERROR("SYS_9002", "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNEXPECTED_ERROR("SYS_9999", "An unexpected error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

}
