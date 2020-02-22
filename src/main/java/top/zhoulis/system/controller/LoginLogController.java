package top.zhoulis.system.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhoulis.common.annotation.Log;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.QueryPage;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysLoginLog;
import top.zhoulis.system.service.LoginLogService;

@RestController
@RequestMapping("/api/loginlog")
@Api(value = "LoginLogController", tags = {"登录日志管理接口"})
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("/list")
    public R findByPage(SysLoginLog loginLog, QueryPage queryPage) {
        return new R<>(super.getData(loginLogService.list(loginLog, queryPage)));
    }

    @Log("删除登录日志")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        try {
            loginLogService.delete(id);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
