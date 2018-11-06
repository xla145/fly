package com.xula.service.imagetext.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.constant.ImageTextConstant;
import com.xula.base.utils.RecordBean;
import com.xula.entity.imagetext.ImageTextGroup;
import com.xula.service.imagetext.IImageTextGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 图文组信息
 *
 * @author xla
 */
@Service("IImageTextGroupService")
public class ImageTextGroupServiceImpl implements IImageTextGroupService {

    public static Logger logger = LoggerFactory.getLogger(ImageTextGroupServiceImpl.class);

    /**
     * 获取组信息
     *
     * @param conn     conn
     * @param pageNo   查询页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public PagePojo<ImageTextGroup> getImageTextGroupPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ImageTextGroup.class, conn, sort, pageNo, pageSize);
    }

    /**
     * @param id 图文组编号
     * @return
     */
    @Override
    public ImageTextGroup getImageTextGroup(Integer id) {
        return BaseDao.dao.queryForEntity(ImageTextGroup.class, new Conditions("id", SqlExpr.EQUAL, id));
    }

    /**
     * @param imageTextGroup 图文组信息
     * @return
     */
    @Override
    public RecordBean<ImageTextGroup> addImageTextGroup(ImageTextGroup imageTextGroup) {
        RecordBean recordBean = checkCode(imageTextGroup.getCode(), imageTextGroup.getId());
        if (!recordBean.isSuccessCode()) {
            return RecordBean.error(recordBean.getMsg());
        }
        imageTextGroup.setCreateTime(new Date());
        imageTextGroup.setUpdateTime(new Date());
        imageTextGroup.setStatus(ImageTextConstant.IMAGE_TEXT_GROUP_DISABLE_STATUS);
        try {
            int result = BaseDao.dao.insert(imageTextGroup);
            if (result != 1) {
                return RecordBean.error("添加图文组信息失败！");
            }
        } catch (Exception e) {
            logger.error("添加图文组信息异常" + e.getMessage());
            return RecordBean.error("添加图文组信息异常！");
        }
        return RecordBean.success("success", imageTextGroup);
    }

    /**
     * 停用或启用组信息
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public RecordBean<String> startOrStopGroup(Integer id, Integer status) {
        status = ImageTextConstant.IMAGE_TEXT_GROUP_DISABLE_STATUS.equals(status) ? ImageTextConstant.IMAGE_TEXT_GROUP_ENABLE_STATUS : ImageTextConstant.IMAGE_TEXT_GROUP_DISABLE_STATUS;
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE image_text_group SET status = ?,update_time = now() WHERE id = ?");
        try {
            int result = BaseDao.dao.update(sql.toString(), status, id);
            if (result != 1) {
                return RecordBean.error("停用或启用组信息失败!");
            }
        } catch (Exception e) {
            logger.error("停用或启用组信息异常!" + e.getMessage());
            return RecordBean.error("停用或启用组信息异常!");
        }
        return RecordBean.success("success");
    }

    @Override
    public RecordBean<ImageTextGroup> updateImageTextGroup(ImageTextGroup imageTextGroup) {
        RecordBean recordBean = checkCode(imageTextGroup.getCode(), imageTextGroup.getId());
        if (!recordBean.isSuccessCode()) {
            return RecordBean.error(recordBean.getMsg());
        }
        imageTextGroup.setUpdateTime(new Date());
        try {
            int result = BaseDao.dao.update(imageTextGroup);
            if (result != 1) {
                return RecordBean.error("修改图文组信息失败！");
            }
        } catch (Exception e) {
            logger.error("修改图文组信息异常" + e.getMessage());
            return RecordBean.error("修改图文组信息异常！");
        }
        return RecordBean.success("success", imageTextGroup);
    }

    /**
     * @param code
     * @return
     */
    @Override
    public RecordBean<String> checkCode(String code, Integer groupId) {
        ImageTextGroup textGroup = BaseDao.dao.queryForEntity(ImageTextGroup.class, new Conditions("code", SqlExpr.EQUAL, code));
        if (textGroup == null || groupId.equals(textGroup.getId())) {
            return RecordBean.success("success!");
        }
        return RecordBean.error("code码重复");
    }
}
