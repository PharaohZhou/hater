package top.zhoulis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.zhoulis.system.mapper")
public class HaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaterApplication.class, args);
    }

}
