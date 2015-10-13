package cn.zno.smse.service.impl;

import java.util.ArrayList;
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
import cn.zno.smse.common.util.StringUtil;
import cn.zno.smse.dao.SystemAccessPermissionMapper;
import cn.zno.smse.dao.SystemMenuMapper;
import cn.zno.smse.dao.SystemRoleMapper;
import cn.zno.smse.dao.SystemRoleMenuLinkMapper;
import cn.zno.smse.dao.SystemUserMapper;
import cn.zno.smse.dao.SystemUserRoleLinkMapper;
import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemAccessPermissionExample;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemMenuExample;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemRoleExample;
import cn.zno.smse.pojo.SystemRoleMenuLink;
import cn.zno.smse.pojo.SystemRoleMenuLinkExample;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.pojo.SystemUserExample;
import cn.zno.smse.pojo.SystemUserRoleLink;
import cn.zno.smse.pojo.SystemUserRoleLinkExample;
import cn.zno.smse.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemMenuMapper systemMenuMapper;
	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private SystemAccessPermissionMapper systemAccessPermissionMapper;
	@Autowired
	private SystemRoleMapper systemRoleMapper;
	@Autowired
	private SystemRoleMenuLinkMapper systemRoleMenuLinkMapper;
	@Autowired
	private SystemUserRoleLinkMapper systemUserRoleLinkMapper;

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
				if (!pid.equals("null")) { // 如果pid为null字符串会报这个异常： net.sf.json.JSONException: Object is null
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
		cn.zno.smse.pojo.SystemRoleExample.Criteria criteria = roleExample
				.createCriteria();
		if (role != null && role.getName() != null) {
			criteria.andNameLike("%" + role.getName() + "%");
		}
		if (role != null && role.getRole() != null) {
			criteria.andRoleLike("%" + role.getRole() + "%");
		}
		int cnt = systemRoleMapper.countByExample(roleExample);
		List<SystemRole> roleList = systemRoleMapper.selectByExample(
				roleExample, rowBounds);
		dataMap.put("total", cnt);
		dataMap.put("rows", roleList);
		return dataMap;
	}

	@Override
	public Map<String, Object> saveMenu(SystemMenu menu, String changes) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			saveMenuTranscational(menu, changes);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (RuntimeException e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void saveMenuTranscational(SystemMenu menu, String changes) {
		// 菜单
		if (menu.getId() == null || menu.getId().trim().equals("")) {
			systemMenuMapper.insertSelective(menu);
		} else {
			systemMenuMapper.updateByPrimaryKeySelective(menu);
		}
		JSONObject jo_changes = JSONObject.fromObject(changes);
		JSONArray delZY = jo_changes.getJSONArray("delZY");
		JSONArray insZY = jo_changes.getJSONArray("insZY");
		JSONArray updZY = jo_changes.getJSONArray("updZY");
		JSONArray delJS = jo_changes.getJSONArray("delJS");
		JSONArray insJS = jo_changes.getJSONArray("insJS");
		// 资源
		for (int i = 0; i < delZY.size(); i++) {
			systemAccessPermissionMapper.deleteByPrimaryKey(delZY
					.getJSONObject(i).getString("id"));
		}
		for (int i = 0; i < insZY.size(); i++) {
			SystemAccessPermission accessPermission = (SystemAccessPermission) JSONObject
					.toBean(insZY.getJSONObject(i),
							SystemAccessPermission.class);
			accessPermission.setMenuId(menu.getId());
			systemAccessPermissionMapper.insertSelective(accessPermission);
		}
		for (int i = 0; i < updZY.size(); i++) {
			SystemAccessPermission accessPermission = (SystemAccessPermission) JSONObject
					.toBean(updZY.getJSONObject(i),
							SystemAccessPermission.class);
			systemAccessPermissionMapper
					.updateByPrimaryKeySelective(accessPermission);
		}
		// 角色
		for (int i = 0; i < delJS.size(); i++) {
			String roleId = delJS.getJSONObject(i).getString("id");
			SystemRoleMenuLinkExample roleMenuLinkExample = new SystemRoleMenuLinkExample();
			cn.zno.smse.pojo.SystemRoleMenuLinkExample.Criteria criteria = roleMenuLinkExample
					.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			criteria.andMenuIdEqualTo(menu.getId());
			systemRoleMenuLinkMapper.deleteByExample(roleMenuLinkExample);
		}
		for (int i = 0; i < insJS.size(); i++) {
			SystemRole role = (SystemRole) JSONObject.toBean(
					insJS.getJSONObject(i), SystemRole.class);
			SystemRoleMenuLink roleMenuLink = new SystemRoleMenuLink();
			roleMenuLink.setMenuId(menu.getId());
			roleMenuLink.setRoleId(role.getId());
			systemRoleMenuLinkMapper.insert(roleMenuLink);
		}
	}

	@Override
	public Map<String, Object> deleteMenu(SystemMenu menu) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			deleteMenuTransactional(menu);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (RuntimeException e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMenuTransactional(SystemMenu menu) {
		SystemMenuExample menuExample = new SystemMenuExample();
		menuExample.setOrderByClause("sort asc");
		List<SystemMenu> menus = systemMenuMapper.selectByExample(menuExample);
		List<String> ids = new ArrayList<String>();
		ids.add(menu.getId()); // 添加本节点ID
		childrenId(menu.getId(), menus, ids); // 添加子节点ID 
		for (String id : ids) {
			systemMenuMapper.deleteByPrimaryKey(id);
			SystemRoleMenuLinkExample roleMenuLinkExample = new SystemRoleMenuLinkExample();
			cn.zno.smse.pojo.SystemRoleMenuLinkExample.Criteria criteria = roleMenuLinkExample
					.createCriteria();
			criteria.andMenuIdEqualTo(id);
			systemRoleMenuLinkMapper.deleteByExample(roleMenuLinkExample);
			SystemAccessPermissionExample apExample = new SystemAccessPermissionExample();
			cn.zno.smse.pojo.SystemAccessPermissionExample.Criteria criteriaAcc = apExample
					.createCriteria();
			criteriaAcc.andMenuIdEqualTo(menu.getId());
			systemAccessPermissionMapper.deleteByExample(apExample);
		}
	}

	private void childrenId(String my, List<SystemMenu> menus,
			List<String> ids) {
		for (SystemMenu menu : menus) {
			String id = menu.getId();
			String pid = menu.getPid();
			my = my == null ? "null" : my;
			pid = pid == null ? "null" : pid;
			if (my.equals(pid)) {
				ids.add(id);
				childrenId(id, menus, ids);
			}
		}
	}

	@Override
	public SystemUser getUserRecord(SystemUser user) {
		user = systemUserMapper.selectByPrimaryKey(user.getId());
		List<SystemRole> roleList = systemRoleMapper.selectByUserId(user.getId());
		user.setRoleList(roleList);
		return user;
	}

	// 用户管理
	private String checkUser(SystemUser user){
		String errorMsg = null;
		// 校验start
		if(user==null){
			errorMsg = "数据不存在！";
		}else if(StringUtil.isBlank(user.getName())){
			errorMsg = "请填写用户名！";
		}else if(StringUtil.isBlank(user.getUsername())){
			errorMsg = "请填写登录用户名！";
		}else if(StringUtil.isBlank(user.getPassword())){
			errorMsg = "请填写登录密码！";
		}else if(StringUtil.isBlank(user.getMobile())){
			errorMsg = "请填写手机号！";
		}else if(StringUtil.isBlank(user.getEmail())){
			errorMsg = "请填写邮箱！";
		}else if(StringUtil.isBlank(user.getId())){
			SystemUserExample userExample = new SystemUserExample();
			cn.zno.smse.pojo.SystemUserExample.Criteria criteria = userExample.createCriteria();
			criteria.andNameEqualTo(user.getName());
			int cnt = systemUserMapper.countByExample(userExample);
			if(cnt > 0 ){
				errorMsg = "用户名已存在！";
			}
		}
		return errorMsg;
	}
	@Override
	public Map<String, Object> saveUserAdd(SystemUser user) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String errorMsg = checkUser(user);
		if(errorMsg != null){
			resultMap.put(Constants.ERROR, errorMsg);
			return resultMap;
		}
		try{
			saveUserAddTransactional(user);
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_INSERT);
		}catch(RuntimeException e){
			logger.error(e.getMessage(), e);
			resultMap.put(Constants.ERROR, Constants.ERROR_INSERT);
		}
		return resultMap;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void saveUserAddTransactional(SystemUser user) {
		systemUserMapper.insertSelective(user);
		if (user.getRoleList() == null)
			return;
		for (SystemRole role : user.getRoleList()) {
			if (role == null)
				continue;
			SystemUserRoleLink userRoleLink = new SystemUserRoleLink();
			userRoleLink.setUserId(user.getId());
			userRoleLink.setRoleId(role.getId());
			systemUserRoleLinkMapper.insert(userRoleLink);
		}
	}

	@Override
	public Map<String, Object> saveUserEdit(SystemUser user) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String errorMsg = checkUser(user);
		if(errorMsg != null){
			resultMap.put(Constants.ERROR, errorMsg);
			return resultMap;
		}
		try{
			saveUserEditTransactional(user);
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_UPDATE);
		}catch(RuntimeException e){
			logger.error(e.getMessage(), e);
			resultMap.put(Constants.ERROR, Constants.ERROR_UPDATE);
		}
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void saveUserEditTransactional(SystemUser user) {
		systemUserMapper.updateByPrimaryKey(user);
		SystemUserRoleLinkExample userRoleLinkExample = new SystemUserRoleLinkExample();
		userRoleLinkExample.createCriteria().andUserIdEqualTo(user.getId());
		systemUserRoleLinkMapper.deleteByExample(userRoleLinkExample);
		if (user.getRoleList() == null)
			return;
		for (SystemRole role : user.getRoleList()) {
			if (role == null)
				continue;
			SystemUserRoleLink userRoleLink = new SystemUserRoleLink();
			userRoleLink.setUserId(user.getId());
			userRoleLink.setRoleId(role.getId());
			systemUserRoleLinkMapper.insert(userRoleLink);
		}
	}
	
	@Override
	public Map<String, Object> deleteUser(String[] ids) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			deleteUserTransactional(ids);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (RuntimeException e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private void deleteUserTransactional(String[] ids){
		for(int i=0;i<ids.length;i++){
			// 第一步：删除用户
			systemUserMapper.deleteByPrimaryKey(ids[i]);
			// 第二部：删除用户角色绑定关系
			SystemUserRoleLinkExample userRoleLinkExample = new SystemUserRoleLinkExample();
			cn.zno.smse.pojo.SystemUserRoleLinkExample.Criteria criteria = userRoleLinkExample.createCriteria();
			criteria.andUserIdEqualTo(ids[i]);
			systemUserRoleLinkMapper.deleteByExample(userRoleLinkExample);
		}
	}

	@Override
	public Map<String, Object> getUserList(SystemUser user, RowBounds rowBounds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SystemUserExample userExample = new SystemUserExample();
		if(user != null){
			cn.zno.smse.pojo.SystemUserExample.Criteria criteria = userExample.createCriteria();
			if(!StringUtil.isBlank(user.getName())){
				criteria.andNameLike("%"+user.getName()+"%");
			}
		}
		int cnt = systemUserMapper.countByExample(userExample);
		List<SystemUser> list = systemUserMapper.selectByExample(userExample,
				rowBounds);
		dataMap.put("total", cnt);
		dataMap.put("rows", list);
		return dataMap;
	}
	
	@Override
	public List<SystemRole> getRoleList() {
		SystemRoleExample roleExample = new SystemRoleExample();
		return systemRoleMapper.selectByExample(roleExample);
	}

	// 角色 
	
	@Override
	public SystemRole getRoleRecord(SystemRole role) {
		return systemRoleMapper.selectByPrimaryKey(role.getId());
	}

	private String checkRole(SystemRole role){
		
		String errorMsg = null;
		// 校验start
		if(role==null){
			errorMsg = "数据不存在！";
		}else if(StringUtil.isBlank(role.getName())){
			errorMsg = "请填写角色名称！";
		}else if(StringUtil.isBlank(role.getRole())){
			errorMsg = "请填写角色！";
		}else if(StringUtil.isBlank(role.getId())){
			SystemRoleExample roleExample = new SystemRoleExample();
			cn.zno.smse.pojo.SystemRoleExample.Criteria criteria = roleExample.createCriteria();
			criteria.andNameEqualTo(role.getName());
			int cnt = systemRoleMapper.countByExample(roleExample);
			if(cnt > 0 ){
				errorMsg = "角色名称已存在！";
			}else{
				roleExample = new SystemRoleExample();
				criteria = roleExample.createCriteria();
				criteria.andRoleEqualTo(role.getRole());
				cnt = systemRoleMapper.countByExample(roleExample);
				if(cnt > 0){
					errorMsg = "角色已存在！";
				}
			}
		}
		return errorMsg;
	}
	@Override
	public Map<String, Object> saveRoleAdd(SystemRole role) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String errorMsg = checkRole(role);
		if(errorMsg != null){
			resultMap.put(Constants.ERROR, errorMsg);
			return resultMap;
		}
		int result = systemRoleMapper.insertSelective(role);
		if(result==1){
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_INSERT);
		}else{
			resultMap.put(Constants.ERROR, Constants.ERROR_INSERT);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> saveRoleEdit(SystemRole role) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String errorMsg = checkRole(role);
		if(errorMsg != null){
			resultMap.put(Constants.ERROR, errorMsg);
			return resultMap;
		}
		int result = systemRoleMapper.updateByPrimaryKey(role);
		if(result==1){
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_UPDATE);
		}else{
			resultMap.put(Constants.ERROR, Constants.ERROR_UPDATE);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getRoleList(SystemRole role, RowBounds rowBounds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SystemRoleExample roleExample = new SystemRoleExample();
		if(role != null){
			cn.zno.smse.pojo.SystemRoleExample.Criteria criteria = roleExample.createCriteria();
			if(!StringUtil.isBlank(role.getName())){
				criteria.andNameLike("%"+role.getName()+"%");
			}
		}
		int cnt = systemRoleMapper.countByExample(roleExample);
		List<SystemRole> list = systemRoleMapper.selectByExample(roleExample,
				rowBounds);
		dataMap.put("total", cnt);
		dataMap.put("rows", list);
		return dataMap;
	}

	@Override
	public Map<String, Object> deleteRole(String[] ids) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			deleteRoleTransactional(ids);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (RuntimeException e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private void deleteRoleTransactional(String[] ids){
		for(int i=0;i<ids.length;i++){
			// 第一步：删除角色
			systemRoleMapper.deleteByPrimaryKey(ids[i]);
			// 第二步：删除用户角色绑定关系
			SystemUserRoleLinkExample userRoleLinkExample = new SystemUserRoleLinkExample();
			cn.zno.smse.pojo.SystemUserRoleLinkExample.Criteria c1 = userRoleLinkExample.createCriteria();
			c1.andRoleIdEqualTo(ids[i]);
			systemUserRoleLinkMapper.deleteByExample(userRoleLinkExample);
			// 第三步：删除角色菜单绑定关系
			SystemRoleMenuLinkExample roleMenuLinkExample = new SystemRoleMenuLinkExample();
			cn.zno.smse.pojo.SystemRoleMenuLinkExample.Criteria c2 = roleMenuLinkExample.createCriteria();
			c2.andRoleIdEqualTo(ids[i]);
			systemRoleMenuLinkMapper.deleteByExample(roleMenuLinkExample);
		}
	}


}
