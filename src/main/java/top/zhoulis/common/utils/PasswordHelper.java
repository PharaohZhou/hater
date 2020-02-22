package top.zhoulis.common.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import top.zhoulis.system.entity.SysUser;

@Component
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Getter
    @Setter
    private String algorithName = "MD5";

    @Getter
    @Setter
    private int hashInterations = 2;

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    //加密算法
    public void encryptPassword(SysUser sysUser) {
        // 如果没有盐值就进行随机生成盐值，但是Shiro进行密码校验并不会再次生成盐值，因为是随机盐，
        // Shiro会根据数据库中储存的盐值以及你注入的加密方式进行校验，而不是使用这个工具类进行校验的。
        //对user对象设置盐：salt；这个盐值是randomNumberGenerator生成的随机数，所以盐值并不需要我们指定
//            sysUser.setSalt(randomNumberGenerator.nextBytes().toHex());

        //调用SimpleHash指定散列算法参数：1、算法名称；2、用户输入的密码；3、盐值（随机生成的）；4、迭代次数
        if (sysUser.getPassword() != null) {
            String newPassword = new SimpleHash(
                    algorithName,
                    sysUser.getPassword(),
                    ByteSource.Util.bytes(sysUser.getSalt()),
                    hashInterations
            ).toHex();
            sysUser.setPassword(newPassword);
        }
    }
}
