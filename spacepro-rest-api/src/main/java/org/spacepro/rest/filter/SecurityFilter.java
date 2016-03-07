package org.spacepro.rest.filter;

import lombok.extern.slf4j.Slf4j;
import org.spacepro.service.user.AuthService;
import org.spaceproservice.exception.AuthenticationException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Slf4j
@Provider
@WebFilter(urlPatterns = "/rest/*")
public class SecurityFilter implements Filter {

    @Inject
    AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        try {
            String loginName = authService.getLoginName(httpRequest.getHeader("X-token"));
            httpRequest.setAttribute("loginName", loginName);
            httpResponse.setHeader("X-token", httpRequest.getHeader("X-token"));
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (AuthenticationException e){
            httpResponse.setStatus(401);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
