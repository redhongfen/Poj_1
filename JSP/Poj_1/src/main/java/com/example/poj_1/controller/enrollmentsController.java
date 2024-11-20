package com.example.poj_1.controller;

import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Enrollments;
import com.example.poj_1.service.EnrollmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/enrollments")
public class enrollmentsController {
    @Autowired
    EnrollmentsService enrollmentsService;

    @PostMapping("/add")
    public Result add(@RequestBody Enrollments enrollments){
        try {
            enrollmentsService.insertEnrollments(enrollments);
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
    public Result update(@RequestBody Enrollments enrollments){
        enrollmentsService.updateEnrollments(enrollments);
        return Result.success();
    }

    @DeleteMapping("/delete/{enrollment_id}")
    public Result delete(@PathVariable String enrollment_id) {
        enrollmentsService.deleteEnrollments(enrollment_id);
        return Result.success();
    }
    /**
     * 批量删除用户信息
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<String> ids) {  //  [7, 8]
        enrollmentsService.batchDeleteEnrollments(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Enrollments> enrollmentsList = enrollmentsService.selectAll();
        return Result.success(enrollmentsList);
    }
    /**
     * 根据ID查询用户信息
     */
    @GetMapping("/selectById/{enrollment_id}")
    public Result selectById(@PathVariable String enrollment_id) {
        Enrollments enrollments = enrollmentsService.selectById(enrollment_id);
        return Result.success(enrollments);
    }


    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String student_id,
                               @RequestParam String course_code) {
        Page<Enrollments> page = enrollmentsService.selectByPage(pageNum, pageSize, student_id, course_code);

        return Result.success(page);
    }

}
