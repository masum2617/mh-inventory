package com.mh.inventory.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents error information for API error responses.
 * This class provides details about an error that occurred during an API request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error information")
public class ErrorInfo {

    @Schema(description = "Application-specific error code", example = "RESOURCE_NOT_FOUND")
    private String code;

    @Schema(description = "Detailed error information")
    private ErrorDetail[] details;

    /**
     * Creates an ErrorInfo instance with a single error detail.
     *
     * @param code    The error code
     * @param field   The field that caused the error (optional)
     * @param message The error message
     * @return A new ErrorInfo instance
     */
    public static ErrorInfo of(String code, String field, String message) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(code);
        errorInfo.setDetails(new ErrorDetail[]{
                ErrorDetail.of(field, message)
        });
        return errorInfo;
    }

    /**
     * Creates an ErrorInfo instance with a single error detail without a field.
     *
     * @param code    The error code
     * @param message The error message
     * @return A new ErrorInfo instance
     */
    public static ErrorInfo of(String code, String message) {
        return of(code, null, message);
    }
}
