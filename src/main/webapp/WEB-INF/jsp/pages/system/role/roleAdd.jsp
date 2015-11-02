<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add</title>
</head>
<body>

	<div class="easyui-panel" style="width: 300px; padding: 10px;">
		<form id="ff" action="system!saveRoleAdd.html" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td colspan="2">
						<b>基本信息</b>
						<hr/>
					</td>
				</tr>
				<tr>
					<td align="left">角色名称:</td>
					<td><input class="easyui-textbox" name="systemRole.name" maxlength="100"></td>
				</tr>
				<tr>
					<td align="left">角色:</td>
					<td><input class="easyui-textbox" name="systemRole.role" maxlength="100"></td>
				</tr>
				<tr>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-back"
						onclick="back()">返回列表</a></td>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-save"
						onclick="saveData()">保存数据</a></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$('#ff').form({
			success : function(data) {
				$.messager.alert('Info', data, 'info');
			}
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