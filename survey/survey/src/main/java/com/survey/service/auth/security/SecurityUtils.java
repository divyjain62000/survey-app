package com.survey.service.auth.security;

import com.survey.config.JwtTokenUtil;
import com.survey.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class SecurityUtils {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public User getUser() {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        log.debug("JWT Token: {}",jwtToken);
        User user=jwtTokenUtil.getUserFromToken(jwtToken);
        log.debug("Uid: {}",user.getId());
        return user;
    }

}
