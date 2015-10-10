<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add</title>
</head>
<body>

	<div class="easyui-panel" title="数据新增"
		style="width: 300px; padding: 10px;">
		<form id="ff" action="person!saveAdd" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td align="left">姓名:</td>
					<td><input class="easyui-textbox" name="person.name" maxlength="100"></td>
				</tr>
				<tr>
					<td>
						<s:radio label="性别" name="person.sex" list="#{'0':'女','1':'男'}"/>
					</td>
				</tr>
				<tr>
					<td align="left">年龄:</td>
					<td><input class="easyui-numberbox" name="person.age"></td>
				</tr>
				<tr>
					<td align="left">出生时间:</td>
					<td><input class="easyui-datetimebox" name="person.birthday"></td>
				</tr>
				<tr>
					<td align="left">身份证类型:</td>
					<td><select class="easyui-combobox" name="person.type">
							<option value="1">一代身份证</option>
							<option value="2">二代身份证</option>
						</select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-save"
						onclick="saveData()">保存</a></td>
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
	</script>
</body>
</html>