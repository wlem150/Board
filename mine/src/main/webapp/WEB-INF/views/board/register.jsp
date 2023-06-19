<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../includes/header.jsp" %>



            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    
                        <div class="panel-heading">
                            Board Register
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form action="/board/register" role="form" method="post">
                            	<div class="form-group">
                            		<label>Title</label> <input class="form-control" name="title">
                            	</div>
                            	<div class="form-group">
                            		<label>Test area</label>
                            		<textarea rows="3" class="form-control" name="content"></textarea>
                            	</div>
                            	<div class="form-group">
                            		<label>Writer</label> <input class="form-control" name="writer">
                            	</div>
                            	<button type="submit" class="btn btn-default">Submit Button</button>
                            	<button type="reset" class="btn btn-default">Reset Button</button>
                            </form>
 
                    </div>

                </div>
     
            </div>
           </div>
           <div class="row">
           	<div class="col-lg-12">
           		<div class="panel panel-default">
           			<div class="panel-heading">
           			File Attach
           			</div>
           			<div class="panel-body">
           				<div class="form-group uploadDiv">
           					<input type="file" name="uploadFile" multiple>
           				</div>
           				<div class='uploadResult'>
           					<ul>
           					</ul>
           				</div>
           			</div>
           		</div>
           	</div>
           </div>
           <script>
           $(document).ready(function (e) {
        	   var formObj = $("form[role='form']");

        	   $("button[type='submit']").on("click", function (e) {
        	     e.preventDefault();
        	     var str = "";

        	     $(".uploadResult ul li").each(function (i, obj) {
        	       var jobj = $(obj);

        	       str +=
        	         "<input type='hidden' name='attachList[" +
        	         i +
        	         "].fileName' value='" +
        	         jobj.data("filename") +
        	         "'>";
        	       str +=
        	         "<input type='hidden' name='attachList[" +
        	         i +
        	         "].uuid' value='" +
        	         jobj.data("uuid") +
        	         "'>";
        	       str +=
        	         "<input type='hidden' name='attachList[" +
        	         i +
        	         "].uploadPath' value='" +
        	         jobj.data("path") +
        	         "'>";
        	       str +=
        	         "<input type='hidden' name='attachList[" +
        	         i +
        	         "].fileType' value='" +
        	         jobj.data("type") +
        	         "'>";
        	     });
        	     formObj.append(str).submit();
        	   });
        	 });

        	 $("input[type='file']").on("change", function (e) {
        	   var formData = new FormData();
        	   var inputFile = $("input[name='uploadFile']");
        	   var files = inputFile[0].files;

        	   for (var i = 0; i < files.length; i++) {
        	     if (!checkExtention(files[i].name, files[i].size)) {
        	       return false;
        	     }
        	     formData.append("uploadFile", files[i]);
        	   }

        	   function checkExtention(fileName, fileSize) {
        	     var regex = new RegExp("(.*?).(exe|sh|zip|alz)$");
        	     var maxSize = 5242880;
        	     if (fileSize >= maxSize) {
        	       alert("파일 사이즈 초과");
        	       return false;
        	     }

        	     if (regex.test(fileName)) {
        	       alert("해당 종류의 파일은 업로드 할 수 없습니다.");
        	       return false;
        	     }
        	     return true;
        	   }

        	   function showUploadResult(uploadResultArr) {
        	     if (!uploadResultArr || uploadResultArr.length == 0) {
        	       return;
        	     }

        	     var uploadUL = $(".uploadResult ul");

        	     var str = "";

        	     $(uploadResultArr).each(function (i, obj) {
        	       if (obj.image) {
        	         var fileCallPath = encodeURIComponent(
        	           obj.uploadPath + "/" + obj.uuid + "s_" + obj.uuid + "_" + obj.fileName
        	         );
        	         str += "<li data-path='" + obj.uploadPath + "'";
        	         str +=
        	           " data-uuid='" +
        	           obj.uuid +
        	           "' data-filename='" +
        	           obj.fileName +
        	           "' data-type='" +
        	           obj.image +
        	           "'>";
        	         str += "<div>";
        	         str += "<span>" + obj.fileName + "</span>";
        	         str +=
        	           "<button type='button' data-file='" +
        	           fileCallPath +
        	           "' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
        	         str += "<img src='/display?fileName=" + fileCallPath + "'>";
        	         str += "</div>";
        	         str += "</li>";
        	       } else {
        	         var fileCallPath = encodeURIComponent(
        	           obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName
        	         );
        	         var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
        	         str += "<li";
        	         str +=
        	           " data-path='" +
        	           obj.uploadPath +
        	           "'" +
        	           " data-uuid='" +
        	           obj.uuid +
        	           "' data-filename='" +
        	           obj.fileName +
        	           "' data-type='" +
        	           obj.image +
        	           "'>" +
        	           "<div>";
        	         str += "<span>" + obj.fileName + "</span>";
        	         str +=
        	           "<button type='button' data-file='" +
        	           fileCallPath +
        	           "' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
        	         str += "<img src='/resources/img/clip.png'>";
        	         str += "</div>";
        	         str += "</li>";
        	       }
        	     });
        	   }

        	   $.ajax({
        	     url: "/uploadAjaxAction",
        	     processData: false,
        	     contentType: false,
        	     data: formData,
        	     type: "POST",
        	     dataType: "json",
        	     success: function (result) {
        	       console.log(result);
        	       showUploadResult(result);
        	     },
        	   });
        	 });



           </script>
          <%@ include file="../includes/footer.jsp"%>