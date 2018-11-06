package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.entity.extend.HotArticle;
import com.xula.entity.extend.HotReply;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
public class GetHotReplyNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        List<HotReply> list = new ArrayList<HotReply>();
        for (int i = 0; i < 5; i++) {
            HotReply hot = new HotReply();
            hot.setUrl("user/"+hot.getUid());
            hot.setAvatar("https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
            hot.setNickname("贤心");
            hot.setUid(10002);
            hot.setReplyNum(106);
            list.add(hot);
        }
        root.put("data", list);
        return root;
    }
}
