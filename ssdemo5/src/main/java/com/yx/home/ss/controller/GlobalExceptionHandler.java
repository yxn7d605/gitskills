package com.yx.home.ss.controller;

import com.yx.home.ss.common.ResponseMode;
import com.yx.home.ss.common.ResponseWrapper;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseMode exceptionHandler(Exception e) {
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            StringBuilder errorMsg = new StringBuilder();
            int lastIdx = bindingResult.getFieldErrors().size() - 1;
            for (int i = 0; i < lastIdx; i++) {
                errorMsg.append(bindingResult.getFieldErrors().get(i).getDefaultMessage()).append(", ");
            }
            errorMsg.append(bindingResult.getFieldErrors().get(lastIdx).getDefaultMessage());

            return ResponseMode.fail(ResponseWrapper.INPUT_PARAM_ERROR.getCode(), errorMsg.toString());
        }

        return ResponseMode.fail(ResponseWrapper.FAILURE);
    }
}
