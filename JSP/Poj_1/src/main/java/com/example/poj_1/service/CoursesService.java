package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Courses;
import com.example.poj_1.eneity.User;
import com.example.poj_1.exception.ServiceException;
import com.example.poj_1.mapper.CoursesMapper;
import com.example.poj_1.mapper.UserMapper;
import com.example.poj_1.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CoursesService {
    @Autowired
    CoursesMapper coursesMapper;
    public void insertCourses(Courses courses){
        coursesMapper.insert(courses);
    }

    public void updateCourses(Courses courses) {
        coursesMapper.updateCourses(courses);
    }
    public void deleteCourses(String course_code) {
        coursesMapper.deleteCourses(course_code);
    }
    public void batchDeleteCourses(List<String> course_codes) {
        for (String course_code : course_codes) {
            coursesMapper.deleteCourses(course_code);  // 7  - 8
        }
    }
    public List<Courses> selectAll() {
        return coursesMapper.selectAll();
    }
    public Courses selectById(String course_code) {
        return coursesMapper.selectById(course_code);
    }
    public Page<Courses> selectByPage(Integer pageNum, Integer pageSize, String course_code, String course_name) {
        // 计算 skipNum，分页的偏移量
        Integer skipNum = (pageNum - 1) * pageSize;

        // 调用 Mapper 查询符合条件的课程列表
        List<Courses> coursesList = coursesMapper.selectByPage(skipNum, pageSize, course_code, course_name);

        // 调用 Mapper 查询符合条件的课程总数
        Integer total = coursesMapper.selectCountByPage(course_code, course_name);

        // 返回分页结果
        Page<Courses> page = new Page<>();
        page.setToto(total);  // 设置总记录数
        page.setList(coursesList);  // 设置当前页的数据
        return page;
    }

}
