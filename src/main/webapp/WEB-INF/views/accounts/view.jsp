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
				账号管理 <small>查看</small>
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
	                           <font size="4" face="verdana">登录名 : ${user.username}</font>
	                       </div>
	                    </div>
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <font size="4" face="verdana">联系人员姓名 : ${user.contactName}</font>
	                       </div>
	                    </div>
					 	<div class="col-md-6">
						 	<div class="form-group">
	                           <font size="4" face="verdana">联系方式 : ${user.contactPhone}</font>
	                       </div>
                       </div>
                       <div class="col-md-6">
	                    	<div class="form-group">
	                           <font size="4" face="verdana">账号类型: ${user.userType}</font>
	                       </div>
	                    </div>
                       <div class="col-md-6">
                       		<div class="form-group">
                                <font size="4" face="verdana">备注 : ${user.remark}</font>
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

