package cn.zno.smse.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.zno.smse.common.util.StringUtils;
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
import cn.zno.smse.pojo.SystemRoleMenuLink;
import cn.zno.smse.pojo.SystemRoleMenuLinkExample;
import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.pojo.SystemUserRoleLink;
import cn.zno.smse.pojo.SystemUserRoleLinkExample;
import cn.zno.smse.service.SystemTx;


@Component
public class SystemTxImpl implements SystemTx {
	
	/*
	 * @see http://docs.spring.io/spring/docs/4.2.0.RC1/spring-framework-reference/htmlsingle/#transaction-declarative-annotations
	 * 
	 * In proxy mode (which is the default), only external method calls coming
	 * in through the proxy are intercepted. This means that self-invocation, in
	 * effect, a method within the target object calling another method of the
	 * target object, will not lead to an actual transaction at runtime even if
	 * the invoked method is marked with @Transactional.
	 */
	
	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private SystemUserRoleLinkMapper systemUserRoleLinkMapper;
	@Autowired
	private SystemRoleMapper systemRoleMapper;
	@Autowired
	private SystemRoleMenuLinkMapper systemRoleMenuLinkMapper;
	@Autowired
	private SystemMenuMapper systemMenuMapper;
	@Autowired
	private SystemAccessPermissionMapper systemAccessPermissionMapper;
	
	/**
	 * 用户新增页面保存
	 * 事务
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserAddTransactional(SystemUser user) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String password = user.getPassword();
		if(StringUtils.isNotBlank(password)){
			user.setPassword(md5.encodePassword(password, null));
		}
		systemUserMapper.insertSelective(user);
		if (user.getRoleList() == null)
			return;
		for (SystemRole role : user.getRoleList()) {
			if (role == null || role.getId() == null)
				continue;
			SystemUserRoleLink userRoleLink = new SystemUserRoleLink();
			userRoleLink.setUserId(user.getId());
			userRoleLink.setRoleId(role.getId());
			systemUserRoleLinkMapper.insert(userRoleLink);
		}
	}
	
	/**
	 * 用户编辑页面保存
	 * 事务
	 * 
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserEditTransactional(SystemUser user) {
		systemUserMapper.updateByPrimaryKey(user);
		SystemUserRoleLinkExample userRoleLinkExample = new SystemUserRoleLinkExample();
		userRoleLinkExample.createCriteria().andUserIdEqualTo(user.getId());
		systemUserRoleLinkMapper.deleteByExample(userRoleLinkExample);
		if (user.getRoleList() == null)
			return;
		for (SystemRole role : user.getRoleList()) {
			if (role == null || role.getId() == null)
				continue;
			SystemUserRoleLink userRoleLink = new SystemUserRoleLink();
			userRoleLink.setUserId(user.getId());
			userRoleLink.setRoleId(role.getId());
			systemUserRoleLinkMapper.insert(userRoleLink);
		}
	}
	
	/**
	 * 删除菜单
	 * 事务
	 * */
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
	
	public void childrenId(String my, List<SystemMenu> menus,
			List<String> ids) {
		for (SystemMenu menu : menus) {
			String id = menu.getId();
			String pid = menu.getPid();
			my = StringUtils.isBlank(my) ? "null" : my;
			pid = StringUtils.isBlank(pid) ? "null" : pid;
			if (my.equals(pid)) {
				ids.add(id);
				childrenId(id, menus, ids);
			}
		}
	}
	
	/**
	 * 删除用户
	 * 事务
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUserTransactional(String[] ids){
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
	
	/**
	 * 删除角色
	 * 事务
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRoleTransactional(String[] ids){
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
	
	/**
	 * 保存菜单
	 * 事务
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveMenuTranscational(SystemMenu menu, String changes) {
		// 菜单
		if (menu.getId() == null || StringUtils.isBlank(menu.getId())) {
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
}
