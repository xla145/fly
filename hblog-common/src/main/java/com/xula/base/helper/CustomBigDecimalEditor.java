package com.xula.base.helper;

import com.xula.base.utils.NumberUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * 类说明: BigDecimal custom property editor<br> 
 * 创建时间: 2008-2-26 下午03:15:03<br> 
 *  
 * @author Seraph<br> 
 * @email: seraph@gmail.com<br> 
 */  
public class CustomBigDecimalEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {  
        if (StringUtils.isEmpty(text)) {
            // Treat empty String as null value.  
            setValue(null);  
        } else {  
            setValue(NumberUtils.getBigDecimal(text));
        }  
    }  
}  