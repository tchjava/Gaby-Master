package com.gaby.util;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
*@discrption:ObjectCopyUtil
*@user:Gaby
*@createTime:2019-03-17 22:41
*/
public class ObjectCopyUtil {
    public static final Logger log = LoggerFactory.getLogger(ObjectCopyUtil.class);

    /**
     * 根据指定类型创建实例
     * 同时将源对象的属性复制到新创建的实例
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createCopyObject(Object source, Class<T> clazz) {
        try {
            if (source != null) {
                T target = clazz.newInstance();
                BeanUtils.copyProperties(source, target);
                return target;
            }
        } catch (IllegalAccessException e) {
            log.error("createCopyObject error", e);
        } catch (InstantiationException e) {
            log.error("createCopyObject error", e);
        }
        return null;
    }
}
