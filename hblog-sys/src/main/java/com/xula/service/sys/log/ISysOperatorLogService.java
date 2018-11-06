package com.xula.service.sys.log;


import com.xula.entity.SysOperatorLog;

/**
 * 日志操作
 * @author xla
 */
public interface ISysOperatorLogService {

    /**
     * 添加系统日志信息
     *
     * @param sysOperatorLog
     */
    void addOperatorLog(SysOperatorLog sysOperatorLog);

}
