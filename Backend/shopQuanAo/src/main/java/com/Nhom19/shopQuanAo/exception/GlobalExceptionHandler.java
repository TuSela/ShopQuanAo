package com.Nhom19.shopQuanAo.exception;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
@RestControllerAdvice
public class GlobalExceptionHandler {
    // AppException (chủ động throw)
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    // Lỗi validate (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        String enumKey = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ErrorCode errorCode;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (Exception e) {
            errorCode = ErrorCode.INVALID_REQUEST;
        }

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    // Optional.get() nhưng không có dữ liệu
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse> handleNoSuchElementException() {

        ErrorCode errorCode = ErrorCode.USER_ID_NOT_EXISTED;

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException() {
        ErrorCode errorCode = ErrorCode.INVALID_FILE;
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
//    // Lỗi không xác định (fallback)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleOtherException() {
//
//        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
//
//        ApiResponse response = new ApiResponse();
//        response.setCode(errorCode.getCode());
//        response.setMessage(errorCode.getMessage());
//
//        return ResponseEntity
//                .status(errorCode.getHttpStatus())
//                .body(response);
//    }
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode= ErrorCode.UNAUTHORIZED;
        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateKey(
            DataIntegrityViolationException ex) {
        ApiResponse response = new ApiResponse();
        String message = "Dữ liệu đã tồn tại (biến thể bị trùng)";
        response.setMessage(message);
        // Nếu muốn check đúng UNIQUE constraint
        if (ex.getCause() != null && ex.getCause().getMessage().contains("UQ_BienThe")) {
            message = "Sản phẩm biến thể đã tồn tại (màu + size)";
            response.setMessage(message);
        }
        response.setCode(9990);
        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
