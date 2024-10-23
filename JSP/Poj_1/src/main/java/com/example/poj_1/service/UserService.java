package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.User;
import com.example.poj_1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public void insertUser(User user){
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }
    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteUser(id);  // 7  - 8
        }
    }
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum = (pageNum - 1) * pageSize;  // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<User> page = new Page<>();
        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
        Integer total = userMapper.selectCountByPage(username, name);
        page.setToto(total);
        page.setList(userList);
        return page;
    }
}
