package com.rico.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DiscoverEchoServer2")
public interface EchoServerClient2 {


    @GetMapping("/index/test/test")
    Object test();
}
