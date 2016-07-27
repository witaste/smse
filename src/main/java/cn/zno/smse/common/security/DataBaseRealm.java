package cn.zno.smse.common.security;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zno.smse.common.util.StringUtils;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

@Service
public class DataBaseRealm extends AuthorizingRealm {

	@Autowired
	private SystemService systemService;
	
	private final static String LOGIN_ERROR = "您输入的帐号或密码有误";

    /**
     * 授权
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SystemUser user = (SystemUser) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(systemService.getRoleSet(user.getUsername()));
		authorizationInfo.setStringPermissions(systemService
				.getPermissionSet(user.getUsername()));
		return authorizationInfo;
	}

    /**
     * 登录 
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		String password = String.valueOf(usernamePasswordToken.getPassword());
		if (username == null || password == null){
			throw new UnknownAccountException(LOGIN_ERROR);
		}else{
			SystemUser user = systemService.getUserByPassword(username, password);
			if (user == null){
				throw new UnknownAccountException(LOGIN_ERROR);
			}else{
				return new SimpleAuthenticationInfo(user, password, getName());
			}
		}
	}
}
