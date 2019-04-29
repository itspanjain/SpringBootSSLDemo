package com.demo.SpringBootSSLDemo.Filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        System.out.println("###Start a transaction for: " + httpServletRequest.getRequestURI());
        System.out.println("###ContentType: " + httpServletRequest.getContentType());

        if(httpServletRequest.getHeader("X-Correlation-Id") != null){
            httpServletResponse.setHeader("X-Correlation-Id",httpServletRequest.getHeader("X-Correlation-Id"));
        }else{
            httpServletResponse.setHeader("X-Correlation-Id","abc123");
        }

        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("###Commit a transaction for: " + httpServletRequest.getRequestURI());
    }

}
