package com.gaby.web.plugin.advice;

import com.alibaba.fastjson.JSONException;
import com.gaby.exception.BssException;
import com.gaby.model.BaseResponse;
import com.gaby.model.DefaultResponse;
import com.gaby.model.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
*@discrption:comment
*@user:Gaby
*@createTime:2019-03-17 17:36
*/
@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exception(Exception e){
        logger.error(e.getLocalizedMessage(),e);
        BssException bssException=null;
        if(e instanceof BssException){
            bssException=(BssException)e;
        }else{
            bssException=new BssException("系统异常,请与管理员联系");
        }
        return exceptionToResponse(bssException);
    }

    /**
     * json数据不规范
     * @param e
     * @return
     */
    @ExceptionHandler(value=JSONException.class)
    public BaseResponse jsonException(JSONException e){
        logger.error(e.getLocalizedMessage(),e);
        return exceptionToResponse(new BssException("参数为空，请通过requestBody传参"));
    }

    /**
     * 表单形式
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public BaseResponse httpMessageNotReadableException(HttpMessageNotReadableException e){
        logger.error(e.getLocalizedMessage(),e);
        return exceptionToResponse(new BssException("参数为空，请通过requestBody传参"));
    }


    /**
     * 异常转成BaseResponse
     * @param bssException
     * @return
     */
    private BaseResponse exceptionToResponse(BssException bssException) {
        BaseResponse baseResponse=new DefaultResponse();
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(bssException.getMessage());
        baseResponse.setResult(resultResponse);
        return baseResponse;
    }
}
