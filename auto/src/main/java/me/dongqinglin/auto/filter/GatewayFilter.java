package me.dongqinglin.auto.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GatewayFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(response.getHeader("isAuthSuccess") == "true"){
            System.out.println("这里这里");
            filterChain.doFilter(request, response);
        }else {
            System.out.println(response.getHeader("isAuthSuccess"));
            System.out.println("哪里哪里");
            response.sendError(403, "权限不足");
        }
    }
}
