package com.mashibing.crsf.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/27 0:29
 * @version: 1.0
 ***********************/
@RestController
public class HiController {

    //去掉WebSecurityConfigV1下的anyRequest().authenticated()后,无需登录就可访问的接口
    @GetMapping("/hi")
    //TIP:使用如下的注解配置,请确认和WebSecurityConfig中的配置不冲突

    // 1.角色关系授权(或的关系, 不支持并且的关系),开启securedEnabled=true
//    @Secured("user")
    // 2.可以实现并且的权限关系(支持并且,表达式),开启prePostEnabled=true
    //单个角色
//    @PreAuthorize("hasRole('user')")
    // 任一角色(或的关系)
//    @PreAuthorize("hasAnyRole('user','admin')")
    // 同时具有角色(且的关系)
//    @PreAuthorize("hasRole('user') and hasRole('admin')")
    // 3.根据方法返回值判断权限,支持spring EL的表达式,当表达式为true有权限访问,false直接放回403
    // 使用场景: 适用于在业务内部才能确定的场景, 举例: A ,B 两个系统, 用户有权限进入A系统,但是有没有权限访问B系统不知道, 得进入系统内部才能确定
    // 跨节点使用
//    @PostAuthorize("returnObject.equals('hi come on!')")
    public String sayHi() {
        //可以在任意位置拿到用户的权限信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object user = authentication.getPrincipal();
        System.out.println("user:" + user.getClass());
        System.out.println("权限:" + authentication.getAuthorities());
        System.out.println("detail:" + authentication.getDetails());
        System.out.println("credential:" + authentication.getCredentials());
        System.out.println("hi");
        return "hi come on!";
    }

    @GetMapping("/admin/hi")
//    @PreAuthorize("hasRole('ROLE_admin')")
    public String adminHi() {
        return "hi admin";
    }

    @GetMapping("/user/hi")
//    @PreAuthorize("hasRole('ROLE_user')")
    public String userHi() {
        return "hi user";
    }


}
