package com.gaby.mybatis.auto.stu.service.impl;

import com.gaby.mybatis.auto.stu.entity.Student;
import com.gaby.mybatis.auto.stu.mapper.StudentMapper;
import com.gaby.mybatis.auto.stu.service.StudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gaby
 * @since 2019-03-19
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
