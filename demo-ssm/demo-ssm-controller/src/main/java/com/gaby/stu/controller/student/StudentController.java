package com.gaby.stu.controller.student;

import com.gaby.facade.stu.StudentFacade;
import com.gaby.model.BaseResponse;
import com.gaby.stu.model.student.query.Request;
import com.gaby.stu.model.student.query.Response;
import com.gaby.web.plugin.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    private StudentFacade studentFacade;

    @RequestMapping("hello")
    public BaseResponse hello() {
        return new BaseResponse();
    }


    @RequestMapping("query")
    public Response query(@RequestBody Request request){
        return studentFacade.query(request);
    }


}
