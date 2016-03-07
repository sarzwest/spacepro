package org.spacepro.rest.filter;

import lombok.extern.slf4j.Slf4j;
import org.spacepro.service.user.AuthService;
import org.spaceproservice.exception.AuthenticationException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Slf4j
@Provider
public class RegistrationFilter implements Filter {

    @Inject
    AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String loginName = httpRequest.getHeader("X-loginName");
        String password = httpRequest.getHeader("X-password");
        if (loginName != null && password != null) {
            authService.registerNewUser(loginName, password);
            httpResponse.setStatus(200);
            return;
        }
        httpResponse.setStatus(400);
        return;
    }

    private boolean authenticate(String loginName, String password, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            String token = authService.authenticate(loginName, password);
            httpRequest.setAttribute("loginName", loginName);
            httpResponse.setHeader("X-token", token);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public void destroy() {

    }
}
