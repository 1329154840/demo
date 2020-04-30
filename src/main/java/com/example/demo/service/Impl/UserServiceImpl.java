package com.example.demo.service.Impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByLogin(Long uid, String passWord) {
        return userMapper.getUserByLogin(uid,passWord);
    }

    @Override
    public List<User> getAllUser() {
       return userMapper.findAll();
    }
}
