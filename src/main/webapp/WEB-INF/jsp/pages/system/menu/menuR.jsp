<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menu</title>
<script type="text/javascript">
	$(document).ready(function(){
		// 资源表格 
		$("#ziyuan").datagrid({
			multiSort:true,
			rownumbers:true,
			remoteSort:false,
			pagination:false,
			toolbar: '#ziyuan_toolbar',
			url:'system!getAccessPermission.html',
			method:'post',
			loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;',
			queryParams: {
				"menu.id": $("#menuId").val()
			},
			onClickCell: editZY
		});
		// 角色表格 
		$("#juese").datagrid({
			multiSort:true,
			rownumbers:true,
			remoteSort:false,
			pagination:false,
			toolbar: '#juese_toolbar',
			url:'system!getRole.html',
			method:'post',
			loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;',
			queryParams: {
				"menu.id": $("#menuId").val()
			}
		});
		// 角色_弹窗 
		$('#juese_wd').window({
		    title: '选择角色',
		    closed: true,
		    cache: false,
		    modal: true
		});
		// 角色_弹窗_表格 
		$("#juese_wd_dg").datagrid({
			multiSort:true,
			rownumbers:true,
			remoteSort:false,
			pagination:true,
			singleSelect:false,
			url:'system!getRoleAll.html',
			method:'post',
			loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;'
		});
	});
	// 角色_弹窗_表格检索数据 
	function searchData() {
		var objs = $("#juese_wd_roleName,#juese_wd_roleRole").get();
		var param = {};
		for(key in objs){
			var obj = objs[key];
			var name = $(obj).attr("textboxname");
			var value = $(obj).textbox("getValue");
			if(value != ''){
				param[name] = value;
			}
		}
		$('#juese_wd_dg').datagrid('options').queryParams = param;
		$('#juese_wd_dg').datagrid('reload');
	}
</script>
</head>
<body>
	<div class="easyui-panel" title="菜单" style="width: 400px;">
		<form id ="menuForm" action="system!saveMenu.html" method="post" enctype="multipart/form-data">
			<table>
				<s:hidden id="menuId" name="menu.id" />
				<s:hidden id="menuPId" name="menu.pid" />
				<s:hidden name="menu.visible" value="1" />
				<s:hidden id="flag" name="flag" />
				<s:hidden id="changes" name="changes" />
				<s:textfield name="menu.name" label="菜单名称" />
				<s:textfield name="menu.url" label="菜单资源" />
				<s:textfield name="menu.sort" label="排序" class="easyui-numberbox" />
				<s:textfield name="menu.icon" label="菜单图标" />
			</table>
		</form>
	</div>
	<div style="height:2px;width: 400px;"></div>
	<table id="ziyuan" class="easyui-datagrid" style="width: 400px;height:auto">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true"></th>
				<th data-options="field:'name',halign:'center',sortable:true,editor:'textbox',width:'40%'">资源名称</th>
				<th data-options="field:'url',halign:'center',sortable:true,editor:'textbox',width:'50%'">资源url</th>
			</tr>
		</thead>
	</table>
	<div style="height:2px;;width: 400px;"></div>
	<table id="juese" class="easyui-datagrid" style="width: 400px;">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true"></th>
				<th data-options="field:'name',halign:'center',sortable:true,editor:'textbox',width:'40%'">角色名</th>
				<th data-options="field:'role',halign:'center',sortable:true,editor:'textbox',width:'50%'">角色</th>
			</tr>
		</thead>
	</table>
	<div style="margin-top:20px; width: 400px;text-align:center">
		<a class="easyui-linkbutton" href="#" onclick="back()">返回列表</a>
		<s:if test="flag != \"R\"">
			<a class="easyui-linkbutton"  href="#" onclick="saveData()">保存数据</a>
		</s:if>
	</div>
	<div id="ziyuan_toolbar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendZY()">添加资源</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeZY()">删除资源</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptZY()">提交</a>
	</div>
	<div id="juese_toolbar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="selectJS()">选择角色</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeJS()">删除角色</a>
	</div>
	<div id="juese_wd" class="easyui-window" style="width:600px;height:auto;padding:10px;">
			<fieldset class="fieldset-self">
			<legend>查询条件</legend>
			<table align="center">
				<tr>
					<td>角色名称：</td>
					<td><input class="easyui-textbox" id="juese_wd_roleName" name="systemRole.name" maxlength="200"></td>
					<td>角色：</td>
					<td><input class="easyui-textbox" id="juese_wd_roleRole" name="systemRole.role" maxlength="200"></td>
				</tr>
				<tr>
					<td colspan=4 align="right">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">检索</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="binding()">绑定角色</a>
					</td>
				</tr>
			</table>
		</fieldset>
		<table id="juese_wd_dg" class="easyui-datagrid" style="width: 100%;">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true"></th>
				<th data-options="field:'name',halign:'center',sortable:true,editor:'textbox',width:'40%'">角色名</th>
				<th data-options="field:'role',halign:'center',sortable:true,editor:'textbox',width:'50%'">角色</th>
			</tr>
		</thead>
		</table>
	</div>
<script type="text/javascript">
	// <----- 资源相关 start 
	function editZY(index, field){
		$('#ziyuan').datagrid('selectRow', index).datagrid('beginEdit', index);
	}
	function appendZY(){
		$('#ziyuan').datagrid('appendRow',{id:'',name:'',url:''});
		var editIndex = $('#ziyuan').datagrid('getRows').length-1;
		$('#ziyuan').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	}
	function removeZY(){
		var rows  = $('#ziyuan').datagrid('getSelections');
		if(rows.length == 0){
			$.messager.alert('警告','请选择要删除的记录');
		}else{
			$.messager.confirm('确认','确定要删除选中的记录吗',function(r){
			    if (r){
			    	for(k in rows){
			    		var index = $('#ziyuan').datagrid('getRowIndex', rows[k]);
			    		$('#ziyuan').datagrid('cancelEdit', index).datagrid('deleteRow', index);
			    	}
			    }
			});
		}
	}
	function acceptZY(){
		var rows = $('#ziyuan').datagrid('getRows');
		for(k in rows){
			var index = $('#ziyuan').datagrid('getRowIndex', rows[k]);
			$('#ziyuan').datagrid('unselectRow',index).datagrid('endEdit', index);
		}
	}
	// 资源相关end ---->
	
	// <------ 角色相关 start
	function appendJS(){
		$('#juese').datagrid('appendRow',{id:'',name:'',role:''});
		var editIndex = $('#juese').datagrid('getRows').length-1;
		$('#juese').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	}
	function selectJS(){
		$('#juese_wd').window('open');
		$('#juese_wd_dg').datagrid('reload');
	}
	function binding(){
		var rowsForBd = $("#juese_wd_dg").datagrid("getSelections");
		if(rowsForBd.length > 0){
			var ids = [];
			var rows = $('#juese').datagrid('getRows');
			for(k in rows){
				ids.push(rows[k].id);
			}
			for(k in rowsForBd){
				if(ids.indexOf(rowsForBd[k].id) == -1){
					$('#juese').datagrid('appendRow',rowsForBd[k]);
				}
			}
			$('#juese_wd').window('close');
		}else{
			$.messager.alert('警告','请选择要绑定的角色');
		}
		
	}
	function removeJS(){
		var rows  = $('#juese').datagrid('getSelections');
		if(rows.length == 0){
			$.messager.alert('警告','请选择要删除的记录');
		}else{
			$.messager.confirm('确认','确定要删除选中的记录吗',function(r){
			    if (r){
			    	for(k in rows){
			    		var index = $('#juese').datagrid('getRowIndex', rows[k]);
			    		$('#juese').datagrid('cancelEdit', index).datagrid('deleteRow', index);
			    	}
			    }
			});
		}
	}
	// 角色相关end ------->
	
	// <--------保存数据 start 
	function saveData(){
		acceptZY();
		var rowsZY = $('#ziyuan').datagrid('getRows');
		for(k in rowsZY){
			var row = rowsZY[k];
			var index = $('#ziyuan').datagrid('getRowIndex', row);
			if($.trim(row.name) == '' || $.trim(row.url) == ''){
				$('#ziyuan').datagrid('selectRow', index);
				$.messager.alert('警告','内容不完整，请重新编辑。【资源】行：'+(index+1));
				return false;
			}
		}
		var rowsJS = $('#juese').datagrid('getRows');
		for(k in rowsJS){
			var row = rowsJS[k];
			var index = $('#juese').datagrid('getRowIndex', row);
			if($.trim(row.name) == '' || $.trim(row.role) == ''){
				$('#juese').datagrid('selectRow', index);
				$.messager.alert('警告','内容不完整，请重新编辑。【角色】行：'+(index+1));
				return false;
			}
		}
		var delZY = $('#ziyuan').datagrid('getChanges','deleted');
		var insZY = $('#ziyuan').datagrid('getChanges','inserted');
		var updZY = $('#ziyuan').datagrid('getChanges','updated');
		var delJS = $('#juese').datagrid('getChanges','deleted');
		var insJS = $('#juese').datagrid('getChanges','inserted');

		var changes = new Object();
		changes['delZY'] = delZY;
		changes['insZY'] = insZY;
		changes['updZY'] = updZY;
		changes['delJS'] = delJS;
		changes['insJS'] = insJS;
		$("#changes").val(JSON.stringify(changes));
		
		$('#menuForm').form('submit', {
		    success:function(data){
		    	window.parent.location.href = "system!initMenu.html";
		    }
		});
		// 保存数据end ----------->
	}
	function back(){
		window.parent.location.href = "system!initMenu.html";
	}
</script>
</body>
</html>