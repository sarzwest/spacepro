package org.spacepro.service.user;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.spacepro.model.entity.User;
import org.spacepro.model.manager.UserManager;
import org.spaceproservice.exception.AuthenticationException;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Stateless
public class AuthService {

    private static final String HASH_SALT = "1981kubvt";
    LoadingCache<String, String> cache;

    @Inject
    UserManager userManager;

    @PostConstruct
    void init() {
        cache = CacheBuilder.newBuilder().
                expireAfterAccess(10, TimeUnit.HOURS).
                build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return "testToken";
                    }
                });
    }

    public String getLoginName(String token) throws AuthenticationException {
        try{
            String loginName = cache.get(token);
            if(loginName != null){
                return loginName;
            }
            throw new AuthenticationException("Token is not valid");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new AuthenticationException("Could not get user by token");
        }
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void registerNewUser(String loginName, String password) {
        User u = new User(loginName, getHashed(password));
        userManager.persist(u);
    }

    public String authenticate(String loginName, String password) throws AuthenticationException {
        try {
            String hashedPassword = getHashed(password);
            User u = userManager.getUserByLoginName(loginName);
            if(u.getPassword().equals(hashedPassword)){
                String token = generateToken();
                cache.put(token, loginName);
                return token;
            }
            throw new AuthenticationException("Bad login details");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AuthenticationException("User cannot be authenticated");
        }
    }

    public void updateCache(String token, String loginName){
        cache.put(token, loginName);
    }

    private String getHashed(String password) {
        return DigestUtils.sha256Hex(HASH_SALT + password);
    }
}
