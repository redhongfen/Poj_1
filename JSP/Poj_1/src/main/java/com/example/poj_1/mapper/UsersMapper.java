package com.example.poj_1.mapper;


import com.example.poj_1.eneity.User;
import com.example.poj_1.eneity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    @Insert("insert into `users` (username, password, role, name) " +
            "values (#{username}, #{password}, #{role}, #{name})")
    void insert(Users users);

    @Update("update `users` set username = #{username}, password = #{password}, role= #{role}, name = #{name}" +
            "where user_id = #{user_id}")
    void updateUsers(Users users);

    @Delete("delete from `users` where user_id = #{user_id}")
    void deleteUsers(Integer user_id);

    @Select("select * from `users` order by user_id desc")
    List<Users> selectAll();
    @Select("select * from `users` where user_id = #{user_id} order by user_id desc")
    Users selectById(Integer id);
    @Select("select * from `users` where username = #{username} and name = #{name} order by id desc")
    List<Users> selectByMore(@Param("username") String username, @Param("name") String name);
    @Select("select * from `users` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    List<Users> selectByMo(String username, String name);
    @Select({
            "<script>",
            "SELECT * FROM `users`",
            "WHERE 1=1",  // 添加一个常量条件，确保 SQL 语法始终有效",
            "<if test='role != null and role != \"\"'>",
            "AND role LIKE CONCAT('%', #{role}, '%')",
            "</if>",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "ORDER BY user_id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Users> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize,
                            @Param("role") String role, @Param("name") String name);
    @Select({
            "<script>",
            "SELECT COUNT(user_id) FROM `users` WHERE 1=1",  // 确保 SQL 语法有效",
            "<if test='role != null and role != \"\"'>",
            "AND role LIKE CONCAT('%', #{role}, '%')",
            "</if>",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("role") String role, @Param("name") String name);

    @Select("select * from `users` where username = #{username} order by id desc")
    Users selectByUsername(String username);
}
