package com.rico.sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.rico.sys.*","com.rico.comm.**","com.rico.api.**"})
public class SysApplication {

    private static final Logger log = LoggerFactory.getLogger(SysApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
        log.info("+++++++++++++SysApplication 系统权限服务动成功！++++++++++");
    }

}
