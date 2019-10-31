package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by sam on 2018/4/30.
 */
@RestController
@RefreshScope
public class HelloController {


    @Value("${server.port}")
    String port;

    @Value("${turn.switch}")
    private String turnSwitch;

    @RequestMapping("/hello")
    public String home(@RequestParam String name) {
        return
                "hello "+name+",i am from port:" +port + ",switch :"+ turnSwitch;
    }

}
