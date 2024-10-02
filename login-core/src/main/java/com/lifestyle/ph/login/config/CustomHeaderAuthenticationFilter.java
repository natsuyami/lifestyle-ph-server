package com.lifestyle.ph.login.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomHeaderAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomHeaderAuthenticationFilter.class);

    private static final String PAYMENT_AUTH="X-Payment-Auth";

    @Value("${spring.security.test.payment.val}")
    private String authPaymentVal;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String customHeaderValue = httpRequest.getHeader(PAYMENT_AUTH);
        String requestUri = httpRequest.getRequestURI();

        LOGGER.info("requestUri: {}", requestUri); // sample return requestUri: /myapp/auth/url
        if (authPaymentVal.equals(customHeaderValue) && "/myapp/videos".contains(requestUri)) {
            chain.doFilter(request, response);
        } else if (!"/myapp/videos".contains(requestUri)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("User is not authenticated to use the resources");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
