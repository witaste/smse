<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<c:set var="cp" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${cp}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cp}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${cp}/css/style.css">
<script type="text/javascript" src="${cp}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${cp}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cp}/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	    var d_buttons = $.extend([], $.fn.datebox.defaults.buttons);
	    d_buttons.splice(1, 0, {
	    	text: '清空',
	    	handler: function(target){
	    		$(target).datebox('setValue','');
	    	}
	    });
	    $('.easyui-datebox').datebox({
	    	buttons: d_buttons,
	    	editable:false,
	    	width:'100px'
	    });
	    
	    var dt_buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
	    dt_buttons.splice(2, 0, {
	    	text: '清空',
	    	handler: function(target){
	    		$(target).datetimebox('setValue','');
	    	}
	    });
	    $('.easyui-datetimebox').datetimebox({
	    	buttons: dt_buttons,
	    	editable:false,
	    	width:'150px'
	    });
	});
	

</script>
</head>
</html>