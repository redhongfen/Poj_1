package com.example.poj_1.mapper;
import com.example.poj_1.eneity.Students;
import com.example.poj_1.eneity.Teachers;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface TeachersMapper {

    @Insert("INSERT INTO teachers (name, subject) VALUES (#{name}, #{subject})")
    void insertTeacher(Teachers teacher);

    @Update("UPDATE teachers SET name = #{name}, subject = #{subject} WHERE teacher_id = #{teacher_id}")
    void updateTeacher(Teachers teacher);

    @Delete("DELETE FROM teachers WHERE teacher_id = #{teacher_id}")
    void deleteTeacher(Integer teacher_id);

    @Select("SELECT * FROM teachers WHERE teacher_id = #{teacher_id}")
    Teachers selectTeacherById(Integer teacher_id);

    @Select("SELECT * FROM teachers")
    List<Teachers> selectAllTeachers();

    @Select({
            "<script>",
            "SELECT * FROM teachers",
            "WHERE 1=1",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "<if test='subject != null and subject != \"\"'>",
            "AND subject LIKE CONCAT('%', #{subject}, '%')",
            "</if>",
            "ORDER BY teacher_id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Teachers> selectByPage(@Param("skipNum") Integer skipNum,
                                @Param("pageSize") Integer pageSize,
                                @Param("name") String name,
                                @Param("subject") String subject);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM teachers",
            "WHERE 1=1",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "<if test='subject != null and subject != \"\"'>",
            "AND subject LIKE CONCAT('%', #{subject}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("name") String name, @Param("subject") String subject);
}
