package com.example.poj_1.mapper;

import com.example.poj_1.eneity.Goods;


import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GoodsMapper {
    @Select("select * from `goods` order by id desc")
    List<Goods> selectAll();

    @Insert("insert into `goods` (goodsName, price, avatar) " +
            "values (#{goodsName}, #{price}, #{avatar})")
    void insert(Goods goods);

    @Update("update `goods` set goodsName = #{goodsName}, price = #{price}, avatar = #{avatar} where id = #{id}")
    void updateGoods(Goods goods);


    @Delete("delete from `goods` where id = #{id}")
    void deleteGoods(Integer id);

    @Select("select * from `goods` where id = #{id} order by id desc")
    Goods selectById(Integer id);
    @Select("select * from `goods` where username = #{goodsName}  order by id desc")
    List<Goods> selectByMore(@Param("goodsName") String goodsName);
    @Select("select * from `goods` where username like concat('%', #{goodsName}, '%') order by id desc")
    List<Goods> selectByMo(String goodsName);


    @Select({
            "<script>",
            "SELECT * FROM goods",
            "WHERE 1=1",
            "<if test='goodsName != null'>",
            "AND goodsName LIKE CONCAT('%', #{goodsName}, '%')",
            "</if>",
            "ORDER BY id DESC",
            "LIMIT #{skipNum}, #{pageSize}",
            "</script>"
    })
    List<Goods> selectByPage(@Param("skipNum") Integer skipNum,
                             @Param("pageSize") Integer pageSize,
                             @Param("goodsName") String goodsName);
    @Select({
            "<script>",
            "SELECT COUNT(id) FROM `goods` WHERE 1=1",  // 确保 SQL 语法有效",
            "<if test='goodsName != null and goodsName != \"\"'>",
            "AND goodsName LIKE CONCAT('%', #{goodsName}, '%')",
            "</if>",
            "</script>"
    })
    int selectCountByPage(@Param("goodsName") String goodsName);

}
