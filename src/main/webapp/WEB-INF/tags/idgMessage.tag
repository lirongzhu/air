<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ attribute name="warnMessage" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="successMessage" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="infoMessage" type="java.lang.String" required="false" rtexprvalue="true" description="222"%>
<%@ attribute name="dangerMessage" type="java.lang.String" required="false" rtexprvalue="true" %>

<script type="text/javascript">

var idgMessageObj = {
		
		iMSuccessAlert : function(_content, _timer, _function){
			
			_timer = (typeof(_timer) == "undefined") ? 3000 : _timer;
			
			$("#idgMesSuccessStr").html(_content);
			$("#idgMessSuccessDiv").show();
			
			setTimeout(function(){
				
				if(typeof(_function) == "function"){
					_function();
				}else{
					$("#idgMessSuccessDiv").hide();
				}
				
			},_timer)
			
		},
		iMFailAlert : function(_content, _timer){
			
			_timer = (typeof(_timer) == "undefined") ? 3000 : _timer;
			
			$("#idgMesFailStr").html(_content);
			$("#idgMessFailDiv").show();
			
			setTimeout(function(){
				
				$("#idgMessFailDiv").hide();
			},timer)
		},
		iMWarnAlert : function(_content, _timer){
			
			_timer = (typeof(_timer) == "undefined") ? 3000 : _timer;
			
			$("#idgMesWarnStr").html(_content);
			$("#idgMessWarnDiv").show();
			
			setTimeout(function(){
				
				$("#idgMessWarnDiv").hide();
			},_timer)
		}
}

var idgConfirmObj = {
		confirm : function(message, _successCallbackEvent, _cancelCallbackEvent){

			$("#openConfirmModalButton").unbind();
			$("#closeConfirmModalButton").unbind();
			
			$("#openConfirmModalButton").bind("click", function(){
				$('#openConfirmModal').modal('hide');
				if(typeof _successCallbackEvent == 'function'){
					_successCallbackEvent();
				}
			})
		
			$("#closeConfirmModalButton").bind("click", function(){
				$('#openConfirmModal').modal('hide');
				if(typeof _cancelCallbackEvent == 'function'){
					_cancelCallbackEvent();
				}
			})
			
			$("#confirmModelMessage").html(message);
			$('#openConfirmModal').modal('show');
		},
		close : function(){
			$('#openConfirmModal').modal('hide');
		}
}

$(function(){
	
	setTimeout(function(){
		
		$("#showMessageContentDiv").hide();
	},3000)
});
</script>

<div class = "center-block" style="width: 30%;position: fixed; right: 15px; z-index:5;top:5px;" id="showMessageContentDiv">
<c:if test="${warnMessage != null && warnMessage != ''}">
	<div class="alert alert-warning alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>${warnMessage}</strong>
	</div>
</c:if>

<c:if test="${successMessage != null && successMessage != ''}">
	<div class="alert alert-success alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>${successMessage}</strong>
	</div>
</c:if>

<c:if test="${infoMessage != null && infoMessage != ''}">
	<div class="alert alert-info alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>${infoMessage}</strong>
	</div>
</c:if>

<c:if test="${dangerMessage != null && dangerMessage != ''}">
	<div class="alert alert-danger alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>${dangerMessage}</strong>
	</div>
</c:if>
</div>

<div class = "center-block" style="width: 30%;position: fixed; right: 15px; z-index:5;top:5px;display: none;" id="idgMessSuccessDiv">
	<div class="alert alert-success alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong id="idgMesSuccessStr"></strong>
	</div>
</div>

<div class = "center-block" style="width: 30%;position: fixed; right: 15px; z-index:5;top:5px;display: none;" id="idgMessFailDiv">
	<div class="alert alert-danger alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong id="idgMesFailStr"></strong>
	</div>
</div>

<div class = "center-block" style="width: 30%;position: fixed; right: 15px; z-index:5;top:5px;display: none;" id="idgMessWarnDiv">
	<div class="alert alert-warning alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong id="idgMesWarnStr"></strong>
	</div>
</div>

<div class="modal fade" id="openConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
 <div class="modal-dialog" style="width: 400px;">
     <div class="modal-content">
         <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
             <h4 class="modal-title" id="myModalLabel">确认操作</h4>
         </div>
         <div class="modal-body">
         	<div class="row">
                 <div class="col-md-12">
                 	<div class="form-group">
                        <label id="confirmModelMessage">确认继续？</label>
                    </div>
                 </div>
              </div>
            </div>
         <div class="modal-footer">
             <button type="button" class="btn btn-primary" id="openConfirmModalButton">确定</button>
             <button type="button" class="btn btn-default" data-dismiss="modal" id="closeConfirmModalButton">关闭</button>
         </div>
        </div>
    </div>
</div>
