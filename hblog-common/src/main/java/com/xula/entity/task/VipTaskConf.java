package com.xula.entity.task;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

/**
 * vip task
 *
 * @author xla
 */
@Data
public class VipTaskConf extends BasePojo {

	@Temporary
	private static final long serialVersionUID = 1L;
	
	private String param;
	/**
	 * VIP升级任务名
	 */
	private String name;
	private String clazzName;
}
