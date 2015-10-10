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

	<div class="easyui-panel" title="数据修改"
		style="width: 300px; padding: 10px;">
		<form id="ff" action="person!saveEdit" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td><s:textfield name="person.name" label="姓名" maxlength="100" /></td>
				</tr>
				<tr>
					<td><s:textfield name="person.age" class="easyui-numberbox" label="年龄"/></td>
				</tr>
				<tr>
					<td><s:textfield name="person.birthday" label="出生时间" class="easyui-datetimebox">
							<s:param name="value">
								<s:date name="person.birthday" format="yyyy-MM-dd HH:mm:ss" />
							</s:param>
						</s:textfield>
					</td>
				</tr>
				<tr>
					<td>
						<s:select list="#{'1':'一代身份证','2':'二代身份证'}" name="person.type"  label="身份证类型"/>
					</td>
				</tr>
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-back"
						href="person!goList">返回列表</a></td>
					<td><a class="easyui-linkbutton" iconCls="icon-save" 
						href="#" onclick="saveData()">保存</a></td>
				</tr>
			</table>
			<s:hidden name="person.id" />
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
	</script>
</body>
</html>