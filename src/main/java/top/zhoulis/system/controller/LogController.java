package top.zhoulis.system.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysLog;
import top.zhoulis.system.service.LogService;

@RestController
@RequestMapping("/api/log")
@Api(value = "LogController", tags = {"日志管理接口"})
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public R findByPage(SysLog log, QueryPage queryPage) {
        return new R<>(super.getData(logService.list(log, queryPage)));
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("log:delete")
    public R delete(@PathVariable Long id) {
        try {
            logService.delete(id);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }}
