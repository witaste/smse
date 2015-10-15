package cn.zno.smse.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.zno.smse.pojo.SystemUser;

public class LoginAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	private SystemUser user;
	private static final String LOGIN = "login";

	public String login() {
		if (user == null)
			return LOGIN;
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		try {
			if (!subject.isAuthenticated())
				subject.login(token);
		} catch (UnknownAccountException e) {
			addActionError(e.getMessage());
			return LOGIN;
		} catch (IncorrectCredentialsException e) {
			addActionError(e.getMessage());
			return LOGIN;
		} catch (LockedAccountException e) {
			addActionError(e.getMessage());
			return LOGIN;
		} catch (AuthenticationException e) {
			addActionError(e.getMessage());
			return LOGIN;
		}
		return SUCCESS;
	}

	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return LOGIN;
	}

	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}
}
