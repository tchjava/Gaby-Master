package com.gaby.service.stu;

import com.gaby.mybatis.auto.stu.entity.Student;
import com.gaby.mybatis.base.service.BaseService;
import com.gaby.stu.model.student.query.Response;

import java.util.Map;

public interface CustomStudentService extends BaseService<Student> {
    Response query(Map map);
}
