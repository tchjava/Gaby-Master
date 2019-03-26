package com.gaby.annotation;

import java.lang.annotation.*;
/**
*@discrption:字段校验器(是否必须)
*@user:Gaby
*@createTime:2019-03-17 21:57
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Field {
    String comment() default "";
    boolean nullable() default true;
}
