package com.king.limit.filter;

import com.king.limit.util.AutoLoadUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitFilter implements Filter {
    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ....
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            Integer maxRequest = Integer.parseInt(AutoLoadUtil.getYamlValue("system.limit.maxRequest"));
            System.out.println("doFilter :maxRequest:>>" + maxRequest);
            if (counter.incrementAndGet() > maxRequest) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter print = resp.getWriter();
                print.write(AutoLoadUtil.getYamlValue("system.limit.message"));
                System.out.println(AutoLoadUtil.getYamlValue("system.limit.message"));
                return;
            }
            filterChain.doFilter(request, response);
        } finally {
//            counter.decrementAndGet();
            System.out.println("doFilter:currentRequest:>>" + counter.decrementAndGet());
        }

        System.out.println("after doFilter....");
    }

    @Override
    public void destroy() {
        // .....
    }
}
