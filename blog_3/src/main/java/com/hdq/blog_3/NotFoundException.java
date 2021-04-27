package com.hdq.blog_3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//@ResponseStatus(HttpStatus.NOT_FOUND)本类的状态是资源找不到，发生此类异常之后，springBoot会自动跳转到404页面
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
