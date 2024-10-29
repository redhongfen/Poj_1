package com.example.poj_1.mapper;

import com.example.poj_1.eneity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (username, password, name, phone, email, address, avatar) " +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insert(User user);

    @Update("update `user` set username = #{username}, password = #{password}, name= #{name}, phone = #{phone}, " +
            "email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from `user` order by id desc")
    List<User> selectAll();
    @Select("select * from `user` where id = #{id} order by id desc")
    User selectById(Integer id);
    @Select("select * from `user` where username = #{username} and name = #{name} order by id desc")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);
    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    List<User> selectByMo(String username, String name);
    @Select({
            "<script>",
            "SELECT * FROM `user`",
            "WHERE 1=1",  // 添加一个常量条件，确保 SQL 语法始终有效",
            "<if test='username != null and username != \"\"'>",
            "AND username LIKE CONCAT('%', #{username}, '%')",
            "</if>",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "ORDER BY id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize,
                            @Param("username") String username, @Param("name") String name);
    @Select({
            "<script>",
            "SELECT COUNT(id) FROM `user` WHERE 1=1",  // 确保 SQL 语法有效",
            "<if test='username != null and username != \"\"'>",
            "AND username LIKE CONCAT('%', #{username}, '%')",
            "</if>",
            "<if test='name != null and name != \"\"'>",
            "AND name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username = #{username} order by id desc")
    User selectByUsername(String username);
}
