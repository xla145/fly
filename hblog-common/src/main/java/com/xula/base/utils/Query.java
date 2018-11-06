/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xula.base.utils;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xla
 * 数据转化
 */

public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) {

        this.putAll(params);
        //分页参数
        this.page = Integer.parseInt(params.get("pageNo").toString());
        this.limit = Integer.parseInt(params.get("pageSize").toString());
        this.put("offset", (page - 1) * limit);
        this.put("pageNo", page);
        this.put("pageSize", limit);

//        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
//        String sidx = params.get("field").toString();
//        String order = params.get("sort").toString();
//        this.put("field", SQLFilter.sqlInject(sidx));
//        this.put("sort", SQLFilter.sqlInject(order));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
