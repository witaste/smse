package cn.zno.smse.service;

import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemUser;


public interface SystemTx {

	/**
	 * 用户新增页面保存
	 * 事务
	 * */
	public void saveUserAddTransactional(SystemUser user);
	
	/**
	 * 用户编辑页面保存
	 * 事务
	 * */
	public void saveUserEditTransactional(SystemUser user);
	
	/**
	 * 删除用户
	 * 事务
	 * */
	public void deleteUserTransactional(String[] ids);
	
	/**
	 * 删除角色
	 * 事务
	 * */
	public void deleteRoleTransactional(String[] ids);
	
	/**
	 * 删除菜单
	 * 事务
	 * */
	public void deleteMenuTransactional(SystemMenu menu);
	/**
	 * 保存菜单信息 
	 * 事务
	 * */
	public void saveMenuTranscational(SystemMenu menu, String changes);

}
