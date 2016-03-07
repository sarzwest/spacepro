package org.spacepro.gui.listener;

import org.spacepro.service.user.AuthService;
import org.spaceproservice.exception.AuthenticationException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    @Inject
    AuthService authService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        authService.registerNewUser("sarzwest", "password");
//        authService.updateCache("testtoken", "sarzwest");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
