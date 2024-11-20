package com.example.poj_1.service;

import com.example.poj_1.common.Page;

import com.example.poj_1.eneity.Teachers;
import com.example.poj_1.mapper.TeachersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class TeachersService {

    @Autowired
    private TeachersMapper teachersMapper;

    public void insertTeacher(Teachers teacher) {
        teachersMapper.insertTeacher(teacher);
    }

    public void updateTeacher(Teachers teacher) {
        teachersMapper.updateTeacher(teacher);
    }

    public void deleteTeacher(Integer teacher_id) {
        teachersMapper.deleteTeacher(teacher_id);
    }

    public Teachers selectTeacherById(Integer teacher_id) {
        return teachersMapper.selectTeacherById(teacher_id);
    }

    public List<Teachers> selectAllTeachers() {
        return teachersMapper.selectAllTeachers();
    }

    public Page<Teachers> selectByPage(Integer pageNum, Integer pageSize, String name, String subject) {
        Integer skipNum = (pageNum - 1) * pageSize;

        Page<Teachers> page = new Page<>();
        List<Teachers> teachersList = teachersMapper.selectByPage(skipNum, pageSize, name, subject);
        Integer total = teachersMapper.selectCountByPage(name, subject);
        page.setToto(total);
        page.setList(teachersList);

        return page;
    }
}
