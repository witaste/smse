<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/include/easyui_nopager.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main</title>
<script type="text/javascript">

	$(document).ready(function() {
		$(".panel-header").css({
			"width" : "168px",
			"height" : "18px",
			"border-color" : "#000000"
		});
		$(".tabs-wrap").css({
			"background-color" : "#FFFFFF"
		});
		$(".tabs").css({
			"padding-left" : "0px"
		});
		
		// 菜单 
		$('#tree').tree({
			url:'system!getTreeNode.html',
			method:'get',
			animate:false,
			lines:true,
			formatter:function(node){
				var id = node.id;
				var text = node.text;
				var url = node.attributes.url;
				var s = '';
				if(node.attributes.isLeaf){
					s = '<a href=\"'+url+'\" onclick=\"return toTab(\''+text+'\',\''+url+'\')\">'+text+'</a>';
				}else{
					s = '<a href='+url+'>'+text+'</a>';
				}
				return s;
			},
			onClick: function(node){
				if(node.state == 'open'){
					var children = $('#tree').tree('getChildren',node.target);
					for(key in children){
						$('#tree').tree('collapse',children[key].target);
					}
				}
				$('#tree').tree('toggle', node.target);
			},
			onLoadSuccess:function(node,data){
				$("span.tree-icon").hide();
			}
		});
	});
	
	function toTab(text,url) {
		if ($('#tabs').tabs('exists', text)) {
			$('#tabs').tabs('select', text);
			var tab = $('#tabs').tabs('getSelected');
			$('#tabs').tabs('update', {
				tab: tab,
				options: {
					title : text,
					content : '<iframe frameborder="0" width="100%" height="100%" src="'+ url +'" scrolling="auto"></iframe>',
					closable : true,
					loadingMessage : '加载中...'
				}
			});		
		} else {
			$('#tabs').tabs('add', {
				title : text,
				content : '<iframe frameborder="0" width="100%" height="100%" src="'+ url +'" scrolling="auto"></iframe>',
				closable : true,
				loadingMessage : '加载中...'
			});
		}
		return false;
	}
</script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<!-- 北域 -->
	<div data-options="region:'north',split:false,border:false" style="height: 94px; overflow-y: hidden;">
		<div style="background-image:url('${cp}/images/main_north_bg.png');background-repeat:repeat-x;height:94px">
			<div style="position: absolute; top: 10px; left: 30px; z-index: 2;">
				<img src="${cp}/images/main_north_logo.png"></img>
			</div>
			<div style='position: absolute; top: 0; right: 0px; z-index: 2;'>
				<div style="display: inline-block; width: auto; height: 30px; color: #FFF; margin-top: 0px;">
					<span style="padding-left: 5px; line-height: 30px; color: #ffffff; margin-top: 3px; display: inline-block">
						admin 
					</span>
					<span onclick="javascript:void(0)" style="cursor: hand; color: #ffffff; cursor: pointer;">
						修改密码 
					</span>
					<span>|</span>
					<span onclick="javascript:void(0)" style="cursor: hand; color: #ffffff; cursor: pointer; padding-right: 40px;">
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
		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div style="background: url(${cp}/images/main_center_bg.gif) fixed center;width:100%;height:100%"></div>
			</div>
		</div>
	</div>
</body>
</html>