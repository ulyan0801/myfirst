package com.my.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
//生效权限注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig0 extends WebSecurityConfigurerAdapter {
    /**
     * 用户服务类
     */
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    /**
     * 认证（Authentication） 授权（Authorization）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启允许iframe 嵌套
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .cors()
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 过滤请求
                .authorizeRequests()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilter(new UsernamePasswordFilter2(authenticationManager()))
                .addFilterBefore(new TokenFilter1(), UsernamePasswordFilter2.class);
    }

    //跨域 结合上面配置方法中的 .cors()
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    /**
     * 配置哪些请求不拦截
     */


    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] AUTH_WHITELIST = {
//            放行接口
//            注册
                "/register",
                "/isregister",
//            退出登录
                "/logout",
                "/findpwd",
                //静态资源
                //因为 yml配置add-mappings: false 目的为了统一异常处理可以拦截到系统404和500异常 但是这个会把静态资源的配置失效 所以要在此设置
                // 放行 图片存在磁盘的自定义目录中 url访问 如 http://localhost:8888/+yml配置中的static-path-pattern/+1.bmp
                // 如yml中 static-path-pattern: /static/ 则访问地址为http://localhost:8888/static/1.bmp此图片既可以放在工程的
                ///resources/static目录下 也可以放在自定义的磁盘路径下 如"file:"+windowsUploadPath, "file:"+LinuxUploadPath 2个变量的值
                //需要一个配置文件 本项目中的 com.my.springboot.swagger.WebMvcConfig
                staticPathPattern + "**",
                "/download",
                //以下放行swagger
                "/swagger-ui**/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/**/api-docs",
        };
        System.out.println("---------配置哪些请求不拦截--------");
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }


    //处理security 抛出的UsernameNotFoundException 捕获不到的问题
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
