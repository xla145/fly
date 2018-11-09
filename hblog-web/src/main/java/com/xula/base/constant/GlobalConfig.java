package com.xula.base.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 全局配置文件
 * 
 */
@Component
public class GlobalConfig {


	/** dev 模式	 暂时会打印出更多操作日志 */
	@Value("${is.dev}")
	public static boolean dev = false ;
	
	/** 等于false的时候， 会关闭数据缓存（主要测试用） */
	public static boolean openCache = true;

	
	/** 短信验证白名单 (测试用) */
	//public static List<String> whiteList;
}
