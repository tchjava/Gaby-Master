package com.gaby.web.plugin.advice;

import com.gaby.model.BaseResponse;
import com.gaby.model.DefaultResponse;
import com.gaby.model.ResultResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
/**
 *@discrption:统一返回处理增强
 *@user:Gaby
 *@createTime:2019-03-17 17:35
 */
@ControllerAdvice
public class MVCResponseAdvice implements ResponseBodyAdvice<BaseResponse> {

  @Override
  public boolean supports(MethodParameter methodParameter, Class aClass) {
    return true;
  }

  @Override
  public BaseResponse beforeBodyWrite(BaseResponse baseResponse, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
      ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
    if (baseResponse == null) {
      return new DefaultResponse();
    }
    if (baseResponse.getResult() == null) {
      baseResponse.setResult(ResultResponse.DEFAULT);
      return baseResponse;
    }
    return baseResponse;
  }
}
