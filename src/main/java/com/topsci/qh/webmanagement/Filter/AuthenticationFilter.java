package com.topsci.qh.webmanagement.Filter;

import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Tools.JWTUtils;
import com.topsci.qh.webmanagement.Tools.JsonUtils;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw.
 * 16-6-20
 */
@WebFilter(urlPatterns = "/*", filterName = "ignore")
@Order(value = 1)
public class AuthenticationFilter implements Filter {
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        if ("/system/login".equals(path)) {
            chain.doFilter(request, response);
            return;
        }
        if (httpServletRequest.getRequestURI().endsWith("html")
                || httpServletRequest.getRequestURI().endsWith("css")
                || httpServletRequest.getRequestURI().endsWith("js")
                || httpServletRequest.getRequestURI().contains("swagger")
                || httpServletRequest.getRequestURI().contains("v2")
                || httpServletRequest.getRequestURI().contains("/system/login")
                || httpServletRequest.getMethod().equals("OPTIONS")
                ) {
            chain.doFilter(request, response);
            return;
        }
        String token = httpServletRequest.getHeader("token");
        System.out.println("请求：" + httpServletRequest.getRequestURI() + "  " + httpServletRequest.getMethod() + "  " + token);

        HttpSession session = httpServletRequest.getSession();
        if (redisTemplate == null) {
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        }
        try {
            String user = JWTUtils.getUserName(token);
            Object o = redisTemplate.opsForValue().get(token);

            if (token == null || !token.startsWith("Bearer ") || user == null || o == null) {
                this.notAuth(httpServletResponse);
                return;
            }
        } catch (Exception e) {
            this.notAuth(httpServletResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


    private void notAuth(HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mes", "登录失效，请重新登录！");
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setAuth(map);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.setContentType("application/json");
        try {
            response.getWriter().println(JsonUtils.beanToJson(reslutParent.toBack()));
            response.getWriter().flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
