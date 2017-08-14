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
	
	function formSave(){
		
		if(!validate_tool.isNotNull($("#shortName").val())){
			changeAlertContent("shortName", "中文简称必输!");
			$("#shortName").focus().css("border","1px solid red");
			return;
		}else if($("#shortName").parent("div").children(":first").next().is("span")){
			$("#shortName").parent("div").children(":first").next().remove();
			$("#shortName").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#fullName").val())){
			changeAlertContent("fullName", "中文全称必输!");
			$("#fullName").focus().css("border","1px solid red");
			return;
		}else if($("#fullName").parent("div").children(":first").next().is("span")){
			$("#fullName").parent("div").children(":first").next().remove();
			$("#fullName").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#engShortName").val())){
			changeAlertContent("engShortName", "英文简称必输!");
			$("#engShortName").focus().css("border","1px solid red");
			return;
		}else if($("#engShortName").parent("div").children(":first").next().is("span")){
			$("#engShortName").parent("div").children(":first").next().remove();
			$("#engShortName").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#engName").val())){
			changeAlertContent("engName", "英文全称必输!");
			$("#engName").focus().css("border","1px solid red");
			return;
		}else if($("#engName").parent("div").children(":first").next().is("span")){
			$("#engName").parent("div").children(":first").next().remove();
			$("#engName").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#itemKey").val())){
			changeAlertContent("itemKey", "关键值必输!");
			$("#itemKey").focus().css("border","1px solid red");
			return;
		}else if($("#itemKey").parent("div").children(":first").next().is("span")){
			$("#itemKey").parent("div").children(":first").next().remove();
			$("#itemKey").css("border","1px solid #D0D0D0");
		}
		
		if($("#itemId").val() == ""){
			
			$.ajax({
		    	url: "checkKey.do",
				type: "GET",
				data: {
					key : $("#itemKey").val(),
					type : "item"
				},
				success:function (data) {
					
					if(data == 0){
						$("#submitForm").submit();
					}else if(data.indexOf("gotoLogin") > -1){
						idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
					}else{
						idgMessageObj.iMWarnAlert("该关键值已被使用，请更换关键值", 2000);
					}
				},
				error:function(data){
					idgMessageObj.iMWarnAlert("网络异常！", 2000);
				}
			})
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
				字典管理 <small>编辑</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="./saveItem.do" method="post" enctype="multipart/form-data">
	
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
				 	<a href="javascript: formSave();" class="btn btn-success">保存</a>
					<a href="./listItem.do?dictionaryId=${dictionary.dictionaryId}" class="btn btn-primary">返回</a>
				</div>
				<input type="hidden" id="dictionaryId" name="dictionary.dictionaryId" value="${dictionary.dictionaryId}"/>
				<input type="hidden" id="itemId" name="itemId" value="${dictionaryItem.itemId}"/>
				
				<input type="hidden" name="createDate" id="createDate" value="<fmt:formatDate value="${dictionaryItem.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
		    	<input type="hidden" name="createUser.id" id="createUserId" value="${dictionaryItem.createUser.id}">
		    	<input type="hidden" name="enable" id="enable" value="${dictionaryItem.enable}">
				<div class="panel-body">
			    	<div class="row">
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>中文简称<font style="color: red">&nbsp;*&nbsp;</font></label>
	                           <input class="form-control" placeholder="中文简称" id="shortName" name="shortName" value="${dictionaryItem.shortName}" maxlength="50">
	                       </div>
	                    </div>
                       <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>中文全称<font style="color: red">&nbsp;*&nbsp;</font></label>
	                           <input class="form-control" placeholder="中文全称" id="fullName" name="fullName" value="${dictionaryItem.fullName}" maxlength="50">
	                       </div>
	                    </div>
						<div class="col-md-6">
	                    	<div class="form-group">
	                           <label>英文简称<font style="color: red">&nbsp;*&nbsp;</font></label>
	                           <input class="form-control" placeholder="英文简称" id="engShortName" name="engShortName" value="${dictionaryItem.engShortName}" maxlength="50">
	                       </div>
	                    </div>
						<div class="col-md-6">
	                    	<div class="form-group">
	                           <label>英文全称<font style="color: red">&nbsp;*&nbsp;</font></label>
	                           <input class="form-control" placeholder="英文全称" id="engName" name="engName" value="${dictionaryItem.engName}" maxlength="50">
	                       </div>
	                    </div>
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>关键值<font style="color: red">&nbsp;*&nbsp;</font></label>
	                           <input class="form-control" placeholder="关键值" id="itemKey" name="itemKey" value="${dictionaryItem.itemKey}" maxlength="50" <c:if test="${dictionaryItem.itemId != null}">readonly="readonly"</c:if>>
	                       </div>
	                    </div>
	                    <div class="col-md-6">
	                    	<div class="form-group">
	                           <label>显示顺序<font style="color: red"</font></label>
	                           <input class="form-control" placeholder="关键值" id="itemSort" name="itemSort" value="${dictionaryItem.itemSort}" maxlength="2">
	                       </div>
	                    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</form>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

