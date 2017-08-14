<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	function gotoLogin(){
		top.document.location.href='login.do';
	}
</script>
</head>
<body style="text-align: center;padding-top: 7%;">
	<img alt="404" src="./images/error-timeout.png">
	<a href="javaScript:gotoLogin();" style="text-decoration:none;"><h2>用户连接超时</h2></a>
	<h5>用户连接超时，请重新登陆！</h5>
</body>
</html>