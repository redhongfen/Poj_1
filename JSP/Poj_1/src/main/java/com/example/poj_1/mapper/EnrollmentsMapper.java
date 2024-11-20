package com.example.poj_1.mapper;

import com.example.poj_1.eneity.Courses;
import com.example.poj_1.eneity.Enrollments;
import com.example.poj_1.eneity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface EnrollmentsMapper {
    @Insert("insert into `enrollments` (enrollment_id, student_id, course_code) " +
            "values (#{enrollment_id}, #{student_id}, #{course_code})")
    void insert(Enrollments enrollments);
    @Update("UPDATE `enrollments` " +
            "SET student_id = #{student_id}, course_code = #{course_code}" +
            "WHERE enrollment_id = #{enrollment_id}")
    void updateEnrollments(Enrollments enrollments);

    @Delete("delete from `enrollments` where enrollment_id = #{enrollment_id}")
    void deleteEnrollments(String enrollment_id);

    @Select("select * from `enrollments` order by enrollment_id desc")
    List<Enrollments> selectAll();

    @Select("select * from `enrollments` where enrollment_id = #{enrollment_id} order by enrollment_id desc")
    Enrollments selectById(String enrollment_id);
    @Select("select * from `courses` where course_code = #{course_code} and course_name = #{course_name} order by course_code desc")
    List<Enrollments> selectByMore(@Param("course_code") String course_code, @Param("course_name") String course_name);
    @Select("select * from `courses` where course_code like concat('%', #{course_code}, '%') and course_name like concat('%', #{course_name}, '%') order by course_code desc")
    List<Enrollments> selectByMo(String course_code, String course_name);
    @Select({
            "<script>",
            "SELECT * FROM `enrollments`",
            "WHERE 1=1",
            "<if test='student_id != null'>",
            "AND student_id LIKE CONCAT('%', #{student_id}, '%')",
            "</if>",
            "<if test='course_code != null'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "ORDER BY course_code DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Enrollments> selectByPage(@Param("skipNum") Integer skipNum,
                               @Param("pageSize") Integer pageSize,
                               @Param("student_id") String student_id,
                               @Param("course_code") String course_code);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM `enrollments`",
            "WHERE 1=1",  // 确保 SQL 语法始终有效",
            "<if test='student_id != null'>",
            "AND student_id LIKE CONCAT('%', #{student_id}, '%')",
            "</if>",
            "<if test='course_code != null'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("student_id") String student_id, @Param("course_code") String course_code);


    @Select("select * from `courses` where course_code = #{course_code} order by course_code desc")
    User selectByUsername(String course_code);
}
