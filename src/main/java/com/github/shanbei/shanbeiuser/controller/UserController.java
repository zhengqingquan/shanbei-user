package com.github.shanbei.shanbeiuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import com.github.shanbei.shanbeiuser.content.UserContent;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.model.domain.request.UserLoginRequest;
import com.github.shanbei.shanbeiuser.model.domain.request.UserRegisterRequest;
import com.github.shanbei.shanbeiuser.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        // 非空检查
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
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

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        // 非空检查
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        // 参数的校验，非业务逻辑的校验。
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        User result = userService.userlogin(userAccount, userPassword, request);

        return ResultUtils.success(result);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        int result = userService.userLogout(request);

        return ResultUtils.success(result);
    }


    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
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

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return null;
        }

        // 参数的非空检查
        if (id <= 0) {
            return null;
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     *
     * @param request Http请求
     * @return bool
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 鉴权，仅管理员可以操作
        // 先获取用户登录态
        Object userObject = request.getSession().getAttribute(UserContent.USER_LOGIN_STATE);
        User user = (User) userObject;
        return user != null && user.getUserRole() == UserContent.ADMIN_ROLE;
    }
}
