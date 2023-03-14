package com.github.shanbei.shanbeiuser.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.exception.BusinessException;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.shanbei.shanbeiuser.constant.UserContent.USER_LOGIN_STATE;

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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 校验用户长度小于4
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }

        // 密码不小于8位
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }

        // 账户不包含任何特殊字符
        String validPatten = "/^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).)$/";
        Matcher matcher = Pattern.compile(validPatten).matcher(userPassword);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含特殊字符");
        }

        // 密码和再次确认时输入的密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码两次输入不一致");
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 加密，插入数据库
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUsername(userAccount); // 先让用户名等于账户名，因为数据库设置为非空字段。

        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "数据库插入失败");
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
        safetyUser.setTags(user.getTags());
        return safetyUser;
    }

    @Override
    public List<User> searchUserByTagsJVM(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 查询所有的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);

        Gson gson = new Gson();
        // Java8特性
        // 流式处理，filter过滤
        // 如果有要我们要的数据返回，否则过滤掉。
        // 过滤的过程可以使用并发计算，将stream()改为parallelStream()
        // 并行流有缺点，因为并发肯定要有线程池，否则会有性能的浪费。
        // 并行流用的是Java1.7带的forkjoinpull什么的。
        // 这里的线程池如果消耗太大，其它地方用的parallelStream就没有线程池用了。
        // 有篇文章parallelStream陷阱
        return userList.stream().filter(user -> {
            String tagsStr = user.getTags();
            if (StringUtils.isBlank(tagsStr)){
                return false;
            }
            // 这里做了一个类型的自动推断。
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());
            // 判空处理
            // Java8特性Optional
            // 这样做可以少写if
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public List<User> searchUserByTagsSQL(List<String> tagNameList) {
        // 如果查询有问题，则谁先返回用谁。
        // 可以并发。
        // 可以sql查询和内存结合。可以先用Sql过滤一部分，后面在进行查询。

        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 拼接 and 查询
        for (String tagName : tagNameList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        // this::getSafetyUser也是Java8的特性。
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除用户登录态。
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




