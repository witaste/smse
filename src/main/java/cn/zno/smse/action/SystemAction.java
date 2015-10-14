package cn.zno.smse.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.service.SystemService;

public class SystemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;
	private String flag = "null";
	private String changes;
	private String[] ids;

	@Autowired
	private SystemService systemService;
	private SystemUser user;
	private SystemMenu menu;
	private SystemRole systemRole;
	// 全部角色信息（用户绑定角色时用）
	private List<SystemRole> systemRoleList;
	
	//*---------------------------------------
	//*----------- 菜单管理 ↓-------------------
	//*---------------------------------------

	// 跳转到 main.jsp
	public String initMain() {
		return "main";
	}

	// 异步加载tree
	public String getTreeNode() {
		jsonObject = systemService.getTreeNode();
		return JSON;
	}

	// 跳转到menu.jsp
	public String initMenu() {
		switch (flag) {
		case "R": {// retrieve
			menu = systemService.getMenu(menu);
			return "menuR";
		}
		case "U": {// update
			menu = systemService.getMenu(menu);
			return "menuR";
		}
		case "D": {// delete
			jsonObject = systemService.deleteMenu(menu);
			return JSON;
		}
		case "ASL": {// add same level
			return "menuR";
		}
		case "ANL": {// add next level
			return "menuR";
		}
		default: {
			return "menuL";
		}
		}
	}

	// 保存菜单信息 
	public String saveMenu() {
		jsonObject = systemService.saveMenu(menu,changes);
		return JSON;
	}
	
	// 异步加载资源信息 
	public String getAccessPermission(){
		jsonObject = systemService.getAccessPermission(menu);
		return JSON;
	}
	// 异步加载[该菜单所属]角色信息
	public String getRole(){
		jsonObject = systemService.getRoleRecord(menu);
		return JSON;
	}
	// 异步加载全部角色信息
	public String getRoleAll(){
		jsonObject = systemService.getRoleAll(systemRole ,getRowBounds());
		return JSON;
	}
	
	//*---------------------------------------
	//*----------- 用户管理 ↓-------------------
	//*---------------------------------------
	public String initUserAdd() {
		systemRoleList = systemService.getRoleList();
		return "userAdd";
	}

	public String initUserDetail() {
		systemRoleList = systemService.getRoleList();
		user = systemService.getUserRecord(user);
		return "userDetail";
	}

	public String initUserEdit() {
		systemRoleList = systemService.getRoleList();
		user = systemService.getUserRecord(user);
		return "userEdit";
	}

	public String initUserList() {
		return "userList";
	}

	public String saveUserAdd() {
		jsonObject = systemService.saveUserAdd(user);
		return JSON;
	}

	public String saveUserEdit() {
		jsonObject = systemService.saveUserEdit(user);
		return JSON;
	}
	public String deleteUser(){
		jsonObject = systemService.deleteUser(ids);
		return JSON;
	}

	// 异步加载列表 
	public String getUserList() {
		jsonObject = systemService.getUserList(user, getRowBounds());
		return JSON;
	}
	
	
	//*---------------------------------------
	//*----------- 角色管理 ↓-------------------
	//*---------------------------------------
	public String initRoleAdd() {
		return "roleAdd";
	}

	public String initRoleDetail() {
		systemRole = systemService.getRoleRecord(systemRole);
		return "roleDetail";
	}

	public String initRoleEdit() {
		systemRole = systemService.getRoleRecord(systemRole);
		return "roleEdit";
	}

	public String initRoleList() {
		return "roleList";
	}

	public String saveRoleAdd() {
		jsonObject = systemService.saveRoleAdd(systemRole);
		return JSON;
	}

	public String saveRoleEdit() {
		jsonObject = systemService.saveRoleEdit(systemRole);
		return JSON;
	}
	public String deleteRole(){
		jsonObject = systemService.deleteRole(ids);
		return JSON;
	}
	// 异步加载列表 
	public String getRoleList() {
		jsonObject = systemService.getRoleList(systemRole, getRowBounds());
		return JSON;
	}
	
	
	//--------------------------------
	//-------setter & getter---------
	//--------------------------------

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public SystemMenu getMenu() {
		return menu;
	}

	public void setMenu(SystemMenu menu) {
		this.menu = menu;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public SystemRole getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(SystemRole systemRole) {
		this.systemRole = systemRole;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}

	public List<SystemRole> getSystemRoleList() {
		return systemRoleList;
	}

	public void setSystemRoleList(List<SystemRole> systemRoleList) {
		this.systemRoleList = systemRoleList;
	}
}
