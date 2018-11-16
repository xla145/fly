package com.xula.service.dict.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;

import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.dict.DictGroup;
import com.xula.entity.dict.DictItem;
import com.xula.service.dict.api.IDictOperService;
import com.xula.service.dict.cache.DictCache;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 字典服务
 * @author xla
 */
@Service("IDictOperService")
public class DictOperServiceImpl implements IDictOperService {

    /**
     * 添加字典组
     * @param dictGroup
     * @return
     */
    @Override
    public RecordBean<DictGroup> addDictGroup(DictGroup dictGroup) {
        dictGroup.setCreateTime(new Date());
        dictGroup.setUpdateTime(new Date());
        int result = BaseDao.dao.insert(dictGroup);
        if (result != 1) {
            return RecordBean.error("添加失败！");
        }
        return RecordBean.success("SUCCESS",dictGroup);
    }

    /**
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<DictGroup> getDictGroupPage(Conditions conn, int pageNo, int pageSize) {
//        Conditions conn = new Conditions("belong", SqlExpr.EQUAL,belong);
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(DictGroup.class,conn,sort,pageNo,pageSize);
    }

    @Override
    public DictGroup getDictGroup(String code) {
        Conditions conn = new Conditions("code", SqlExpr.EQUAL,code);
        return BaseDao.dao.queryForEntity(DictGroup.class,conn);
    }

    @Override
    public RecordBean<String> checkGroupCode(String code, Integer id) {
        DictGroup dictGroup = getDictGroup(code);
        if (dictGroup == null || id.equals(dictGroup.getId())) {
            return RecordBean.success("success!");
        }
        return RecordBean.error("code码重复");
    }

    /**
     * 修改字典组
     * @param dictGroup
     * @return
     */
    @Override
    public RecordBean<DictGroup> updateDictGroup(DictGroup dictGroup) {
        dictGroup.setUpdateTime(new Date());
        int result = BaseDao.dao.update(dictGroup);
        if (result != 1) {
            return RecordBean.error("修改失败！");
        }
        return RecordBean.success("success",dictGroup);
    }

    /**
     * 添加字典项
     * @param dictItem
     * @return
     */
    @Override
    public RecordBean<DictItem> addDictItem(DictItem dictItem) {
        dictItem.setCreateTime(new Date());
        dictItem.setUpdateTime(new Date());
        int result = BaseDao.dao.insert(dictItem);
        if (result != 1) {
            return RecordBean.error("添加失败！");
        }
        return RecordBean.success("success",dictItem);
    }

    /**
     * 修改字典项
     * @param dictItem
     * @return
     */
    @Override
    public RecordBean<DictItem> updateDictItem(DictItem dictItem) {
        dictItem.setUpdateTime(new Date());
        int result = BaseDao.dao.update(dictItem);
        if (result != 1) {
            return RecordBean.error("修改失败！");
        }
        DictCache.getCache().invalidate(dictItem.getGroupCode(),dictItem.getName());
        return RecordBean.success("success",dictItem);
    }

    /**
     *
     * @param code
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<DictItem> getDictItemPage(String code, int pageNo, int pageSize) {
        Conditions conn = new Conditions("group_code", SqlExpr.EQUAL,code);
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(DictItem.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取字典项 列表
     * @param code 字典组code
     * @return
     */
    @Override
    public List<DictItem> getDictItemListByCode(String code) {
        Conditions conn = new Conditions("group_code", SqlExpr.EQUAL,code);
        return BaseDao.dao.queryForListEntity(DictItem.class,conn);
    }

    /**
     * 获取字典项
     * @param id
     * @return
     */
    @Override
    public DictItem getDictItem(Integer id) {
        return BaseDao.dao.queryForEntity(DictItem.class,id);
    }

    /**
     * 删除字典项
     * @param id
     * @return
     */
    @Override
    public RecordBean<String> delDictItem(Integer id) {
       StringBuffer sql = new StringBuffer();
       sql.append("DELETE FROM dict_item WHERE id = ?");
       int result =  BaseDao.dao.update(sql.toString(),id);
       if (result == 1) {
           return RecordBean.success("删除成功！");
       }
       return RecordBean.error("删除失败！");
    }


}
