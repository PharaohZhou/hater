package top.zhoulis.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhoulis.system.entity.SysUser;

public interface UserService extends IService<SysUser> {

    /**
     * 根据name查询用户
     * @param username
     * @return
     */
    SysUser findByName(String username);

    /**
     * 新增
     * @param sysUser
     */
    void add(SysUser sysUser);

    /**
     * 更新
     * @param sysUser
     */
    void update(SysUser sysUser);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);
}
