package com.Nhom19.shopQuanAo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    SQL_EXCEPTION(9990,"Dữ liệu không hợp lệ",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1010,"Bạn Không có quyền truy cập",HttpStatus.FORBIDDEN),
    PRODUCT_TYPE_NOT_FOUND(6002,"Không tìm thấy loại sản phẩm",HttpStatus.NOT_FOUND),
    PRODUCT_TYPE_EXISTED(6001,"Loại sản phẩm đã tồn tại",HttpStatus.CONFLICT),
    PRODUCT_SIZE_NOT_FOUND(6002,"Không tìm thấy size sản phẩm",HttpStatus.NOT_FOUND),
    PRODUCT_SIZE_EXISTED(6001,"Size đã tồn tại",HttpStatus.CONFLICT),
    PRODUCT_COLOR_EXISTED(5002,"Màu đã tồn tại",HttpStatus.CONFLICT),
    PRODUCT_COLOR_NOT_FOUND(5001,"Không tìm thấy màu sản phẩm",HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_NOT_FOUND(4001,"Không tìm thấy phương thức thanh toán",HttpStatus.NOT_FOUND),
    INVALID_FILE(1007, "File không hợp lệ", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(2007,"Không tìm thấy đơn hàng",HttpStatus.NOT_FOUND),
    COMMENT_ALREADY_EXISTS(2006,"Đơn hàng đã được đánh giá",HttpStatus.CONFLICT),
    ORDER_NOT_COMPLETED(2005,"Đơn hàng chưa được giao",HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(2002,"Không tìm thấy sản phẩm",HttpStatus.NOT_FOUND),
    PRODUCT_NOT_EXISTED(2001, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_NOT_EXISTED(2002, "Sản phẩm biến thể không tồn tại", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_EXISTED(2003, "Sản phẩm không tồn tại trong giỏ hàng", HttpStatus.NOT_FOUND),
    FORBIDDEN_CART_ACCESS(2004, "Bạn không có quyền thao tác giỏ hàng này", HttpStatus.FORBIDDEN),
    ADDRESS_NOT_EXISTED(1010, "Không tìm thấy địa chỉ", HttpStatus.NOT_FOUND),
    USER_ID_NOT_EXISTED(1004, "Không tìm thấy người dùng với mã đã cung cấp", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1001, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Không thể xác thực tài khoản đăng nhập", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1003, "Tên đăng nhập không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_CONFIRM_NOT_MATCH(1008, "Mật khẩu xác nhận không trùng khớp", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.CONFLICT),
    INVALID_REQUEST(1007, "Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST),
    CART_NOT_EXISTED(1009, "Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(9999, "Đã xảy ra lỗi không xác định trong hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
