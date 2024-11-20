package com.example.poj_1.mapper;

import com.example.poj_1.eneity.Students;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentsMapper {

    @Insert("INSERT INTO students (student_id, name, major, grade) " +
            "VALUES (#{student_id}, #{name}, #{major}, #{grade})")
    void insert(Students student);

    @Update("UPDATE students SET name = #{name}, major = #{major}, grade = #{grade} " +
            "WHERE student_id = #{student_id}")
    void updateStudent(Students student);

    @Delete("DELETE FROM students WHERE student_id = #{student_id}")
    void deleteStudent(String student_id);

    @Select("SELECT * FROM students ORDER BY student_id DESC")
    List<Students> selectAll();

    @Select("SELECT * FROM students WHERE student_id = #{student_id}")
    Students selectById(String student_id);

    @Select({
            "<script>",
            "SELECT * FROM students",
            "WHERE 1=1",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "<if test='major != null and major != \"\"'>",
            "AND major LIKE CONCAT('%', #{major}, '%')",
            "</if>",
            "ORDER BY student_id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Students> selectByPage(@Param("skipNum") Integer skipNum,
                                @Param("pageSize") Integer pageSize,
                                @Param("name") String name,
                                @Param("major") String major);

    @Select({
            "<script>",
            "SELECT COUNT(student_id) FROM students WHERE 1=1",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "<if test='major != null and major != \"\"'>",
            "AND major LIKE CONCAT('%', #{major}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("name") String name, @Param("major") String major);
}
