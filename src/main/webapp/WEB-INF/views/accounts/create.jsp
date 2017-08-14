<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script src="../assets/js/validate_tool.js"></script>

<script type="text/javascript">
	function changeAlertContent(id, content){
		if($("#"+id+"").parent("div").children(":first").next().is("span")){
			$("#"+id+"").parent("div").children(":first").next().remove();
		}
		$("#"+id+"").parent("div").children(":first").after("<span style='font-size: small; color: red;font-weight: 200;'>&nbsp;&nbsp;&nbsp;&nbsp;"+content+"</span>");
	}
	
	function formSubmit(){
		if(!validate_tool.isNotNull($("#userName").val())){
			
			changeAlertContent("userName", "登录名必输!");
			$("#userName").focus().css("border","1px solid red");
			return;
		}else {
			$.ajax({
	            url: "findByName.do",
	            type: "POST",
	            dataType:"text",
	            data: {
	            	userName : $("#userName").val()
	            },
	            success:function (data) {
	            	if(data == 0 && $("#id").val() == ""){
	            		changeAlertContent("userName", "登录名已经存在!");
	            		$("#userName").focus().css("border","1px solid red");
	            		return;
	            	}else if(data.indexOf("gotoLogin") > -1){
	            		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
	                }else{
	                	if(!validate_tool.isNotNull($("#password").val())){
	            			changeAlertContent("password", "密码必输!");
	            			$("#password").focus().css("border","1px solid red");
	            			return;
	            		}else if($("#password").parent("div").children(":first").next().is("span")){
	            			$("#password").parent("div").children(":first").next().remove();
	            			$("#password").css("border","1px solid #D0D0D0");
	            		}
	            		
	            		if(!validate_tool.isNotNull($("#contactName").val())){
	            			changeAlertContent("contactName", "联系人员姓名必输!");
	            			$("#contactName").focus().css("border","1px solid red");
	            			return;
	            		}else if($("#contactName").parent("div").children(":first").next().is("span")){
	            			$("#contactName").parent("div").children(":first").next().remove();
	            			$("#contactName").css("border","1px solid #D0D0D0");
	            		}
	            		
	            		if(!validate_tool.isPhone($("#contactPhone").val())){
	            			changeAlertContent("contactPhone", "联系方式必输、或请确认对错!");
	            			$("#contactPhone").focus().css("border","1px solid red");
	            			return;
	            		}else if($("#contactPhone").parent("div").children(":first").next().is("span")){
	            			$("#contactPhone").parent("div").children(":first").next().remove();
	            			$("#contactPhone").css("border","1px solid #D0D0D0");
	            		}
	            		
	            		$("#submitForm").submit();
	                }
	            },
	            error:function(data){
	            	idgMessageObj.iMWarnAlert("网络异常！", 2000);
	            }
	        })
			if($("#userName").parent("div").children(":first").next().is("span")){
				$("#userName").parent("div").children(":first").next().remove();
				$("#userName").css("border","1px solid #D0D0D0");
			}
		}
	}
		
	</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
	<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
		infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				账号管理 <small>编辑</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="./save.do" method="post">
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
				 	<a href="javascript: formSubmit();" class="btn btn-success">保存</a>
					<a href="./list.do" class="btn btn-primary">返回</a>
				</div>
				<div class="panel-body">
					<div class="row">
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>登录名<font style="color: red">*</font></label>
	                           <input class="form-control" placeholder="登录名" maxlength="50" name="username" id="userName" value="${user.username}">
	                       </div>
	                    </div>
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>密码<font style="color: red">*</font></label>
	                           <input class="form-control" type="password" placeholder="密码" maxlength="128" name="password" id="password" value="${user.password}">
	                           <input class="form-control" type="hidden" name="salt" value="${user.salt}">
	                           <input class="form-control" type="hidden" name="oldPassword" value="${user.password}">
	                       </div>
	                    </div>
					 	<div class="col-md-6">
						 	<div class="form-group">
	                           <label>联系人员姓名<font style="color: red">*</font></label>
	                           <input class="form-control" placeholder="联系人员姓名"  maxlength="20" name="contactName" id="contactName" value="${user.contactName}">
	                       </div>
                       </div>
                       <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>联系方式<font style="color: red">*</font></label>
	                           <input class="form-control" placeholder="联系方式" maxlength="20" name="contactPhone" id="contactPhone" value="${user.contactPhone}">
	                       </div>
	                    </div>
                       <div class="col-md-6">
                       		<div class="form-group">
                                <label>账号类型</label>
                                <select class="form-control" name="userType">
                                	<c:forEach var="userType" items="${userType}">
                                		<option value="${userType}" 
                                			<c:if test="${user.userType == userType}">selected="selected"</c:if>>${userType}
                                		</option>
                                	</c:forEach>
                                </select>
                            </div>
                      </div>
                     <div class="col-md-12">
                       <div class="form-group">
                           <label>备注</label>
                           <textarea class="form-control" rows="3" placeholder="备注"name="remark">${user.remark}</textarea>
                       </div>
                     </div>
				</div>
			</div>
			<!--End Advanced Tables -->
		</div>
	</div>
	</div>
	 <input type="hidden" id="id" name="id" value="${user.id}">
	 <input type="hidden" name="createDate" id="createDate" value="<fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
	</form>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

