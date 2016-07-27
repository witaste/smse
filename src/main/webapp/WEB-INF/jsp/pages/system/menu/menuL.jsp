<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menu</title>
<script type="text/javascript">
$(document).ready(function() {
	// 菜单 
	$('#tree').tree({
		url:'${baseUrl}system/getTreeNode.json',
		method:'get',
		animate:false,
		lines:true,
		onLoadSuccess:function(node,data){
			$("#tree").tree("expandAll");
		},
		onContextMenu : function(e, node) {
			e.preventDefault(); // 阻止默认行为 
			$('#tree').tree('select', node.target); // 选中
			$("#menu").menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
});

function detail(){
	var node = $('#tree').tree('getSelected');
	if(node){
		$("#treeDetail").attr("src","${baseUrl}system/menuOption.htm?flag=R&menuId="+node.id);
	}
}

function edit(){
	var node = $('#tree').tree('getSelected');
	if (node) {
		$("#treeDetail").attr("src","${baseUrl}system/menuOption.htm?flag=U&menuId="+node.id);
	}
}

function addSameLevel(){
	var node = $('#tree').tree('getSelected');
	if(node){
		var param = "?flag=ASL";
		if(node.attributes.pid != undefined){
			param += "&menuPId="+node.attributes.pid;
		}
		$("#treeDetail").attr("src","${baseUrl}system/menuOption.htm" + param);
	}
}

function addNextLevel(){
	var node = $('#tree').tree('getSelected');
	if(node){
		$("#treeDetail").attr("src","${baseUrl}system/menuOption.htm?flag=ANL&menuPId="+node.id);
	}
}

function del(){
	var node = $('#tree').tree('getSelected');
	if (node) {
		$.messager.confirm('确认','确定要删除选中的节点及其子节点吗',function(r){
		    if (r){
		    	$.get("${baseUrl}system/menuDelete.json?flag=D&id="+node.id, function(data){
		    		  window.parent.location.href = "${baseUrl}system/main.htm";
		    	});
		    }
		});
	}
}

</script>
</head>
<body>
	<table width="100%" style="height: 100%">
		<tr>
			<td width="200px" valign="top">
				<div style="border:1px solid #95B8E7; background: #E8F1FF; height: 500px; width: 160px; float: left;">
					<table>
						<caption>
							<span style="font-size: 20px;">菜单信息</span>
						</caption>
						<tbody>
							<tr>
								<td>
									<ul id="tree" class="easyui-tree"></ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</td>
			<td valign="top">
				<iframe id="treeDetail" frameborder="0" width="100%" height="550px;"></iframe>
			</td>
		</tr>
	</table>
	<div id="menu" class="easyui-menu" style="width: 120px;">
		<div onclick="detail()">菜单明细</div>
		<div onclick="edit()">菜单编辑</div>
		<div onclick="del()">菜单删除</div>
		<div onclick="addSameLevel()">增加同级菜单</div>
		<div onclick="addNextLevel()">增加下级菜单</div>
	</div>
</body>
</html>