package com.xula.service.imagetext.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.xula.base.utils.RecordBean;
import com.xula.entity.ImageTextItem;
import com.xula.service.imagetext.IImageTextItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 图文链接管理
 * @author xla
 */
@Service("IImageTextItemService")
public class IImageTextItemServiceImpl implements IImageTextItemService {

    public static Logger logger = LoggerFactory.getLogger(IImageTextItemServiceImpl.class);

    /**
     * 获取图文链接信息
     *
     * @param conn     conn
     * @param pageNo   查询页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public PagePojo<ImageTextItem> getImageTextItemPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ImageTextItem.class, conn, sort, pageNo, pageSize);
    }

    /**
     * 获取图文链接信息
     *
     * @param id 图文组编号
     * @return
     */
    @Override
    public ImageTextItem getImageTextItem(Integer id) {
        return BaseDao.dao.queryForEntity(ImageTextItem.class, new Conditions("id", SqlExpr.EQUAL, id));
    }

    /**
     * 添加图文链接信息
     *
     * @param imageTextItem 图文链接信息
     * @return
     */
    @Override
    public RecordBean<ImageTextItem> addImageTextItem(ImageTextItem imageTextItem) {
        logger.info("添加图文链接信息" + JSON.toJSON(imageTextItem));
        imageTextItem.setCreateTime(new Date());
        imageTextItem.setUpdateTime(new Date());
        try {
            int result = BaseDao.dao.insert(imageTextItem);
            if (result != 1) {
                return RecordBean.error("添加图文链接信息失败！");
            }
        } catch (Exception e) {
            logger.error("添加图文链接异常！" + e.getMessage());
            return RecordBean.error("添加图文链接信息异常！");
        }
        return RecordBean.success("", imageTextItem);
    }

    /**
     * 修改图文链接信息
     *
     * @param imageTextItem 图文链接信息
     * @return
     */
    @Override
    public RecordBean<ImageTextItem> updateImageTextItem(ImageTextItem imageTextItem) {
        logger.info("修改图文链接信息" + JSON.toJSON(imageTextItem));
        imageTextItem.setUpdateTime(new Date());
        try {
            int result = BaseDao.dao.update(imageTextItem);
            if (result != 1) {
                return RecordBean.error("修改图文链接信息失败！");
            }
        } catch (Exception e) {
            logger.error("修改图文链接异常！" + e.getMessage());
            return RecordBean.error("修改图文链接信息异常！");
        }
        return RecordBean.success("", imageTextItem);
    }

    /**
     * 删除图文链接信息
     *
     * @param id 图文链接编号
     * @return
     */
    @Override
    public RecordBean<String> delImageTextItem(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM image_text_item WHERE id = ?");
        try {
            int result = BaseDao.dao.update(sql.toString(), id);
            if (result != 1) {
                return RecordBean.error("删除图文链接信息失败！");
            }
        } catch (Exception e) {
            logger.error("删除图文链接信息异常！" + e.getMessage());
            return RecordBean.error("删除图文链接信息异常！");
        }
        return RecordBean.success("success！");

    }
}
