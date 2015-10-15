<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit</title>
</head>
<body>

	<div class="easyui-panel" style="width: 300px; padding: 10px;">
		<form id="ff" action="system!saveUserEdit.html" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td colspan="2">
						<b>基本信息</b>
						<hr/>
					</td>
				</tr>
				<tr>
					<td><s:textfield name="user.name" label="用户名" maxlength="100" /></td>
				</tr>
				<tr>
					<td><s:textfield name="user.username" label="登录名"/></td>
				</tr>
				<tr>
					<td><s:textfield name="user.password" label="登录密码"/></td>
				</tr>
				<tr>
					<td><s:textfield name="user.mobile" label="手机号"/></td>
				</tr>
				<tr>
					<td><s:textfield name="user.email" label="电子邮箱"/></td>
				</tr>
				<tr align="center">
					<td colspan = 2>
						<hr/>
					</td>
				</tr>
				<tr>
					<td colspan = 2>
						<table width="100%">
							<tbody>
								<tr>
									<td colspan="2">
										<b>用户角色</b>
										<hr/>
									</td>
								</tr>
								<s:iterator value="systemRoleList" status="out">
									<s:if test="#out.first">
										<tr>
									</s:if>
									<td>
										<s:set name="f" value="false" />
										<s:iterator value="user.roleList" status="in">
											<s:if test="%{id == systemRoleList[#out.index].id}">
												<s:set name="f" value="true" />
											</s:if>
										</s:iterator>
										<s:if test="%{#f}">
											<input type="checkbox" name="user.roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" checked="checked" />
											<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
										</s:if>
										<s:else>
											<input type="checkbox" name="user.roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" />
											<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
										</s:else>
									</td>
									<s:if test="%{#out.count % 2 == 0 && !#out.last}">
										</tr>
										<tr>
									</s:if>
									<s:if test="#out.last">
										</tr>
									</s:if>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan = 2 align="right">
					<a class="easyui-linkbutton" iconCls="icon-back" href="#" onclick = "back()">返回列表</a>
					<a class="easyui-linkbutton" iconCls="icon-save" href="#" onclick="saveData()">保存数据</a></td>
				</tr>
			</table>
			<s:hidden name="user.id" />
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			// 设置表单 
			$('#ff').form({
				success : function(data) {
					$.messager.alert('Info', data, 'info');
				}
			});
		});

		function saveData() {
			$('#ff').submit();
		}
		function back(){
			window.parent.toTab('用户列表','system!initUserList.html');
		}
	</script>
</body>
</html>