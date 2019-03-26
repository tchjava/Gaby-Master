package com.gaby.util;

import java.util.Random;
/**
*@discrption:随机工具类
*@user:Gaby
*@createTime:2019-03-17 22:04
*/
public class RandomUtil {
    public static String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String BASE_EASY = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    public static String UPPER_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String INT_BASE = "0123456789";

    /**
     * 功能描述: <br>
     * 〈随机生成length位字符串〉
     *
     * @param length 长度
     * @return:java.lang.String
     * @Author:Gaby
     * @Date: 2018/5/11 11:55
     */
    public static String getRandomStr(String base, int length) {

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈随机生成length位字符串〉
     *
     * @param length 长度
     * @return:java.lang.String
     * @Author:Gaby
     * @Date: 2018/5/11 11:55
     */
    public static String getRandomStr(int length) {

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE.length());
            sb.append(BASE.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈随机生成length位整数〉
     *
     * @param length 长度
     * @return:java.lang.Integer
     * @Author:Gaby
     * @Date: 2018/5/11 11:55
     */
    public static String getRandomInt(int length) {

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(INT_BASE.length());
            sb.append(INT_BASE.charAt(number));
        }
        return sb.toString();
    }
}
