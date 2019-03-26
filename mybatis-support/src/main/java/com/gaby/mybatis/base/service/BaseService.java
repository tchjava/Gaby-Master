package com.gaby.mybatis.base.service;

import com.baomidou.mybatisplus.service.IService;
/**
*@discrption:service层的父级接口
*@user:Gaby
*@createTime:2019-03-18 23:33
*/
public interface BaseService<T> extends IService<T> {
    boolean insertVal(T t);
}
