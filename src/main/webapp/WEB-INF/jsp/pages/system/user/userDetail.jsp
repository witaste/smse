<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail</title>
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
				<td align="left">${user.name }</td>
			</tr>
			<tr>
				<td align="right">登录名：</td>
				<td align="left">${user.username }</td>
			</tr>
			<tr>
				<td align="right">登录密码：</td>
				<td align="left">${user.password }</td>
			</tr>
			<tr>
				<td align="right">手机号：</td>
				<td align="left">${user.mobile }</td>
			</tr>
			<tr>
				<td align="right">电子邮箱：</td>
				<td align="left">${user.email }</td>
			</tr>
			<tr align="center">
				<td colspan = 2>
					<hr/>
				</td>
			</tr>
			<tr>
				<td colspan = 2>
					<table>
						<tbody>
							<tr>
								<td colspan="2">
									<b>用户角色</b>
									<hr/>
								</td>
							</tr>
							<c:forEach items="${systemRoleList }" var="role" varStatus="out">
								<c:if test="${out.first }">
									<tr>
								</c:if>
								<td>
									<c:set var="f" value="false" />
									<c:forEach items="${user.roleList }" var="userRole">
										<c:if test="${userRole.id == role.id}">
											<c:set var="f" value="true" />
										</c:if>
									</c:forEach>
									<c:if test="${f }">
										<input type="checkbox" name="roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" disabled="disabled" checked="checked" />
										<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
									</c:if>
									<c:if test="${!f }">
										<input type="checkbox" name="roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}" disabled="disabled" />
										<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
									</c:if>
								</td>
								<c:if test="${out.count % 2 == 0 && !out.last}">
									</tr>
									<tr>
								</c:if>
								<c:if test="${out.last }">
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="#" onclick = "back()" class="easyui-linkbutton" iconCls="icon-back">返回列表</a></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	function back() {
		window.parent.toTab('用户列表', '${baseUrl}system/initUserList.htm');
	}
</script>
</html>