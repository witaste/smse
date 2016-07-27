package cn.zno.smse.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zno.smse.pojo.SystemUser;

@Controller
public class LoginController {

	@RequestMapping(value="login")
	public String login(Model model ,SystemUser user) {
		// 用户不存在 
		if (user == null || user.getUsername() == null || user.getPassword() == null){
			model.addAttribute("error", "请填写账号密码");
			return "system/login";
		}
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		String error = null;
		try {
			if (!subject.isAuthenticated()){
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
		if(error != null){
			model.addAttribute("error", error);
			model.addAttribute("username", user.getUsername());
			model.addAttribute("password", user.getPassword());
			return "system/login";
		}
		// 登录成功 
		else{
			model.addAttribute("user", (SystemUser)SecurityUtils.getSubject().getPrincipal());
			return "redirect:system/main.htm";
		}
	}

	@RequestMapping(value="logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "system/login";
	}
}
