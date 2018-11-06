package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.entity.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
public class GetLinkNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

        Object type = params.get("type");

        if (type == null) {
            root.put("data", null);
            return root;
        }

        int t = Integer.parseInt(type.toString());

        List<Link> links = getLinkList(t);

        root.put("data", links);
        return root;
    }

    /**
     * 返回list
     * @param type
     * @return
     */
    private List<Link> getLinkList(Integer type) {
        List<Link> list = new ArrayList<Link>();

        if (type == 1) {
            for (int i = 0; i < 6; i++) {
                Link link = new Link();
                link.setUrl("http://localhost:8088/");
                link.setName("test");
                list.add(link);
            }
        }

        if (type == 2) {
            for (int i = 0; i < 6; i++) {
                Link link = new Link();
                link.setUrl("http://fly.layui.com/jie/5366/");
                link.setName("layui 常见问题的处理和实用干货集锦");
                list.add(link);
            }
        }
        return list;
    }
}
