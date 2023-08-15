package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86158
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-08-14 18:42:03
* @Entity com.example.model.domain.User.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




