package com.xula.base.utils;

import org.springframework.cache.interceptor.KeyGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;


/**
 * 自定义key生成
 * @author xla
 */
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuffer key = new StringBuffer();
        key.append(target.getClass().getSimpleName());
        key.append(".");
        key.append(method.getName());
        key.append("[");
        if (params != null) {
            ByteArrayOutputStream byt = null;
            ObjectOutputStream oos = null;
            try {
                byt = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(byt);
                for (Object obj : params) {
                    oos.writeObject(obj);
                    String str = byt.toString("UTF-8");
                    key.append("." + str);
                    oos.reset();
                    byt.reset();
                }
            } catch (Exception e) {
                return null;
            } finally {
                try {
                    oos.close();
                    byt.close();
                } catch (IOException e) {
                }
            }
        }
        key.append("]");
        //防止　key 太长
        return Md5Utils.md5(key.toString());
    }
}
