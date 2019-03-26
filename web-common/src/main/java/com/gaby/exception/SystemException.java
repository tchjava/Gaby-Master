package com.gaby.exception;
/**
* @Description:    系统异常类
* @Author:         wengzhongjie
* @CreateDate:     2019-03-14 14:05
*/
public class SystemException extends BaseException{

  public SystemException() {
  }

  public SystemException(String message) {
    super(message);
  }

  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }
}
