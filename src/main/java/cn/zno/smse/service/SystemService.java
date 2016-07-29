package cn.zno.smse.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.RowBounds;

import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemUser;

public interface SystemService {
	public Map<String,Object> savePassword(String oldPassword ,String newPassword, String confirmPassword);
	public JSONArray getTreeNode();
	public SystemMenu getMenu(SystemMenu menu);
	public SystemMenu getMenuById(String menuId);
	public Map<String,Object> getAccessPermission(SystemMenu menu);
	public Map<String,Object> getRoleRecord(SystemMenu menu);
	public Map<String,Object> getRoleAll(SystemRole role, RowBounds rowBounds);
	public Map<String,Object> saveMenu(SystemMenu menu,String changes);
	public Map<String,Object> deleteMenu(SystemMenu menu);
	
	public SystemUser getUserRecord(SystemUser user);
	public Map<String,Object> saveUserAdd(SystemUser user);
	public Map<String,Object> saveUserEdit(SystemUser user);
	public Map<String,Object> deleteUser(String[] ids);
	public Map<String,Object> getUserList(SystemUser user, RowBounds rowBounds);
	
	public SystemRole getRoleRecord(SystemRole role);
	public Map<String,Object> saveRoleAdd(SystemRole role);
	public Map<String,Object> saveRoleEdit(SystemRole role);
	public Map<String,Object> deleteRole(String[] ids);
	public Map<String,Object> getRoleList(SystemRole role, RowBounds rowBounds);
	public List<SystemRole> getRoleList();
	

	public Set<String> getRoleSet(String username);
	public Set<String> getPermissionSet(String username);
	public List<SystemAccessPermission> getPermissionAll();
	public SystemUser getUserByPassword(String username,String password);
	
}
