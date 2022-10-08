package com.rico.sys.controller;

import com.tencent.cloud.polaris.PolarisDiscoveryProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class EchoController {

    private final PolarisDiscoveryProperties properties;

    EchoController(PolarisDiscoveryProperties properties) {
            this.properties = properties;
    }

    @GetMapping(value = "/echo")
    public String echo() {
        return "Hello PolarisMesh rico" ;
    }

       /* @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return "Hello PolarisMesh " + string + ", I'm " + properties.getService();
        }*/
    }