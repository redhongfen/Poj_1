package com.example.poj_1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @RequestMapping
    public String testString(){
        return "test";
    }
}
