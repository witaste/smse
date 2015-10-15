<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List</title>
<%@ include file="/jsp/common/include/easyui.jsp"%>
</head>
<body>
	<fieldset class="fieldset-self">
		<legend>查询条件</legend>
		<table align="center">
			<tr>
				<td>用户名：</td>
				<td><input class="easyui-textbox" name="user.name" maxlength="100"></td>
			</tr>
			<tr>
				<td colspan =2  align="right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">检索</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteData()">删除</a>
					
				</td>
			</tr>
		</table>
	</fieldset>

	<br />
	<span id="msg"></span>
	<s:actionmessage cssStyle="color: green;" />
	<s:actionerror cssStyle="color: red;" />
	<table id="dg" class="easyui-datagrid" style="width: 100%;"
		data-options="multiSort:true,
				rownumbers:true,
				remoteSort:false,
				pagination:true,
				url:'system!getUserList.html',
				method:'post',
				loadMsg:'数据加载中..&nbsp;&nbsp;&nbsp;&nbsp;'
				">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',halign:'center',sortable:true,width:'16%'">用户名</th>
				<th data-options="field:'username',halign:'center',sortable:true,width:'16%'">登录名</th>
				<th data-options="field:'password',halign:'center',sortable:true,width:'16%'">登录密码</th>
				<th data-options="field:'mobile',halign:'center',sortable:true,width:'16%'">手机号</th>
				<th data-options="field:'email',halign:'center',sortable:true,width:'16%'">电子邮箱</th>
				<th
					data-options="field:'id',halign:'center',align:'center',
						formatter:function(value,row,index){
							var del ='&lt;a href=# onclick=deleteData(&quot;'+value+'&quot;)>删除&lt;/a>';
							var detail ='&lt;a href=# onclick=retrieveData(&quot;'+value+'&quot;)>明细&lt;/a>';
							var edit ='&lt;a href=# onclick=editData(&quot;'+value+'&quot;)>编辑&lt;/a>';
							return detail +' '+ edit +' '+ del;
						},"
					width="15%">操作</th>
			</tr>
		</thead>
	</table>


	<script type="text/javascript">
		function deleteData(id){
			$.messager.confirm('确认','确定要删除选中的记录吗',function(r){
			    if (r){
			    	var param = "";
					if(id == undefined){
						var rows = $("#dg").datagrid("getSelections");
						for(k in rows){
							param += "&ids=" + rows[k].id;
						}
					}else{
						param += "&ids=" + id;
					}
					$.ajax({
					   type: "POST",
					   url: "system!deleteUser.html",
					   data: param,
					   success: function(msg){
						   // alert(JSON.stringify(msg));
						   if(msg.success != undefined){
							   $("#dg").datagrid("reload");
						   }
						   showMsg(msg);
						   setTimeout("closeMsg()",4000); 
					   }
					});
			    }//end if(r) 
			});
		}
		function retrieveData(id){
			window.parent.toTab('用户明细','system!initUserDetail.html?user.id=' + id);
		}
		function editData(id){
			window.parent.toTab('用户编辑','system!initUserEdit.html?user.id=' + id);
		}
		function showMsg(msg){
			if(msg.success != undefined){
				$("#msg").text(msg.success);
				$("#msg").css("color","green");
			}else if(msg.error != undefined){
				$("#msg").text(msg.error);
				$("#msg").css("color","red");
			}
		}
		function closeMsg(){
			$("#msg").text("");
		}
	</script>

</body>
</html>