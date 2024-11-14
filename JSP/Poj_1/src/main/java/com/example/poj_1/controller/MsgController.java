package com.example.poj_1.controller;

import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Msg;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.MsgService;
import com.example.poj_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    MsgService msgService;

    @PostMapping("/add")
    public Result add(@RequestBody Msg msg){
        try {
            msgService.insertMsg(msg);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据错误");
            }else {
                return  Result.error("系统错误");
            }
        }
        return Result.success();
    }
    @PutMapping("/update")
    public Result update(@RequestBody Msg msg){
        msgService.updateMsg(msg);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        msgService.deleteMsg(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {  //  [7, 8]
        System.out.println("-----------------------------!!!!--------------");
        System.out.println("Received IDs: " + ids);  // 添加日志输出
        msgService.batchDeleteMsg(ids);
        return Result.success();
    }
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Msg> userList = msgService.selectAll();
        return Result.success(userList);
    }
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Msg msg = msgService.selectById(id);
        return Result.success(msg);
    }
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String user) {
        Page<Msg> page = msgService.selectByPage(pageNum, pageSize, user);
        return Result.success(page);
    }
}
