package com.example.service;
import java.util.Date;

import com.example.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    void testAdd(){
        User user=new User();
        user.setUsername("test");
        user.setUserAccount("1234");
        user.setAvatarUrl("2345");
        user.setGender(0);
        user.setUserPassword("123");
        user.setEmail("234");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }
}