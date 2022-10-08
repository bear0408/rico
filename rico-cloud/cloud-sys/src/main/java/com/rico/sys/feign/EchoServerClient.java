package com.rico.sys.feign;

import com.rico.comm.constant.FeignConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = FeignConstant.DISCOVER_ECHO_SERVER)
public interface EchoServerClient {
    @GetMapping("/echo")
    String echo();

    @GetMapping("/page")
    Object selectPage();

    @GetMapping("/index/test/test")
    Object test();
}
