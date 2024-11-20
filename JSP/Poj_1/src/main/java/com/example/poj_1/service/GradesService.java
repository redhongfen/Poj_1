package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Grades;
import com.example.poj_1.mapper.GradesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradesService {

    @Autowired
    private GradesMapper gradesMapper;

    public void insertGrades(Grades grades) {
        gradesMapper.insert(grades);
    }

    public void updateGrades(Grades grades) {
        gradesMapper.updateGrades(grades);
    }

    public void deleteGrades(Integer id) {
        gradesMapper.deleteGrades(id);
    }

    public void batchDeleteGrades(List<Integer> ids) {
        gradesMapper.batchDeleteGrades(ids);
    }

    public List<Grades> selectAll() {
        return gradesMapper.selectAll();
    }

    public Grades selectById(Integer id) {
        return gradesMapper.selectById(id);
    }

    public Page<Grades> selectByPage(Integer pageNum, Integer pageSize, String course_code, String student_id) {
        Integer skipNum = (pageNum - 1) * pageSize;

        Page<Grades> page = new Page<>();
        List<Grades> gradesList = gradesMapper.selectByPage(skipNum, pageSize, course_code, student_id);
        Integer total = gradesMapper.selectCountByPage(course_code, student_id);

        page.setToto(total);
        page.setList(gradesList);

        return page;
    }
}

