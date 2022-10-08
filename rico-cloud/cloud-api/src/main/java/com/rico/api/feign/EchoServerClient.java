package com.rico.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DiscoverEchoServer")
public interface EchoServerClient {
    @GetMapping("/echo")
    String echo();

    @GetMapping("/page")
    Object selectPage();

}
