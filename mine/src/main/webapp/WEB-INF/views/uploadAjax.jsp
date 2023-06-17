<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page session="false" %>
        <%@ page language="java" pageEncoding="utf-8" %>
            <html>

            <head>
                <title>Home</title>
                <meta charset="utf-8">
                <script src="https://code.jquery.com/jquery-3.7.0.js"
                    integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>

                <style type="text/css">
                    .uploadResult {
                        width: 100%;
                        background-color: gray;
                    }

                    .uploadResult ul {
                        display: flex;
                        flex-flow: row;
                        justify-content: center;
                        align-items: center;
                    }

                    .uploadResult ul li {
                        list-style: none;
                        padding: 10px;
                    }

                    .uploadResult ul li img {
                        width: 20px;
                    }
                </style>
            </head>

            <body>
                <div class='uploadDiv'>
                    <input type='file' name='uploadFile' multiple>
                </div>
                <button id='uploadBtn'>Upload</button>
                <div class="uploadResult">
                    <ul>

                    </ul>
                </div>
                <script type="text/javascript">
                    var regex = new RegExp("(.*?)\.(exe|sh|zip|aiz)$");
                    var maxSize = 5242880;

                    function checkExtension(fileName, fileSize) {
                        if (fileSize >= maxSize) {
                            alert("파일 사이즈 초과");
                            return false;
                        }
                        if (regex.test(fileName)) {
                            alert("해당 종류의 파일은 업로드할 수 없습니다.");
                            return false;
                        }
                        return true;
                    }

                    var uploadResult = $(".uploadResult ul");

                    function showUploadedFile(uploadResultArr) {
                        var str = "";
                        $(uploadResultArr).each(function (i, obj) {
                        	if (!obj.image) {
                        		  console.log(obj.image);
                        		  str += "<li><img src='/resources/img/clip.png'>" + obj.fileName + "</li>";
                        		} else {
                        		  var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                        		  str += "<li><img src='display?fileName=" + fileCallPath + "'></li>";
                        		}
                        });
                        uploadResult.append(str);
                    }


                    $(document).ready(function () {
                        var cloneObj = $(".uploadDiv").clone();

                        $("#uploadBtn").on("click", function (e) {
                            var formData = new FormData();
                            var inputFile = $("input[name='uploadFile']");
                            var files = inputFile[0].files;
                            console.log(files);

                            for (var i = 0; i < files.length; i++) {
                                if (!checkExtension(files[i].name, files[i].size)) {
                                    return false;
                                }

                                formData.append("uploadFile", files[i]);
                            }



                            $.ajax({
                                url: '/uploadAjaxAction',
                                processData: false,
                                contentType: false,
                                data: formData,
                                type: 'POST',
                                dataType: 'json',
                                success: function (result) {
                                    console.log(result);
                                    showUploadedFile(result);
                                    $(".uploadDiv").html(cloneObj.html());
                                }
                            });
                        });



                    });


                </script>
            </body>

            </html>