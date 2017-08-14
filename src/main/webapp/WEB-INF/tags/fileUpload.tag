<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">

$(function(){
	
	$("#fileUploadButton").click(function(){
		$('#fileUploadModal').modal('show');
	})
	
});
</script>
<form method="post" enctype="multipart/form-data">
	<button type="button" class="btn btn-info" id="fileUploadButton">文件上传</button>
	
	<div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
 <div class="modal-dialog" style="width: 400px;">
     <div class="modal-content">
         <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
             <h4 class="modal-title" id="myModalLabel">文件上传</h4>
         </div>
         <div class="modal-body">
         	<div class="row">
                 <div class="col-md-12">
                 	<div class="form-group">
                       <input type="file" name="fileUpload" id="fileUpload">
                    </div>
                 </div>
              </div>
            </div>
         <div class="modal-footer">
         	 <button type="button" class="btn btn-primary" id="downloadTemplate">下载模板</button>
             <button type="button" class="btn btn-primary" id="fileUpload">上传</button>
             <button type="button" class="btn btn-default" data-dismiss="modal" id="closeDialog">关闭</button>
         </div>
        </div>
    </div>
</div>
</form>
