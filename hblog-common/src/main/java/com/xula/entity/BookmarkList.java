package com.xula.entity;


import com.xula.entity.imagetext.ImageTextItem;
import lombok.Data;

import java.util.List;


/**
 * 书签列表
 * @author xla
 */
@Data
public class BookmarkList {

    private Integer id;

    private String name;

    private Integer status;

    private String code;

    private List<ImageTextItem> imageTextItemList;

}
