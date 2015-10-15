package cn.zno.smse.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemRoleExample;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.pojo.SystemUserExample;
import cn.zno.smse.service.SystemService;
import cn.zno.smse.service.SystemTx;

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
	@Autowired
	private SystemTx systemTx;

	//*====================================================
	//*===================== 菜单管理 start ========== 
	//*===============================================
	@Override
	public JSONArray getTreeNode() {
		// 获取[当前用户]全部菜单信息
		SystemUser user = (SystemUser)SecurityUtils.getSubject().getPrincipal();
		List<SystemMenu> menus = systemMenuMapper.selectForUser(user.getUsername());
		// 绘树
		return children(null, menus);
	}

	/**
	 * 递归绘树
	 * */
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

	/**
	 * 获取当前菜单对应的所有角色
	 * */
	@Override
	public Map<String, Object> getRoleRecord(SystemMenu menu) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<SystemRole> roleList = systemRoleMapper.selectByMenuId(menu
				.getId());
		dataMap.put("rows", roleList);
		return dataMap;
	}

	/**
	 * 获取全部角色
	 * 按检索条件检索
	 * */
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
			systemTx.saveMenuTranscational(menu, changes);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (Exception e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}
	
	@Override
	public Map<String, Object> deleteMenu(SystemMenu menu) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			systemTx.deleteMenuTransactional(menu);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (Exception e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	/**
	 * 获取用户信息 
	 * 包含该用户所有角色
	 * */
	@Override
	public SystemUser getUserRecord(SystemUser user) {
		user = systemUserMapper.selectByPrimaryKey(user.getId());
		List<SystemRole> roleList = systemRoleMapper.selectByUserId(user.getId());
		user.setRoleList(roleList);
		return user;
	}

	//*====================================================
	//*===================== 用户管理 start ========== 
	//*===============================================
	/**
	 * 检验用户信息 
	 * */
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
			}else{
				userExample = new SystemUserExample();
				criteria = userExample.createCriteria();
				criteria.andUsernameEqualTo(user.getUsername());
				cnt = systemUserMapper.countByExample(userExample);
				if(cnt > 0){
					errorMsg = "登录名已存在！";
				}
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
			systemTx.saveUserAddTransactional(user);
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_INSERT);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMap.put(Constants.ERROR, Constants.ERROR_INSERT);
		}
		return resultMap;
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
			systemTx.saveUserEditTransactional(user);
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_UPDATE);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMap.put(Constants.ERROR, Constants.ERROR_UPDATE);
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> deleteUser(String[] ids) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			systemTx.deleteUserTransactional(ids);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (Exception e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	@Override
	public Map<String, Object> getUserList(SystemUser user, RowBounds rowBounds) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SystemUserExample userExample = new SystemUserExample();
		if(user != null){
			cn.zno.smse.pojo.SystemUserExample.Criteria criteria = userExample.createCriteria();
			if(StringUtil.isNotBlank(user.getName())){
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

	//*====================================================
	//*===================== 角色管理 start ========== 
	//*===============================================
	
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
			if(StringUtil.isNotBlank(role.getName())){
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
			systemTx.deleteRoleTransactional(ids);
			dataMap.put(Constants.SUCCESS, Constants.SUCCESS_MSG);
		} catch (Exception e) {
			dataMap.put(Constants.ERROR, Constants.ERROR_MSG);
			logger.error(e.getMessage(), e);
		}
		return dataMap;
	}

	
	//*====================================================
	//*===================== 权限控制 start ========== 
	//*===============================================
	
	@Override
	public Set<String> getRoleSet(String username) {
		List<SystemRole> roleList = systemRoleMapper.selectByUsername(username);
		Set<String> roles = new HashSet<String>();
		for(SystemRole role : roleList){
			if(role == null)
				continue;
			roles.add(role.getRole());
		}
		roles.add("admin");
		return roles;
	}

	@Override
	public Set<String> getPermissionSet(String username) {
		List<SystemAccessPermission> list = systemAccessPermissionMapper.selectByUsername(username);
		Set<String> permissions = new HashSet<String>();
		for (SystemAccessPermission permission : list) {
			if (permission == null)
				continue;
			permissions.add(permission.getUrl());
		}
		return permissions;
	}

	@Override
	public SystemUser getUserByPassword(String username , String password) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return systemUserMapper.selectByPassword(paramMap);
	}

	@Override
	public List<SystemAccessPermission> getPermissionAll() {
		return systemAccessPermissionMapper.selectByExample(new SystemAccessPermissionExample());
	}
}
