package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.model.domain.User;
import com.example.model.request.UserLoginRequest;
import com.example.model.request.UserRegisterRequest;
import com.example.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);
    }
    @GetMapping("/search")
    public List<User> searchUser(String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        return userService.list(queryWrapper);
    }
    @PostMapping("/delete")
    public boolean deleteUser(long id){
        if(id<=0){
            return false;
        }
        return userService.removeById(id);
    }
}
