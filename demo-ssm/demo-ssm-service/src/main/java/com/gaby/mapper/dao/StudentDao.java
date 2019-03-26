package com.gaby.mapper.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gaby.mybatis.auto.stu.entity.Student;
import com.gaby.stu.model.student.query.Response;

import java.util.Map;

public interface StudentDao extends BaseMapper<Student> {
    Response query(Map map);
}
