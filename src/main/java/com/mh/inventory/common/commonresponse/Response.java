package com.mh.inventory.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response wrapper for all API endpoints.
 * This class provides a consistent structure for all API responses.
 *
 * @param <T> The type of data being returned
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    @Schema(description = "Response status information")
    private ResponseStatus status;

    @Schema(description = "Response data payload")
    private T data;

    @Schema(description = "Pagination information, if applicable")
    private PaginationInfo pagination;

    @Schema(description = "Error information, if applicable")
    private ErrorInfo error;
}
