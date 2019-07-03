package com.xula.service.imagetext;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.ImageTextItem;


/**
 * Created by Administrator on 2017/9/19/019.
 */
public interface IImageTextItemService {
    /**
     * 分页查询 图文链接
     *
     * @param conn     conn
     * @param pageNo   查询页码
     * @param pageSize 每页数量
     * @return
     */
    public PagePojo<ImageTextItem> getImageTextItemPage(Conditions conn, int pageNo, int pageSize);


    /**
     * 获取图文链接
     *
     * @param id 图文组编号
     * @return coupon
     */
    public ImageTextItem getImageTextItem(Integer id);

    /**
     * 添加图文链接信息
     *
     * @param imageTextItem 图文链接信息
     * @return
     */
    public RecordBean<ImageTextItem> addImageTextItem(ImageTextItem imageTextItem);


    /**
     * 修改图文链接信息
     *
     * @param imageTextItem 图文链接信息
     * @return
     */
    public RecordBean<ImageTextItem> updateImageTextItem(ImageTextItem imageTextItem);


    /**
     * 删除图文链接信息
     *
     * @param id 图文链接编号
     * @return
     */
    public RecordBean<String> delImageTextItem(Integer id);

}
