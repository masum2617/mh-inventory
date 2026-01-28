package com.mh.inventory.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a detailed error for API error responses.
 * This class provides specific information about an individual error.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Detailed error information")
public class ErrorDetail {

    @Schema(description = "The field that caused the error (if applicable)", example = "email")
    private String field;

    @Schema(description = "The error message", example = "Email format is invalid")
    private String message;

    /**
     * Creates an ErrorDetail instance.
     *
     * @param field   The field that caused the error (optional)
     * @param message The error message
     * @return A new ErrorDetail instance
     */
    public static ErrorDetail of(String field, String message) {
        return ErrorDetail.builder()
                .field(field)
                .message(message)
                .build();
    }

    /**
     * Creates an ErrorDetail instance without a field.
     *
     * @param message The error message
     * @return A new ErrorDetail instance
     */
    public static ErrorDetail of(String message) {
        return of(null, message);
    }
}
