package com.example.demo.config;

import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@WebFilter(urlPatterns = "/IsticharaConsultation/api/*")
public class CorsFilter implements Filter {

    private static final List<String> allowedOrigins = Arrays.asList("http://localhost:4200", "https://yassinemj.github.io");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String origin = httpRequest.getHeader("Origin");

        // Check if the origin is allowed
        if (allowedOrigins.contains(origin)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        }
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Proceed with the next filter in the chain
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
