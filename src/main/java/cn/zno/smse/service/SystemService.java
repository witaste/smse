package cn.zno.smse.service;

import java.util.Map;

import net.sf.json.JSONArray;
import cn.zno.smse.pojo.SystemMenu;

public interface SystemService {
	public JSONArray getTreeNode();
	public SystemMenu getMenu(SystemMenu menu);
	public Map<String,Object> getAccessPermission(SystemMenu menu);
	public Map<String,Object> getRole(SystemMenu menu);
	

}
