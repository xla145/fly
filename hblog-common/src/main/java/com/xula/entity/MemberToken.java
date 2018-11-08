package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;

/**
 * token
 * @author xla
 */
@Data
public class MemberToken extends BasePojo {
	
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer uid;
	private String token;                           
	private Date loginTime;                    
	private Date tenancyTerm;                     
	private Integer status;  
	private String loginIp;                        
	private String loginAddress;                    
	private String loginWay;                    
	private String remark;
	
}
