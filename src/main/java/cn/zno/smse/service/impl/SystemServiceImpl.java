package cn.zno.smse.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.zno.smse.common.constants.Constants;
import cn.zno.smse.common.constants.EasyUIConstants;
import cn.zno.smse.dao.SystemAccessPermissionMapper;
import cn.zno.smse.dao.SystemMenuMapper;
import cn.zno.smse.dao.SystemRoleMapper;
import cn.zno.smse.dao.SystemRoleMenuLinkMapper;
import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemAccessPermissionExample;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemMenuExample;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemRoleExample;
import cn.zno.smse.pojo.SystemRoleMenuLink;
import cn.zno.smse.pojo.SystemRoleMenuLinkExample;
import cn.zno.smse.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemMenuMapper systemMenuMapper;
	@Autowired
	private SystemAccessPermissionMapper systemAccessPermissionMapper;
	@Autowired
	private SystemRoleMapper systemRoleMapper;
	@Autowired
	private SystemRoleMenuLinkMapper systemRoleMenuLinkMapper;
	
	
	@Override
	public JSONArray getTreeNode() {
		// 获取全部数据
		SystemMenuExample menuExample = new SystemMenuExample();
		menuExample.setOrderByClause("sort asc");
		List<SystemMenu> menus = systemMenuMapper.selectByExample(menuExample);
		// 绘树
		return children(null, menus);
	}

	public JSONArray children(String my, List<SystemMenu> menus) {
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
				JSONArray myChildChildren = children(id, menus);
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
				if (!pid.equals("null")) { //如果pid为null字符串会报这个异常： net.sf.json.JSONException: Object is null
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
	public Map<String, Object> getAccessPermission(SystemMenu menu) {
		SystemAccessPermissionExample apExample = new SystemAccessPermissionExample();
		cn.zno.smse.pojo.SystemAccessPermissionExample.Criteria criteria = apExample
				.createCriteria();
		criteria.andMenuIdEqualTo(menu.getId());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SystemAccessPermission> accessPermissionList = systemAccessPermissionMapper
				.selectByExample(apExample);
		dataMap.put("rows", accessPermissionList);
		return dataMap;
	}

	@Override
	public Map<String, Object> getRole(SystemMenu menu) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SystemRole> roleList = systemRoleMapper.selectByMenuId(menu
				.getId());
		dataMap.put("rows", roleList);
		return dataMap;
	}
	@Override
	public Map<String, Object> getRoleAll(SystemRole role, RowBounds rowBounds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SystemRoleExample roleExample = new SystemRoleExample();
		cn.zno.smse.pojo.SystemRoleExample.Criteria criteria = roleExample.createCriteria();
		if(role != null && role.getName() != null){
			criteria.andNameLike("%" + role.getName() + "%");
		}
		if(role != null && role.getRole() != null){
			criteria.andRoleLike("%" + role.getRole() + "%");
		}
		int cnt = systemRoleMapper.countByExample(roleExample);
		List<SystemRole> roleList = systemRoleMapper.selectByExample(roleExample,
				rowBounds);
		dataMap.put("total", cnt);
		dataMap.put("rows", roleList);
		return dataMap;
	}

	@Override
	public Map<String,Object> saveMenu(SystemMenu menu,String changes) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			saveMenuTranscational(menu,changes);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		}catch(RuntimeException e){
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void saveMenuTranscational(SystemMenu menu,String changes) {
		// 菜单 
		if(menu.getId() == null || menu.getId().trim().equals("")){
			systemMenuMapper.insertSelective(menu);
		}else{
			systemMenuMapper.updateByPrimaryKeySelective(menu);
		}
		JSONObject jo_changes = JSONObject.fromObject(changes);
		JSONArray delZY = jo_changes.getJSONArray("delZY");
		JSONArray insZY = jo_changes.getJSONArray("insZY");
		JSONArray updZY = jo_changes.getJSONArray("updZY");
		JSONArray delJS = jo_changes.getJSONArray("delJS");
		JSONArray insJS = jo_changes.getJSONArray("insJS");
		// 资源
		for(int i = 0;i<delZY.size();i++){
			systemAccessPermissionMapper.deleteByPrimaryKey(delZY.getJSONObject(i).getString("id"));
		}
		for(int i = 0;i<insZY.size();i++){
			 SystemAccessPermission accessPermission = (SystemAccessPermission)JSONObject.toBean(insZY.getJSONObject(i), SystemAccessPermission.class);
			 accessPermission.setMenuId(menu.getId());
			 systemAccessPermissionMapper.insertSelective(accessPermission);
		}
		for(int i = 0;i<updZY.size();i++){
			 SystemAccessPermission accessPermission = (SystemAccessPermission)JSONObject.toBean(updZY.getJSONObject(i), SystemAccessPermission.class);
			 systemAccessPermissionMapper.updateByPrimaryKeySelective(accessPermission);
		}
		// 角色
		for(int i = 0;i<delJS.size();i++){
			String roleId = delJS.getJSONObject(i).getString("id");
			systemRoleMapper.deleteByPrimaryKey(roleId);
			SystemRoleMenuLinkExample roleMenuLinkExample = new SystemRoleMenuLinkExample();
			cn.zno.smse.pojo.SystemRoleMenuLinkExample.Criteria criteria = roleMenuLinkExample.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			criteria.andMenuIdEqualTo(menu.getId());
			systemRoleMenuLinkMapper.deleteByExample(roleMenuLinkExample);
		}
		for(int i = 0;i<insJS.size();i++){
			 SystemRole role = (SystemRole)JSONObject.toBean(insJS.getJSONObject(i), SystemRole.class);
			 SystemRoleMenuLink roleMenuLink = new SystemRoleMenuLink();
			 roleMenuLink.setMenuId(menu.getId());
			 roleMenuLink.setRoleId(role.getId());
			 systemRoleMenuLinkMapper.insert(roleMenuLink);
		}
	}
	
}
