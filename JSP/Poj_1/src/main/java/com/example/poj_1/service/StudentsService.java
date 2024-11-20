package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Students;
import com.example.poj_1.mapper.StudentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsService {

    @Autowired
    private StudentsMapper studentsMapper;

    public void insertStudent(Students student) {
        studentsMapper.insert(student);
    }

    public void updateStudent(Students student) {
        studentsMapper.updateStudent(student);
    }

    public void deleteStudent(String student_id) {
        studentsMapper.deleteStudent(student_id);
    }

    public void batchDeleteStudents(List<String> studentIds) {
        for (String student_id : studentIds) {
            studentsMapper.deleteStudent(student_id);
        }
    }

    public List<Students> selectAll() {
        return studentsMapper.selectAll();
    }

    public Students selectById(String student_id) {
        return studentsMapper.selectById(student_id);
    }

    public Page<Students> selectByPage(Integer pageNum, Integer pageSize, String name, String major) {
        Integer skipNum = (pageNum - 1) * pageSize;

        Page<Students> page = new Page<>();
        List<Students> studentsList = studentsMapper.selectByPage(skipNum, pageSize, name, major);
        Integer total = studentsMapper.selectCountByPage(name, major);

        page.setToto(total);
        page.setList(studentsList);
        return page;
    }
}
