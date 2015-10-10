<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menu</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#ziyuan").datagrid({
			title:'资源',
			multiSort:true,
			rownumbers:true,
			remoteSort:false,
			pagination:false,
			url:'system!getAccessPermission',
			method:'post',
			loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;',
			queryParams: {
				"menu.id": $("#menuId").val()
			}
		});
		$("#juese").datagrid({
			title:'角色',
			multiSort:true,
			rownumbers:true,
			remoteSort:false,
			pagination:false,
			url:'system!getRole',
			method:'post',
			loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;',
			queryParams: {
				"menu.id": $("#menuId").val()
			}
		});
	});
</script>
</head>
<body>
	<div class="easyui-panel" title="菜单" style="width: 300px; padding: 10px;">
		<table>
			<s:hidden id="menuId" name="menu.id"></s:hidden>
			<s:textfield name="menu.name" label="菜单名称" />
			<s:textfield name="menu.url" label="菜单资源" />
			<s:textfield name="menu.sort" label="排序" class="easyui-numberbox" />
			<s:textfield name="menu.icon" label="菜单图标" />
		</table>
	</div>
	<table id="ziyuan" class="easyui-datagrid" style="width: 300px;">
		<thead>
			<tr>
				<th data-options="field:'name',halign:'center',sortable:true">资源名</th>
				<th data-options="field:'url',halign:'center',sortable:true">资源url</th>
			</tr>
		</thead>
	</table>
	<table id="juese" class="easyui-datagrid" style="width: 300px;">
		<thead>
			<tr>
				<th data-options="field:'name',halign:'center',sortable:true">角色名</th>
				<th data-options="field:'role',halign:'center',sortable:true">角色</th>
			</tr>
		</thead>
	</table>

</body>
</html>