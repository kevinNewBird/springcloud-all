package com.mashibing.jwt01.filter;


import com.mashibing.jwt01.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/5/3 10:37
 * @version: 1.0
 ***********************/
@Component
@WebFilter(filterName = "jwtAuthFilter", urlPatterns = "/*")
public class JwtAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("jwt auth starting...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("非法的请求!");
        }
        String subject = JwtUtil.parseToken(token);
        if (StringUtils.isEmpty(subject)) {
            throw new RuntimeException("token非法!");
        }
        // 续期:自动续(时间间隔),手动续
        System.out.println(subject);
        chain.doFilter(request, response);
    }
}
