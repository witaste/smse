package cn.zno.smse.controller;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zno.smse.common.constants.ShiroConstants;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

@Controller
public class LoginController {
	
	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "login")
	public String login(Model model, SystemUser user) {
		// 用户不存在
		if (user == null || user.getUsername() == null || user.getPassword() == null) {
			model.addAttribute("error", "请填写账号密码");
			return "system/login";
		}

		Subject subject = SecurityUtils.getSubject();
		String error = null;
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			if (!subject.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				subject.login(token);
			}
		} catch (UnknownAccountException e) {
			error = e.getMessage();
		} catch (IncorrectCredentialsException e) {
			error = e.getMessage();
		} catch (LockedAccountException e) {
			error = e.getMessage();
		} catch (AuthenticationException e) {
			error = e.getMessage();
		} catch (Exception e) {
			error = e.getMessage();
		}
		// 登录失败
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			return "system/login";
		}
		// 登录成功
		else {
			Set<String> roleSet = systemService.getRoleSet(username);
			Set<String> permissionSet = systemService.getPermissionSet(username);

			SimpleAuthorizationInfo permsInfo = new SimpleAuthorizationInfo();
			permsInfo.setRoles(roleSet);
			permsInfo.setStringPermissions(permissionSet);
			subject.getSession().setAttribute(ShiroConstants.permsInfo, permsInfo);
			
			model.addAttribute("user", (SystemUser) subject.getPrincipal());
			
			return "redirect:system/main.htm";
		}
	}

	@RequestMapping(value = "logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "system/login";
	}
}
