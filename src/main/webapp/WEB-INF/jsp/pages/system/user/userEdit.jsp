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
					<td align="left">用户名:</td>
					<td><input class="easyui-textbox" name="name" data-options="required:true,validType:['length[0,10]','notBlank']" value="${user.name }"></td>
				</tr>
				<tr>
					<td align="left">登录名:</td>
					<td><input class="easyui-textbox" name="username" data-options="iconCls:'icon-man',iconWidth:38,required:true,validType:['length[0,20]','notBlank']" value="${user.username }"></td>
				</tr>
				<tr>
					<td align="left">登录密码:</td>
					<td><input class="easyui-textbox" name="password" data-options="type:'password',iconCls:'icon-lock',iconWidth:38,required:true,validType:['length[0,20]','notBlank']" value="${user.password }"></td>
				</tr>
				<tr>
					<td align="left">手机号:</td>
					<td><input class="easyui-textbox" name="mobile" data-options="required:true,validType:['mobile']" value="${user.mobile }"></td>
				</tr>
				<tr>
					<td align="left">电子邮箱:</td>
					<td><input class="easyui-textbox" name="email" data-options="required:true,validType:['email']" value="${user.email }"></td>
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
											<input type="checkbox" name="roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}"  checked="checked" />
											<label for="roleList_${out.index}">${systemRoleList[out.index].name}</label>
										</c:if>
										<c:if test="${!f }">
											<input type="checkbox" name="roleList[${out.index}].id" value="${systemRoleList[out.index].id}" id="roleList_${out.index}"  />
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
					<td colspan = 2 align="right">
					<a class="easyui-linkbutton" iconCls="icon-back" href="#" onclick = "back()">返回列表</a>
					<a class="easyui-linkbutton" iconCls="icon-save" href="#" onclick="saveData()">保存数据</a></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${user.id }" />
		</form>
	</div>
	<script type="text/javascript">
	
		$(document).ready(function() { 
			$('#ff').ajaxForm({
				type:'POST',
				url:'${baseUrl}system/saveUserEdit.json',
				beforeSubmit:function(arr, form, options){
					return $(form).form('enableValidation').form('validate');
				},
				success : function(data) {
					if(data.error == undefined){
						$.messager.alert('Info',data.success , 'info');
					}else{
						$.messager.alert('Error',data.error , 'error');
					}
				}
			});
		});

		function saveData() {
			$('#ff').submit();
		}
		function back(){
			window.parent.toTab('用户列表','${baseUrl}system/initUserList.htm');
		}
	</script>
</body>
</html>