<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
    <link rel="icon" href="images/title-ico.ico" type="image/x-ico"/> 
    <!-- Bootstrap Styles-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
	
	   <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="assets/js/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
	 
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>
    <!-- Morris Chart Js -->
    <script src="assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="assets/js/morris/morris.js"></script>
	
	<script src="assets/js/easypiechart.js"></script>
	<script src="assets/js/easypiechart-data.js"></script>
	
    <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script>
	<script>
	
		$(function(){
		
			$("#sideNav").click(function(){
				
				if($("#sideNav").data("show") == true){
					$("#sideNav").data("show", false);
					$("#sideNav").css("right", "240px");
					$("#main-menu").css("display","none");
					$("#page-wrapper").css("margin-left","0px");
				}else{
				
					$("#sideNav").data("show", true);
					$("#sideNav").css("right", "-23px");
					$("#main-menu").css("display","block");
					$("#page-wrapper").css("margin-left","260px");
				}
			})
			
			$("#main-menu li a").click(function(){
			
				$("#main-menu li a").removeClass("active-menu");
				$(this).addClass("active-menu");
			})
			
			$('#main-menu').metisMenu();
		})
	
	</script>
</head>
<body style="overflow: hidden;">
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="dashboard.html"><i class="fa fa-comments"></i> <strong>MASTER </strong></a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="./changeInfo.do" target="frame-inner"><i class="fa fa-user fa-fw"></i>个人信息</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="./logout.do"><i class="fa fa-sign-out fa-fw"></i>登出</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
		<div id="sideNav" href="" data-show="true"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                	<shiro:hasAnyRoles name="admin,normal,auditor,information">
	                	<li>
	                        <a href="#"><i class="fa fa-sitemap"></i>线路发布<span class="fa arrow"></span></a>
	                        <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./tourism/list.do" target="frame-inner">基本信息</a>
	                            </li>
	                        </ul>
	                        <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./payment/list.do" target="frame-inner">订单查看</a>
	                            </li>
	                        </ul>
	                    </li>
                	</shiro:hasAnyRoles>
	                <shiro:hasAnyRoles name="admin,auditor,information">
                        <li>
                            <a href="#"><i class="fa fa-sitemap"></i>资讯管理<span class="fa arrow"></span></a>
                             <ul class="nav nav-second-level">
                                <li>
                                    <a href="./information/list.do" target="frame-inner">资讯管理</a>
                                </li>
                            </ul>
                        </li>
                    </shiro:hasAnyRoles>
	                <shiro:hasAnyRoles name="admin">
	                    <li>
	                        <a href="#"><i class="fa fa-sitemap"></i>系统设置<span class="fa arrow"></span></a>
	                        <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./dictionary/list.do" target="frame-inner">字典管理</a>
	                            </li>
	                        </ul>
	                        <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./user/list.do" target="frame-inner">账号管理</a>
	                            </li>
	                        </ul>
	                         <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./customer/list.do" target="frame-inner">客户管理</a>
	                            </li>
	                        </ul>
	                         <ul class="nav nav-second-level">
	                            <li>
	                                <a href="./message/list.do" target="frame-inner">短信发送</a>
	                            </li>
	                        </ul>
	                    </li>
	                </shiro:hasAnyRoles>
                </ul>
            </div>
        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" style="padding-right: 0px;">
		
            <div id="page-inner" style="padding: 0px;">
				<iframe style="border: none;width:100%;" src="./welcome/list.do" name="frame-inner">

				 </iframe>  
	<script>
	
		var ifm= document.getElementsByName("frame-inner")[0];
		ifm.style.height=document.documentElement.clientHeight -100 +"px";
	</script>				 
		   </div>
            <!-- /. PAGE INNER  -->
       
		</div>
        <!-- /. PAGE WRAPPER  -->
    </div>
 


</body>

</html>