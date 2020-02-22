package top.zhoulis.common.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import top.zhoulis.system.entity.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 */
public class BaseController {
    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected SysUser getCurrentUser() {
        return (SysUser) getSubject().getPrincipal();
    }
    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }

    public Map<String, Object> getData(IPage<?> page) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }

    public Map<String, Object> getToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", getSession().getId());
        return map;
    }

}
