package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Goods;
import com.example.poj_1.eneity.User;
import com.example.poj_1.exception.ServiceException;
import com.example.poj_1.mapper.GoodsMapper;
import com.example.poj_1.mapper.UserMapper;
import com.example.poj_1.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    public void insertGoods(Goods goods){
        goodsMapper.insert(goods);
    }

    public void updateGoods(Goods goods) {
        goodsMapper.updateGoods(goods);
    }
    public void deleteGoods(Integer id) {
        goodsMapper.deleteGoods(id);
    }
    public void batchDeleteGoods(List<Integer> ids) {
        for (Integer id : ids) {
            goodsMapper.deleteGoods(id);  // 7  - 8
        }
    }
    public List<Goods> selectAll() {
        return goodsMapper.selectAll();
    }
    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }
    public Page<Goods> selectByPage(Integer pageNum, Integer pageSize, String goodsName) {
        Integer skipNum = (pageNum - 1) * pageSize;  // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<Goods> page = new Page<>();
        List<Goods> goodsList = goodsMapper.selectByPage(skipNum, pageSize, goodsName);
        Integer total = goodsMapper.selectCountByPage(goodsName);
        page.setToto(total);
        page.setList(goodsList);
        return page;
    }

}
