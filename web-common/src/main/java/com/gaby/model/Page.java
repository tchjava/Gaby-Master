package com.gaby.model;

import com.gaby.annotation.Field;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
*@discrption:分页对象
*@user:Gaby
*@createTime:2019-03-17 21:56
*/
@Data
public class Page <T extends BaseResponse> implements Serializable {
    @Field(comment = "当前页数")
    private Integer pageNum=1;
    @Field(comment = "每页记录数")
    private Integer pageSize=20;
    @Field(comment = "需要排序的字段")
    private String sortField;
    @Field(comment = "升降序,默认升序, 0升序 ,1降序")
    private int sort;
    @Field(comment = "总数")
    private int total;
    @Field(comment ="数据")
    private List<T> beanList;
}
