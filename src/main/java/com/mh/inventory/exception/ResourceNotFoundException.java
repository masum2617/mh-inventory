package com.mh.inventory.exception;


import com.mh.inventory.enums.ErrorCode;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends ApplicationException {

    /**
     * Creates a new ResourceNotFoundException with the default error code.
     */
    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }

    /**
     * Creates a new ResourceNotFoundException with a custom message.
     *
     * @param message The custom error message
     */
    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    /**
     * Creates a new ResourceNotFoundException for a specific resource type and ID.
     *
     * @param resourceType The type of resource (e.g., "User", "Hospital")
     * @param resourceId   The ID of the resource
     */
    public ResourceNotFoundException(String resourceType, Object resourceId) {
        super(ErrorCode.RESOURCE_NOT_FOUND, resourceType + " with ID " + resourceId + " not found");
    }

    /**
     * Creates a new ResourceNotFoundException with a specific error code.
     *
     * @param errorCode The specific error code
     */
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Creates a new ResourceNotFoundException with a specific error code and custom message.
     *
     * @param errorCode The specific error code
     * @param message   The custom error message
     */
    public ResourceNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
