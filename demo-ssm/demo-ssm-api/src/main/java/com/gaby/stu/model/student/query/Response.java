package com.gaby.stu.model.student.query;

import com.gaby.annotation.Field;
import com.gaby.model.BaseResponse;
import lombok.Data;

@Data
public class Response extends BaseResponse {
    @Field(comment = "学生标识")
    private String id;
    @Field(comment = "姓名")
    private String name;
    @Field(comment = "性别")
    private String sex;
}
