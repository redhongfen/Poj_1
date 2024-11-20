package com.example.poj_1.controller;

import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Courses;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.CoursesService;
import com.example.poj_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CoursesController {
    @Autowired
    CoursesService coursesService;

    @PostMapping("/add")
    public Result add(@RequestBody Courses courses){
        try {
            coursesService.insertCourses(courses);
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
    public Result update(@RequestBody Courses courses){
        coursesService.updateCourses(courses);
        return Result.success();
    }

    @DeleteMapping("/delete/{course_code}")
    public Result delete(@PathVariable String course_code) {
        coursesService.deleteCourses(course_code);
        return Result.success();
    }
    /**
     * 批量删除用户信息
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<String> ids) {  //  [7, 8]
        coursesService.batchDeleteCourses(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Courses> coursesList = coursesService.selectAll();
        return Result.success(coursesList);
    }
    /**
     * 根据ID查询用户信息
     */
    @GetMapping("/selectById/{course_code}")
    public Result selectById(@PathVariable String course_code) {
        Courses courses = coursesService.selectById(course_code);
        return Result.success(courses);
    }


    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String course_code,
                               @RequestParam String course_name) {
        Page<Courses> page = coursesService.selectByPage(pageNum, pageSize, course_code, course_name);

        return Result.success(page);
    }




}
