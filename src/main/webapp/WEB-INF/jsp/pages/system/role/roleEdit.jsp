<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit</title>
</head>
<body>

	<div class="easyui-panel" style="width: 300px; padding: 10px;">
		<form id="ff">
			<table>
				<tr>
					<td colspan="2">
						<b>基本信息</b>
						<hr/>
					</td>
				</tr>
				<tr>
					<td>角色名称:</td><td><input type="text" name="name" value="${systemRole.name }" data-options="required:true,validType:['length[0,10]','notBlank']" class="easyui-textbox" /></td>
				</tr>
				<tr>
					<td>角色:</td><td><input type="text" name="role" value="${systemRole.role }" data-options="required:true,validType:['length[0,10]','notBlank']" class="easyui-textbox" /></td>
				</tr>
				<tr>
					<td colspan = 2 align="right">
					<a class="easyui-linkbutton" iconCls="icon-back" href="#" onclick="back()">返回列表</a>
					<a class="easyui-linkbutton" iconCls="icon-save" href="#" onclick="saveData()">保存数据</a></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${systemRole.id}"/>
		</form>
	</div>
	<script type="text/javascript">
	
		$(document).ready(function() { 
			$('#ff').ajaxForm({
				type:'POST',
				url:'${baseUrl}system/saveRoleEdit.json',
				success : function(data) {
					if(data.success != undefined){
						$.messager.alert('Info',data.success , 'info');
					}else if(data.error != undefined){
						$.messager.alert('Error',data.error , 'error');
					}else{
						$.messager.alert('Error',"未知错误" , 'error');
					}
				}
			});
		});
		
		function saveData() {
			$('#ff').submit();
		}
		function back(){
			window.parent.toTab('角色列表','${baseUrl}system/initRoleList.htm');
		}
	</script>
</body>
</html>