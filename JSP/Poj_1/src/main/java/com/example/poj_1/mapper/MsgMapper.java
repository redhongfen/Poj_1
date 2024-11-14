package com.example.poj_1.mapper;


import com.example.poj_1.eneity.Msg;
import com.example.poj_1.eneity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MsgMapper {
    @Insert("insert into `msg` (msg, time, user) " +
            "values (#{msg}, #{time}, #{user})")
    void insert(Msg msg);
    @Update("update `msg` set msg = #{msg}, time = #{time}, user= #{user} " + "where id = #{id}")
    void updateMsg(Msg msg);
    @Delete("delete from `msg` where id = #{id}")
    void deleteMsg(Integer id);
    @Select("select * from `msg` order by id desc")
    List<Msg> selectAll();
    @Select("select * from `msg` where id = #{id} order by id desc")
    Msg selectById(Integer id);
    @Select("select * from `msg` where user = #{user} order by id desc")
    List<Msg> selectByMore(@Param("user") String user);
    @Select("select * from `msg` where user like concat('%', #{user}, '%') order by id desc")
    List<Msg> selectByMo(String user);
    @Select({
            "<script>",
            "SELECT * FROM `msg`",
            "WHERE 1=1",  // 添加一个常量条件，确保 SQL 语法始终有效",
            "<if test='user != null and user != \"\"'>",
            "AND user LIKE CONCAT('%', #{user}, '%')",
            "</if>",
            "ORDER BY id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Msg> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize,
                            @Param("user") String user);
    @Select({
            "<script>",
            "SELECT COUNT(id) FROM `msg` WHERE 1=1",  // 确保 SQL 语法有效",
            "<if test='user != null and user != \"\"'>",
            "AND user LIKE CONCAT('%', #{user}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("user") String user);

}
