package com.gaby.util;

import java.util.ArrayList;
import java.util.List;
/**
*@discrption:对象转换器
*@user:Gaby
*@createTime:2019-03-17 22:41
*/
public class ModelDtoConvertUtil {
    /**
     * 将model转换成DTO
     *
     * @param model
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertModel2DTO(Object model, Class<T> clazz) {
        return ObjectCopyUtil.createCopyObject(model, clazz);
    }

    /**
     * 将DTO转换成Model
     *
     * @param model
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertDTO2Model(Object model, Class<T> clazz) {
        return ObjectCopyUtil.createCopyObject(model, clazz);
    }

    /**
     * 将Model列表转化成Dto列表
     *
     * @param modelList
     * @param clazz     DTO 的类型
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> List<T> convertModelList2DTOList(List<K> modelList, Class<T> clazz) {
        return convertList(modelList, clazz);
    }

    /**
     * 将DTO列表转化成Model列表
     *
     * @param dtoList
     * @param clazz   model的类型
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> List<T> convertDtoList2ModelList(List<K> dtoList, Class<T> clazz) {

        return convertList(dtoList, clazz);
    }

    private static <K, T> List<T> convertList(List<K> pramList, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        if (pramList != null && pramList.size() > 0) {
            for (K pram : pramList) {
                T model = convertModel2DTO(pram, clazz);
                if (model != null) {
                    list.add(model);
                }
            }
        }
        return list;
    }
}
