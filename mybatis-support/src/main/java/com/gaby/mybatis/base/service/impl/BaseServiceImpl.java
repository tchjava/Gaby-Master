package com.gaby.mybatis.base.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gaby.mybatis.base.entity.IEntity;
import com.gaby.mybatis.base.mapper.SqlDao;
import com.gaby.mybatis.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
/**
*@discrption:service的顶层实现
*@user:Gaby
*@createTime:2019-03-18 23:32
*/
public class BaseServiceImpl<M extends BaseMapper<T>,T extends IEntity> extends ServiceImpl<M,T> implements BaseService<T> {
    @Autowired(required = false)
    private SqlDao sqlDao;
    @Override
    public boolean insertVal(T t) {
        int result =this.baseMapper.insert(t);
        t.pkVal(sqlDao.selectLastId());
        return retBool(result);
    }
}
