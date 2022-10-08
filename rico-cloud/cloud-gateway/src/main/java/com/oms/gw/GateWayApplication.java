package com.oms.gw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author rico
 * @date 2022.8.16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
    
    private static final Logger log = LoggerFactory.getLogger(GateWayApplication.class);
    public static void main(String[] args) {

        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(GateWayApplication.class, args);
        log.info("+++++++++++++GateWayApplication网关启动成功！++++++++++");
    }


}
