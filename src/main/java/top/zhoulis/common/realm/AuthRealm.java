package top.zhoulis.common.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.system.entity.SysUser;
import top.zhoulis.system.service.UserService;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份校验
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        SysUser sysUser = userService.findByName(username);
        if (sysUser == null) {
            throw new GlobalException(new UnknownAccountException().getMessage());
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser,
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
