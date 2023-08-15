package com.example.service.impl;

import com.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;
    @Test
    void testInsert(){
        String account="momo";
        String password="123456";
        String newPassword="123456";
        long result = userService.userRegister(account, password, newPassword);
        Assertions.assertEquals(-1,result);
    }
}