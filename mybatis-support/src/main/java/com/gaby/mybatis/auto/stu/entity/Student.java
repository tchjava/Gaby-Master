package com.gaby.mybatis.auto.stu.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.gaby.mybatis.base.entity.IEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaby
 * @since 2019-03-19
 */
public class Student implements Serializable, IEntity {

    @TableId(value = "id", type = IdType.INPUT)
    @TableField("s_id")
    private String sId;
    @TableField("s_name")
    private String sName;
    @TableField("s_birth")
    private Date sBirth;
    @TableField("s_sex")
    private String sSex;

    public String getsId() {
        return sId;
    }

    public Student setsId(String sId) {
        if (sId != null)
            sId = sId.trim();
        this.sId = sId;
        return this;
    }

    public String getsName() {
        return sName;
    }

    public Student setsName(String sName) {
        if (sName != null)
            sName = sName.trim();
        this.sName = sName;
        return this;
    }

    public Date getsBirth() {
        return sBirth;
    }

    public Student setsBirth(Date sBirth) {
        this.sBirth = sBirth;
        return this;
    }

    public String getsSex() {
        return sSex;
    }

    public Student setsSex(String sSex) {
        if (sSex != null)
            sSex = sSex.trim();
        this.sSex = sSex;
        return this;
    }

    /**
     * 表名
     */
    public static final String TABLE_NAME = "student";
    public static final String S_ID = "s_id";

    public static final String S_NAME = "s_name";

    public static final String S_BIRTH = "s_birth";

    public static final String S_SEX = "s_sex";

    @Override
    public Student pkVal(Serializable val) {
        this.sId = String.valueOf(val.toString());
        return this;
    }
}

