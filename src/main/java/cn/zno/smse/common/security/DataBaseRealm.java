package cn.zno.smse.common.security;

import org.apache.shiro.SecurityUtils;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.zno.smse.common.constants.ShiroConstants;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

@Service
public class DataBaseRealm extends AuthorizingRealm {

	private final static String LOGIN_ERROR = "您输入的帐号或密码有误";
	
	@Autowired
	private SystemService systemService;

	/**
	 * 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		if (!SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
			return null;
		}
		return (SimpleAuthorizationInfo) SecurityUtils.getSubject().getSession().getAttribute(ShiroConstants.permsInfo);
	}

	/**
	 * 登录信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = String.valueOf(token.getPassword());
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		SystemUser principal = systemService.getUserByPassword(username, md5.encodePassword(password, null));
		if (principal == null)
			throw new UnknownAccountException(LOGIN_ERROR);
		return new SimpleAuthenticationInfo(principal, password, getName());
	}
}
