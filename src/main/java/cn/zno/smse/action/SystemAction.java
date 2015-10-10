package cn.zno.smse.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.service.SystemService;

public class SystemAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;
	private String flag = "null";

	@Autowired
	private SystemService systemService;
	
	private SystemMenu menu;
	private List<SystemAccessPermission> accessPermissionList; 

	public String initMain() {
		return "initMain";
	}

	public String getTreeNode() {
		jsonObject = systemService.getTreeNode();
		return JSON;
	}

	public String initMenu() {
		switch (flag) {
		case "C": {
			return "crudMenu";
		}
		case "R": {
			if(menu != null){
				menu = systemService.getMenu(menu);
			}
			return "crudMenu";
		}
		case "U": {
			return "crudMenu";
		}
		case "D": {
			return "crudMenu";
		}
		default: {
			return "initMenu";
		}
		}
	}

	public String saveMenu() {
		return "initMenu";
	}
	
	public String getAccessPermission(){
		jsonObject = systemService.getAccessPermission(menu);
		return JSON;
	}
	public String getRole(){
		jsonObject = systemService.getRole(menu);
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

	public List<SystemAccessPermission> getAccessPermissionList() {
		return accessPermissionList;
	}

	public void setAccessPermissionList(
			List<SystemAccessPermission> accessPermissionList) {
		this.accessPermissionList = accessPermissionList;
	}
	
}
