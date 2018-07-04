package com.supervisions.framework.aspectj;

import com.supervisions.framework.web.domain.Message;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(Exception.class)
    public Message handleException(Exception e) {
        return Message.error();
    }
}
