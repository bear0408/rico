package com.rico.api.controller;

import com.rico.api.feign.EchoServerClient;
import com.rico.api.feign.EchoServerClient2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

   @Autowired
    private  EchoServerClient client;

    @Autowired
    private EchoServerClient2 client2;

    @GetMapping(value = "/echo")
    public String echo() {
      String ss=  client.echo();
        return client.echo();
    }

    @GetMapping(value = "/selectList")
    public Object selectPage(){
        return client.selectPage();
    }

    @GetMapping("/index/test/test")
    Object test(){
        return client2.test();
    }
}
