package com.example.poj_1.service;

import com.example.poj_1.common.Page;
import com.example.poj_1.eneity.User;
import com.example.poj_1.eneity.Users;
import com.example.poj_1.exception.ServiceException;
import com.example.poj_1.mapper.UserMapper;
import com.example.poj_1.mapper.UsersMapper;
import com.example.poj_1.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UsersService {
    @Autowired
    UsersMapper usersMapper;
    public void insertUsers(Users users){
        usersMapper.insert(users);
    }

    public void updateUsers(Users users) {
        usersMapper.updateUsers(users);
    }
    public void deleteUsers(Integer id) {
        usersMapper.deleteUsers(id);
    }
    public void batchDeleteUsers(List<Integer> ids) {
        for (Integer id : ids) {
            usersMapper.deleteUsers(id);  // 7  - 8
        }
    }
    public List<Users> selectAll() {
        return usersMapper.selectAll();
    }
    public Users selectById(Integer id) {
        return usersMapper.selectById(id);
    }
    public Page<Users> selectByPage(Integer pageNum, Integer pageSize, String role, String name) {
        Integer skipNum = (pageNum - 1) * pageSize;  // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<Users> page = new Page<>();
        List<Users> usersList = usersMapper.selectByPage(skipNum, pageSize, role, name);
        Integer total = usersMapper.selectCountByPage(role, name);
        page.setToto(total);
        page.setList(usersList);
        return page;
    }

    public Users login(Users users) {
        Users upusers=usersMapper.selectByUsername(users.getUsername());
        if(upusers==null){
            throw new ServiceException("用户名或密码错误");
        }
        if(!Objects.equals(users.getPassword(), upusers.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        String token = TokenUtils.createToken(upusers.getUser_id().toString(), upusers.getPassword());
        upusers.setToken(token);
        return upusers;
    }

}
