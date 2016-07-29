package cn.zno.smse.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zno.smse.pojo.Page;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

@Controller
@RequestMapping(value="system")
public class SystemController {


	@Autowired
	private SystemService systemService;
	
	//*---------------------------------------
	//*----------- 菜单管理 ↓-------------------
	//*---------------------------------------

	// 跳转到 main.jsp
	@RequestMapping(value="main.htm")
	public String main(Model model) {
		SystemUser user = (SystemUser)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "system/main";
	}
	
	// 修改密码
	@RequestMapping(value="savePassword.json")
	@ResponseBody
	public Object savePassword(String oldPassword ,String newPassword, String confirmPassword){
		return systemService.savePassword(oldPassword, newPassword, confirmPassword);
	}
	
	// 异步加载tree
	@RequestMapping(value="getTreeNode.json")
	@ResponseBody
	public Object getTreeNode() {
		return systemService.getTreeNode();
	}

	// 跳转到menu.jsp
	@RequestMapping(value="initMenu.htm")
	public String initMenu() {
		return "system/menu/menuL";
	}
	
	@RequestMapping(value="menuOption.htm")
	public String menuOption(Model model ,String flag,String menuId,String menuPId){
		SystemMenu menu = systemService.getMenuById(menuId);
		model.addAttribute("menu", menu);
		model.addAttribute("flag", flag);
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuPId", menuPId);
		return "system/menu/menuR";
	}
	
	@RequestMapping(value="deleteMenu.json")
	@ResponseBody
	public Object deleteMenu(SystemMenu menu){
		return systemService.deleteMenu(menu);
	}

	// 保存菜单信息
	@RequestMapping(value="saveMenu.json")
	@ResponseBody
	public Object saveMenu(SystemMenu menu,String changes) {
		return systemService.saveMenu(menu,changes);
	}
	
	// 异步加载资源信息 
	@RequestMapping(value="getAccessPermission.json")
	@ResponseBody
	public Object getAccessPermission(SystemMenu menu){
		return systemService.getAccessPermission(menu);
	}
	
	// 异步加载[该菜单所属]角色信息
	@RequestMapping(value="getRole.json")
	@ResponseBody
	public Object getRole(SystemMenu menu){
		return systemService.getRoleRecord(menu);
	}
	
	// 异步加载全部角色信息
	@RequestMapping(value="getRoleAll.json")
	@ResponseBody
	public Object getRoleAll(SystemRole systemRole,Page page){
		return systemService.getRoleAll(systemRole ,page.getRowBounds());
	}
	
	//*---------------------------------------
	//*----------- 用户管理 ↓-------------------
	//*---------------------------------------
	@RequestMapping(value="initUserAdd.htm")
	public String initUserAdd(Model model) {
		List<SystemRole> systemRoleList = systemService.getRoleList();
		model.addAttribute("systemRoleList", systemRoleList);
		return "system/user/userAdd";
	}

	@RequestMapping(value="initUserDetail.htm")
	public String initUserDetail(Model model ,SystemUser user) {
		List<SystemRole> systemRoleList = systemService.getRoleList();
		user = systemService.getUserRecord(user);
		model.addAttribute("systemRoleList", systemRoleList);
		model.addAttribute("user", user);
		return "system/user/userDetail";
	}

	@RequestMapping(value="initUserEdit.htm")
	public String initUserEdit(Model model ,SystemUser user) {
		List<SystemRole> systemRoleList = systemService.getRoleList();
		user = systemService.getUserRecord(user);
		model.addAttribute("systemRoleList", systemRoleList);
		model.addAttribute("user", user);
		return "system/user/userEdit";
	}
	
	@RequestMapping(value="initUserList.htm")
	public String initUserList() {
		return "system/user/userList";
	}

	@RequestMapping(value="saveUserAdd.json")
	@ResponseBody
	public Object saveUserAdd(SystemUser user) {
		return systemService.saveUserAdd(user);
	}

	@RequestMapping(value="saveUserEdit.json")
	@ResponseBody
	public Object saveUserEdit(SystemUser user) {
		return systemService.saveUserEdit(user);
	}
	
	@RequestMapping(value="deleteUser.json")
	@ResponseBody
	public Object deleteUser(String[] ids){
		return systemService.deleteUser(ids);
	}

	// 异步加载列表 
	@RequestMapping(value="getUserList.json")
	@ResponseBody
	public Object getUserList(SystemUser user,Page page) {
		return systemService.getUserList(user, page.getRowBounds());
	}
	
	
	//*---------------------------------------
	//*----------- 角色管理 ↓-------------------
	//*---------------------------------------
	@RequestMapping(value="initRoleAdd.htm")
	public String initRoleAdd() {
		return "system/role/roleAdd";
	}

	@RequestMapping(value="initRoleDetail.htm")
	public String initRoleDetail(Model model ,SystemRole systemRole) {
		systemRole = systemService.getRoleRecord(systemRole);
		model.addAttribute("systemRole", systemRole);
		return "system/role/roleDetail";
	}

	@RequestMapping(value="initRoleEdit.htm")
	public String initRoleEdit(Model model ,SystemRole systemRole) {
		systemRole = systemService.getRoleRecord(systemRole);
		model.addAttribute("systemRole", systemRole);
		return "system/role/roleEdit";
	}

	@RequestMapping(value="initRoleList.htm")
	public String initRoleList() {
		return "system/role/roleList";
	}

	@RequestMapping(value="saveRoleAdd.json")
	@ResponseBody
	public Object saveRoleAdd(SystemRole systemRole) {
		return systemService.saveRoleAdd(systemRole);
	}

	@RequestMapping(value="saveRoleEdit.json")
	@ResponseBody
	public Object saveRoleEdit(SystemRole systemRole) {
		return systemService.saveRoleEdit(systemRole);
	}
	
	@RequestMapping(value="deleteRole.json")
	@ResponseBody
	public Object deleteRole(String[] ids){
		return systemService.deleteRole(ids);
	}
	
	// 异步加载列表 
	@RequestMapping(value="getRoleList.json")
	@ResponseBody
	public Object getRoleList(SystemRole systemRole ,Page page) {
		return systemService.getRoleList(systemRole, page.getRowBounds());
	}
	
}
