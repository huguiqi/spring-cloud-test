package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sam on 2018/4/30.
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return
                restTemplate.getForObject("http://www.baidu.com", String.class);
    }


    @RequestMapping("/hi2")
    public String home2(@RequestParam String name) {
        return
                restTemplate.getForObject("http://appservice/hi?name="+name, String.class);
    }

}
