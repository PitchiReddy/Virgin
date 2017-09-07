package com.virginvoyages.api;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // Set the correlation ID
        HttpServletRequest req = (HttpServletRequest) request;
        String correlationID = req.getHeader("X-Correlation-ID");
        String clientID = req.getHeader("X-VV-Client-ID");
        if (StringUtils.isEmpty(correlationID)) {
            correlationID = UUID.randomUUID().toString();
        }
        MDC.put("correlationID", correlationID);
        MDC.put("clientID", clientID);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}