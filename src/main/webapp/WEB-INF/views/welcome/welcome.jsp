<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>

<script type="text/javascript">

</script>
</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
	<form action="./list.do" method="post" name="submitForm" id="submitForm">
	<div class="row">
	      <div class="col-md-8 col-sm-8">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    	你有待审核的线路
                </div>
                <div class="panel-body">
                   <div class="table-responsive">
						<table class="table table-striped table-bordered table-hover">
						    <thead>
						        <tr>
						            <th style="width: 30%;">名称</th>
						            <th style="width: 20%;">编号</th>
						            <th style="width: 10%;">出发地</th>
						            <th style="width: 20%;">目的地</th>
						            <th style="width: 10%;">天数</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:if test="${fn:length(myTourism.content) le 0}">
									<tr>
										<td colspan="5" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
						    	<c:forEach items="${myTourism.content}" var="myTourism">
							        <tr>
							            <td><c:out value="${myTourism.routeName}"/></td>
							            <td><c:out value="${myTourism.productNumber}"/></td>
							            <td><c:out value="${myTourism.pointOfDeparture}"/></td>
							            <td><c:out value="${myTourism.destination}"/></td>
							            <td><c:out value="${myTourism.routeDays}"/></td>
							        </tr>
						        </c:forEach>
						    </tbody>
						</table>
					</div>
                </div>
                <div class="panel-footer">
                	 <a href="./../examine/list.do">查看更多>></a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-sm-4">
            <div class="panel panel-info">
                <div class="panel-heading">
                    	个人登录信息
                </div>
                <div class="panel-body">
                   	<h4>${myLoginInfo.operationUser.contactName}</h4>
                   	<h4>上次登录时间 ： </h4>
                   	<h4><fmt:formatDate value="${myLoginInfo.operationDate}" pattern="yyyy-MM-dd HH:mm:ss" /></h4>
                </div>
            </div>
        </div>
      <shiro:hasAnyRoles name="auditor,admin">
		<div class="col-md-8 col-sm-8">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    	有路线需要您审核
                </div>
                <div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover">
						    <thead>
						        <tr>
						            <th style="width: 30%;">名称</th>
						            <th style="width: 20%;">编号</th>
						            <th style="width: 10%;">出发地</th>
						            <th style="width: 20%;">目的地</th>
						            <th style="width: 10%;">天数</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:if test="${fn:length(waitForExamine.content) le 0}">
									<tr>
										<td colspan="5" style="text-align: center;">暂无资料</td>
									</tr>
								</c:if>
						        <c:forEach items="${waitForExamine.content}" var="wairTourism">
							        <tr>
							            <td><c:out value="${wairTourism.routeName}"/></td>
							            <td><c:out value="${wairTourism.productNumber}"/></td>
							            <td><c:out value="${wairTourism.pointOfDeparture}"/></td>
							            <td><c:out value="${wairTourism.destination}"/></td>
							            <td><c:out value="${wairTourism.routeDays}"/></td>
							        </tr>
						        </c:forEach>
						    </tbody>
						</table>
					</div>
                </div>
                <div class="panel-footer">
                   <a href="./../tourism/list.do">查看更多>></a>
                </div>
            </div>
        </div>
    </shiro:hasAnyRoles>
	</div>
	</form>
	<footer>
		<p>
		</p>
	</footer>
</body>
</html>

