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
public class LoginFilter implements Filter {

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
            try {
                authenticate(loginName, password, httpRequest, httpResponse);
            }catch (AuthenticationException e){
                httpResponse.setStatus(401);
            }
            return;
        }
    }

    private void authenticate(String loginName, String password, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws AuthenticationException {
            String token = authService.authenticate(loginName, password);
            httpResponse.setHeader("X-token", token);
    }

    @Override
    public void destroy() {

    }
}
