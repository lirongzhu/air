<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">

	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				客户管理 <small>查看</small>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="./list.do" class="btn btn-primary">返回</a>
				</div>
				<div class="panel-body">
					<div class="row">
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <font size="4" face="verdana">登录名 : ${customer.loginName}</font>
	                       </div>
	                    </div>
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <font size="4" face="verdana">真实名称 : ${customer.trueName}</font>
	                       </div>
	                    </div>
					 	<div class="col-md-6">
						 	<div class="form-group">
	                           <font size="4" face="verdana">联系方式 : ${customer.contact}</font>
	                       </div>
                       </div>
                       <div class="col-md-6">
	                    	<div class="form-group">
	                           <font size="4" face="verdana">创建日期 : <fmt:formatDate value="${customer.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /> </font>
	                       </div>
	                    </div>
                       <div class="col-md-6">
                       		<div class="form-group">
                                <font size="4" face="verdana">用户状态 : ${customer.state}</font>
                            </div>
                      </div>
				</div>
			</div>
			<!--End Advanced Tables -->
		</div>
	</div>
	</div>
	 <input type="hidden" id="id" name="id" value="${customer.id}">
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

