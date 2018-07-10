package com.king.limit.filter;

import com.king.limit.util.AutoLoadUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LimitFilter implements Filter {
    private static int currentRequest;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        currentRequest = 0;
        System.out.println("currentRequest:>>"+currentRequest);
        // 获取配置文件中配置的最大允许的请求量
        System.out.println("init:maxRequest:>>"+Integer.parseInt(AutoLoadUtil.getYamlValue("system.limit.maxRequest")));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if ("false".equals(AutoLoadUtil.getYamlValue("system.limit.debug"))) {
            ++ currentRequest;
            System.out.println("doFilter:currentRequest:>>"+currentRequest);
            Integer maxRequest = Integer.parseInt(AutoLoadUtil.getYamlValue("system.limit.maxRequest"));
            System.out.println("doFilter :maxRequest:>>"+maxRequest);
            if (currentRequest > maxRequest) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter print = resp.getWriter();
                print.write(AutoLoadUtil.getYamlValue("system.limit.message"));
                System.out.println(AutoLoadUtil.getYamlValue("system.limit.message"));
                return;
            }
        }

        filterChain.doFilter(request, response);
        currentRequest --;
        System.out.println("doFilter:currentRequest--:>>"+currentRequest);
        System.out.println("after doFilter....");
    }

    @Override
    public void destroy() {
        System.out.println("destory().....");
    }
}
