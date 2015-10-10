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

	<div class="easyui-panel" title="数据明细"
		style="width: 300px; padding: 10px;">
		<table>
			<tr>
				<td align="right">姓名：</td>
				<td align="left"><s:property value="person.name" /></td>
			</tr>
			<tr>
				<td align="right">年龄：</td>
				<td align="left"><s:property value="person.age" /></td>
			</tr>
			<tr>
				<td align="right">出生时间：</td>
				<td align="left"><s:date name="person.birthday" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td align="right">身份证类型：</td>
				<td align="left">
					<s:if test="person.type == 1">一代身份证</s:if>
					<s:elseif test="person.type == 2">二代身份证</s:elseif>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="person!goList"
					class="easyui-linkbutton" iconCls="icon-back">返回列表</a></td>
			</tr>
		</table>
	</div>
</body>
</html>