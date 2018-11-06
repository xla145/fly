package com.xula.base.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xla
 */
public abstract class ModuleData {

    static Logger logger = LoggerFactory.getLogger(ModuleData.class);

    public Map<String, Object> root = new HashMap<String, Object>();

    public abstract Map<String, Object> getModelData(Map<String, Object> params) throws Exception;

}
