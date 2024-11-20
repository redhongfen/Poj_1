package com.example.poj_1.mapper;

import com.example.poj_1.eneity.Courses;
import com.example.poj_1.eneity.Msg;
import com.example.poj_1.eneity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoursesMapper {
    @Insert("insert into `courses` (course_code, course_name, credits,description,semester) " +
            "values (#{course_code}, #{course_name}, #{credits}, #{description}, #{semester})")
    void insert(Courses courses);
    @Update("UPDATE `courses` " +
            "SET course_name = #{course_name}, credits = #{credits}, description = #{description}, semester = #{semester} " +
            "WHERE course_code = #{course_code}")
    void updateCourses(Courses courses);

    @Delete("delete from `courses` where course_code = #{course_code}")
    void deleteCourses(String course_code);
    @Select("select * from `courses` order by course_code desc")
    List<Courses> selectAll();
    @Select("select * from `courses` where course_code = #{course_code} order by course_code desc")
    Courses selectById(String course_code);
    @Select("select * from `courses` where course_code = #{course_code} and course_name = #{course_name} order by course_code desc")
    List<Courses> selectByMore(@Param("course_code") String course_code, @Param("course_name") String course_name);
    @Select("select * from `courses` where course_code like concat('%', #{course_code}, '%') and course_name like concat('%', #{course_name}, '%') order by course_code desc")
    List<Courses> selectByMo(String course_code, String course_name);
    @Select({
            "<script>",
            "SELECT * FROM `courses`",
            "WHERE 1=1",
            "<if test='course_code != null'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "<if test='course_name != null'>",
            "AND course_name LIKE CONCAT('%', #{course_name}, '%')",
            "</if>",
            "ORDER BY course_code DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Courses> selectByPage(@Param("skipNum") Integer skipNum,
                               @Param("pageSize") Integer pageSize,
                               @Param("course_code") String course_code,
                               @Param("course_name") String course_name);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM `courses`",
            "WHERE 1=1",  // 确保 SQL 语法始终有效",
            "<if test='course_code != null'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "<if test='course_name != null'>",
            "AND course_name LIKE CONCAT('%', #{course_name}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("course_code") String course_code, @Param("course_name") String course_name);


    @Select("select * from `courses` where course_code = #{course_code} order by course_code desc")
    User selectByUsername(String course_code);

}
