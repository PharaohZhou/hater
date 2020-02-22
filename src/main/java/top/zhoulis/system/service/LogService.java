package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysLog;

public interface LogService extends IService<SysLog> {
    IPage<SysLog> list(SysLog sysLog, QueryPage queryPage);

    @Async
    void saveLog(ProceedingJoinPoint proceedingJoinPoint, SysLog log) throws JsonProcessingException;

    void delete(Long id);
}
