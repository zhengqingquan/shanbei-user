package com.github.shanbei.shanbeiuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 96400
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-02-23 23:02:11
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 校验输入值非空
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }

        // 校验用户长度小于4
        if (userAccount.length()<4){
            return -1;
        }

        // 密码不小于8位
        if(userPassword.length()<8){
            return -1;
        }

        // 账户不包含任何特殊字符
        String validPatten = "/^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).)$/";
        Matcher matcher = Pattern.compile(validPatten).matcher(userPassword);
        if(matcher.find()){
            return -1;
        }

        // 密码和再次确认时输入的密码相同
        if (!userPassword.equals(checkPassword)){
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count>0){
            return -1;
        }

        // 加密，插入数据库
        final String SALT = "shanbei";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUsername(userAccount); // 先让用户名等于账户名，因为数据库设置为非空字段。

        boolean saveResult = this.save(user);
        if (!saveResult){
            return -1;
        }

        // 返回用户 ID
        return user.getId();
    }
}




