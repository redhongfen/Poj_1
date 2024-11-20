package com.example.poj_1.controller;


import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Students;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.StudentsService;
import com.example.poj_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    StudentsService studentsService;

    @PostMapping("/add")
    public Result add(@RequestBody Students student) {
        try {
            studentsService.insertStudent(student);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("学生信息插入失败，可能已存在");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Students student) {
        studentsService.updateStudent(student);
        return Result.success();
    }

    @DeleteMapping("/delete/{student_id}")
    public Result delete(@PathVariable String student_id) {
        studentsService.deleteStudent(student_id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<String> studentIds) {
        studentsService.batchDeleteStudents(studentIds);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Students> studentList = studentsService.selectAll();
        return Result.success(studentList);
    }

    @GetMapping("/selectById/{student_id}")
    public Result selectById(@PathVariable String student_id) {
        Students student = studentsService.selectById(student_id);
        return Result.success(student);
    }

    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String major) {
        Page<Students> page = studentsService.selectByPage(pageNum, pageSize, name, major);
        return Result.success(page);
    }
}
