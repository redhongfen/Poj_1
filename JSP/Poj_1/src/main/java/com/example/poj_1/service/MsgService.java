package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.Msg;
import com.example.poj_1.eneity.User;
import com.example.poj_1.mapper.MsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgService {
    @Autowired
    MsgMapper msgMapper;

    public void insertMsg(Msg msg){
        msgMapper.insert(msg);
    }
    public void updateMsg(Msg msg) {
        msgMapper.updateMsg(msg);
    }
    public void deleteMsg(Integer id) {
        msgMapper.deleteMsg(id);
    }
    public void batchDeleteMsg(List<Integer> ids) {
        for (Integer id : ids) {
            msgMapper.deleteMsg(id);  // 7  - 8
        }
    }
    public List<Msg> selectAll() {
        return msgMapper.selectAll();
    }
    public Msg selectById(Integer id) {
        return msgMapper.selectById(id);
    }
    public Page<Msg> selectByPage(Integer pageNum, Integer pageSize, String user) {
        Integer skipNum = (pageNum - 1) * pageSize;  // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<Msg> page = new Page<>();
        List<Msg> userList = msgMapper.selectByPage(skipNum, pageSize, user);
        Integer total = msgMapper.selectCountByPage(user);
        page.setToto(total);
        page.setList(userList);
        return page;
    }
}
