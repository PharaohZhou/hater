package top.zhoulis.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.system.entity.SysLoginLog;

public interface LoginLogService extends IService<SysLoginLog> {

    void saveLog(SysLoginLog loginLog);

    IPage<SysLoginLog> list(SysLoginLog loginLog, QueryPage queryPage);

    void delete(Long id);

}
