package com.xula.base.engine;

import com.jfinal.kit.SyncWriteMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * 建造操作类实例工厂
 *
 * @author xla
 */
public class ClazzFactory {

    static Logger logger = LoggerFactory.getLogger(ClazzFactory.class);

    static Map<String, ModuleData> instanceMap = new SyncWriteMap<>();

    public static ModuleData getOperator(String clazz)  {
        if (StringUtils.isBlank(clazz)) {
            logger.error("class无效, class:" + clazz);
            return null;
        }
        try {
            ModuleData clz = instanceMap.get(clazz);
            if (clz == null) {
                ModuleData c =  (ModuleData) Class.forName(clazz).newInstance();
                instanceMap.put(clazz, c);
                return c;
            }
            return clz;
        } catch (Exception e) {
            logger.error("反射失败，reason：{}",e.getMessage());
        }
        return null;
    }
}
