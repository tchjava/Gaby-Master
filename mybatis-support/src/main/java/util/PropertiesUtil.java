package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
*@discrption:属性工具类
*@user:Gaby
*@createTime:2019-02-15 0:28
*/
public class PropertiesUtil {
    private final static Properties properties=new Properties();
   static{
       InputStream is = PropertiesUtil.class.getResourceAsStream("/generator.properties");
       try {
           properties.load(is);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   public static String getValue(String key){
       return properties.getProperty(key);
   }
    /**
     * 表名数组
     */
   public static String[] getTables(String key){
       String tables=properties.getProperty(key);
       return tables.indexOf(",")!=-1?tables.split(","):new String[]{tables};
   }
    /**
     * 是否生成实体常量
     */
    public static boolean isEntityColumnConstant(){
       return Boolean.parseBoolean(properties.getProperty("entity.column.constant"));
    }
}
