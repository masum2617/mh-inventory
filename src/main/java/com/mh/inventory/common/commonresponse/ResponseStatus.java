package com.mh.inventory.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Represents the status information of an API response.
 * This class provides information about the success or failure of an API request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response status information")
public class ResponseStatus {

    @Schema(description = "HTTP status code", example = "200")
    private int code;

    @Schema(description = "Indicates if the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Human-readable status message", example = "Operation successful")
    private String message;

    /**
     * Creates a success status with the default HTTP status code (200 OK).
     *
     * @param message The success message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus success(String message) {
        return ResponseStatus.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message(message)
                .build();
    }

    /**
     * Creates a success status with a custom HTTP status code.
     *
     * @param statusCode The HTTP status code
     * @param message    The success message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus success(int statusCode, String message) {
        return ResponseStatus.builder()
                .code(statusCode)
                .success(true)
                .message(message)
                .build();
    }

    /**
     * Creates a failed status with the default HTTP status code (200 OK).
     *
     * @param message The failed message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus failed(String message) {
        return ResponseStatus.builder()
                .code(HttpStatus.OK.value())
                .success(false)
                .message(message)
                .build();
    }

    /**
     * Creates a success status with a custom HTTP status code.
     *
     * @param statusCode The HTTP status code
     * @param message    The success message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus failed(int statusCode, String message) {
        return ResponseStatus.builder()
                .code(statusCode)
                .success(false)
                .message(message)
                .build();
    }

    /**
     * Creates an error status.
     *
     * @param statusCode The HTTP status code
     * @param message    The error message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus error(int statusCode, String message) {
        return ResponseStatus.builder()
                .code(statusCode)
                .success(false)
                .message(message)
                .build();
    }

    /**
     * Creates an error status from an HttpStatus enum.
     *
     * @param httpStatus The HTTP status
     * @param message    The error message
     * @return A new ResponseStatus instance
     */
    public static ResponseStatus error(HttpStatus httpStatus, String message) {
        return error(httpStatus.value(), message);
    }
}
