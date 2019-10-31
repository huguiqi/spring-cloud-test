package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sam on 2018/4/30.
 */
@RestController
@RefreshScope
@Order(value = 100)
public class Hello2Controller {


    @Value("${server.port}")
    String port;

    @Value("${turn.switch}")
    private String turnSwitch;

    @RequestMapping("/hello")
    public String home(@RequestParam String name) {
        return
                "hello "+name+",i am from port:" +port + ",appName :"+ turnSwitch;
    }

}
