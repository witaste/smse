package cn.zno.smse.service;

import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import net.sf.json.JSONArray;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemRole;

public interface SystemService {
	public JSONArray getTreeNode();
	public SystemMenu getMenu(SystemMenu menu);
	public Map<String,Object> getAccessPermission(SystemMenu menu);
	public Map<String,Object> getRole(SystemMenu menu);
	public Map<String,Object> getRoleAll(SystemRole role, RowBounds rowBounds);
	public Map<String,Object> saveMenu(SystemMenu menu,String changes);
	

}
