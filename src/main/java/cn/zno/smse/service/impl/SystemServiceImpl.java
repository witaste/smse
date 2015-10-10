package cn.zno.smse.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zno.smse.common.constants.EasyUIConstants;
import cn.zno.smse.dao.SystemAccessPermissionMapper;
import cn.zno.smse.dao.SystemMenuMapper;
import cn.zno.smse.dao.SystemRoleMapper;
import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemAccessPermissionExample;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemMenuExample;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SystemMenuMapper systemMenuMapper;
	@Autowired
	private SystemAccessPermissionMapper systemAccessPermissionMapper;
	@Autowired
	private SystemRoleMapper systemRoleMapper;

	@Override
	public JSONArray getTreeNode() {
		// 获取全部数据
		SystemMenuExample menuExample = new SystemMenuExample();
		menuExample.setOrderByClause("sort asc");
		List<SystemMenu> menus = systemMenuMapper.selectByExample(menuExample);
		// 绘树
		return myChildren(null, menus);
	}

	public JSONArray myChildren(String my, List<SystemMenu> menus) {
		JSONArray myChildren = new JSONArray();
		for (SystemMenu menu : menus) {
			String id = menu.getId();
			String pid = menu.getPid();
			my = my == null ? "null" : my;
			pid = pid == null ? "null" : pid;
			if (my.equals(pid)) {
				JSONObject myChild = new JSONObject();
				myChild.put(EasyUIConstants.ID, id);
				myChild.put(EasyUIConstants.TEXT, menu.getName());
				myChild.put(EasyUIConstants.CHECKED, false);
				JSONArray myChildChildren = myChildren(id, menus);
				boolean isLeaf = true;
				if (myChildChildren.size() > 0) {
					isLeaf = false;
					myChild.put(EasyUIConstants.STATE, "closed");
					myChild.put(EasyUIConstants.CHILDREN, myChildChildren);
				}
				JSONObject attributes = new JSONObject();
				String url = menu.getUrl();
				url = (url == null || url == "#") ? "javascript:void(0)" : url;
				attributes.put("url", url);
				String icon = menu.getIcon();
				if (icon != null) {
					attributes.put("icon", icon);
				}
				attributes.put("show", menu.getShow());
				if (!pid.equals("null")) {
					attributes.put("pid", pid);
				}
				attributes.put("isLeaf", isLeaf);
				myChild.put(EasyUIConstants.ATTRIBUTES, attributes);
				myChildren.add(myChild);
			}
		}
		return myChildren;
	}

	@Override
	public SystemMenu getMenu(SystemMenu menu) {
		if (menu == null || menu.getId() == null)
			return null;
		return systemMenuMapper.selectByPrimaryKey(menu.getId());
	}

	@Override
	public Map<String,Object> getAccessPermission(SystemMenu menu) {
		SystemAccessPermissionExample apExample = new SystemAccessPermissionExample();
		cn.zno.smse.pojo.SystemAccessPermissionExample.Criteria criteria = apExample.createCriteria();
		criteria.andMenuIdEqualTo(menu.getId());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SystemAccessPermission> accessPermissionList = systemAccessPermissionMapper.selectByExample(apExample);
		dataMap.put("rows", accessPermissionList);
		return dataMap;
	}

	@Override
	public Map<String, Object> getRole(SystemMenu menu) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SystemRole> roleList = systemRoleMapper.selectByMenuId(menu.getId());
		dataMap.put("rows", roleList);
		return dataMap;
	}
}
