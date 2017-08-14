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
	    	$("#queryForm").submit();
	    })
	    
	    var timer = null; 
	    $("#listTable tbody tr").dblclick(function(){
	    	timer && clearTimeout(timer); 
	    	window.location.href = "./create.do?id=" + $(this).data("id");
	    })
	    
	    $("#closeModal").click(function(){
	    	$('#setHotModal').modal('hide');
	    })
	    
	    $("#saveHotModal").click(function(){
	    	
	    	if($("#hotType").val() == 3){
	    		ajaxSet();
	    	}else{
	    		
	    		$('#setHotModal').modal('hide');
	    		idgConfirmObj.confirm("如果已有相同推送级别，将被覆盖。确认操作？", ajaxSet)
	    	}
	    })
	});

	function ajaxSet(){
		
		$.ajax({
		      url: "setHot.do",
		      type: "GET",
		      data: {
		      	infoId : $("#setHotInfoId").val(),
		      	hotType : $("#hotType").val()
		      },
		      success:function (data) {
		      	
		    	$('#setHotModal').modal('hide');
		    	
		      	if(data == 1){
		      		idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){ $("#queryForm").submit();});
		      	}else if(data.indexOf("gotoLogin") > -1){
		      		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
		        }else{
		      		idgMessageObj.iMWarnAlert("操作失败", 2000);
		      	}
		      },
		      error:function(data){
				
				$('#setHotModal').modal('hide');
	      		idgMessageObj.iMWarnAlert("网络异常！", 2000);
		      }
		  })
	}
	
	function cancelHot(cancelId){
		
		idgConfirmObj.confirm("确认取消？", function(){ 
			
			$.ajax({
		      url: "cancelHot.do",
		      type: "GET",
		      data: {
		    	  cancelId : cancelId
		      },
		      success:function (data) {
		      	
		      	if(data == 1){
		      		idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){$("#queryForm").submit();});
		      	}else if(data.indexOf("gotoLogin") > -1){
		      		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
		          }else{
		        	idgMessageObj.iMWarnAlert("操作失败", 2000);
		      	}
		      },
		      error:function(data){
		    	  idgMessageObj.iMWarnAlert("网络异常！", 2000);
		      }
		  })
		})
	}
	
	function deleteInfo(deleteId){
		
		idgConfirmObj.confirm("确认删除？", function(){ 
			
			 $.ajax({
			      url: "delete.do",
			      type: "GET",
			      data: {
			      	deleteId : deleteId
			      },
			      success:function (data) {
			      	
			      	if(data == 1){
			      		idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){$("#queryForm").submit();});
			      	}else if(data.indexOf("gotoLogin") > -1){
			      		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
			          }else{
			        	idgMessageObj.iMWarnAlert("操作失败", 2000);
			      	}
			      },
			      error:function(data){
			    	  idgMessageObj.iMWarnAlert("网络异常！", 2000);
			      }
			  })
		})
	}
	
	function openSetHot(hotId){
		
		$("#setHotInfoId").val(hotId);
		$('#setHotModal').modal('show');
	}

</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<form action="./list.do" method="post" name="queryForm" id="queryForm">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				资讯管理 <small>列表</small>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="./create.do" class="btn btn-primary">新增资讯</a>
				</div>
				<div class="panel-body">
					<div class="table-responsive" style="overflow-x: hidden;">
						<div class="row">
							<div class="col-sm-7">
							</div>
							<div class="col-sm-4">
								<input type="input" class="form-control" name="serach_content" placeholder="标题" value="${param.serach_content}">
							</div>
							<div class="col-sm-1">
								<button class="btn btn-primary" id="queryButton" name="queryButton">查询</button>
							</div>
						</div>
						<br>
						<table class="table table-striped table-bordered table-hover" id="listTable">
							<thead>
								<tr>
									<th style="width: 20%;">标题</th>
									<th style="width: 10%;">状态</th>
									<th style="width: 10%;">类型</th>
									<th style="width: 10%;">创建日期</th>
									<th style="width: 10%;">创建人</th>
									<th style="width: 10%;">热门类型</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(pagedList.content) le 0}">
									<tr>
										<td colspan="11" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
								<c:forEach items="${pagedList.content}" var="information" varStatus="status" >
									<tr data-id="${information.informationId}" class="
										<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
										<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
									">
										<td>${information.informationTitle}</td>
										<td>${information.informationState}</td>
										<td>${information.informationType.fullName}</td>
										<td><fmt:formatDate value="${information.createDate}" pattern="yyyy/MM/dd" /></td>
										<td>${information.createUser.contactName}</td>
										<td>
											<c:if test="${information.hotType eq 0}">主推</c:if>
											<c:if test="${information.hotType eq 1}">次级推送</c:if>
											<c:if test="${information.hotType eq 2}">三级推送</c:if>
											<c:if test="${information.hotType eq 3}">轮播图</c:if>
										</td>
										<td>
											<a href="./view.do?id=${information.informationId}">查看</a>
											<a href="./create.do?id=${information.informationId}">修改</a>
											<a href="javascript:deleteInfo(${information.informationId});">删除</a>
											<c:if test="${information.informationType.fullName == '促销'}">
												<a href="javascript:openSetHot(${information.informationId});">设置促销热门</a>
											</c:if>
											<c:if test="${information.hotType eq 3}">
												<a href="javascript:cancelHot(${information.informationId});">取消轮播图</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<tags:idgPage pagedList="${pagedList}"/>
						<input type="hidden" id="setHotInfoId"/>
						<div class="modal fade" id="setHotModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
						    <div class="modal-dialog">
						        <div class="modal-content">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						                <h4 class="modal-title" id="myModalLabel">设置热门</h4>
						            </div>
						            <div class="modal-body">
						            	<div class="row">
						                    <div class="col-md-12">
						                    	<div class="form-group">
					                                <label>热门类型</label>
					                                <select class="form-control" name="hotType" id="hotType">
					                                	<option value="0">主推</option>
					                                	<option value="1">次级推送</option>
					                                	<option value="2">三级推送</option>
					                                	<option value="3">轮播图</option>
					                                </select>
					                            </div>
						                    </div>
				                     	</div>
					                </div>
						            <div class="modal-footer">
						                <button type="button" class="btn btn-primary" id="saveHotModal">确定</button>
						                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeModal">关闭</button>
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
	</form>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

