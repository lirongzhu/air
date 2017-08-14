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
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
<tags:idgMessage warnMessage="${warnMessage}" successMessage="${successMessage}" 
	infoMessage="${infoMessage}" dangerMessage="${dangerMessage}" />
	<form action="./list.do" method="post" name="submitForm" id="submitForm">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				订单查看 <small>列表</small>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="table-responsive" style="overflow-x: hidden;">
						<div class="row">
							<div class="col-sm-7">
							</div>
							<div class="col-sm-4">
								<input type="input" class="form-control" placeholder="客户姓名/客户联系方式/路线编号/路线内容"  name="serach_content" value="${param.serach_content}">
								
							</div>
							<div class="col-sm-1">
								<button class="btn btn-primary" id="queryButton" name="queryButton">查询</button>
							</div>
						</div>
						<br>
						<table class="table table-striped table-bordered table-hover" id="listTable">
							<thead>
								<tr>
									<th style="width: 20%;">订单编号</th>
									<th style="width: 10%;">出行时间</th>
									<th style="width: 10%;">客户姓名</th>
									<th style="width: 10%;">联系方式</th>
									<th style="width: 10%;">下单日期</th>
									<th style="width: 10%;">实际支付</th>
									<th style="width: 10%;">订单状态</th>
									<th style="width: 10%;">支付类型</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(pagedList.content) le 0}">
									<tr>
										<td colspan="9" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
								<c:forEach items="${pagedList.content}" var="payment" varStatus="status" >
									<tr data-id="${payment.paymentId}" class="
										<c:if test="${status.index % 2 eq 1}">odd gradeX</c:if>
										<c:if test="${status.index % 2 eq 0}">even gradeX</c:if>
									">
										<td>${payment.sn}</td>
										<td><fmt:formatDate value="${payment.tourismDeparture.dateOfDeparture}" pattern="yyyy/MM/dd" /></td>
										<td>${payment.contactsName}</td>
										<td>${payment.phoneNumber}</td>
										<td><fmt:formatDate value="${payment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
										<td>${payment.actualAmount}</td>
										<td>${payment.paymentStatus}</td>
										<td>
										  <c:if test="${payment.deposit == 0}">线下付款</c:if>
										  <c:if test="${payment.deposit == 1}">预付定金</c:if>
										  <c:if test="${payment.deposit == 2}">全额支付</c:if>
										</td>
										<td>
											<a href="./view.do?id=${payment.paymentId}">查看</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<tags:idgPage pagedList="${pagedList}"/>
					</div>
				</div>
			</div>
			<!--End Advanced Tables -->
		</div>
	</div>
	</form>
</body>
</html>

