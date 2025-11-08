package com.ly.app.common.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ly.app.common.annotation.AuthCheck;
import com.ly.app.common.constant.UserConstant;
import com.ly.app.domain.enums.UserRoleEnum;
import com.ly.app.common.enums.error.ErrorCode;
import com.ly.app.common.units.AssertUtil;
import com.ly.app.domain.vo.user.SafeUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 权限校验拦截器
 * 基于 SaToken 实现权限控制
 *
 * @author liu yi
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthInterceptor {

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 1. 获取需要的角色
        String mustRole = authCheck.mustRole();

        // 2. 判断用户是否登录（使用 SaToken）
        AssertUtil.isTrue(StpUtil.isLogin(), ErrorCode.NOT_LOGIN_ERROR);

        // 3. 从 SaToken Session 中获取用户信息
        SafeUserVO loginUser = (SafeUserVO) StpUtil.getSession().get(UserConstant.USER_LOGIN_STATUS);
        AssertUtil.isNotNull(loginUser, ErrorCode.NOT_LOGIN_ERROR);

        // 4. 判断权限
        String userRole = loginUser.getUserRole();
        UserRoleEnum mustRoleEnum = UserRoleEnum.getRoleByKey(mustRole);
        UserRoleEnum userRoleEnum = UserRoleEnum.getRoleByKey(userRole);

        AssertUtil.isNotNull(userRoleEnum, ErrorCode.NO_AUTH_ERROR);

        // 如果不需要特定角色，直接放行
        if (ObjectUtil.isNull(mustRoleEnum)) {
            return joinPoint.proceed();
        }

        // 校验是否为管理员权限
        AssertUtil.isFalse(UserRoleEnum.ADMIN.equals(mustRoleEnum) &&
                !UserRoleEnum.ADMIN.equals(userRoleEnum), ErrorCode.NO_AUTH_ERROR);

        return joinPoint.proceed();
    }
}
