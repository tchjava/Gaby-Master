package com.gaby.model;

import java.io.Serializable;
/**
*@discrption:结果返回对象
*@user:Gaby
*@createTime:2019-03-17 21:56
*/
public class ResultResponse implements Serializable {
  public final static ResultResponse DEFAULT=new ResultResponse();
  /**
   * 是否成功
   */
  private boolean success=true;
  /**
   * 信息内容
   */
  private String message="请求成功";

  public ResultResponse(){}
  public ResultResponse(boolean success,String message){
    this.success=success;
    this.message=message;
  }


  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
