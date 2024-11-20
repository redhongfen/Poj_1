package com.example.poj_1.mapper;

import com.example.poj_1.eneity.Grades;
import com.example.poj_1.eneity.Msg;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GradesMapper {

    @Insert("INSERT INTO `grades` (course_code, score, student_id) " +
            "VALUES (#{course_code}, #{score}, #{student_id})")
    void insert(Grades grades);

    @Update("UPDATE `grades` SET course_code = #{course_code}, score = #{score}, student_id = #{student_id} " +
            "WHERE grade_id = #{grade_id}")
    void updateGrades(Grades grades);

    @Delete("DELETE FROM `grades` WHERE grade_id = #{id}")
    void deleteGrades(Integer id);

    @Delete({
            "<script>",
            "DELETE FROM `grades` WHERE grade_id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void batchDeleteGrades(@Param("ids") List<Integer> ids);

    @Select("select * from `grades` order by grade_id desc")
    List<Grades> selectAll();

    @Select("SELECT * FROM `grades` WHERE grade_id = #{id}")
    Grades selectById(Integer id);

    @Select({
            "<script>",
            "SELECT * FROM `grades`",
            "WHERE 1=1",
            "<if test='course_code != null and course_code != \"\"'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "<if test='student_id != null and student_id != \"\"'>",
            "AND student_id LIKE CONCAT('%', #{student_id}, '%')",
            "</if>",
            "ORDER BY grade_id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Grades> selectByPage(@Param("skipNum") Integer skipNum,
                              @Param("pageSize") Integer pageSize,
                              @Param("course_code") String course_code,
                              @Param("student_id") String student_id);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM `grades`",
            "WHERE 1=1",
            "<if test='course_code != null and course_code != \"\"'>",
            "AND course_code LIKE CONCAT('%', #{course_code}, '%')",
            "</if>",
            "<if test='student_id != null and student_id != \"\"'>",
            "AND student_id LIKE CONCAT('%', #{student_id}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("course_code") String course_code, @Param("student_id") String student_id);
}
