package com.example.poj_1.controller;

import com.example.poj_1.common.Page;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Grades;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/grades")
public class GradesController {

    @Autowired
    private GradesService gradesService;

    @PostMapping("/add")
    public Result add(@RequestBody Grades grades) {
        try {
            gradesService.insertGrades(grades);
            return Result.success();
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("插入数据错误：重复的键值");
            } else {
                return Result.error("系统错误：" + e.getMessage());
            }
        }
    }

    @PutMapping("/update")
    public Result update(@RequestBody Grades grades) {
        gradesService.updateGrades(grades);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        gradesService.deleteGrades(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        gradesService.batchDeleteGrades(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Grades> gradesList = gradesService.selectAll();
        return Result.success(gradesList);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Grades grades = gradesService.selectById(id);
        return Result.success(grades);
    }

    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String course_code,
                               @RequestParam(required = false) String student_id) {
        Page<Grades> page = gradesService.selectByPage(pageNum, pageSize, course_code, student_id);
        return Result.success(page);
    }

}
