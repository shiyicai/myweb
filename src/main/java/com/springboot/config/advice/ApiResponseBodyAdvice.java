package com.springboot.config.advice;


import com.springboot.response.ApiResponseBody;
import com.springboot.response.BaseResponseBody;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //exclude string class
        return !returnType.getMethod().getReturnType().isAssignableFrom(String.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if(BaseResponseBody.class.isAssignableFrom(body.getClass())){
            return body;
        }
        ApiResponseBody result = new ApiResponseBody("1","success");
        result.setBody(body);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ApiResponseBody handleException(HttpServletRequest request, Exception e){
        ApiResponseBody result = new ApiResponseBody("0","failure");
        result.setBody(e.getMessage());
        return result;
    }


}
