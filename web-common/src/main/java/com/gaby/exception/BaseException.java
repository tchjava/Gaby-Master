package com.gaby.exception;
/**
* @Description:    自定义的异常父类
* @Author:         wengzhongjie
* @CreateDate:     2019-03-14 14:05
*/
public class BaseException extends RuntimeException{

  public BaseException() {
  }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
    super(message);
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
