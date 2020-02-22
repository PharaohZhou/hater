package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysLog;

public interface LogService extends IService<SysLog> {
    IPage<SysLog> list(SysLog sysLog, QueryPage queryPage);

    void saveLog(ProceedingJoinPoint proceedingJoinPoint, SysLog log);

    void delete(Long id);
}
