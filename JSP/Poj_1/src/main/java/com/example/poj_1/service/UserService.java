package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.User;
import com.example.poj_1.exception.ServiceException;
import com.example.poj_1.mapper.UserMapper;
import com.example.poj_1.utils.TokenUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public User login(User user) {
        User upuser=userMapper.selectByUsername(user.getUsername());
        if(upuser==null){
            throw new ServiceException("用户名或密码错误");
        }
        if(!Objects.equals(user.getPassword(), upuser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        String token = TokenUtils.createToken(upuser.getId().toString(), upuser.getPassword());
        upuser.setToken(token);
        return upuser;
    }

    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

    public void resetPassword(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new ServiceException("验证错误");
        }
        dbUser.setPassword("123");   // 重置密码
        userMapper.updateUser(dbUser);
    }
}
