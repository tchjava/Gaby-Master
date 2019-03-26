package com.gaby.service.stu.impl;

import com.gaby.mapper.dao.StudentDao;
import com.gaby.mybatis.auto.stu.entity.Student;
import com.gaby.mybatis.base.service.impl.BaseServiceImpl;
import com.gaby.service.stu.CustomStudentService;
import com.gaby.stu.model.student.query.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomStudentServiceImpl extends BaseServiceImpl<StudentDao, Student> implements CustomStudentService {


    @Override
    public Response query(Map map) {
        return this.baseMapper.query(map);
    }


}
