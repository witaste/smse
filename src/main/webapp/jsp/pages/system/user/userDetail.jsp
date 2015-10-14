<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="easyui-panel" style="width: 300px; padding: 10px;">
		<table>
			<tr>
				<td colspan="2">
					<b>基本信息</b>
					<hr/>
				</td>
			</tr>
			<tr>
				<td align="right">用户名：</td>
				<td align="left"><s:property value="user.name" /></td>
			</tr>
			<tr>
				<td align="right">登录名：</td>
				<td align="left"><s:property value="user.username" /></td>
			</tr>
			<tr>
				<td align="right">登录密码：</td>
				<td align="left"><s:property value="user.password" /></td>
			</tr>
			<tr>
				<td align="right">手机号：</td>
				<td align="left"><s:property value="user.mobile" /></td>
			</tr>
			<tr>
				<td align="right">电子邮箱：</td>
				<td align="left"><s:property value="user.email" /></td>
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
										<input type="checkbox" name="user.roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" disabled="disabled" checked="checked" />
										<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
									</s:if>
									<s:else>
										<input type="checkbox" name="user.roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" disabled="disabled" />
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
				<td></td>
				<td><a href="#" onclick = "back()" class="easyui-linkbutton"
					iconCls="icon-back">返回列表</a></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	function back() {
		window.parent.toTab('用户列表', 'system!initUserList.html');
	}
</script>
</html>