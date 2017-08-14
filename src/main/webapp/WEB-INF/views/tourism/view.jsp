<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<% 	request.setAttribute("vEnter", "\n"); %>

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
				路线行程 <small>查看</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="" method="post">
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<c:if test="${itinerary.enable == '正常'}">
						<a href="./list.idg" class="btn btn-primary">返回</a>
					</c:if>
					<c:if test="${itinerary.enable == '停用'}">
						<a href="./offLineList.idg" class="btn btn-primary">返回</a>
					</c:if>
				</div>
				<div class="panel-body">
					<div>
					  <ul class="nav nav-tabs" role="tablist">
					    <li role="presentation" class="active">
					    	<a href="#base_info" aria-controls="base_info" role="tab" data-toggle="tab">基本信息</a>
					    </li>
					    <li role="presentation">
					    	<a href="#feature" aria-controls="feature" role="tab" data-toggle="tab">行程特色</a>
					    </li>
					    <li role="presentation">
					    	<a href="#scheduling" aria-controls="scheduling" role="tab" data-toggle="tab">行程安排</a>
					    </li>
					    <li role="presentation">
					    	<a href="#cost_statement" aria-controls="cost_statement" role="tab" data-toggle="tab">费用说明</a>
					    </li>
					    <li role="presentation">
					    	<a href="#special_notice" aria-controls="special_notice" role="tab" data-toggle="tab">特别提示</a>
					    </li>
					    <li role="presentation">
					    	<a href="#image_upload" aria-controls="image_upload" role="tab" data-toggle="tab">背景上传</a>
					    </li>
					  </ul>

					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="base_info">
					    <br>
					    	<input class="form-control" type="hidden" name="itineraryId" id="itineraryId" value="${itinerary.itineraryId}">
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">路线名称 : ${itinerary.routeName}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">产品编号 : ${itinerary.productNumber}</font>
			                       </div>
			                    </div>
							 	<div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">出发地点 : ${itinerary.pointOfDeparture}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">目的地 : ${itinerary.destination}</font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
		                       		<div class="form-group">
		                                <font size="4" face="verdana">行程天数 : ${itinerary.routeDays}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">行程类型 : ${itinerary.tourismType.fullName}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">原价 : ${itinerary.originalPrice}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">折扣 : ${itinerary.discount}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
								 	<div class="form-group">
			                           <font size="4" face="verdana">现价 : ${itinerary.nowPrice}</font>
			                       </div>
		                       </div>
								<div class="col-md-6">
								  <div class="form-group">
								      <font size="4" face="verdana">产品类型 : ${itinerary.rangType.fullName}</font>
								  </div>
								</div>
								<div class="col-md-6">
								  <div class="form-group">
								      <font size="4" face="verdana">所需证件 : ${itinerary.cardType.fullName}</font>
								  </div>
								</div>
								<div class="col-md-6">
								  <div class="form-group">
								      <font size="4" face="verdana">所属模块 : ${itinerary.belongFunction.fullName}</font>
								  </div>
								</div>
								<div class="col-md-12">
								  <div class="form-group">
								      <font size="4" face="verdana">关键字 : ${itinerary.keyWord}</font>
								  </div>
								</div>
								<div class="col-md-12">
								  <div class="form-group">
								      <font size="4" face="verdana">路线简介 : ${itinerary.routeIntroduction}</font>
								  </div>
								</div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="feature" >
					    
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
							                'inserttable', 'deletetable', 'insertparagraphbeforetable', 
							                'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 
							                'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
							    			]],
							    		readonly:true
							    	});
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
								    UE.Editor.prototype.getActionUrl = function(action) {  
								        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadvideo') {  
								            return '.././common/uploadFile.idg';  
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
					    	<script id="editor" type="text/plain" style="width:100%;height:600px;"></script>
					    	<input class="form-control" type="hidden" name="tourismFeature.textDescription" id="textDescription"/>
					    	<div style="display: none;" id="textDescriptionHiddenDiv">${feature.textDescription}</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="scheduling">
					    	<div class="row">&nbsp;</div>
					    	<table class="table table-striped table-bordered table-hover" id="schedulinglistTable">
							<thead>
								<tr>
									<th style="width=10%;">天数</th>
									<th style="width=10%;">交通</th>
									<th style="width=10%;">住宿</th>
									<th style="width=10%;">餐饮</th>
									<th>景点</th>
								</tr>
							</thead>
							<tbody id="scheduling_tbody">
								<c:forEach items="${schedulingList}" var="scheduling" varStatus="schStatus">
									<tr data-index="${schStatus.index}">
										<td>第<c:out value="${scheduling.days}"/>天</td>
										<td><c:out value="${scheduling.traffic}"/></td>
										<td><c:out value="${scheduling.stay}"/></td>
										<td><c:out value="${scheduling.repast}"/></td>
										<td><c:out value="${scheduling.attractions}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					    </div>
					    <div role="tabpanel" class="tab-pane" id="cost_statement">
					    	<div class="row">&nbsp;</div>
					    	<table class="table table-striped table-bordered table-hover" id="costStatementlistTable">
								<thead>
									<tr>
										<th>费用说明</th>
									</tr>
								</thead>
								<tbody id="costStatement_tbody">
									<c:forEach items="${costStatementList}"  var="costStatement">
										<tr>
											<td>${fn:replace(costStatement.costStatement,vEnter,"<BR/>") }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="special_notice">
					    	<br>
					    	<div class="row">&nbsp;</div>
					    	<table class="table table-striped table-bordered table-hover" id="specialNoticelistTable">
								<thead>
									<tr>
										<th>特别提示</th>
									</tr>
								</thead>
								<tbody id="specialNotice_tbody">
									<c:forEach items="${specialNoticeList}"  var="specialNotice">
										<tr>
											<td>${fn:replace(specialNotice.specialNotice,vEnter,"<BR/>") }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
					    </div>
					    <div class="modal fade" id="examineOpinionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header">
							                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							                <h4 class="modal-title" id="myModalLabel">审核意见</h4>
							            </div>
							            <div class="modal-body">
							            	<div class="row">
							                    <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="5" placeholder="审核意见" name="examineOpinionDialog" id="examineOpinionDialog"></textarea>
							                       </div>
						                     	</div>
							                </div>
							            </div>
							            <div class="modal-footer">
							                <button type="button" class="btn btn-primary" id="saveExamineOpinionModal">确定</button>
							                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeExamineOpinionModal">关闭</button>
							            </div>
							        </div>
							    </div>
							</div>
							<div role="tabpanel" class="tab-pane" id="image_upload">
					    	<br>
					    	<div class="row">&nbsp;</div>
					    	<div class="row">
			                    <div class="col-md-12">
			                    	<c:if test="${itinerary.pictureRelativePath != ''}">
			                    		<div style="width: 300px; height: 200px;">
				                    		<img alt="图片预览" src="${itinerary.pictureRelativePath}" style="width: 100%;height: 100%;">
				                    	</div>
			                    	</c:if>
			                    </div>
							</div>
					    </div>
					  </div>
					</div>
				</div>
			</div>
			<!--End Advanced Tables -->
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

