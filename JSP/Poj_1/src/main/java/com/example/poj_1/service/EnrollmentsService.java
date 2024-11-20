package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Enrollments;
import com.example.poj_1.mapper.EnrollmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentsService {
    @Autowired
    EnrollmentsMapper enrollmentsMapper;
    public void insertEnrollments(Enrollments enrollments){
        enrollmentsMapper.insert(enrollments);
    }

    public void updateEnrollments(Enrollments enrollments) {
        enrollmentsMapper.updateEnrollments(enrollments);
    }
    public void deleteEnrollments(String enrollment_id) {
        enrollmentsMapper.deleteEnrollments(enrollment_id);
    }
    public void batchDeleteEnrollments(List<String> enrollment_ids) {
        for (String enrollment_id : enrollment_ids) {
            enrollmentsMapper.deleteEnrollments(enrollment_id);  // 7  - 8
        }
    }
    public List<Enrollments> selectAll() {
        return enrollmentsMapper.selectAll();
    }
    public Enrollments selectById(String enrollment_id) {
        return enrollmentsMapper.selectById(enrollment_id);
    }
    public Page<Enrollments> selectByPage(Integer pageNum, Integer pageSize, String course_code, String course_name) {
        // 计算 skipNum，分页的偏移量
        Integer skipNum = (pageNum - 1) * pageSize;

        // 调用 Mapper 查询符合条件的课程列表
        List<Enrollments> enrollmentsList = enrollmentsMapper.selectByPage(skipNum, pageSize, course_code, course_name);

        // 调用 Mapper 查询符合条件的课程总数
        Integer total = enrollmentsMapper.selectCountByPage(course_code, course_name);

        // 返回分页结果
        Page<Enrollments> page = new Page<>();
        page.setToto(total);  // 设置总记录数
        page.setList(enrollmentsList);  // 设置当前页的数据
        return page;
    }

}
