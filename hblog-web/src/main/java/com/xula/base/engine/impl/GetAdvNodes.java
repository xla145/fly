package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.entity.Adv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
public class GetAdvNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        List<Adv> list = new ArrayList<Adv>();
        for (int i = 0; i < 2; i++) {
            Adv adv = new Adv();
            adv.setUrl("http://layim.layui.com/?from=fly");
            adv.setName("LayIM 3.0 - layui 旗舰之作");
            adv.setTimeLimit("2017.09.25-2099.01.01");
            list.add(adv);
        }
        root.put("data", list);
        return root;
    }
}
