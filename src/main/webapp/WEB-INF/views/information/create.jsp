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
<script src="../assets/js/validate_tool.js"></script>
<script type="text/javascript">
	function changeAlertContent(id, content){
		if($("#"+id+"").parent("div").children(":first").next().is("span")){
			$("#"+id+"").parent("div").children(":first").next().remove();
		}
		$("#"+id+"").parent("div").children(":first").after("<span style='font-size: small; color: red;font-weight: 200;'>&nbsp;&nbsp;&nbsp;&nbsp;"+content+"</span>");
	}
	
	function formSave(){
		
		if(!validate_tool.isNotNull($("#informationTitle").val())){
			changeAlertContent("informationTitle", "资讯标题必输!");
			$("#informationTitle").focus().css("border","1px solid red");
			return;
		}else if($("#informationTitle").parent("div").children(":first").next().is("span")){
			$("#informationTitle").parent("div").children(":first").next().remove();
			$("#informationTitle").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#informationId").val())){
			if(!validate_tool.isNotNull($("#imageFile").val())){
				changeAlertContent("imageFile", "背景必选!");
				$("#imageFile").focus().css("border","1px solid red");
				return;
			}else if($("#imageFile").parent("div").children(":first").next().is("span")){
				$("#imageFile").parent("div").children(":first").next().remove();
				$("#imageFile").css("border","1px solid #D0D0D0");
			}
		}
		
		if(document.getElementById("imageFile").files.length != 0){
			var size =  document.getElementById("imageFile").files[0].size;
			
			if(size > 2 * 1024 * 1024){
				changeAlertContent("imageFile", "上传图片大小不能超过2M");
				$("#imageFile").focus().css("border","1px solid red");
				return;
			}else if($("#imageFile").parent("div").children(":first").next().is("span")){
				$("#imageFile").parent("div").children(":first").next().remove();
				$("#imageFile").css("border","1px solid #D0D0D0");
			}
		}
		
		var ueContet = UE.getEditor('editor').getContent();
		
		$("#informationContent").val(ueContet);
		$("#submitForm").submit();
	}

</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				资讯管理 <small>编辑</small>
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
					  <ul class="nav nav-tabs" role="tablist">
					    <li role="presentation" class="active">
					    	<a href="#base_info" aria-controls="base_info" role="tab" data-toggle="tab">基本信息</a>
					    </li>
					    <li role="presentation">
					    	<a href="#info_content" aria-controls="info_content" role="tab" data-toggle="tab">资讯内容</a>
					    </li>
					  </ul>

					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="base_info">
					    	<br>
					    	<input type="hidden" name="picPath" id="picPath" value="${information.picPath}"/>
					    	<input type="hidden" name="hotType" id="hotType" value="${information.hotType}">
					    	<input type="hidden" name="createDate" id="createDate" value="<fmt:formatDate value="${information.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					    	<input type="hidden" name="createUser.id" id="createUserId" value="${information.createUser.id}">
					    	<input type="hidden" name="informationState" id="informationState" value="${information.informationState}">
					    	
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <label>资讯标题<font style="color: red">&nbsp;*&nbsp;</font></label>
			                           <input class="form-control" placeholder="资讯标题" id="informationTitle" name="informationTitle" value="${information.informationTitle}" maxlength="64">
			                       </div>
			                    </div>
		                       <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>资讯类型</label>
		                                <select class="form-control" name="informationType.itemId" id="informationType">
		                                	<c:forEach var="informationType" items="${informationType}">
		                                		<option value="${informationType.itemId}" 
		                                			<c:if test="${information.informationType.itemId == informationType.itemId}">selected="selected"</c:if>>${informationType.fullName}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
							<div class="col-md-12">
								<div class="form-group">
									<label>背景上传<font style="color: red">&nbsp;*&nbsp;</font></label>
									<input type="file" name="imageFile" id="imageFile"/>
								</div>
							</div>
							<div class="col-md-12">
								<c:if test="${information.picPath != '' && information.picPath != null}">
									<div style="width: 300px; height: 200px;">
										<img alt="图片预览" src="${information.picPath}" style="width: 100%;height: 100%;">
									</div>
								</c:if>
							</div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="info_content">
					    
					    	<script type="text/javascript">
					    	
							    $(function(){
							    	
							    	var ue = UE.getEditor('editor',{
							    		toolbars:[[
							    			'Source', 'Undo', 'Redo', '|',
							    			'bold','italic', 'underline', 'fontborder', 'strikethrough', '|', 
							    			'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|',
							                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
							                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
							                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
							                'simpleupload', 'emotion','date', 'time','|',
							                'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
							                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
							    			]]
							    	});
									
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
								    UE.Editor.prototype.getActionUrl = function(action) {  
								        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadvideo') {  
								        	return $("#serviceAddress").val() + 'common/uploadImage.do';  
								        } else {  
								            return this._bkGetActionUrl.call(this, action);  
								        }  
								    } 
								    
								    ue.addListener("ready", function () {
							    		ue.setContent($("#textDescriptionHiddenDiv").html(), '');
							        });
							    })
							   
					    	</script>
					    	<br>
					    	<input type="hidden" id="serviceAddress" value="${serviceAddress}">
					    	<script id="editor" type="text/plain" style="width:100%;height:600px;"></script>
					    	<input type="hidden" name="informationId" id="informationId" value="${information.informationId}"/>
					    	<input type="hidden" name="informationContent" id="informationContent"/>
					    	<div style="display: none;" id="textDescriptionHiddenDiv">${information.informationContent}</div>
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

