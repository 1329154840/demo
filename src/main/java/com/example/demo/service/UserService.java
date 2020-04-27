package com.example.demo.service;

import com.example.demo.enity.User;

import java.util.List;


public interface UserService {

    List<User> getAllUser();

    User getUserByLogin(Long uid,String passWord);
}
