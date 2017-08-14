<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ attribute name="pagedList" type="org.springframework.data.domain.Page" required="true" rtexprvalue="true"%>
<script type="text/javascript">

$(function(){
	
	var currentPage = $("#currentPage").val();
	var totalPage = $("#totalPage").val();
	
	if(currentPage == 0){
		$("#idgPageListTable ul li:eq(0)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(1)").attr("class", "paginate_button disabled");
	}
	
	if(currentPage == totalPage-1){
		$("#idgPageListTable ul li:eq(3)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(4)").attr("class", "paginate_button disabled");
	} 
	/* if(currentPage <= 1 && currentPage >= totalPage-1){
		$("#idgPageListTable ul li:eq(0)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(1)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(3)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(4)").attr("class", "paginate_button disabled");
	}else if(currentPage <= 1){
		$("#idgPageListTable ul li:eq(0)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(1)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(3)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(4)").attr("class", "paginate_button");
	}else if(currentPage >= totalPage-1){
		$("#idgPageListTable ul li:eq(0)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(1)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(3)").attr("class", "paginate_button disabled");
		$("#idgPageListTable ul li:eq(4)").attr("class", "paginate_button disabled");
	}else{
		$("#idgPageListTable ul li:eq(0)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(1)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(3)").attr("class", "paginate_button");
		$("#idgPageListTable ul li:eq(4)").attr("class", "paginate_button");
	} */
	
	$("#idgPageListTable ul li").click(function(){
		$("#currentPage").val($(this).data("page"));
		
		if($("#currentPage").val() < 0){
			$("#currentPage").val(0);
		}
		
		if($("#currentPage").val() > totalPage -1 ){
			$("#currentPage").val(totalPage - 1);
		}
		
		if($("#sort_element").val() != "" && $("#sort_direction").val() != ""){
			$("#sort").val($("#sort_element").val() + "," + $("#sort_direction").val());
		}
		
		$("#queryButton").click();
	})
	
	
	$("#listTable thead tr th").click(function(){
		
		if(typeof($(this).data('sort')) != 'undefined'){
			
			pageSort($(this).data('sort'));
	        $("#queryForm").submit();
		}
	})
	
   function pageSort(element){
        
        if(element == $("#sort_element").val()){
            if($("#sort_direction").val() == "" || $("#sort_direction").val() == 'desc'){
                $("#sort_direction").val('asc');
            }else{
                $("#sort_direction").val('desc');
            }
        }else{
            $("#sort_element").val(element);
            $("#sort_direction").val('asc');
        }
        
        $("#sort").val($("#sort_element").val() + "," + $("#sort_direction").val());
    }

});
</script>
<div class="row">
	<input type="hidden" id="currentPage" name="currentPage" value="${pagedList.number}">
	<input type="hidden" id="totalPage" value="${pagedList.totalPages}">
	<div class="col-sm-6">
		显示 ${pagedList.number * 10 + 1} 至 ${pagedList.number * 10 + 10} 笔资料  共笔${pagedList.totalElements }资料
	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_simple_numbers" id="idgPageListTable">
			<ul class="pagination">
				<li class="paginate_button" aria-controls="dataTables-example" tabindex="0" data-page="0">
					<a href="javascript:void(0);">首页</a>
				</li>
				<li class="paginate_button" aria-controls="dataTables-example" tabindex="0" data-page="${pagedList.number - 1}">
					<a href="javascript:void(0);">上一页</a>
				</li>
				<li class="paginate_button disabled" aria-controls="dataTables-example">
					<a href="javascript:void(0);">${pagedList.number + 1}</a>
				</li>
				<li class="paginate_button" aria-controls="dataTables-example" tabindex="0" data-page="${pagedList.number + 1}">
					<a href="javascript:void(0);">下一页</a>
				</li>
				<li class="paginate_button" aria-controls="dataTables-example" tabindex="0" data-page="${pagedList.totalPages - 1}">
					<a href="javascript:void(0);">尾页</a>
				</li>
			</ul>
		</div>
	</div>
</div>

<input id="sort" type="hidden" name="sort" value="">
<input id="sort_element" type="hidden" name="sort_element" value="${param.sort_element}">
<input id="sort_direction" type="hidden" name="sort_direction" value="${param.sort_direction}">