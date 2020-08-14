package com.topsci.qh.webmanagement.Filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zlli on 2020/6/9.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@WebFilter(urlPatterns = "/*",filterName = "MyCorsFilter")
@Order(value = 1)
public class MyCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) res;
        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Allow", "POST, GET, OPTIONS, DELETE");
        response.setHeader("XDomainRequestAllowed","1");
        response.setHeader("XDomainRequestAllowed","1");
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}