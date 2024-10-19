package com.example.poj_1;

import com.example.poj_1.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @RequestMapping
    public Result testString(){
        return Result.success("test_1 ");
    }
}
