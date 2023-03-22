package com.github.shanbei.shanbeiuser.verification;

import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

public class UserVerification {

    /**
     * 用户id校验
     * id必须为数值类型，且大于0
     *
     * @param id 用户id
     * @return 是否合法
     */
    public static boolean UserIdVer(long id) {

        // id大于0
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id错误");
        }

        // 合法
        return true;
    }

    /**
     * 用户账号校验
     * 账号必须非空，且不小于4位
     *
     * @param userAccount 用户账号
     * @return 是否合法
     */
    public static boolean userAccountVer(String userAccount) {

        // 账号非空
        if (StringUtils.isAnyBlank(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 账号不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }

        // 合法
        return true;
    }

    /**
     * 用户密码校验
     * 密码非空，且不小于8位
     *
     * @param userPassword 用户密码
     * @return 是否合法
     */
    public static boolean userPasswordVer(String userPassword) {

        // 密码非空
        if (StringUtils.isAnyBlank(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 密码不小于8位
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }

        // 合法
        return true;
    }
}
