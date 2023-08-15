package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.domain.User;
import com.example.service.UserService;
import com.example.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.contant.UserContant.LOGIN_STATE;
import static com.example.contant.UserContant.SALT;

/**
* @author 86158
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-08-14 18:42:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1，校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length()<4){
            return -1;
        }
        if(userPassword.length()<6||userPassword.length()>16){
            return -1;
        }
        //账户不包含重复字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return -1;
        }
        //2.加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3.插入数据
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        int saveResult = userMapper.insert(user);
        if(saveResult<1){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1，校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<6||userPassword.length()>16){
            return null;
        }
        //账户不包含重复字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            return null;
        }
        //用户脱敏
        User newUser = getHandleUser(user);
        //记录登录态
        request.getSession().setAttribute(LOGIN_STATE,newUser);
        return newUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getHandleUser(User originUser){
        if(originUser==null){
            return null;
        }
        User newUser=new User();
        newUser.setId(originUser.getId());
        newUser.setUsername(originUser.getUsername());
        newUser.setUserAccount(originUser.getUserAccount());
        newUser.setAvatarUrl(originUser.getAvatarUrl());
        newUser.setUserRole(originUser.getUserRole());
        newUser.setGender(originUser.getGender());
        newUser.setEmail(originUser.getEmail());
        newUser.setUserStatus(originUser.getUserStatus());
        return newUser;
    }
}




