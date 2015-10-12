package cn.zno.smse.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.service.SystemService;

public class SystemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;
	private String flag = "null";
	private String changes;

	@Autowired
	private SystemService systemService;
	
	private SystemMenu menu;
	private SystemRole systemRole;

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
		jsonObject = systemService.getRole(menu);
		return JSON;
	}
	// 异步加载全部角色信息
	public String getRoleAll(){
		jsonObject = systemService.getRoleAll(systemRole ,getRowBounds());
		return JSON;
	}

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

}
