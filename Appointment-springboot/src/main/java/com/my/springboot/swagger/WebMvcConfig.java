package com.my.springboot.swagger;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.my.springboot.empty.User;
import com.my.springboot.empty.UserRoles;
import com.my.springboot.service.impl.UserRolesServiceImpl;
import com.my.springboot.service.impl.UserServiceImpl;
import com.my.springboot.utils.system.JudgeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//设置静态资源目录 启动检测和自动创建静态资源目录和管理账号等功能
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${windows.resources.path}")
    private String windowsResourcesPath;

    @Value("${linux.resources.path}")
    private String LinuxResourcesPath;
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRolesServiceImpl userRolesService;

    /**
     * 解决配置文件添加了add-mappings: false后导致swagger失效的问题
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String[] ResourceLocations = {
                "classpath:/META-INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
                "classpath:/html/",
                "file:" + windowsResourcesPath,
                "file:" + LinuxResourcesPath
        };
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/", "/static", "/public");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        //因为 yml配置add-mappings: false 目的为了统一异常处理可以拦截到系统404和500异常 但是这个会把静态资源的配置失效 所以要在此设置
        // 放行 图片存在磁盘的自定义目录中 url访问 如 http://localhost:8888/+yml配置中的static-path-pattern/+1.bmp 此图片既可以放在工程的
        ///resources/static目录下 也可以放在自定义的磁盘路径下 如"file:"+windowsUploadPath, "file:"+LinuxUploadPath 2个变量的值
        //还需要在 security中放行 静态访问路径
        //如yml中 static-path-pattern: /static/ 则访问地址为http://localhost:8888/static/1.bmp
        registry.addResourceHandler(staticPathPattern).addResourceLocations(ResourceLocations);

    }


    @PostConstruct
    public void createFileToDisk() {
        LambdaQueryWrapper<User> userQueryWrapperr = new LambdaQueryWrapper<>();
        userQueryWrapperr.eq(User::getUsername, "admin");
        User userServiceOne = userService.getOne(userQueryWrapperr);
        if (userServiceOne == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123456");
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setUserNickname("超级管理员");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            user.setLastLoginTime(formatter.format(date));
            user.setCreateTime(formatter.format(date));
            //插入用户表
            userService.save(user);
            //设置注册用户默认权限为普通权限
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(user.getId());
            userRoles.setRolesId(1);
            //插入权限表
            boolean save = userRolesService.save(userRoles);
            if (save) {
                System.out.println("---------自动创建后台账号成功----------");
                System.out.println("--------后台账号密码 admin 123456-----------");
                System.out.println("--------具有ROLE_ADMIN权限的账号都可以登录后台-----------");

            } else {
                System.out.println("---------自动创建后台账号失败----------");
                System.out.println("---------手动创建后台账号 需user_roles将根据创建的后台账号id在roles_id表中设置为1----------");
                System.out.println("--------具有ROLE_ADMIN权限的账号都可以登录后台-----------");
            }
        } else {
            System.out.println("--------后台账号密码 admin 123456-----------");
            System.out.println("--------具有ROLE_ADMIN权限的账号都可以登录后台-----------");
        }

        File dest = null;
        if (JudgeSystem.isLinux()) {

            dest = new File(LinuxResourcesPath);//创建目录
            if (!dest.exists()) {
                dest.mkdirs();
            }
        } else if (JudgeSystem.isWindows()) {
            dest = new File(windowsResourcesPath);//创建目录

        }
        if (dest != null) {
            if (!dest.exists()) {
                dest.mkdirs();

            }
            System.out.println("---------保存静态文件目录创建成功 位置----------" + windowsResourcesPath);
            System.out.println("---------访问静态文件方法--- http://localhost:端口号/static/xxx.jpg -------");
            System.out.println("---------访问静态文件方法如--- http://localhost:8888/static/test.bmp -------");
            System.out.println("---------查看接口文档 http://localhost:你的端口/swagger-ui.html -------");
            System.out.println("---------查看接口文档如 http://localhost:8888/swagger-ui.html -------");
            System.out.println("---------所有接口的访问都需要携带token才能访问，如需放行接口，无需携带token方法就可以访问接口，在源码文件com/my/springboot/security/SecurityConfig0.java中配置 -------");
            System.out.println("---------源码除了com/my/springboot/controller目录是接口实现文件，其他都是框架搭建部分，接口实现基本上流程都是获取客户端请求或携带数据的请求，使用mybatis-plus对数据库增删改查或无数据操作，最后把结果返回给客户端-------");
            System.out.println("---------如启动报错Web server failed to start. Port 8888 was already in use.服务器用到8888端口被占用错误，解决办法：-------");
            System.out.println("---------win+R 输入cmd打开黑窗口输入 netstat -aon|findstr \"8888\"\n 接着输入taskkill /f /t /pid xxx(xxx是查看出来的进程号）或自行百度服务器端口被占用或重启电脑-------");
        } else {
            System.out.println("---------静态资源目录创建失败----------");
        }
    }

}
