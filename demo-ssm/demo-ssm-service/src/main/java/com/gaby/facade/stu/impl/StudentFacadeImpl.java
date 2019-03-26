package com.gaby.facade.stu.impl;

import com.gaby.facade.stu.StudentFacade;
import com.gaby.service.stu.CustomStudentService;
import com.gaby.stu.model.student.query.Request;
import com.gaby.stu.model.student.query.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentFacadeImpl implements StudentFacade {

    @Autowired
    private CustomStudentService customStudentService;
    @Override
    public Response query(Request request) {
        Map map = new HashMap();
        map.put("item", request);
        return customStudentService.query(map);
    }
}
