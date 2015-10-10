<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/jsp/common/include/easyui.jsp"%>
</head>
<body>
	<fieldset class="fieldset-self">
		<legend>查询条件</legend>
		<table align="center">
			<tr>
				<td>姓名：</td>
				<td><input class="easyui-textbox" name="person.name" maxlength="100"></td>
				<td>性别：</td>
				<td><select class="easyui-combobox" name="person.sex" data-options="editable:false,panelHeight:'auto'">
						<option value="">--全部--</option>
						<option value="0">女</option>
						<option value="1">男</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td><input class="easyui-numberbox" name="person.age"></td>
				<td>出生时间：</td>
				<td><input class="easyui-datetimebox" name="person.start"> - <input class="easyui-datebox" name="person.end"></td>
			</tr>
			<tr>
				<td>身份证类型：</td>
				<td><select class="easyui-combobox" name="person.type" data-options="editable:false,panelHeight:'auto'">
						<option value="">--全部--</option>
						<option value="1">一代身份证</option>
						<option value="2">二代身份证</option>
					</select>
				</td>
				<td
				 align="right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">检索</a></td>
			</tr>
		</table>
	</fieldset>

	<br />
	<s:actionmessage cssStyle="color: green;" />
	<s:actionerror cssStyle="color: red;" />
	<table id="dg" class="easyui-datagrid" style="width: 100%;"
		data-options="multiSort:true,
				rownumbers:true,
				remoteSort:false,
				pagination:true,
				url:'person!getList',
				method:'post',
				loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;'
				">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',halign:'center',sortable:true">姓名</th>
				<th data-options="field:'sex',halign:'center',sortable:true,formatter:
				function(value,row){
						var s;
						if(value==0){
							s = '女';
						}else if(value==1){
							s = '男';
						}
						return s;
					}">性别</th>
				<th data-options="field:'age',halign:'center',sortable:true">年龄</th>
				<th data-options="field:'birthday',halign:'center',sortable:true,formatter:
					function(value,row){
					 return (value+'').replace('T',' ');
					}
				">出生时间</th>
				<th data-options="field:'type',halign:'center',sortable:true,formatter:
					function(value,row){
						var s;
						if(value==1){
							s = '一代身份证';
						}else if(value==2){
							s = '二代身份证';
						}
						return s;
					}
				">身份证类型</th>
				<th
					data-options="field:'id',halign:'center',align:'center',
						formatter:function(value,row,index){
							var del ='&lt;a href=# onclick=deleteSingle(&quot;person!deleteSingle?person.id='+value+'&quot;,'+index+')>删除&lt;/a>';
							var goDetail ='&lt;a href=&quot;person!goDetail?person.id='+value+'&quot;>明细&lt;/a>';
							var goEdit ='&lt;a href=&quot;person!goEdit?person.id='+value+'&quot;>编辑&lt;/a>';
							return del +' '+ goDetail +' '+ goEdit;
						},"
					width="15%">操作</th>
			</tr>
		</thead>
	</table>


	<script type="text/javascript">
		
	</script>

</body>
</html>