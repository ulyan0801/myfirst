package com.my.springboot.security;

import com.my.springboot.utils.Result.ResponseJson;
import com.my.springboot.utils.jwt.JwtTokenUtil;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UsernamePasswordFilter2 extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public UsernamePasswordFilter2(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setPostOnly(true);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("----------UsernamePasswordFilter2---------");

//        获取参数判断为空等
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        username = Objects.isNull(username) ? "" : username.trim();
        password = Objects.isNull(password) ? "" : password.trim();
//获取用户名和密码 用户端的未加密的
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        UserDetailsBean user = (UserDetailsBean) auth.getPrincipal();
        if (user.getStatus()) {
            String authrorities = user.getAuthorities().size() > 0 ? user.getAuthorities().toString().replaceAll("(?:\\[|null|\\]| +)", "") : user.getAuthorities().toString();
            String token = JwtTokenUtil.createToken(user.getUsername(), authrorities);
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            List<Map> list = new ArrayList<>();
            list.add(map);
            ResponseJson.success(response, 200, "登录成功", list);
        } else {
            ResponseJson.fail(response, 400, "你的账号已被禁用,更多信息请登录xxx官网查询");
        }

        System.out.println("---------登录成功----------");
    }


    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if (e instanceof InternalAuthenticationServiceException) {
            InternalAuthenticationServiceException exception = (InternalAuthenticationServiceException) e;
            if (exception.getCause() != null) {
                System.out.println("----------ttt---------" + exception.getCause());
//              数据库连接异常
                if (exception.getCause() instanceof MyBatisSystemException) {
                    ResponseJson.fail(response, 400, "数据库连接失败");
                }
            }
        } else if (e instanceof UsernameNotFoundException) {
            UsernameNotFoundException exception = (UsernameNotFoundException) e;
            ResponseJson.fail(response, 400, exception.getMessage());

        } else {
            ResponseJson.fail(response, 400, "账号或者密码错误");
        }

        System.out.println("---------登录失败2----------" + e.getClass() + "---" + e.getMessage());

    }
}


