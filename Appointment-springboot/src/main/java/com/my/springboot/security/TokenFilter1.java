package com.my.springboot.security;


import com.my.springboot.utils.Result.ResponseJson;
import com.my.springboot.utils.jwt.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

//全局路径拦截 拦截判断Token 是否存在和有效等
public class TokenFilter1 extends OncePerRequestFilter {
    //所有没有放行的url经过次过滤器
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("---------TokenFilter1-拦截未放行的url是否携带token---------" + request.getRequestURI());
        if ("/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }


        try {
            String header = request.getHeader("Authorization");
            System.out.println("-----------header--------" + header);
            String token = header.split(" ")[1];
            JwtTokenUtil.verifyToken(token);
            // 从Token中解密获取用户名
            String userName = JwtTokenUtil.getUserNameFromToken(token);

            String role = JwtTokenUtil.getUserRoleFromToken(token);
            // 将ROLE_XXX,ROLE_YYY格式的角色字符串转换为数组
            String[] roles = role.split(",");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String s : roles) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName, token, authorities));
        } catch (Exception e) {
            ResponseJson.fail(response, 401, "你的身份信息已失效,请重新登录");
            return;
        }

        chain.doFilter(request, response);
    }

}
