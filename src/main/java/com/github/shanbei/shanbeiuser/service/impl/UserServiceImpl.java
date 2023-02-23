package com.github.shanbei.shanbeiuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 96400
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-02-23 23:02:11
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




