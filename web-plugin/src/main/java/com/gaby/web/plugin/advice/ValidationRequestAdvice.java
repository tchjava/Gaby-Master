package com.gaby.web.plugin.advice;

import com.gaby.exception.BssException;
import com.gaby.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
/**
*@discrption:请求参数的校验器
*@user:Gaby
*@createTime:2019-03-17 17:46
*/
@ControllerAdvice
public class ValidationRequestAdvice extends RequestBodyAdviceAdapter {
    private Logger logger = LoggerFactory.getLogger(ValidationRequestAdvice.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            if(body!=null){
                Field[] fields = body.getClass().getDeclaredFields();
                Field.setAccessible(fields,true);
                for(Field field:fields){
                    field.setAccessible(true);
                    if(field.isAnnotationPresent(com.gaby.annotation.Field.class)){
                        com.gaby.annotation.Field fieldAnnotation = field.getAnnotation(com.gaby.annotation.Field.class);
                        if(!fieldAnnotation.nullable()){
                            if(null==field.get(body)){
                                throw new BssException(fieldAnnotation.comment() + "不能为空");
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            throw new SystemException("系统异常:"+e.getMessage());
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
