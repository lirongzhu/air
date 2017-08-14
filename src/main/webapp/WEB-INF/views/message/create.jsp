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
<script src="../assets/js/validate_tool.js"></script>
<script type="text/javascript">
	
	function changeAlertContent(id, content){
		if($("#"+id+"").parent("div").children(":first").next().is("span")){
			$("#"+id+"").parent("div").children(":first").next().remove();
		}
		$("#"+id+"").parent("div").children(":first").after("<span style='font-size: small; color: red;font-weight: 200;'>&nbsp;&nbsp;&nbsp;&nbsp;"+content+"</span>");
	}
	
	function formSave(){
		
		if(!validate_tool.isNotNull($("#templateName").val())){
			changeAlertContent("templateName", "短信名称不能为空!");
			$("#templateName").focus().css("border","1px solid red");
			return;
		}else if($("#templateName").parent("div").children(":first").next().is("span")){
			$("#templateName").parent("div").children(":first").next().remove();
			$("#templateName").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#templateContent").val())){
			changeAlertContent("templateContent", "短信内容不能为空!");
			$("#templateContent").focus().css("border","1px solid red");
			return;
		}else if($("#templateContent").parent("div").children(":first").next().is("span")){
			$("#templateContent").parent("div").children(":first").next().remove();
			$("#templateContent").css("border","1px solid #D0D0D0");
		}
		
		$("#submitForm").submit();
	}

</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				模板发送 <small>编辑</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="./save.do" method="post" enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
				 	<a href="javascript: formSave();" class="btn btn-success">保存</a>
					<a href="./list.do" class="btn btn-primary">返回</a>
				</div>
				<div class="panel-body">
					<div>
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="base_info">
					    	<br>
					    	<input type="hidden" name="templateId" id="templateId" value="${messageTemplate.templateId}"/><!-- 存放修改条目的id -->
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <label>短信名称<font style="color: red">&nbsp;*&nbsp;</font></label>
			                           <input class="form-control" placeholder="短信名称" id="templateName" name="templateName" value="${messageTemplate.templateName}" maxlength="64">
			                       </div>
			                    </div>
		                       <div class="col-md-12">
		                       		<div class="form-group">
		                                <label>短息内容<font style="color: red">&nbsp;*&nbsp;</font></label>
		                                <textarea class="form-control" placeholder="短息内容" name="templateContent" id="templateContent"  maxlength="250" rows="5" >${messageTemplate.templateContent}</textarea>
		                            </div>
		                      </div>
							</div>
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

