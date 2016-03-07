package org.spacepro.model.manager;

import org.spacepro.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserManager extends AbstractManager {

    public User getUserByLoginName(String loginName) {
        Query q = em.createQuery("select u from User u where u.loginName = :loginName")
                .setParameter("loginName", loginName);
        List<User> users = q.getResultList();
        return users.isEmpty()?null:users.get(0);
    }

}
