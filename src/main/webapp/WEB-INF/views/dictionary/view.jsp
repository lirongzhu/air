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
	    
	});
	
</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<form action="./listItem.do?dictionaryId=${dictionary.dictionaryId}" method="post" name="queryForm" id="queryForm">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				字典管理 <small>列表</small>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="./list.do" class="btn btn-primary">返回</a>
				</div>
				<div class="panel-body">
					<div class="table-responsive" style="overflow-x: hidden;">
						<div class="row">
							<div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">中文简称 : ${dictionary.shortName}</font>
		                       </div>
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">中文全称 : ${dictionary.shortName}</font>
		                       </div>
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">英文简称 : ${dictionary.engShortName}</font>
		                       </div>
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">英文全称 : ${dictionary.engName}</font>
		                       </div>
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">关键值 : ${dictionary.dictionaryKey}</font>
		                       </div>
		                    </div>
		                    <div class="col-md-6">
		                    	<div class="form-group">
		                    		<font size="4" face="verdana">类型: ${dictionary.dictionaryType}</font>
		                       </div>
		                    </div>
						</div>
						<br>
						<table class="table table-striped table-bordered table-hover" id="listTable">
							<thead>
								<tr>
									<th>中文简称</th>
									<th>中文全称</th>
									<th>英文简称</th>
									<th>英文全称</th>
									<th>关键值</th>
									<th>显示顺序</th>
									<th>是否启用</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(dictionaryItemList) le 0}">
									<tr>
										<td colspan="11" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
								<c:forEach items="${dictionaryItemList}" var="dictionaryItem" varStatus="status" >
									<tr data-id="${dictionaryItem.itemId}" class="
										<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
										<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
									">
										<td>${dictionaryItem.shortName}</td>
										<td>${dictionaryItem.fullName}</td>
										<td>${dictionaryItem.engShortName}</td>
										<td>${dictionaryItem.engName}</td>
										<td>${dictionaryItem.itemKey}</td>
										<td>${dictionaryItem.itemSort}</td>
										<td>${dictionaryItem.enable}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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

