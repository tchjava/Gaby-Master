package com.gaby.mybatis.auto.stu.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.gaby.mybatis.base.entity.IEntity;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaby
 * @since 2019-03-19
 */
public class Teacher implements Serializable, IEntity {

    @TableId(value = "id", type = IdType.INPUT)
    @TableField("t_id")
    private String tId;
    @TableField("t_name")
    private String tName;
    private Integer age;
    private String type;

    public String gettId() {
        return tId;
    }

    public Teacher settId(String tId) {
        if (tId != null)
            tId = tId.trim();
        this.tId = tId;
        return this;
    }

    public String gettName() {
        return tName;
    }

    public Teacher settName(String tName) {
        if (tName != null)
            tName = tName.trim();
        this.tName = tName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Teacher setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getType() {
        return type;
    }

    public Teacher setType(String type) {
        if (type != null)
            type = type.trim();
        this.type = type;
        return this;
    }

    /**
     * 表名
     */
    public static final String TABLE_NAME = "teacher";
    public static final String T_ID = "t_id";

    public static final String T_NAME = "t_name";

    public static final String AGE = "age";

    public static final String TYPE = "type";

    @Override
    public Teacher pkVal(Serializable val) {
        this.tId = String.valueOf(val.toString());
        return this;
    }
}

