package com.yfancy.service.user.dao;

import com.yfancy.common.base.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    public User getUserById(int id);

    public List<User> getAllUser();

    public void insert(User user);
}
