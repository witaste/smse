package cn.zno.smse.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.zno.smse.common.constants.Constants;
import cn.zno.smse.common.constants.EasyUIConstants;
import cn.zno.smse.common.util.StringUtils;
import cn.zno.smse.dao.SystemAccessPermissionMapper;
import cn.zno.smse.dao.SystemMenuMapper;
import cn.zno.smse.dao.SystemRoleMapper;
import cn.zno.smse.dao.SystemUserMapper;
import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemAccessPermissionExample;
import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemMenuExample;
import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemRoleExample;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.pojo.SystemUserExample;
import cn.zno.smse.service.SystemService;
import cn.zno.smse.service.SystemTx;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	private SystemTx systemTx;

	//*====================================================
	//*===================== 菜单管理 start ========== 
	//*===============================================
	@Override
	public JSONArray getTreeNode() {
		List<SystemMenu> menus = null;
		SystemUser user = (SystemUser)SecurityUtils.getSubject().getPrincipal();
		// root 用户能查看全部菜单
		if(user.getUsername().equals("root")){
			SystemMenuExample menuExample = new SystemMenuExample();
			menuExample.setOrderByClause("sort asc");
			menus = systemMenuMapper.selectByExample(menuExample);
		}else{
			// 获取[当前用户]全部菜单信息
			menus = systemMenuMapper.selectForUser(user.getUsername());
		}
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
			my = StringUtils.isBlank(my) ? "null" : my;
			pid = StringUtils.isBlank(pid) ? "null" : pid;
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
				url = (StringUtils.isBlank(url) || url == "#") ? "" : url;
				attributes.put("url", url);
				String icon = menu.getIcon();
				if (icon != null) {
					attributes.put("icon", icon);
				}
				attributes.put("visible", menu.getVisible());
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
	public SystemMenu getMenuById(String menuId) {
		if (menuId == null)
			return null;
		return systemMenuMapper.selectByPrimaryKey(menuId);
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
		}else if(StringUtils.isBlank(user.getName())){
			errorMsg = "请填写用户名！";
		}else if(StringUtils.isBlank(user.getUsername())){
			errorMsg = "请填写登录用户名！";
		}else if(StringUtils.isBlank(user.getPassword())){
			errorMsg = "请填写登录密码！";
		}else if(StringUtils.isBlank(user.getMobile())){
			errorMsg = "请填写手机号！";
		}else if(StringUtils.isBlank(user.getEmail())){
			errorMsg = "请填写邮箱！";
		}else if(StringUtils.isBlank(user.getId())){
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
			if(StringUtils.isNotBlank(user.getName())){
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
		}else if(StringUtils.isBlank(role.getName())){
			errorMsg = "请填写角色名称！";
		}else if(StringUtils.isBlank(role.getRole())){
			errorMsg = "请填写角色！";
		}else if(StringUtils.isBlank(role.getId())){
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
			if(StringUtils.isNotBlank(role.getName())){
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
		if(username == null || password == null)
			return null;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return systemUserMapper.selectByPassword(paramMap);
	}

	@Override
	public List<SystemAccessPermission> getPermissionAll() {
		return systemAccessPermissionMapper.selectByExample(new SystemAccessPermissionExample());
	}

	@Override
	public Map<String, Object> savePassword(String oldPassword, String newPassword, String confirmPassword) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if(newPassword == null || oldPassword == null || confirmPassword == null){
			resultMap.put(Constants.ERROR, Constants.ERROR_INVALID_PARAMETER);
			return resultMap;
		}
		if(!newPassword.equals(confirmPassword)){
			resultMap.put(Constants.ERROR, "新密码不匹配");
			return resultMap;
		}
		
		SystemUser user = (SystemUser)SecurityUtils.getSubject().getPrincipal();
		
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		if(!md5PasswordEncoder.isPasswordValid(user.getPassword(), oldPassword, null)){
			resultMap.put(Constants.ERROR, "密码不正确");
			return resultMap;
		}
		SystemUser systemUser = new SystemUser();
		systemUser.setId(user.getId());
		systemUser.setPassword(md5PasswordEncoder.encodePassword(newPassword, null));
		systemUserMapper.updateByPrimaryKeySelective(systemUser);
		SecurityUtils.getSubject().logout();
		resultMap.put(Constants.SUCCESS, "密码修改成功，请重新登陆。");
		return resultMap;
	}
}
