package top.zhoulis.system.controller.router;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.controller.BaseController;
import top.zhoulis.common.utils.AddressUtil;
import top.zhoulis.common.utils.HttpContextUtil;
import top.zhoulis.common.utils.IpUtil;
import top.zhoulis.common.utils.R;
import top.zhoulis.system.entity.SysLoginLog;
import top.zhoulis.system.service.LoginLogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "LoginController", tags = {"登录接口"})
public class LoginController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @PostMapping("/login")
    public R login(@RequestParam(value = "username",required = false) String username,
                   @RequestParam(value = "password",required = false) String password){
        Subject subject = getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            //记录登录日志
            SysLoginLog log = new SysLoginLog();
            //获取http请求
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            String ip = IpUtil.getIpAddr(request);
            log.setIp(ip);
            log.setUsername(super.getCurrentUser().getUsername());
            log.setLocation(AddressUtil.getAddress(ip));
            log.setCreateTime(new Date());
            String header = request.getHeader(CommonConstant.USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            log.setDevice(browser.getName() + "--" + operatingSystem.getName());
            loginLogService.saveLog(log);
            Map<String, Object> map = new HashMap<>();
            map.put("token", subject.getSession().getId());
            map.put("user", this.getCurrentUser());
            return new R<>(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(e);
        }
    }

    /**
     * 注销接口
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public R logout() {
        Subject subject = getSubject();
        subject.logout();
        return new R();
    }
}
