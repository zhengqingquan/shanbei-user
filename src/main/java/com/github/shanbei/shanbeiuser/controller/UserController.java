package com.github.shanbei.shanbeiuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.shanbei.shanbeiuser.content.UserContent;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.model.domain.request.UserRegisterRequest;
import com.github.shanbei.shanbeiuser.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        // 非空检查
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 参数的校验。
        // 非业务逻辑的校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {

        // 非空检查
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();

        // 参数的校验。
        // 非业务逻辑的校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.userlogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)){
            return new ArrayList<>();
        }

        // 偷懒，没定义服务层
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        return userService.list(queryWrapper);
    }


    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)){
            return false;
        }

        // 参数的非空检查
        if (id <= 0) {
            return false;
        }

        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     * @param request
     * @return bool
     */
    private boolean isAdmin(HttpServletRequest request){
        // 鉴权，仅管理员可以操作
        Object userObject = request.getSession().getAttribute(UserContent.USER_LOGIN_STATE);
        User user = (User) userObject;
        return user != null && user.getRole() == UserContent.ADMIN_ROLE;
    }
}
