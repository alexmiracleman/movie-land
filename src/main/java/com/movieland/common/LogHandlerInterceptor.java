package com.movieland.common;

import com.movieland.service.impl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static com.movieland.service.impl.AuthenticationService.BEARER;

@RequiredArgsConstructor
public class LogHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    private final JwtService jwtService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MDC.put("UUID", UUID.randomUUID().toString());

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            MDC.put("userType", "GUEST");
        }
        else {
            String token = authHeader.replaceFirst(BEARER, StringUtils.EMPTY);
            String userTypeValue = jwtService.extractUsername(token);
            MDC.put("userType", userTypeValue);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
