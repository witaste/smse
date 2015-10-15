<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录 - SMSE</title>
</head>
<body>
	<s:actionerror cssStyle="color: red"/>
	<s:actionmessage cssStyle="color: green"/>
	<div class="easyui-panel" style="width: 300px; padding: 10px;">
		<form id="ff" action="login!login.html" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td colspan="2">
						<b>登录</b>
						<hr/>
					</td>
				</tr>
				<tr>
					<td align="left">账号:</td>
					<td><input class="easyui-textbox" name="user.username" data-options="iconCls:'icon-man',iconWidth:38,prompt:'请输入'"></td>
				</tr>
				<tr>
					<td align="left">密码:</td>
					<td><input class="easyui-textbox" name="user.password" type="password" data-options="iconCls:'icon-lock',iconWidth:38"></td>
				</tr>
				<tr>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="login()">登录</a></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		function login() {
			$('#ff').submit();
		}
	</script>
</body>
</html>