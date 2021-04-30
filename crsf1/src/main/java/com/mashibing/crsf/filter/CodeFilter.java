package com.mashibing.crsf.filter;

import com.google.code.kaptcha.Constants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/30 17:25
 * @version: 1.0
 ***********************/
public class CodeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getServletPath();


        if (uri.equals("/login") && req.getMethod().equalsIgnoreCase("post")) {


            String sessionCode = req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
            String formCode = req.getParameter("code").trim();

            if (StringUtils.isEmpty(formCode)) {
                throw new RuntimeException("验证码不能为空");
            }
            if (sessionCode.equalsIgnoreCase(formCode)) {

                System.out.println("验证通过");

            } else {
                throw new RuntimeException("验证码错误!");
            }
//            System.out.println(req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
//            throw new AuthenticationServiceException("xx");
        }

        chain.doFilter(request, response);
    }
}
