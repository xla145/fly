package com.xula.service.dict.api;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.dict.DictGroup;
import com.xula.entity.dict.DictItem;

import java.util.List;

/**
 * 字典服务
 */
public interface IDictOperService {


    /**
     * 添加字典组
     * @param dictGroup
     * @return
     */
    RecordBean<DictGroup> addDictGroup(DictGroup dictGroup);

    /**
     * 获取字典组
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    PagePojo<DictGroup> getDictGroupPage(Conditions conn, int pageNo, int pageSize);



    /**
     * 获取字典组
     * @param code
     * @return
     */
    DictGroup getDictGroup(String code);


    /**
     * 验证code是否存在
     * @param code
     * @param id
     * @return
     */
    RecordBean<String> checkGroupCode(String code, Integer id);

    /**
     * 修改字典组
     * @param dictGroup
     * @return
     */
    RecordBean<DictGroup> updateDictGroup(DictGroup dictGroup);

    /**
     * 添加字典组
     * @param dictItem
     * @return
     */
    RecordBean<DictItem> addDictItem(DictItem dictItem);

    /**
     * 修改字典组
     * @param dictItem
     * @return
     */
    RecordBean<DictItem> updateDictItem(DictItem dictItem);


    /**
     * 分页获取字典项
     * @param code
     * @param pageNo
     * @param pageSize
     * @return
     */
    PagePojo<DictItem> getDictItemPage(String code, int pageNo, int pageSize);

    /**
     * 获取字典项
     * @param code
     * @return
     */
    List<DictItem> getDictItemListByCode(String code);

    /**
     * 获取字典项
     * @param id
     * @return
     */
    DictItem getDictItem(Integer id);


    /**
     * 获取字典项
     * @param id
     * @return
     */
    RecordBean<String> delDictItem(Integer id);

}
