package com.xula.service.imagetext;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.imagetext.ImageTextGroup;


/**
 * Created by Administrator on 2017/9/19/019.
 *
 * @author 图文组
 */
public interface IImageTextGroupService {
    /**
     * 分页查询 图文组
     *
     * @param conn     conn
     * @param pageNo   查询页码
     * @param pageSize 每页数量
     * @return
     */
    public PagePojo<ImageTextGroup> getImageTextGroupPage(Conditions conn, int pageNo, int pageSize);


    /**
     * 获取图文组信息
     *
     * @param id 图文组编号
     * @return coupon
     */
    public ImageTextGroup getImageTextGroup(Integer id);

    /**
     * 添加图文组信息
     *
     * @param imageTextGroup 图文组信息
     * @return
     */
    public RecordBean<ImageTextGroup> addImageTextGroup(ImageTextGroup imageTextGroup);

    /**
     * 启用或停用 图文组
     *
     * @param id
     * @param status
     * @return
     */
    public RecordBean<String> startOrStopGroup(Integer id, Integer status);

    /**
     * 修改图文组信息
     *
     * @param imageTextGroup 图文组信息
     * @return
     */
    public RecordBean<ImageTextGroup> updateImageTextGroup(ImageTextGroup imageTextGroup);

    /**
     * 验证code是否重复
     *
     * @param code
     * @return
     */
    public RecordBean<String> checkCode(String code, Integer groupId);

}
