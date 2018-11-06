package com.xula.service.sys.log.impl;

import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.SysOperatorLog;
import com.xula.service.sys.log.ISysOperatorLogService;
import org.springframework.stereotype.Service;

/**
 * 日志操作实现
 * @author xla
 */
@Service("ISysOperatorLogService")
public class ISysOperatorLogServiceImpl implements ISysOperatorLogService {

    @Override
    public void addOperatorLog(SysOperatorLog sysOperatorLog) {
        BaseDao.dao.insert(sysOperatorLog);
    }
}
