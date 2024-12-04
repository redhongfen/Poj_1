package com.example.poj_1.controller;
import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Msg;
import com.example.poj_1.eneity.Teachers;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.MsgService;
import com.example.poj_1.service.TeachersService;
import com.example.poj_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin
public class TeachersController {

    @Autowired
    TeachersService teachersService;

    @PostMapping("/add")
    public Result addTeacher(@RequestBody Teachers teacher) {
        try {
            teachersService.insertTeacher(teacher);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to add teacher: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result updateTeacher(@RequestBody Teachers teacher) {
        try {
            teachersService.updateTeacher(teacher);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to update teacher: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{teacher_id}")
    public Result deleteTeacher(@PathVariable Integer teacher_id) {
        try {
            teachersService.deleteTeacher(teacher_id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("Failed to delete teacher: " + e.getMessage());
        }
    }

    @GetMapping("/getById/{teacher_id}")
    public Result getTeacherById(@PathVariable Integer teacher_id) {
        Teachers teacher = teachersService.selectTeacherById(teacher_id);
        if (teacher != null) {
            return Result.success(teacher);
        } else {
            return Result.error("Teacher not found");
        }
    }

    @GetMapping("/getAll")
    public Result getAllTeachers() {
        List<Teachers> teachersList = teachersService.selectAllTeachers();
        return Result.success(teachersList);
    }

    @GetMapping("/selectByPage")
    public Result getTeachersByPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String subject) {
        Page<Teachers> page = teachersService.selectByPage(pageNum, pageSize, name, subject);
        return Result.success(page);
    }
}
