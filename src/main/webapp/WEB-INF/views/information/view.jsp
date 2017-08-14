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

</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				资讯管理 <small>查看</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="./save.do" method="post" enctype="multipart/form-data">
	
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
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
					    	<script type="text/javascript">
					    		
					    	</script>
					    	
					    	<input type="hidden" name="createDate" id="createDate" value="<fmt:formatDate value="${information.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					    	<input type="hidden" name="createUser.id" id="createUserId" value="${information.createUser.id}">
					    	<input type="hidden" name="informationState" id="informationState" value="${information.informationState}">
					    	
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">资讯标题 : ${information.informationTitle}</font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
		                       		<div class="form-group">
		                                <font size="4" face="verdana">资讯类型 : ${information.informationType.fullName}</font>
		                            </div>
		                      </div>
		                    <br>
							<div class="col-md-12">
								<div class="form-group">
									<label>背景</label>
								</div>
							</div>
							<div class="col-md-12">
								<div style="width: 300px; height: 200px;">
									<img alt="图片预览" src="${information.picPath}" style="width: 100%;height: 100%;">
								</div>
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
							    			]],readonly:true
							    	});
							    	
								    ue.addListener("ready", function () {
							    		ue.setContent($("#textDescriptionHiddenDiv").html(), '');
							        });
							    })
							   
					    	</script>
					    	<br>
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

