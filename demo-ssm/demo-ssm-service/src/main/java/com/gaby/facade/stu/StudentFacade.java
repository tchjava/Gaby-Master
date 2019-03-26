package com.gaby.facade.stu;

import com.gaby.stu.model.student.query.Request;
import com.gaby.stu.model.student.query.Response;

public interface StudentFacade {
    Response query(Request request);
}
