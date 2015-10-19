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
		<form id="ff" action="system!saveRoleEdit.html" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td colspan="2">
						<b>基本信息</b>
						<hr/>
					</td>
				</tr>
				<tr>
					<td><s:textfield name="systemRole.name" label="角色名称" maxlength="100" /></td>
				</tr>
				<tr>
					<td><s:textfield name="systemRole.role" label="角色"/></td>
				</tr>
				<tr>
					<td colspan = 2 align="right">
					<a class="easyui-linkbutton" iconCls="icon-back" href="#" onclick = "back()">返回列表</a>
					<a class="easyui-linkbutton" iconCls="icon-save" href="#" onclick="saveData()">保存数据</a></td>
				</tr>
			</table>
			<s:hidden name="systemRole.id" />
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
			window.parent.toTab('角色列表','system!initRoleList.html');
		}
	</script>
</body>
</html>