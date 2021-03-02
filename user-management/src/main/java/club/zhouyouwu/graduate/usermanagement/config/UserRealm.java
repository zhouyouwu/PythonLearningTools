package club.zhouyouwu.graduate.usermanagement.config;

import club.zhouyouwu.graduate.usermanagement.entity.Role;
import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Set;
import java.util.stream.Collectors;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        long userId = Long.parseLong(principalCollection.getPrimaryPrincipal().toString());
        User user = userService.getUserInfo(userId, "Auth");

        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
        authorization.setRoles(roles);
        return authorization;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        long userId = Long.parseLong(authenticationToken.getPrincipal().toString());
        User user = userService.getUserInfo(userId, "Auth");
        if(user == null){
            throw new UnknownAccountException();
        }

        String password = authenticationToken.getCredentials().toString();
        if(!BCrypt.checkpw(password, user.getPassword())){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(userId, password, "UserRealm");
    }
}
