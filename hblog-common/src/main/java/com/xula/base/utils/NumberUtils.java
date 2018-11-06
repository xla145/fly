package com.xula.base.utils;

import java.math.BigDecimal;

/**
 * 类说明: Number handle utils<br>
 * 创建时间: 2007-10-4 下午05:08:48<br>
 *
 * @author Seraph<br>
 * @email: seraph@gmail.com<br>
 */
public class NumberUtils {

    public static BigDecimal getBigDecimal(String s) {
        return new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}  