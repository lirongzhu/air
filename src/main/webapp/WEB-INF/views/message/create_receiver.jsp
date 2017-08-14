<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<% 	request.setAttribute("vEnter", "\n"); %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script src="../assets/js/validate_tool.js"></script>

<link href="../assets/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet" />
<link href="../assets/js/Multiple-Dates-Picker-for-jQuery-UI-latest/jquery-ui.multidatespicker.css" rel="stylesheet" />

<script src="../assets/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="../assets/js/Multiple-Dates-Picker-for-jQuery-UI-latest/jquery-ui.multidatespicker.js"></script>

<script type="text/javascript">

	function Add(num){
		if(num==1){
			$('#roleModal').modal('show');
		}else{
			$('#staffModal').modal('show');
		}
	}
	
	/* function closeSoleModal(){
		
		$('#staffModal').modal('hide');
	} */
	
	$(function(){
		
		//角色选择
		$("#saveSoleModal").click(function(){

			$.ajax({
	            url: "saveSole.do",
	            type: "POST",
	            data: {
	            	templateId : $("#templateId").val(),
	            	userType : $("#userType").val()
	            },
	            success:function (data) {
	            	if(data == "1"){
	            		window.location.reload();
	            	}else if(data.indexOf("gotoLogin") > -1){
	            		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
	                }
	            },
	            error:function(data){
	            	 idgMessageObj.iMWarnAlert("网络异常！", 2000);
	            }
	        })
		})
		
		//人员选择
		$("#savestaffModal").click(function(){
			var list = "";
			$("input:checkbox").each(function(){
				if($(this).is(":checked")){
					list += ($(this).val()+";");
				}
			})
			$.ajax({
	            url: "saveReceiver.do",
	            type: "POST",
	            data: {
	            	templateId : $("#templateId").val(),
	            	userId : list
	            },
	            success:function (data) {
	            	if(data == "1"){
	            		window.location.reload();
	            	}else if(data.indexOf("gotoLogin") > -1){
	            		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
	                }
	            },
	            error:function(data){
	            	 idgMessageObj.iMWarnAlert("网络异常！", 2000);
	            }
	        })
		})
	})
	
	function depDelete(deleteId){
		
		$.ajax({
            url: "deleteReceiver.do",
            type: "GET",
            data: {
            	receiverId : deleteId
            },
            success:function (data) {
            	/* if(data == "1"){
            		window.location.reload();
            	}else if(data.indexOf("gotoLogin") > -1){
            		idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
                } */
                
            	if(data == 1){
		      		idgMessageObj.iMSuccessAlert("操作成功", 1000, function(){$("#queryForm").submit();});
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
	}
</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				短信接收 <small>编辑</small>
			</h1>
		</div>
	</div>
	<form id="queryForm" action="createReceiver.do" method="post" >
	
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
				 	<a href="javascript: Add(1)" class="btn btn-success">添加角色</a>
				 	<a href="javascript: Add(2)" class="btn btn-success">添加人员</a>
					<a href="./list.do" class="btn btn-primary">返回</a>
				</div>
				<div class="panel-body">
					<div>
					    <br>
					    	<input class="form-control" type="hidden" name="templateId" id=templateId value="${messageTemplate.templateId}">
					    	<div class="row">
			                    <div class="col-md-12">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">${messageTemplate.templateName}</font>
			                       </div>
			                    </div>
								<div class="col-md-12">
								  <div class="form-group">
								      <font size="4" face="verdana">${fn:replace(messageTemplate.templateContent,vEnter,"<BR/>") }</font>
								  </div>
								</div>
							</div>
					    </div>
					    <button style="display: none;" id="queryButton" name="queryButton">查询</button>
					    <div role="tabpanel" class="tab-pane active"  id="departure">
					    	<br>
					    	<div class="row">&nbsp;</div>
					    	<table class="table table-striped table-bordered table-hover" id="departurelistTable">
							<thead>
								<tr>
									<th>姓名</th>
									<th>角色</th>
									<th>电话号码</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="departure_tbody">
								<c:if test="${fn:length(pagedList.content) le 0}">
									<tr>
										<td colspan="11" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
								<c:forEach items="${pagedList.content}" var="messageReceiver" varStatus="status" >
									<tr data-id="${messageReceiver.receiverId}" class="
										<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
										<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
									">
										<td>${messageReceiver.user.username}</td>
										<td>${messageReceiver.user.userType}</td>
										<td>${messageReceiver.user.contactPhone}</td>
										<td>
											<a href="javascript:depDelete(${messageReceiver.receiverId});">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<tags:idgPage pagedList="${pagedList}"/>
			    	 	<div class="modal fade" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
						    <div class="modal-dialog">
						        <div class="modal-content">
						            <div class="modal-header">
						                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						                <h4 class="modal-title" id="myModalLabel">选择角色</h4>
						            </div>
						            <div class="modal-body">
						            	<div class="row">
					                     	 <div class="col-md-12">
						                       <div class="form-group">
						                          <select class="form-control"  name="userType" id="userType">
						                              <c:forEach items="${userType}" var="ut">
						                                  <option value="${ut}">${ut}</option>
						                              </c:forEach>
                              					  </select>
						                       </div>
					                     	</div>
					                     	</div>
						                </div>
							            <div class="modal-footer">
							                <button type="button" class="btn btn-primary" id="saveSoleModal">确定</button>
							                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeSoleModal">关闭</button>
							            </div>
						            </div>
						        </div>
						    </div>
						    <div class="modal fade" id="staffModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" style="height:500px;">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header">
							                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							                <h4 class="modal-title" id="myModalLabel">选择人员</h4>
							            </div>
							            <div class="modal-body" style="height:320px;overflow-y: auto;">
						                     	<table class="table table-striped table-bordered table-hover" id="listTable" style="height:470px;">
												<thead>
													<tr>
														<th style="width: 7%;"></th>
														<th >姓名</th>
														<th style="width: 25%;">电话</th>
														<th style="width: 25%;">角色</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${user}" var="user" varStatus="status" >
														<tr data-id="${user.id}" class="
															<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
															<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
														">
															<td style="text-align: center;"><input type="checkbox" value="${user.id}"/></td>
															<td>${user.contactName}</td>
															<td>${user.contactPhone}</td>
															<td>${user.userType}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
						                </div>
						            <div class="modal-footer">
						                <button type="button" class="btn btn-primary" id="savestaffModal">确定</button>
						                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeStaffModal">关闭</button>
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
</body>
</html>

