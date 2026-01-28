package com.mh.inventory.exception;

import com.mh.inventory.enums.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception thrown when validation fails.
 */
public class ValidationException extends ApplicationException {

    private final Map<String, String> fieldErrors;

    /**
     * Creates a new ValidationException with the default error code.
     */
    public ValidationException() {
        super(ErrorCode.VALIDATION_ERROR);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Creates a new ValidationException with a custom message.
     *
     * @param message The custom error message
     */
    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Creates a new ValidationException with field errors.
     *
     * @param fieldErrors Map of field names to error messages
     */
    public ValidationException(Map<String, String> fieldErrors) {
        super(ErrorCode.VALIDATION_ERROR);
        this.fieldErrors = fieldErrors;
    }

    /**
     * Creates a new ValidationException with a custom message and field errors.
     *
     * @param message     The custom error message
     * @param fieldErrors Map of field names to error messages
     */
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = fieldErrors;
    }

    /**
     * Creates a new ValidationException with a specific error code.
     *
     * @param errorCode The specific error code
     */
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Creates a new ValidationException with a specific error code and custom message.
     *
     * @param errorCode The specific error code
     * @param message   The custom error message
     */
    public ValidationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.fieldErrors = new HashMap<>();
    }

    /**
     * Creates a new ValidationException with a specific error code, custom message, and field errors.
     *
     * @param errorCode   The specific error code
     * @param message     The custom error message
     * @param fieldErrors Map of field names to error messages
     */
    public ValidationException(ErrorCode errorCode, String message, Map<String, String> fieldErrors) {
        super(errorCode, message);
        this.fieldErrors = fieldErrors;
    }

    /**
     * Gets the field errors.
     *
     * @return Map of field names to error messages
     */
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * Adds a field error.
     *
     * @param field   The field name
     * @param message The error message
     * @return This ValidationException instance for method chaining
     */
    public ValidationException addFieldError(String field, String message) {
        this.fieldErrors.put(field, message);
        return this;
    }
}
