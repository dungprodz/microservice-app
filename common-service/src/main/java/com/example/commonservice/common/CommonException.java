package com.example.commonservice.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException  extends RuntimeException{
    private final String code;
    private final String message;
    private final HttpStatus status;

    public CommonException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }

    // 400 - Bad Request
    public static CommonException badRequest(String message) {
        return new CommonException("ERR_400", message, HttpStatus.BAD_REQUEST);
    }

    // 401 - Unauthorized
    public static CommonException unauthorized(String message) {
        return new CommonException("ERR_401", message, HttpStatus.UNAUTHORIZED);
    }

    // 403 - Forbidden
    public static CommonException forbidden(String message) {
        return new CommonException("ERR_403", message, HttpStatus.FORBIDDEN);
    }

    // 404 - Not Found
    public static CommonException notFound(String message) {
        return new CommonException("ERR_404", message, HttpStatus.NOT_FOUND);
    }

    // 409 - Conflict
    public static CommonException conflict(String message) {
        return new CommonException("ERR_409", message, HttpStatus.CONFLICT);
    }

    // 422 - Unprocessable Entity
    public static CommonException unprocessableEntity(String message) {
        return new CommonException("ERR_422", message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // 500 - Internal Server Error
    public static CommonException internalServerError(String message) {
        return new CommonException("ERR_500", message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
