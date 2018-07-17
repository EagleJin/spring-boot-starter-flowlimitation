package com.king.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LimitController {
    @RequestMapping("/limit")
    public String limit(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("limit start...");
        System.out.println("limit do something...");
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.setContentType("text/palin;charset=UTF-8");
        try {
            response.getWriter().write("limit success....");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
