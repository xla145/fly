
package com.xula.entity.imagetext;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;

/**
 * 图文链信息
 * @author xla
 */
@Data
public class ImageTextItem extends BasePojo {

    @Temporary
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Integer groupId;
    private String groupCode;
    private String title;
    private String desc;
    private String imgUrl;
    private String url;
    private Integer weight;
    private Date createTime;
    private Date updateTime;
    private String remark;

}

