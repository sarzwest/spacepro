package org.spacepro.service.user;

import org.spacepro.model.entity.User;
import org.spacepro.model.manager.UserManager;
import org.spaceproservice.exception.EntityException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    UserManager userManager;

    public void saveUser(User u) {
        userManager.persist(u);
    }

    public User getUser(String loginName) throws EntityException {
        User u = userManager.getUserByLoginName(loginName);
        if (u == null) {
            throw new EntityException("User with login " + loginName + " does not exist");
        }
        return u;
    }

    public Long getUserId(String loginName) throws EntityException {
        return getUser(loginName).getId();
    }

}
