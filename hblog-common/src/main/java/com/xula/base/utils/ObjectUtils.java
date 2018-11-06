package com.xula.base.utils;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lonecloud on 17/3/12.
 * 用于对Object进行解析并且转换成Map键值对的形式
 *
 * @author xla
 * @version 1.0
 */
public class ObjectUtils {

    private static final String JAVAP = "java.";
    private static final String JAVADATESTR = "java.util.Date";

    private static Map<String, Object> map = new HashMap<>();
    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        initDeclaredFields(clazz,obj);
        return map;
    }


    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;

    }

    /**
     * 获取字段列表
     * @param clazz
     */
    private static void initDeclaredFields(Class<?> clazz,Object object) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            String fileName = field.getName();
            map.put(fileName, field.get(object));
        }
        initDeclaredFields(clazz.getSuperclass(),object);
    }


    /**
     * 利用递归调用将Object中的值全部进行获取
     *
     * @param timeFormatStr 格式化时间字符串默认<strong>2017-03-10 10:21</strong>
     * @param obj           对象
     * @param excludeFields 排除的属性
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> objectToMapString(String timeFormatStr, Object obj, String... excludeFields) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();

        if (excludeFields.length != 0) {
            List<String> list = Arrays.asList(excludeFields);
            objectTransfer(timeFormatStr, obj, map, list);
        } else {
            objectTransfer(timeFormatStr, obj, map, null);
        }
        return map;
    }


    /**
     * 递归调用函数
     *
     * @param obj           对象
     * @param map           map
     * @param excludeFields 对应参数
     * @return
     * @throws IllegalAccessException
     */
    private static Map<String, String> objectTransfer(String timeFormatStr, Object obj, Map<String, String> map, List<String> excludeFields) throws IllegalAccessException {
        boolean isExclude = false;
        //默认字符串
        String formatStr = "YYYY-MM-dd HH:mm:ss";
        //设置格式化字符串
        if (timeFormatStr != null && !timeFormatStr.isEmpty()) {
            formatStr = timeFormatStr;
        }
        if (excludeFields != null) {
            isExclude = true;
        }
        Class<?> clazz = obj.getClass();
        //获取值
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = clazz.getSimpleName() + "." + field.getName();
            //判断是不是需要跳过某个属性
            if (isExclude && excludeFields.contains(fieldName)) {
                continue;
            }
            //设置属性可以被访问
            field.setAccessible(true);
            Object value = field.get(obj);
            Class<?> valueClass = value.getClass();
            if (valueClass.isPrimitive()) {
                map.put(fieldName, value.toString());
            //判断是不是基本类型
            } else if (valueClass.getName().contains(JAVAP)) {
                if (valueClass.getName().equals(JAVADATESTR)) {
                    //格式化Date类型
                    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
                    Date date = (Date) value;
                    String dataStr = sdf.format(date);
                    map.put(fieldName, dataStr);
                } else {
                    map.put(fieldName, value.toString());
                }
            } else {
                objectTransfer(timeFormatStr, value, map, excludeFields);
            }
        }
        return map;
    }
}