package com.xula.base.helper;

import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理controller 绑定参数 date转换问题
 * 
 * @author caibin
 *
 */
public class SpecialDateEditor extends PropertyEditorSupport {  
  
    @Override  
    public void setAsText(String dateStr) throws IllegalArgumentException {
    	Date date = null;
    	if(StringUtils.isNotBlank(dateStr)){
    		//依次尝试转换不同格式的日期数据
    		date = formatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), dateStr);
    		
    		if(date == null){
    			date = formatDate(new SimpleDateFormat("yyyy-MM-dd"), dateStr);
    		}
    		
    		if(date == null){
    			date = formatDate(new SimpleDateFormat("yyyy-MM"), dateStr);
    		}
    	}
        setValue(date);
    }
	
    
    /**
     * 转换日期格式数据
     * @param format
     * @param dateStr
     * @return
     */
	private Date formatDate(SimpleDateFormat format, String dateStr){
		try {
			return format.parse(dateStr);  
        } catch (ParseException e) {}
		return null;
	}
}  