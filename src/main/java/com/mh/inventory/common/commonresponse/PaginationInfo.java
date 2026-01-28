package com.mh.inventory.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * Represents pagination information for paginated API responses.
 * This class provides metadata about the pagination state of a response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Pagination information")
public class PaginationInfo {

    @Schema(description = "Current page number (0-based)", example = "0")
    private int page;

    @Schema(description = "Number of items per page", example = "10")
    private int size;

    @Schema(description = "Total number of elements across all pages", example = "100")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "10")
    private int totalPages;

    /**
     * Creates a PaginationInfo instance from a Spring Data Page object.
     *
     * @param page The Spring Data Page
     * @return A new PaginationInfo instance
     */
    public static PaginationInfo fromPage(Page<?> page) {
        return PaginationInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * Creates a PaginationInfo instance from individual pagination parameters.
     *
     * @param page          The current page number (0-based)
     * @param size          The page size
     * @param totalElements The total number of elements
     * @param totalPages    The total number of pages
     * @return A new PaginationInfo instance
     */
    public static PaginationInfo of(int page, int size, long totalElements, int totalPages) {
        return PaginationInfo.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
