package com.Nhom19.shopQuanAo.exception;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
    public class GlobalExceptionHandler {
//        @ExceptionHandler(value = Exception.class)
//        ResponseEntity<ApiResponse> handleAppException(Exception ex){
//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setCode(ErrorCode.UNAUTHORIZED.getCode());
//            apiResponse.setMessage(ErrorCode.UNAUTHORIZED.getMessage());
//            return ResponseEntity.badRequest().body(apiResponse);
//        }

        @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiResponse> handleAppException(AppException ex){

            ErrorCode errorCode = ex.getErrorCode();
            ApiResponse apiResponse = new ApiResponse();

            apiResponse.setMessage(errorCode.getMessage());
            apiResponse.setCode(errorCode.getCode());

            return ResponseEntity.badRequest().body(apiResponse);
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException ex){

            String enumkey = ex.getBindingResult().getFieldError().getDefaultMessage();
            ErrorCode errorCode = ErrorCode.valueOf(enumkey);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage(errorCode.getMessage());
            apiResponse.setCode(errorCode.getCode());
            return ResponseEntity.badRequest().body(apiResponse);
        }
        @ExceptionHandler(value = NoSuchElementException.class)
        ResponseEntity<ApiResponse> handleException(Exception ex){
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(ErrorCode.USER_ID_NOT_EXISTED.getCode());
            apiResponse.setMessage(ErrorCode.USER_ID_NOT_EXISTED.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

