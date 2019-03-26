package com.gaby.model;
/**
* @Description:    默认的返回对象
* @Author:         wengzhongjie
* @CreateDate:     2019-03-14 13:27
*/
public class DefaultResponse extends BaseResponse{
  public final static DefaultResponse DEFAULT_RESPONSE=new DefaultResponse(){
    {
      setResult(new ResultResponse());
    }
  };

}
