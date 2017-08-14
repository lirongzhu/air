<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>

<script type="text/javascript">
	$(document).ready(function () {
	    
	    $("#queryButton").click(function(){
	    	$("#submitForm").submit();
	    })
	    
	    var timer = null; 
	    $("#listTable tbody tr").dblclick(function(){
	    	timer && clearTimeout(timer); 
	    	window.location.href = "./create.do?id=" + $(this).data("id");
	    })
	});
	
	function formDelete(disableId){
		
		idgConfirmObj.confirm("确认停用？", function(){ 
				
			$.ajax({
	            url: "disable.do",
	            type: "POST",
	            data: {
	            	disableId : disableId
	            },
	            success:function (data) {
	
	                if(data == "1"){
	                	idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){ $("#submitForm").submit();});
	                }else if(data.indexOf("gotoLogin") > -1){
	                	idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
	                }else{
	                	idgMessageObj.iMWarnAlert("操作失败", 2000);
	                }
	            }
	        })
		})
	}
	
	function formReset(resetId){
		
		$.ajax({
	       url: "reset.do",
	       type: "POST",
	       data: {
	       	resetId : resetId
	       },
	       success:function (data) {
	
	           if(data == "1"){
	        	   idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){ $("#submitForm").submit();});
	           }else if(data.indexOf("gotoLogin") > -1){
	        	   idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
	           }else{
	        	   idgMessageObj.iMWarnAlert("操作失败", 2000);
	           }
	       }
	   })
	}

</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<form action="./list.do" method="post" name="submitForm" id="submitForm">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				账号管理 <small>列表</small>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="./create.do" class="btn btn-primary">新增</a>
				</div>
				<div class="panel-body">
					<div class="table-responsive" style="overflow-x: hidden;">
						<div class="row">
							<div class="col-sm-7">
							</div>
							<div class="col-sm-4">
								<input type="input" class="form-control" placeholder="登录名/联系名称/联系电话" name="serach_content" value="${param.serach_content}">
							</div>
							<div class="col-sm-1">
								<button class="btn btn-primary" id="queryButton" name="queryButton" >查询</button>
							</div>
						</div>
						<br>
						<table class="table table-striped table-bordered table-hover" id="listTable">
							<thead>
								<tr>
									<th>登录名</th>
									<th>登录类型</th>
									<th>联系名称</th>
									<th>联系电话</th>
									<th>登录状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(pagedList.content) le 0}">
									<tr>
										<td colspan="4" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
								<c:forEach items="${pagedList.content}" var="user" varStatus="status" >
									<tr data-id="${user.id}" class="
										<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
										<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
									">
										<td>${user.username}</td>
										<td>${user.userType}</td>
										<td>${user.contactName}</td>
										<td>${user.contactPhone}</td>
										<td>${user.userStatus}</td>
										<td>
											<a href="./view.do?id=${user.id}">查看</a>
											<a href="./create.do?id=${user.id}">修改</a>
											<c:if test="${user.userStatus == '正常'}">
												<a href="javascript: formDelete(${user.id});">停用</a>
											</c:if>
											<c:if test="${user.userStatus == '停用'}">
												<a href="javascript: formReset(${user.id});">启用</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<tags:idgPage pagedList="${pagedList}"/>
					</div>
				</div>
			</div>
			<!--End Advanced Tables -->
		</div>
	</div>
	</form>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

