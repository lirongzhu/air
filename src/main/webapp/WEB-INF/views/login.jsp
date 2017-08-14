<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>登录</title>
    <link rel="icon" href="images/title-ico.ico" type="image/x-ico"/> 
    <!-- Bootstrap Styles-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
	
	   <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="assets/js/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
	 
    <style type="text/css">
    	
    	.loginDiv{
    		margin-top:22%;
    		margin-left:35%;
    		width: 30%;
    		border: solid 1px #ffffff;
    		padding: 30px 25px 10px;
    		border-radius: 10px;
    		text-align: center;
    	}
    	
    	body{
    		background-image: url("images/background-image.jpg");
			background-repeat:no-repeat;repeat-x;repeat-y;
    	}
    </style>
	<script>
	
		$(function(){
		
			$("#submitButton").click(function(){
				$("#submitForm").submit();
			});
			
			setTimeout(function(){
				
				$("#showMessageContentDiv").hide();
			},3000)
		});

		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];         
            if(e && e.keyCode==13){ // enter 键
            	$("#submitForm").submit();
            }
        }; 
	</script>
</head>
<body style="overflow: hidden;">
	<c:if test="${dangerMessage != null && dangerMessage != ''}">
		<div class = "center-block" style="width: 20%;position: fixed; margin-left: 40%; z-index:5;margin-top:70px;" id="showMessageContentDiv">
			<div class="alert alert-danger alert-dismissible" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <strong>${dangerMessage}</strong>
			</div>
		</div>
	</c:if>
	<form action="./login.do" method="post" id="submitForm">
		<div class="loginDiv">
			<h3 style="color: #ffffff;margin-bottom: 30px;">登录</h3>
			<div class="form-group">
	    		<input type="text" class="form-control" placeholder="账号" name="username" value="">
	  		</div>
	  		<div class="form-group">
	    		<input type="password" class="form-control" placeholder="密码" name="password" value="">
	  		</div>
	  		<h5 style="color: #ffffff;margin-bottom: 10px;text-align: right;">忘记密码</h5>
	  		<div class="form-group">
	    		<button type="button" class="btn btn-primary" style="width: 100%;" id="submitButton">登录</button>
	  		</div>
		</div>
	</form>
</body>
</html>