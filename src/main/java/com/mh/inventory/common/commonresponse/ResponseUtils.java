package com.mh.inventory.common.commonresponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility class for creating standardized responses.
 * This class provides methods for creating success and error responses.
 */
public class ResponseUtils {

    /**
     * Create a success response with the given message and data.
     *
     * @param message The success message
     * @param data    The response data
     * @param <T>     The type of the response data
     * @return A StandardResponse with the success status and data
     */
    public static <T> Response<T> createSuccessResponse(String message, T data) {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.success(message));
        response.setData(data);
        return response;
    }

    /**
     * Create a success response with the given message and no data.
     *
     * @param message The success message
     * @return A StandardResponse with the success status and no data
     */
    public static Response<Void> createSuccessResponse(String message) {
        Response<Void> response = new Response<>();
        response.setStatus(ResponseStatus.success(message));
        return response;
    }

    /**
     * Create a failed response with the given message and data.
     *
     * @param message The failed message
     * @param data    The response data
     * @param <T>     The type of the response data
     * @return A StandardResponse with the failed status and data
     */
    public static <T> Response<T> createFailedResponse(String message, T data) {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.failed(message));
        response.setData(data);
        return response;
    }

    /**
     * Create a failed response with the given message and no data.
     *
     * @param message The failed message
     * @return A StandardResponse with the failed status and no data
     */
    public static Response<Void> createFailedResponse(String message) {
        Response<Void> response = new Response<>();
        response.setStatus(ResponseStatus.failed(message));
        return response;
    }

    /**
     * Create a ResponseEntity with a success response.
     *
     * @param message The success message
     * @param data    The response data
     * @param <T>     The type of the response data
     * @return A ResponseEntity with the success response
     */
    public static <T> ResponseEntity<Response<T>> success(String message, T data) {
        return ResponseEntity.ok(createSuccessResponse(message, data));
    }

    /**
     * Create a ResponseEntity with a success response and no data.
     *
     * @param message The success message
     * @return A ResponseEntity with the success response
     */
    public static ResponseEntity<Response<Void>> success(String message) {
        return ResponseEntity.ok(createSuccessResponse(message));
    }

    /**
     * Create a ResponseEntity with a success response.
     *
     * @param message     The success message
     * @param data        The response data
     * @param <T>         The type of the response data
     * @param headerName  The response header name
     * @param headerValue The response header value
     * @return A ResponseEntity with the success response
     */
    public static <T> ResponseEntity<Response<T>> success(String message, T data, String headerName, String headerValue) {
        return ResponseEntity
                .ok()
                .header(headerName, headerValue)
                .body(createSuccessResponse(message, data));
    }


    /**
     * Create a ResponseEntity with a success response.
     *
     * @param message     The success message
     * @param headerName  The response header name
     * @param headerValue The response header value
     * @return A ResponseEntity with the success response
     */
    public static ResponseEntity<Response<Void>> success(String message, String headerName, String headerValue) {
        return ResponseEntity
                .ok()
                .header(headerName, headerValue)
                .body(createSuccessResponse(message));
    }

    /**
     * Creates a success response with data and a custom HTTP status.
     *
     * @param data       The response data
     * @param message    The success message
     * @param httpStatus The HTTP status
     * @param <T>        The type of data
     * @return A ResponseEntity containing a StandardResponse
     */
    public static <T> ResponseEntity<Response<T>> success(String message, T data, HttpStatus httpStatus) {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.success(httpStatus.value(), message));
        response.setData(data);
        return ResponseEntity.status(httpStatus).body(response);
    }

    /**
     * Creates a success response with data and a custom HTTP status.
     *
     * @param message    The success message
     * @param httpStatus The HTTP status
     * @return A ResponseEntity containing a StandardResponse
     */
    public static ResponseEntity<Response<Void>> success(String message, HttpStatus httpStatus) {
        Response<Void> response = new Response<>();
        response.setStatus(ResponseStatus.success(httpStatus.value(), message));
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }


    /**
     * Create a ResponseEntity with a failed response.
     *
     * @param message The failed message
     * @param data    The response data
     * @param <T>     The type of the response data
     * @return A ResponseEntity with the failed response
     */
    public static <T> ResponseEntity<Response<T>> failed(String message, T data) {
        return ResponseEntity.ok(createFailedResponse(message, data));
    }

    /**
     * Create a ResponseEntity with a failed response and no data.
     *
     * @param message The failed message
     * @return A ResponseEntity with the failed response
     */
    public static ResponseEntity<Response<Void>> failed(String message) {
        return ResponseEntity.ok(createFailedResponse(message));
    }

    /**
     * Create a ResponseEntity with a failed response.
     *
     * @param message     The failed message
     * @param data        The response data
     * @param <T>         The type of the response data
     * @param headerName  The response header name
     * @param headerValue The response header value
     * @return A ResponseEntity with the failed response
     */
    public static <T> ResponseEntity<Response<T>> failed(String message, T data, String headerName, String headerValue) {
        return ResponseEntity
                .ok()
                .header(headerName, headerValue)
                .body(createFailedResponse(message, data));
    }


    /**
     * Create a ResponseEntity with a failed response.
     *
     * @param message     The failed message
     * @param headerName  The response header name
     * @param headerValue The response header value
     * @return A ResponseEntity with the failed response
     */
    public static ResponseEntity<Response<Void>> failed(String message, String headerName, String headerValue) {
        return ResponseEntity
                .ok()
                .header(headerName, headerValue)
                .body(createFailedResponse(message));
    }

    /**
     * Creates a failed response with data and a custom HTTP status.
     *
     * @param data       The response data
     * @param message    The failed message
     * @param httpStatus The HTTP status
     * @param <T>        The type of data
     * @return A ResponseEntity containing a StandardResponse
     */
    public static <T> ResponseEntity<Response<T>> failed(String message, T data, HttpStatus httpStatus) {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.failed(httpStatus.value(), message));
        response.setData(data);
        return ResponseEntity.status(httpStatus).body(response);
    }

    /**
     * Creates a failed response with data and a custom HTTP status.
     *
     * @param message    The failed message
     * @param httpStatus The HTTP status
     * @return A ResponseEntity containing a StandardResponse
     */
    public static ResponseEntity<Response<Void>> failed(String message, HttpStatus httpStatus) {
        Response<Void> response = new Response<>();
        response.setStatus(ResponseStatus.failed(httpStatus.value(), message));
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
