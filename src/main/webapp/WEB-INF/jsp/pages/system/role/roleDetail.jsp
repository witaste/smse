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
				<td align="right">角色名称：</td>
				<td align="left">${systemRole.name }</td>
			</tr>
			<tr>
				<td align="right">角色：</td>
				<td align="left">${systemRole.role }</td>
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
		window.parent.toTab('角色列表', '${baseUrl}system/initRoleList.htm');
	}
</script>
</html>