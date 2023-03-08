package com.github.shanbei.shanbeiuser.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.content.UserContent;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.shanbei.shanbeiuser.content.UserContent.USER_LOGIN_STATE;

/**
 * @author 96400
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-02-23 23:02:11
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    // 加密盐值
    private static final String SALT = "shanbei";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 校验输入值非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

        // 校验用户长度小于4
        if (userAccount.length() < 4) {
            return -1;
        }

        // 密码不小于8位
        if (userPassword.length() < 8) {
            return -1;
        }

        // 账户不包含任何特殊字符
        String validPatten = "/^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).)$/";
        Matcher matcher = Pattern.compile(validPatten).matcher(userPassword);
        if (matcher.find()) {
            return -1;
        }

        // 密码和再次确认时输入的密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 加密，插入数据库
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUsername(userAccount); // 先让用户名等于账户名，因为数据库设置为非空字段。

        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }

        // 返回用户 ID
        return user.getId();
    }

    @Override
    public User userlogin(String userAccount, String userPassword, HttpServletRequest request) {

        // 校验输入值非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        // 校验用户长度小于4
        if (userAccount.length() < 4) {
            return null;
        }

        // 密码不小于8位
        if (userPassword.length() < 8) {
            return null;
        }

        // 账户不包含任何特殊字符
        String validPatten = "/^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).)$/";
        Matcher matcher = Pattern.compile(validPatten).matcher(userPassword);
        if (matcher.find()) {
            return null;
        }

        // 把输入的密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询用户是否存在，若不存在返回null并打印日志。
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPasswod.");
            return null;
        }

        // 用户脱敏
        // 防止用户信息泄露给前端
        User safetyUser = getSafetyUser(user);

        // 记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        // 返回脱敏后的用户信息
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User user) {

        // 非空校验
        if (user == null) {
            return null;
        }

        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(new Date());
        safetyUser.setUserRole(user.getUserRole());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除用户登录态。
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




