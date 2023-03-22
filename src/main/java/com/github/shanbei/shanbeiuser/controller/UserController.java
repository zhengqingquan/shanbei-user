package com.github.shanbei.shanbeiuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import com.github.shanbei.shanbeiuser.constant.UserContent;
import com.github.shanbei.shanbeiuser.exception.BusinessException;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.model.domain.dto.UserLoginRequest;
import com.github.shanbei.shanbeiuser.model.domain.dto.UserRegisterRequest;
import com.github.shanbei.shanbeiuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CrossOrigin注解可以解决跨域问题。比较简单粗暴。
 * 跨域只能防止浏览器一端，而不能防御由Java发起的请求。
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求数据
     * @return 通用返回类
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        // 非空检查
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 参数的校验，非业务逻辑的校验。
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求数据
     * @param request          请求
     * @return 通用返回类
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        // 非空检查
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        // 参数的校验，非业务逻辑的校验。
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User result = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(result);
    }

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 通用返回类
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.userLogout(request);
        return ResultUtils.success();
    }

    /**
     * 搜索用户
     *
     * @param username 用户名
     * @param request 请求
     * @return 通用返回类
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            return ResultUtils.success(new ArrayList<>());
        }

        // 偷懒，没定义服务层
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> result = userList.stream().map(userService::getSafetyUser).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    /**
     * 搜索用户
     *
     * @param request
     * @return
     */
    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request) {
        // 如果登录了使用推荐算法一
        // 如果未登录使用默认推荐算法。

        User loginUser = userService.getLoginUser(request);
        // 如果有缓存，直接读取缓存
        String redisKey = String.format("shanbei:user:recommend:%s", loginUser.getId());
        ValueOperations<String, Object> valueConstants = redisTemplate.opsForValue();
        Page<User> userPage = (Page<User>) redisTemplate.opsForValue().get(redisKey);

        if (userPage != null) {
            return ResultUtils.success(userPage);
        }

        // 无缓存，查数据。

        // 偷懒，没定义服务层
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 分页
        // Page<User> userPage = userService.page(new Page<>(pageNum,pageSize),queryWrapper);

        // 写缓存
        try {
            valueConstants.set(redisKey, userPage);
        } catch (Exception e) {
            log.error("redis set key error", e);
        }
        return ResultUtils.success(userPage);
    }

    /**
     * 查询当前用户
     *
     * @param request HTTP请求
     * @return BaseResponse<User>
     */
    @GetMapping("/currentUser")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        // 获取用户登录态
        Object userObject = request.getSession().getAttribute(UserContent.USER_LOGIN_STATE);
        User currentUser = (User) userObject;
        if (currentUser == null) {
            return null;
        }
        long userId = currentUser.getId();
        // 校验用户是否合法
        User user = userService.getById(userId);
        User result = userService.getSafetyUser(user);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户
     *
     * @param id      用户ID
     * @param request 请求
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {

        // 权限检查
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 参数的非空检查
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }


    /**
     * 更新用户信息
     * 如果有不希望用户修改的内容，可以封装一个UserDTO
     *
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(User user, HttpServletRequest request) {
        // 1. 校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        // 2. 校验权限，是否有权限更新用户信息
        // 3. 触发更新
        boolean result = userService.updateUser(user, loginUser);
        return ResultUtils.success(result);

    }

    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUsersByTags(@RequestParam(required = false) List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUserByTagsSQL(tagNameList);
        return ResultUtils.success(userList);
    }
}
