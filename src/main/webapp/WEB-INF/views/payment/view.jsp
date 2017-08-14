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

</head>
<body style="background-color: #f3f3f3; overflow-x: hidden; padding-right: 15px;">
	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">
				订单信息 <small>查看</small>
			</h1>
		</div>
	</div>
	<form id="submitForm" action="" method="post">
	<div class="row">
		<div class="col-md-12">
			<!-- Advanced Tables -->
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
					    	<a href="#departure" aria-controls="departure" role="tab" data-toggle="tab">行程信息</a>
					    </li>
					    <li role="presentation">
					    	<a href="#customer" aria-controls="customer" role="tab" data-toggle="tab">客户信息</a>
					    </li>
					  </ul>

					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="base_info">
					    <br>
					    	<input class="form-control" type="hidden" name="paymentId" id="paymentId" value="${payment.paymentId}">
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">联系人姓名 : ${payment.contactsName}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">成人人数 : ${payment.itemCount}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
                                    <div class="form-group">
                                       <font size="4" face="verdana">儿童人数 : ${payment.childItemCount}</font>
                                   </div>
                                </div>
							 	<div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">代金券 : ${payment.cashCoupon.id}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">成人单价 : ${payment.adultUnitPrice}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">儿童单价 : ${payment.childUnitPrice}</font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
		                       		<div class="form-group">
		                                <font size="4" face="verdana">总价 : ${payment.totalPrice}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">实际支付 : ${payment.actualAmount}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">电话号码 : ${payment.phoneNumber}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">微信号码 : ${payment.weixinNumber}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">QQ号码 : ${payment.QQNumber}</font>
		                            </div>
		                      </div>
		                    <%--   <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">成人信息1 : ${payment.travelers1}</font>
		                            </div>
		                      </div>
		                      <div class="col-md-6">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">成人信息2 : ${payment.travelers2}</font>
		                            </div>
		                      </div> --%>
		                      <div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">创建日期 : <fmt:formatDate value="${payment.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">订单状态 : ${payment.paymentStatus}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-12">
		                       		<div class="form-group">
		                       			<font size="4" face="verdana">备注 : ${payment.remark}</font>
		                            </div>
		                      </div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="departure">
					    <br>
					    	<input class="form-control" type="hidden" name="departure" id="departure" value="${departure.id}">
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">团号 : ${departure.groupNumber}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">
				                           	<c:if test="${departure.deposit==1}">是否有定金 : 是</c:if>
				                           	<c:if test="${departure.deposit==0}">是否有定金 : 否</c:if>
				                       </font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">成人价格 : ${departure.adultPrice}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">儿童价格 : ${departure.kidPrice}</font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">最大客户数量 : ${departure.maxCustomer}</font>
			                       </div>
			                    </div>
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">出发日期 : <fmt:formatDate value="${departure.dateOfDeparture}" pattern="yyyy/MM/dd HH:mm:ss" /></font>
			                       </div>
			                    </div>
							</div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="customer">
					    <br>
					    	<input class="form-control" type="hidden" name="customer" id="customer" value="${customer.id}">
					    	<div class="row">
			                    <div class="col-md-6">
			                    	<div class="form-group">
			                           <font size="4" face="verdana">登陆名 : ${customer.loginName}</font>
			                       </div>
			                    </div>
							 	<div class="col-md-6">
								 	<div class="form-group">
								 		<font size="4" face="verdana">真实姓名 : ${customer.trueName}</font>
			                       </div>
		                       </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">联系方式 : ${customer.contact}</font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">创建日期 : <fmt:formatDate value="${customer.createDate}" pattern="yyyy/MM/dd HH:mm:ss" /></font>
			                       </div>
			                    </div>
		                       <div class="col-md-6">
			                    	<div class="form-group">
			                    		<font size="4" face="verdana">状态 : ${customer.state}</font>
			                       </div>
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

