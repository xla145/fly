package com.xula.entity.task;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * vip task
 *
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
