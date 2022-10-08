package com.rico.sys.controller;

import com.rico.sys.feign.EchoServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchosController {

   @Autowired(required = true)
    private  EchoServerClient client;

/*    @Autowired
    private EchoServerClient2 client2;*/

    @GetMapping(value = "/echos")
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
        return client.test();
    }
}
