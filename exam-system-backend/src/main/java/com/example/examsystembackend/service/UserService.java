package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}
