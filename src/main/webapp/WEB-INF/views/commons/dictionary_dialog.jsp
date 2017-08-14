<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	var IDDialog;
	
	$(function(){
		
		IDDialog = {
				closeSetValue : "", 
				openDialog : function(){
				
				$("#alreadyChooseDic").val("");
				
				$.ajax({
			      url: "../common/getBusinessDictionary.do",
			      type: "GET",
			      success:function (data) {
			    	  
			    	  var JsonData =  eval("(" + data + ")");
			    	  var innerHtml = "";
			    	  
			    	  innerHtml += "<option value=''>请选择</option>";
			    	  
			    	  for(var i=0; i<JsonData.data.length; i++){
			    		  innerHtml += "<option value="+JsonData.data[i].dictionaryKey+">" + JsonData.data[i].fullName + "</option>";
			    	  }
			    	  
			    	  $("#dicSelect").empty();
			    	  $("#dicSelect").append(innerHtml);
			    	  console.info(innerHtml);
			    	  
			    	  dicSelectOnChange();
			    	  
			    	  $('#idgDicDialog').modal('show');
			      }
			  })
			}
		}
		
		$("#closeAndClearIDD").click(function(){
			$('#idgDicDialog').modal('hide');
			clearIDDContent();
		})
		
		$("#saveIDD").click(function(){
			
			var alreadyChooseVal = $("#alreadyChooseDic").val();
			
			if($("#dicItemSelect option:selected").text() != "请选择"){
				alreadyChooseVal += $("#dicItemSelect option:selected").text() + ";" ;
			}
			 $("#alreadyChooseDic").val(alreadyChooseVal);
		})
		
		$("#closeAndSaveIDD").click(function(){
			
			if(IDDialog.closeSetValue != "" && typeof $("#" + IDDialog.closeSetValue) != "undefined"){
				$("#" + IDDialog.closeSetValue).val($("#alreadyChooseDic").val());
			}
			
			$('#idgDicDialog').modal('hide');
			clearIDDContent();			    			
		})
		
	});
	
	function clearIDDContent(){
		
		$("#dicSelect").empty();
		$("#dicItemSelect").empty("");
	}
	
	function dicSelectOnChange(){
		
		if($("#dicSelect").val() != ""){
			
			$.ajax({
		      url: "../common/getDictionaryItemByParent.do",
		      type: "GET",
		      data: {
		    	  dictionaryKey : $("#dicSelect").val()
		      },
		      success:function (data) {

		    	  var JsonData =  eval("(" + data + ")");
		    	  var innerHtml = "";
		    	  
		    	  innerHtml += "<option value=''>请选择</option>";
		    	  
		    	  for(var i=0; i<JsonData.data.length; i++){
		    		  innerHtml += "<option value="+JsonData.data[i].itemKey+">" + JsonData.data[i].fullName + "</option>";
		    	  }
		    	  
		    	  $("#dicItemSelect").empty();
		    	  $("#dicItemSelect").append(innerHtml);
		      }
		  })
		}else{
			 $("#dicItemSelect").append("<option value=''>请选择</option>");
		}
	}
</script>

<div class="modal fade" id="idgDicDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body">
            	<div class="row">
                    <div class="col-md-12">
                    	<div class="form-group">
							<select class="form-control" name="dicSelect" id="dicSelect" onchange="dicSelectOnChange();">
                            </select>
                       </div>
                    </div>
                    <div class="col-md-12">
                    	<div class="form-group">
							<select class="form-control" name="dicItemSelect" id="dicItemSelect">
                            </select>
                       </div>
                    </div>
                  	<div class="col-md-12">
                       <div class="form-group">
                           <textarea class="form-control" rows="2" name="alreadyChooseDic" id="alreadyChooseDic" readonly="readonly"></textarea>
                       </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
             	<button type="button" class="btn btn-primary" id="saveIDD">添加</button>
             	<button type="button" class="btn btn-primary" id="closeAndSaveIDD">确定并关闭</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeAndClearIDD">关闭</button>
            </div>
        </div>
    </div>
</div>