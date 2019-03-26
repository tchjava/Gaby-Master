package com.gaby.model;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
/**
*@discrption:返回对象的父类
*@user:Gaby
*@createTime:2019-03-17 21:56
*/
public class BaseResponse implements Serializable {

  /**
   * 状态对象
   */
  private ResultResponse result;
  /**
   * 数据
   */
  private JSONObject extend;

  public ResultResponse getResult() {
    return result;
  }

  public void setResult(ResultResponse result) {
    this.result = result;
  }

  public JSONObject getExtend() {
    return extend;
  }

  public void setExtend(JSONObject extend) {
    this.extend = extend;
  }
}
