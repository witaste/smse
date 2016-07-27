<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="baseUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
<c:set var="cp" value="/${pageContext.request.contextPath}/" />

<link rel="stylesheet" type="text/css" href="${baseUrl}easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${baseUrl}easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${baseUrl}css/style.css">
<script type="text/javascript" src="${baseUrl}easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}easyui/jquery.form.js"></script>
<script type="text/javascript" src="${baseUrl}easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseUrl}easyui/easyui-lang-zh_CN.js"></script>
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
	    
	    // 新增验证方法 
	    $.extend($.fn.validatebox.defaults.rules, {
	        notBlank: {
	            validator: function(value){
	                return $.trim(value) != '';
	            },
	            message: 'Can not be blank.'
	        },
	        mobile:{
	        	validator: function(value){
	                return /^1[0-9]{10}$/.test(value);
	            },
	            message: 'Invalid phone number.'
	        }
	    });
	});
	

</script>
