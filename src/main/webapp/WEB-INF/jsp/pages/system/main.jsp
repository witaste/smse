<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SMSE - help me help you </title>
<script type="text/javascript">

	$(document).ready(function() {
		$(".panel-header").css({
			"width" : "168px",
			"height" : "18px",
			"border-color" : "#000000"
		});
		$(".window-header").css({
			"width" : "100%",
		});
		$(".tabs-wrap").css({
			"background-color" : "#FFFFFF"
		});
		$(".tabs").css({
			"padding-left" : "0px"
		});
		
		// 菜单 
		$('#tree').tree({
			url:'${baseUrl}system/getTreeNode.json',
			method:'get',
			animate:false,
			lines:true,
			formatter:function(node){
				var id = node.id;
				var text = node.text;
				var url = node.attributes.url;
				var s = '';
				if(node.attributes.isLeaf){
					if(url){
						s = '<span style="cursor:pointer" onclick=\"return toTab(\''+text+'\',\'${baseUrl}'+url+'\')\">'+text+'</span>';
					}else{
						s = '<span style="cursor:pointer">'+text+'</span>';
					}
				}else if(node.attributes.pid == undefined){
					s = '<b><span style="font:italic bold 50px;cursor:pointer;">'+text+'</span></b>';
				}else{
					s = '<span>'+text+'</span>';
				}
				return s;
			},
			onClick: function(node){
				if(node.state == 'open'){// 关闭该节点时先关闭子节点    
					var children = $('#tree').tree('getChildren',node.target);
					for(key in children){
						$('#tree').tree('collapse',children[key].target);
					}
				}else{ //打开该节点时先关闭所有节点再展开到该节点  
					$('#tree').tree('collapseAll');
					$('#tree').tree('expandTo', node.target);
				}
				$('#tree').tree('toggle', node.target);// 关闭或展开该节点的子节点 
			},
			onLoadSuccess:function(node,data){
				$("span.tree-icon").hide();
				$("#tree a").css({"text-decoration":"none","color": "#000"});
			}
		});
		
		// tabs
		$('#tabs').tabs({
			fit:true,
			border:false,
			onContextMenu : function(e,title,index){
				e.preventDefault(); // 阻止默认行为 
				$("#menu").menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		
		// password
		$('#passwordForm').ajaxForm({
			type:'POST',
			url:'${baseUrl}system/savePassword.json',
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
		
		// init 关闭修改密码window 
		$('#mpw').window('close');
	});
	
	function toTab(text,url) {
		var pannelOptions = {
				title : text,
				content : '<iframe frameborder="0" width="100%" height="100%" src="'+ url +'" scrolling="auto"></iframe>',
				closable : true,
				loadingMessage : '加载中...'
			};
		
		if ($('#tabs').tabs('exists', text)) {
			$('#tabs').tabs('select', text);
			var currentTab = $('#tabs').tabs('getSelected');
			$('#tabs').tabs('update', {
				tab: currentTab,
				options: pannelOptions
			});		
		} else {
			$('#tabs').tabs('add', pannelOptions);
		}
		return false;
	}
	
	// 退出登录
	function logout() {
		$.messager.confirm('确认','确定要退出系统吗',function(r){
		    if (r){
		    	window.parent.location.href = '${baseUrl}logout';
		    }
		});
	}
	
	// 修改密码
	function modifyPassword(){
		$('#passwordForm').clearForm();
		$('#mpw').window('open');
	}
	function savePassword(){
		$('#passwordForm').submit();
	}
	
	function closeCurrent(){
		$(".tabs .tabs-selected .tabs-close").click();
	}
	function closeOthers(){
		$(".tabs li:not(.tabs-selected) .tabs-close").click();
		$(".tabs .tabs-close").parent().click();
	}
	function closeAll(){
		$(".tabs .tabs-close").click();
	}
</script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">

	<!-- 北域 -->
	<div data-options="region:'north',split:false,border:false" style="height: 94px; overflow-y: hidden;">
		<div style="background-image:url('${baseUrl}/images/main_north_bg.png');background-repeat:repeat-x;height:94px">
			<div style="position: absolute; top: 10px; left: 30px; z-index: 2;">
				<img src="${baseUrl}/images/main_north_logo.png"></img>
			</div>
			<div style='position: absolute; top: 0; right: 0px; z-index: 2;'>
				<div style="display: inline-block; width: auto; height: 30px; color: #FFF; margin-top: 0px;">
					<span style="padding-left: 5px; line-height: 30px; color: #ffffff; margin-top: 3px; display: inline-block">
						${user.name }
					</span>
					<span onclick="modifyPassword()" style="cursor: hand; color: #ffffff; cursor: pointer;">
						修改密码 
					</span>
					<span>|</span>
					<span onclick="logout()" style="cursor: hand; color: #ffffff; cursor: pointer; padding-right: 40px;">
						注销 
					</span>
				</div>
			</div>
		</div>
	</div>

	<!-- 西域 -->
	<div data-options="region:'west',title:'菜单导航',split:false,border:false,plain:true" style="width: 178px; background-color: #ffffff; position: relative; zoom: 1; overflow-y: auto; overflow-x: hidden; height: 562px; display: block;">
		<div class="easyui-panel" style="padding: 5px; border: 0px">
			<ul id="tree" class="easyui-tree"></ul>
		</div>
	</div>

	<!-- 中原 -->
	<div data-options="region:'center',iconCls:'icon-home',split:false,border:false">
		<div id="tabs" class="easyui-tabs">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div style="background: url(${baseUrl}/images/main_center_bg.gif) fixed center;width:100%;height:100%"></div>
			</div>
		</div>
	</div>
	
	<!-- 右键弹出菜单 -->
	<div id="menu" class="easyui-menu" style="width: 120px;">
		<div onclick="closeCurrent()">Close</div>
		<div onclick="closeOthers()">Close Others</div>
		<div onclick="closeAll()">Close All</div>
	</div>
	
	<!-- 修改密码窗口 -->
	<div id="mpw" class="easyui-window" title="修改密码" data-options="width:300,height:180,left:'40%',top:'20%',inline:true,border:'thin',minimizable:false,maximizable:false,collapsible:false" >
		<form id="passwordForm">
			<table style="margin:10px;">
				<tr><td>旧密码：</td><td><input type="password" class="easyui-textbox" name="oldPassword" data-options="type:'password',iconCls:'icon-lock',iconWidth:38,required:true,validType:['length[0,20]','notBlank']"></input></td></tr>
				<tr><td>新密码：</td><td><input type="password" class="easyui-textbox" name="newPassword" data-options="type:'password',iconCls:'icon-lock',iconWidth:38,required:true,validType:['length[0,20]','notBlank']"></input></td></tr>
				<tr><td>请确认：</td><td><input type="password" class="easyui-textbox" name="confirmPassword" data-options="type:'password',iconCls:'icon-lock',iconWidth:38,required:true,validType:['length[0,20]','notBlank']"></input></td></tr>
				<tr>
					<td colspan="2" align="right" style="padding-top:10px;">
						<a id="saveBtn" href="#" onclick="savePassword()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
					</td>
				</tr>
			</table>
        </form>
    </div>
</body>
</html>