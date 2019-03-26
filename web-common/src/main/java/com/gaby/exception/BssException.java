package com.gaby.exception;
/**
* @Description:    业务异常类
* @Author:         wengzhongjie
* @CreateDate:     2019-03-14 14:04
*/
public class BssException extends BaseException{

  public BssException() {
  }

    public BssException(Throwable cause) {
        super(cause);
    }

    public BssException(String message) {
    super(message);
  }

  public BssException(String message, Throwable cause) {
    super(message, cause);
  }
}
