<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<% 	request.setAttribute("vEnter", "\n"); %>
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
		
		if(!validate_tool.isNotNull($("#routeName").val())){
			changeAlertContent("routeName", "路线名称必输!");
			$("#routeName").focus().css("border","1px solid red");
			return;
		}else if($("#routeName").parent("div").children(":first").next().is("span")){
			$("#routeName").parent("div").children(":first").next().remove();
			$("#routeName").css("border","1px solid #D0D0D0");
		}
		if(!validate_tool.isNotNull($("#productNumber").val())){
			changeAlertContent("productNumber", "产品编号必输!");
			$("#productNumber").focus().css("border","1px solid red");
			return;
		}else if($("#productNumber").parent("div").children(":first").next().is("span")){
			$("#productNumber").parent("div").children(":first").next().remove();
			$("#productNumber").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#pointOfDeparture").val())){
			changeAlertContent("pointOfDeparture", "出发地点必输!");
			$("#pointOfDeparture").focus().css("border","1px solid red");
			return;
		}else if($("#pointOfDeparture").parent("div").children(":first").next().is("span")){
			$("#pointOfDeparture").parent("div").children(":first").next().remove();
			$("#pointOfDeparture").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#destination").val())){
			changeAlertContent("destination", "目的地必输!");
			$("#destination").focus().css("border","1px solid red");
			return;
		}else if($("#destination").parent("div").children(":first").next().is("span")){
			$("#destination").parent("div").children(":first").next().remove();
			$("#destination").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#originalPrice").val())){
			changeAlertContent("originalPrice", "原价必输!");
			$("#originalPrice").focus().css("border","1px solid red");
			return;
		}else if($("#originalPrice").parent("div").children(":first").next().is("span")){
			$("#originalPrice").parent("div").children(":first").next().remove();
			$("#originalPrice").css("border","1px solid #D0D0D0");
		}
		
		/* if(!validate_tool.isNotNull($("#discount").val())){
			changeAlertContent("discount", "折扣必输!");
			$("#discount").focus().css("border","1px solid red");
			return;
		}else if($("#discount").parent("div").children(":first").next().is("span")){
			$("#discount").parent("div").children(":first").next().remove();
			$("#discount").css("border","1px solid #D0D0D0");
		} */
		
		if(!validate_tool.isNotNull($("#nowPrice").val())){
			changeAlertContent("nowPrice", "现价必输!");
			$("#nowPrice").focus().css("border","1px solid red");
		}else if($("#nowPrice").parent("div").children(":first").next().is("span")){
			$("#nowPrice").parent("div").children(":first").next().remove();
			$("#nowPrice").css("border","1px solid #D0D0D0");
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
		
		$("#textDescription").val(ueContet);
		$("#examineResult").val("9");
		$("#submitForm").submit();
	}
	
	function formSubmit(){
		var ueContet = UE.getEditor('editor').getContent();
		
		if(!validate_tool.isNotNull($("#routeName").val())){
			changeAlertContent("routeName", "路线名称必输!");
			$("#routeName").focus().css("border","1px solid red");
			return;
		}else if($("#routeName").parent("div").children(":first").next().is("span")){
			$("#routeName").parent("div").children(":first").next().remove();
			$("#routeName").css("border","1px solid #D0D0D0");
		}
		if(!validate_tool.isNotNull($("#productNumber").val())){
			changeAlertContent("productNumber", "产品编号必输!");
			$("#productNumber").focus().css("border","1px solid red");
			return;
		}else if($("#productNumber").parent("div").children(":first").next().is("span")){
			$("#productNumber").parent("div").children(":first").next().remove();
			$("#productNumber").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#pointOfDeparture").val())){
			changeAlertContent("pointOfDeparture", "出发地点必输!");
			$("#pointOfDeparture").focus().css("border","1px solid red");
			return;
		}else if($("#pointOfDeparture").parent("div").children(":first").next().is("span")){
			$("#pointOfDeparture").parent("div").children(":first").next().remove();
			$("#pointOfDeparture").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#destination").val())){
			changeAlertContent("destination", "目的地必输!");
			$("#destination").focus().css("border","1px solid red");
			return;
		}else if($("#destination").parent("div").children(":first").next().is("span")){
			$("#destination").parent("div").children(":first").next().remove();
			$("#destination").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#originalPrice").val())){
			changeAlertContent("originalPrice", "原价必输!");
			$("#originalPrice").focus().css("border","1px solid red");
			return;
		}else if($("#originalPrice").parent("div").children(":first").next().is("span")){
			$("#originalPrice").parent("div").children(":first").next().remove();
			$("#originalPrice").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#discount").val())){
			changeAlertContent("discount", "折扣必输!");
			$("#discount").focus().css("border","1px solid red");
			return;
		}else if($("#discount").parent("div").children(":first").next().is("span")){
			$("#discount").parent("div").children(":first").next().remove();
			$("#discount").css("border","1px solid #D0D0D0");
		}
		
		if(!validate_tool.isNotNull($("#nowPrice").val())){
			changeAlertContent("nowPrice", "现价必输!");
			$("#nowPrice").focus().css("border","1px solid red");
		}else if($("#nowPrice").parent("div").children(":first").next().is("span")){
			$("#nowPrice").parent("div").children(":first").next().remove();
			$("#nowPrice").css("border","1px solid #D0D0D0");
		}
		
		$("#textDescription").val(ueContet);
		$("#examineResult").val("10");
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
				行程发布 <small>编辑</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="./save.do" method="post" enctype="multipart/form-data">
	
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
				 	<a href="javascript: formSave();" class="btn btn-success">保存</a>
				 	<a href="javascript: formSubmit();" class="btn btn-success">提交审核</a>
					<a href="./list.do" class="btn btn-primary">返回</a>
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
					    	<script type="text/javascript">
					    		
						    	$(function(){
						    		
						    		changeRouteDays();
						    		
						    		$("#routeDays").change(function(){
						    			changeRouteDays();
						    		})
						    	})
						    	
						    	function changeRouteDays(){
						    		
						    		var routeDays = $("#routeDays").val();
					    			var daysHtml = ""
					    			for(var i = 1; i<= routeDays; i ++){
					    				daysHtml += ("<option value="+i+"> 第" +i+ "天 </option>");
					    			}
					    			
					    			$("#daysDialog").empty();
					    			$("#daysDialog").append(daysHtml);
						    	}
						    	
						    	function addKeyWord(){
						    		IDDialog.closeSetValue = "keyWord";
						    		IDDialog.openDialog();
						    	}
						    		
					    	</script>
					    	
					    	<input type="hidden" name="itineraryId" id="itineraryId" value="${itinerary.itineraryId}">
					    	<input type="hidden" name="createDate" id="createDate" value="<fmt:formatDate value="${itinerary.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					    	<input type="hidden" name="createUser.id" id="createUserId" value="${itinerary.createUser.id}">
					    	<input type="hidden" name="enable" id="enable" value="${itinerary.enable}">
					    	<input type="hidden" name="examineResult.itemId" id="examineResult" value="${itinerary.examineResult.itemId}"/>
					    	
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <label>路线名称<font style="color: red">&nbsp;*&nbsp;</font></label>
			                           <input class="form-control" placeholder="路线名称" id="routeName" name="routeName" value="${itinerary.routeName}" maxlength="256">
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <label>产品编号<font style="color: red">*</font></label>
			                          <input class="form-control" placeholder="产品编号" id="productNumber" name="productNumber" value="${itinerary.productNumber}" maxlength="32">
			                       </div>
			                    </div>
							 	<div class="col-md-6">
								 	<div class="form-group">
			                           <label>出发地点<font style="color: red">*</font></label>
			                           <input class="form-control" placeholder="出发地点" id="pointOfDeparture" name="pointOfDeparture" value="${itinerary.pointOfDeparture}" maxlength="256">
			                       </div>
		                       </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                           <label>目的地<font style="color: red">*</font></label>
			                           <input class="form-control" placeholder="目的地" id="destination" name="destination" value="${itinerary.destination}" maxlength="256">
			                       </div>
			                    </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>行程类型</label>
		                                <select class="form-control" name="tourismType.itemId">
		                                	<c:forEach var="tourismType" items="${tourismType}">
		                                		<option value="${tourismType.itemId}" 
		                                			<c:if test="${itinerary.tourismType.itemId == tourismType.itemId}">selected="selected"</c:if>>${tourismType.fullName}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>产品类别</label>
		                                <select class="form-control" name="rangType.itemId">
		                                	<c:forEach var="rangType" items="${rangType}">
		                                		<option value="${rangType.itemId}" 
		                                			<c:if test="${itinerary.rangType.itemId == rangType.itemId}">selected="selected"</c:if>>${rangType.fullName}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>所需证件</label>
		                                <select class="form-control" name="cardType.itemId">
		                                	<c:forEach var="cardType" items="${cardType}">
		                                		<option value="${cardType.itemId}" 
		                                			<c:if test="${itinerary.cardType.itemId == cardType.itemId}">selected="selected"</c:if>>${cardType.fullName}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>所属模块</label>
		                                <select class="form-control" name="belongFunction.itemId">
		                                	<c:forEach var="belongFunction" items="${belongFunction}">
		                                		<option value="${belongFunction.itemId}" 
		                                			<c:if test="${itinerary.belongFunction.itemId == belongFunction.itemId}">selected="selected"</c:if>>${belongFunction.fullName}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
		                       <div class="col-md-6">
		                       		<div class="form-group">
		                                <label>行程天数</label>
		                                <select class="form-control" name="routeDays" id="routeDays">
		                                	<c:forEach var="routeDay" items="${routeDays}">
		                                		<option value="${routeDay}" 
		                                			<c:if test="${itinerary.routeDays == routeDay}">selected="selected"</c:if>>${routeDay}
		                                		</option>
		                                	</c:forEach>
		                                </select>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
								 	<div class="form-group">
			                           <label>原价<font style="color: red">*</font></label>
			                           <input class="form-control" placeholder="原价" id="originalPrice" name="originalPrice" value="${itinerary.originalPrice}" maxlength="14">
			                       </div>
		                       </div>
		                       <div class="col-md-6">
								 	<div class="form-group">
			                           <label>折扣</label>
			                           <input class="form-control" placeholder="折扣" id="discount" name="discount" value="${itinerary.discount}" maxlength="3">
			                       </div>
		                       </div>
		                       <div class="col-md-6">
								 	<div class="form-group">
			                           <label>现价<font style="color: red">*</font></label>
			                           <input class="form-control" placeholder="现价" id="nowPrice" name="nowPrice" value="${itinerary.nowPrice}" maxlength="14">
			                       </div>
		                       </div>
		                       <div class="col-md-12">
								  <div class="form-group">
								      <label>关键字</label>
								      <label><a href="javaScript:addKeyWord();">添加</a></label>
								      <textarea class="form-control" rows="3" placeholder="关键字" id="keyWord" name=keyWord maxlength="255">${itinerary.keyWord}</textarea>
								  </div>
								</div>
								<div class="col-md-12">
								  <div class="form-group">
						    			<label>路线简介</label>
								      <textarea class="form-control" rows="3" placeholder="路线简介" id="routeIntroduction" name=routeIntroduction maxlength="256">${itinerary.routeIntroduction}</textarea>
								  </div>
								</div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="feature">
					    
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
							                'simpleupload','insertimage','emotion','date', 'time','|',
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
					    	<input type="hidden" name="tourismFeature.textDescription" id="textDescription"/>
					    	<input type="hidden" name="tourismFeature.featureId" id="featureId" value="${feature.featureId}"/>
					    	<div style="display: none;" id="textDescriptionHiddenDiv">${feature.textDescription}</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="scheduling">
					    	<script type="text/javascript">
					    	
						    	$(function(){
						    		
						    		$("#closeSchedulingModal").click(function(){
						    			$('#schedulingModal').modal('hide');
						    			 
						    			//$("#daysDialog").val("");
						    			$("#trafficDialog").val("");
						    			$("#stayDialog").val("");
						    			$("#repastDialog").val("");
						    			$("#attractionsDialog").val("");
						    			
						    			$("#openSchDialogModifyIndex").val("");
						    		})
						    		
						    		$("#saveSchedulingModal").click(function(){
						    			
						    			var subscriptSch = $("#scheduling_tbody > tr:last-child").data("index");
						    			
						    			if(typeof subscriptSch == "undefined"){
						    				subscriptSch = 0;
						    			}else{
						    				subscriptSch += 1;
						    			}
						    			
						    			//新增TR
						    			if($("#openSchDialogModifyIndex").val() == ""){
						    				
						    				var schedulingHtml = "<tr data-index='" + subscriptSch + "'>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Days' name='schedulingList["+subscriptSch+"].days' type='hidden' value='"+ $("#daysDialog").val() +"'>第" + $("#daysDialog").val() + "天</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Traffic' name='schedulingList["+subscriptSch+"].traffic' type='hidden' value='"+ $("#trafficDialog").val() +"'>" + $("#trafficDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Stay' name='schedulingList["+subscriptSch+"].stay' type='hidden' value='"+ $("#stayDialog").val() +"'>" + $("#stayDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Repast' name='schedulingList["+subscriptSch+"].repast' type='hidden' value='"+ $("#repastDialog").val() +"'>" + $("#repastDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Attractions' name='schedulingList["+subscriptSch+"].attractions' type='hidden' value='"+ $("#attractionsDialog").val() +"'>" + $("#attractionsDialog").val() + "</td>" +
		    									"<td>" + 
			    									"<a href='javaScript:schModify("+subscriptSch+")'>编辑</a>&nbsp;" + 
													"<a href='javaScript:schDelete("+subscriptSch+")'>删除</a>" + 
												"</td>" +
		    								"</tr>";
	    								
	    									$("#scheduling_tbody").append(schedulingHtml);
						    			}
						    			//新增TD
						    			else{
						    				
						    				subscriptSch = $("#openSchDialogModifyIndex").val();
						    				var oldSchId = $("#schedulingList"+subscriptSch+"SchedulingId").val();
						    				
						    				var schedulingHtml = 	
						    					"<td><input id='schedulingList"+subscriptSch+"Days' name='schedulingList["+subscriptSch+"].days' type='hidden' value='"+ $("#daysDialog").val() +"'>第" + $("#daysDialog").val() + "天</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Traffic' name='schedulingList["+subscriptSch+"].traffic' type='hidden' value='"+ $("#trafficDialog").val() +"''>" + $("#trafficDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Stay' name='schedulingList["+subscriptSch+"].stay' type='hidden' value='"+ $("#stayDialog").val() +"'>" + $("#stayDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Repast' name='schedulingList["+subscriptSch+"].repast' type='hidden' value='"+ $("#repastDialog").val() +"'>" + $("#repastDialog").val() + "</td>" + 
		    									"<td><input id='schedulingList"+subscriptSch+"Attractions' name='schedulingList["+subscriptSch+"].attractions' type='hidden' value='"+ $("#attractionsDialog").val() +"'>" + $("#attractionsDialog").val() + "</td>" +
		    									"<td>";
		    									
		    									 if(typeof oldSchId != "undefined"){
		    										 schedulingHtml += "<input id='schedulingList"+subscriptSch+"SchedulingId' name='schedulingList["+subscriptSch+"].schedulingId' type='hidden' value='"+oldSchId+"'>";
		    									 }else{
		    										 schedulingHtml += "<input id='schedulingList"+subscriptSch+"SchedulingId' name='schedulingList["+subscriptSch+"].schedulingId' type='hidden' value=''>";
		    									 }
		    										
		    									 schedulingHtml += ("<a href='javaScript:schModify("+subscriptSch+")'>编辑</a>&nbsp;" + 
													"<a href='javaScript:schDelete("+subscriptSch+")'>删除</a>" + 
												"</td>");
		    									
		    								$("#scheduling_tbody > [data-index="+subscriptSch+"]").empty();
		    								$("#scheduling_tbody > [data-index="+subscriptSch+"]").append(schedulingHtml);
						    			}
						    			
						    			$('#schedulingModal').modal('hide');
						    			
						    			//$("#daysDialog").val("");
						    			$("#trafficDialog").val("");
						    			$("#stayDialog").val("");
						    			$("#repastDialog").val("");
						    			$("#attractionsDialog").val("");
						    			
						    			$("#openSchDialogModifyIndex").val("");
						    		})
						    	})
						    	
						    	function schedulingAdd(){
						    		$('#schedulingModal').modal('show');
						    	}
						    	
						    	function schModify(tabIndex){

						    		$("#daysDialog").val($("#schedulingList"+tabIndex+"Days").val());
					    			$("#trafficDialog").val($("#schedulingList"+tabIndex+"Traffic").val());
					    			$("#stayDialog").val($("#schedulingList"+tabIndex+"Stay").val());
					    			$("#repastDialog").val($("#schedulingList"+tabIndex+"Repast").val());
					    			$("#attractionsDialog").val($("#schedulingList"+tabIndex+"Attractions").val());
					    			
					    			$("#openSchDialogModifyIndex").val(tabIndex);
					    			
					    			$('#schedulingModal').modal('show');
						    	}
						    	
						    	function schDelete(tabIndex){
						    		
						    		//没有ID 直接删除行 有ID 访问ajax删除后台
						    		if(typeof $("#schedulingList"+tabIndex+"SchedulingId").val() != "undefined"
						    				&& $("#schedulingList"+tabIndex+"SchedulingId").val() != ""){
						    			var deleteId = $("#schedulingList"+tabIndex+"SchedulingId").val();

						    			$.ajax({
						    	             url: "deleteItem.do",
						    	             type: "GET",
						    	             contentType:'text/html;charset=UTF-8', 
						    	             data: {
						    	            	 deleteId : deleteId,
						    	            	 deleteType : "scheduling"
						    	             },
						    	             success:function (data) {

						    	                 if(data == "1"){
						    	                	 idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){  $("#scheduling_tbody > [data-index="+tabIndex+"]").remove();});
						    	                 }else if(data.indexOf("gotoLogin") > -1){
						    	                	 idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
						    	                 }else{
						    	                	 idgMessageObj.iMWarnAlert("操作失败", 2000);
						    	                 }
						    	             }
						    	         })
						    		}else{
						    			$("#scheduling_tbody > [data-index="+tabIndex+"]").remove();
						    		}
						    	}
					    	</script>
					    	<br>
					    	<a href="javascript: schedulingAdd()" class="btn btn-success">添加</a>
					    	<div class="row">&nbsp;</div>
					    	<input type="hidden" id="openSchDialogModifyIndex">
					    	<table class="table table-striped table-bordered table-hover" id="schedulinglistTable">
							<thead>
								<tr>
									<th style="width=10%;">天数</th>
									<th style="width=10%;">交通</th>
									<th style="width=10%;">住宿</th>
									<th style="width=10%;">餐饮</th>
									<th >景点</th>
									<th style="width=10%;">操作</th>
								</tr>
							</thead>
							<tbody id="scheduling_tbody">
								<c:forEach items="${schedulingList}" var="scheduling" varStatus="schStatus">
									<tr data-index="${schStatus.index}">
										<td>
											第<c:out value="${scheduling.days}"/>天
											<input id='schedulingList${schStatus.index}Days' name='schedulingList[${schStatus.index}].days' type='hidden' value="${scheduling.days}">
										</td>
										<td>
											<c:out value="${scheduling.traffic}"/>
											<input id='schedulingList${schStatus.index}Traffic' name='schedulingList[${schStatus.index}].traffic' type='hidden' value="${scheduling.traffic}">
										</td>
										<td>
											<c:out value="${scheduling.stay}"/>
											<input id='schedulingList${schStatus.index}Stay' name='schedulingList[${schStatus.index}].stay' type='hidden' value="${scheduling.stay}">
										</td>
										<td>
											<c:out value="${scheduling.repast}"/>
											<input id='schedulingList${schStatus.index}Repast' name='schedulingList[${schStatus.index}].repast' type='hidden' value="${scheduling.repast}">
										</td>
										<td>
											<c:out value="${scheduling.attractions}"/>
											<input id='schedulingList${schStatus.index}Attractions' name='schedulingList[${schStatus.index}].attractions' type='hidden' value="${scheduling.attractions}">
										</td>
										<td>
											<input id='schedulingList${schStatus.index}SchedulingId' name='schedulingList[${schStatus.index}].schedulingId' type='hidden' value="${scheduling.schedulingId}">
											<a href="javascript:schModify(${schStatus.index});">编辑</a>
											<a href="javascript:schDelete(${schStatus.index});">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				    	 	<div class="modal fade" id="schedulingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header">
							                <h4 class="modal-title" id="myModalLabel">行程安排</h4>
							            </div>
							            <div class="modal-body">
							            	<div class="row">
							                    <div class="col-md-12">
							                    	<div class="form-group">
							                           <select class="form-control" name="daysDialog" id="daysDialog">
							                           		<option value="1">第1天</option>
						                                </select>
							                       </div>
							                    </div>
							                    <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="2" placeholder="交通" name="trafficDialog" id="trafficDialog"></textarea>
							                       </div>
						                     	</div>
						                     	 <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="2" placeholder="住宿" name="stayDialog" id="stayDialog"></textarea>
							                       </div>
						                     	</div>
						                     	 <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="2" placeholder="餐饮" name="repastDialog" id="repastDialog"></textarea>
							                       </div>
						                     	</div>
						                     	 <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="2" placeholder="景点" name="attractionsDialog" id="attractionsDialog"></textarea>
							                       </div>
						                     	</div>
							                </div>
							            </div>
							            <div class="modal-footer">
							                <button type="button" class="btn btn-primary" id="saveSchedulingModal">确定</button>
							                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeSchedulingModal">关闭</button>
							            </div>
							        </div>
							    </div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="cost_statement">
					    	<script type="text/javascript">
					    	
						    	$(function(){
						    		
						    		$("#closeCostStatementModal").click(function(){
						    			$('#costStatementModal').modal('hide');
						    			 
						    			$("#costStatementDialog").val("");
							    			
						    			$("#openCostDialogModifyIndex").val("");
						    		})
						    		
						    		$("#saveCostStatementModal").click(function(){
						    			
						    			if(!validate_tool.isNotNull($("#costStatementDialog").val())){
						    				changeAlertContent("costStatementDialog", "费用说明必输!");
						    				$("#costStatementDialog").focus().css("border","1px solid red");
						    				return;
						    			}else if($("#costStatementDialog").parent("div").children(":first").next().is("span")){
						    				$("#costStatementDialog").parent("div").children(":first").next().remove();
						    				$("#costStatementDialog").css("border","1px solid #D0D0D0");
						    			}

						    			var subscriptCost = $("#costStatement_tbody > tr:last-child").data("index");

						    			if(typeof subscriptCost == "undefined"){
						    				subscriptCost = 0;
						    			}else{
						    				subscriptCost += 1;
						    			}
						    			
						    			//新增TR
						    			if($("#openCostDialogModifyIndex").val() == ""){
						    				
						    				var costStatementHtml = 
						    					"<tr data-index='" + subscriptCost + "'>" + 
			    									"<td><input id='costStatementList"+subscriptCost+"CostStatement' name='costStatementList["+subscriptCost+"].costStatement' type='hidden' value='"+ $("#costStatementDialog").val() +"'>" + $("#costStatementDialog").val().replace(new RegExp("\n",'gm'),'<br/>') + "</td>" + 
			    									"<td>" + 
				    									"<a href='javaScript:costModify("+subscriptCost+")'>编辑</a>&nbsp;" + 
														"<a href='javaScript:costDelete("+subscriptCost+")'>删除</a>" + 
													"</td>" +
			    								"</tr>";
		    								
		    								$("#costStatement_tbody").append(costStatementHtml);
						    			}
						    			//新增TD
						    			else{
						    				
						    				subscriptCost = $("#openCostDialogModifyIndex").val();
						    				var oldSchId = $("#costStatementList"+subscriptCost+"costStatementId").val();
						    				
						    				var costModifyVal = $("#costStatementList"+subscriptCost+"CostStatementId").val();
						    				
						    				var costStatementHtml = 	
						    					"<td><input id='costStatementList"+subscriptCost+"CostStatement' name='costStatementList["+subscriptCost+"].costStatement' type='hidden' value='"+ $("#costStatementDialog").val() +"'>" + $("#costStatementDialog").val().replace(new RegExp("\n",'gm'),'<br/>') + "</td>" + 
		    									"<td>";
		    									
		    									  if(typeof costModifyVal == "undefined"){
		    										  costStatementHtml += "<input id='costStatementList"+subscriptCost+"CostStatementId' name='costStatementList["+subscriptCost+"].costStatementId' type='hidden' value=''>"
		    									  }else{
		    										  costStatementHtml += "<input id='costStatementList"+subscriptCost+"CostStatementId' name='costStatementList["+subscriptCost+"].costStatementId' type='hidden' value="+costModifyVal+">"
		    									  }
		    									  
		    									  costStatementHtml +=( 
			    									"<a href='javaScript:costModify("+subscriptCost+")'>编辑</a>&nbsp;" + 
													"<a href='javaScript:costDelete("+subscriptCost+")'>删除</a>" + 
												"</td>");
		    									
		    								$("#costStatement_tbody > [data-index="+subscriptCost+"]").empty();
		    								$("#costStatement_tbody > [data-index="+subscriptCost+"]").append(costStatementHtml);
						    			}
						    			
						    			$('#costStatementModal').modal('hide');
						    			
						    			$("#costStatementDialog").val("");
						    			$("#openCostDialogModifyIndex").val("");
						    			
						    		})
						    	})
						    	
						    	function costStatementAdd(){
						    		$('#costStatementModal').modal('show');
						    	}
						    	
								function costModify(tabIndex){
						    		
					    			$("#costStatementDialog").val($("#costStatementList"+tabIndex+"CostStatement").val());
					    			
					    			$("#openCostDialogModifyIndex").val(tabIndex);
					    			
					    			$('#costStatementModal').modal('show');
						    	}
						    	
						    	function costDelete(tabIndex){
						    		
						    		//没有ID 直接删除行 有ID 访问ajax删除后台
						    		if(typeof $("#costStatementList"+tabIndex+"CostStatementId").val() != "undefined"){
						    			var deleteId = $("#costStatementList"+tabIndex+"CostStatementId").val();

						    			$.ajax({
						    	             url: "deleteItem.do",
						    	             type: "GET",
						    	             contentType:'text/html;charset=UTF-8', 
						    	             data: {
						    	            	 deleteId : deleteId,
						    	            	 deleteType : "costStatement"
						    	             },
						    	             success:function (data) {

						    	                 if(data == "1"){
						    	                	 idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){ $("#costStatement_tbody > [data-index="+tabIndex+"]").remove();});
						    	                 }else if(data.indexOf("gotoLogin") > -1){
						    	                	 idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
						    	                 }else{
						    	                	 idgMessageObj.iMWarnAlert("操作失败", 2000);
						    	                 }
						    	             }
						    	         })
						    		}else{
						    			$("#costStatement_tbody > [data-index="+tabIndex+"]").remove();
						    		}
						    	}
					    	</script>
					    	<br>
					    	<a href="javascript: costStatementAdd()" class="btn btn-success">添加</a>
					    	<div class="row">&nbsp;</div>
					    	<input type="hidden" id="openCostDialogModifyIndex">
					    	
					    	<table class="table table-striped table-bordered table-hover" id="costStatementlistTable">
								<thead>
									<tr>
										<th style="width: 85%;">费用说明</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="costStatement_tbody">
									<c:forEach items="${costStatementList}"  var="costStatement" varStatus="costStatus">
										<tr data-index="${costStatus.index}">
											<td>
												${fn:replace(costStatement.costStatement,vEnter,"<BR/>") }
												<input id='costStatementList${costStatus.index}CostStatement' name='costStatementList[${costStatus.index}].costStatement' type='hidden' value="${costStatement.costStatement}">
											</td>
											<td>
												<input id='costStatementList${costStatus.index}CostStatementId' name='costStatementList[${costStatus.index}].costStatementId' type='hidden' value="${costStatement.costStatementId}">
												<a href="javaScript:costModify(${costStatus.index})">编辑</a>
												<a href="javaScript:costDelete(${costStatus.index})">删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				    	 	<div class="modal fade" id="costStatementModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header">
							                <h4 class="modal-title" id="myModalLabel">费用说明</h4>
							            </div>
							            <div class="modal-body">
							            	<div class="row">
							                    <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="5" placeholder="费用说明" name="costStatementDialog" id="costStatementDialog" maxlength="250"></textarea>
							                       </div>
						                     	</div>
							                </div>
							            </div>
							            <div class="modal-footer">
							                <button type="button" class="btn btn-primary" id="saveCostStatementModal">确定</button>
							                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeCostStatementModal">关闭</button>
							            </div>
							        </div>
							    </div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="special_notice">
					    	<script type="text/javascript">
					    	
						    	$(function(){
						    		
						    		$("#closeSpecialNoticeModal").click(function(){
						    			 $('#specialNoticeModal').modal('hide');
						    			 $("#specialNoticeDialog").val("");
						    			 $("#openSpeDialogModifyIndex").val("");
						    		})
						    		
						    		$("#saveSpecialNoticeModal").click(function(){
						    			
						    			if(!validate_tool.isNotNull($("#specialNoticeDialog").val())){
						    				changeAlertContent("specialNoticeDialog", "特别提示必输!");
						    				$("#specialNoticeDialog").focus().css("border","1px solid red");
						    				return;
						    			}else if($("#specialNoticeDialog").parent("div").children(":first").next().is("span")){
						    				$("#specialNoticeDialog").parent("div").children(":first").next().remove();
						    				$("#specialNoticeDialog").css("border","1px solid #D0D0D0");
						    			}
						    			
						    			var subscriptSpe = $("#specialNotice_tbody > tr:last-child").data("index");
						    			
						    			if(typeof subscriptSpe == "undefined"){
						    				subscriptSpe = 0;
						    			}else{
						    				subscriptSpe += 1;
						    			}
						    			
						    			//新增TR
						    			if($("#openSpeDialogModifyIndex").val() == ""){
							    			var specialNoticeHtml = 
							    				"<tr data-index='" + subscriptSpe + "'>" + 
			    									"<td><input id='specialNoticeList"+subscriptSpe+"SpecialNotice' name='specialNoticeList["+subscriptSpe+"].specialNotice' type='hidden' value='"+ $("#specialNoticeDialog").val() +"'>" + $("#specialNoticeDialog").val().replace(new RegExp("\n",'gm'),'<br/>') + "</td>" + 
			    									"<td>" + 
				    									"<a href='javaScript:speModify("+subscriptSpe+")'>编辑</a>&nbsp;" + 
														"<a href='javaScript:speDelete("+subscriptSpe+")'>删除</a>" + 
													"</td>" +
			    								"</tr>";
								    								
							    			$("#specialNotice_tbody").append(specialNoticeHtml);
						    			}
						    			//新增TD
						    			else{
						    				
						    				subscriptSpe = $("#openSpeDialogModifyIndex").val();
						    				var oldSpeId = $("#specialNoticeList"+subscriptSpe+"SpecialNoticeId").val();
						    				
						    				var specialNoticeHtml = 	
						    					"<td><input id='specialNoticeList"+subscriptSpe+"SpecialNotice' name='specialNoticeList["+subscriptSpe+"].specialNotice' type='hidden' value='"+ $("#specialNoticeDialog").val() +"'>" + $("#specialNoticeDialog").val().replace(new RegExp("\n",'gm'),'<br/>') + "</td>" + 
		    									"<td>";
		    									
		    								if(typeof oldSpeId == "undefined"){
		    									specialNoticeHtml += "<input id='specialNoticeList"+subscriptSpe+"SchedulingId' name='specialNoticeList["+subscriptSpe+"].specialNoticeId' type='hidden' value=''>"
		    								}else{
		    									specialNoticeHtml += "<input id='specialNoticeList"+subscriptSpe+"SchedulingId' name='specialNoticeList["+subscriptSpe+"].specialNoticeId' type='hidden' value='"+oldSpeId+"'>"
		    								}
		    									
		    								specialNoticeHtml += (	
		    									"<a href='javaScript:speModify("+subscriptSpe+")'>编辑</a>&nbsp;" + 
												"<a href='javaScript:speDelete("+subscriptSpe+")'>删除</a>" + 
											"</td>");
		    									
		    								$("#specialNotice_tbody > [data-index="+subscriptSpe+"]").empty();
		    								$("#specialNotice_tbody > [data-index="+subscriptSpe+"]").append(specialNoticeHtml);
						    			}
						    			
						    			$('#specialNoticeModal').modal('hide');
						    			
						    			$("#specialNoticeDialog").val("");
						    			$("#openSpeDialogModifyIndex").val("");
						    			
						    		})
						    	})
						    	
						    	function specialNoticeAdd(){
						    		$('#specialNoticeModal').modal('show');
						    	}
						    	
								function speModify(tabIndex){
						    		
					    			$("#specialNoticeDialog").val($("#specialNoticeList"+tabIndex+"SpecialNotice").val());
					    			
					    			$("#openSpeDialogModifyIndex").val(tabIndex);
					    			
					    			$('#specialNoticeModal').modal('show');
						    	}
						    	
						    	function speDelete(tabIndex){
						    		
						    		//没有ID 直接删除行 有ID 访问ajax删除后台
						    		if(typeof $("#specialNoticeList"+tabIndex+"SpecialNoticeId").val() != "undefined"){
						    			var deleteId = $("#specialNoticeList"+tabIndex+"SpecialNoticeId").val();

						    			$.ajax({
						    	             url: "deleteItem.do",
						    	             type: "GET",
						    	             contentType:'text/html;charset=UTF-8', 
						    	             data: {
						    	            	 deleteId : deleteId,
						    	            	 deleteType : "specialNotice"
						    	             },
						    	             success:function (data) {

						    	                 if(data == "1"){
						    	                	 idgMessageObj.iMSuccessAlert("操作成功", 2000, function(){ $("#specialNotice_tbody > [data-index="+tabIndex+"]").remove();});
						    	                 }else if(data.indexOf("gotoLogin") > -1){
						    	                	 idgMessageObj.iMWarnAlert("用户连接超时，请重新登陆！", 2000);
						    	                 }else{
						    	                	 idgMessageObj.iMWarnAlert("操作失败", 2000);
						    	                 }
						    	             }
						    	         })
						    		}else{
						    			$("#specialNotice_tbody > [data-index="+tabIndex+"]").remove();
						    		}
						    	}
					    	</script>
					    	<br>
					    	<a href="javascript: specialNoticeAdd()" class="btn btn-success">添加</a>
					    	<div class="row">&nbsp;</div>
					    	<input type="hidden" id="openSpeDialogModifyIndex">
					    	<table class="table table-striped table-bordered table-hover" id="specialNoticelistTable">
								<thead>
									<tr>
										<th style="width: 85%;">特别提示</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="specialNotice_tbody">
									<c:forEach items="${specialNoticeList}"  var="specialNotice"  varStatus="speStatus">
										<tr data-index="${speStatus.index}">
											<td>
												${fn:replace(specialNotice.specialNotice,vEnter,"<BR/>") }
												<input id='specialNoticeList${speStatus.index}SpecialNotice' name='specialNoticeList[${speStatus.index}].specialNotice' type='hidden' value="${specialNotice.specialNotice}">
											</td>
											<td>
												<input id='specialNoticeList${speStatus.index}SpecialNoticeId' name='specialNoticeList[${speStatus.index}].specialNoticeId' type='hidden' value="${specialNotice.specialNoticeId}">
												<a href="javaScript:speModify(${speStatus.index})">编辑</a>
												<a href="javaScript:speDelete(${speStatus.index})">删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				    	 	<div class="modal fade" id="specialNoticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header">
							                <h4 class="modal-title" id="myModalLabel">特别提示</h4>
							            </div>
							            <div class="modal-body">
							            	<div class="row">
							                    <div class="col-md-12">
							                       <div class="form-group">
							                           <textarea class="form-control" rows="5" placeholder="特别提示" name="specialNoticeDialog" id="specialNoticeDialog" maxlength="250"></textarea>
							                       </div>
						                     	</div>
							                </div>
							            </div>
							            <div class="modal-footer">
							                <button type="button" class="btn btn-primary" id="saveSpecialNoticeModal">确定</button>
							                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeSpecialNoticeModal">关闭</button>
							            </div>
							        </div>
							    </div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="image_upload">
					    	<br>
					    	<div class="row">&nbsp;</div>
					    	<input type="hidden" name="pictureRelativePath" id="pictureRelativePath" value="${itinerary.pictureRelativePath}"/>
					    	<div class="row">
			                    <div class="col-md-12">
			                    	<div class="form-group">
			                           <label>背景上传</label>
                                       <input type="file" name="imageFile" id="imageFile"/>
			                       </div>
			                    </div>
			                    <div class="col-md-12">
			                    	<c:if test="${itinerary.pictureRelativePath != '' && itinerary.pictureRelativePath != null}">
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
		</div>
	</div>
	</div>
	</form>
	<jsp:include page="/WEB-INF/views/commons/dictionary_dialog.jsp"></jsp:include>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

