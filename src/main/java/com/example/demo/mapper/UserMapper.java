package com.example.demo.mapper;

import com.example.demo.enity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("Select uid,user_name,pass_word From user")
    List<User> findAll();

    @Select("Select uid,user_name,pass_word From user where uid = #{uid} and pass_word = #{passWord} ")
    User getUserByLogin(Long uid,String passWord);

}
