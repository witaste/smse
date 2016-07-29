<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add</title>
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
					<td align="left">角色名称:</td>
					<td><input class="easyui-textbox" name="name" data-options="required:true,validType:['length[0,10]','notBlank']" class="easyui-textbox" ></td>
				</tr>
				<tr>
					<td align="left">角色:</td>
					<td><input class="easyui-textbox" name="role" data-options="required:true,validType:['length[0,10]','notBlank']" class="easyui-textbox" ></td>
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
	
		$(document).ready(function() { 
			$('#ff').ajaxForm({
				type:'POST',
				url:'${baseUrl}system/saveRoleAdd.json',
				beforeSubmit:function(arr, form, options){
					return $(form).form('enableValidation').form('validate');
				},
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