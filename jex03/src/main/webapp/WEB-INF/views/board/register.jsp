<%--
  Created by IntelliJ IDEA.
  User: jisoo
  Date: 21. 8. 11.
  Time: 오후 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>

<div class="row">
    <div class="col-lg-2">
        <h1 class="page-header">Board Register</h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board Register</div>
            <div class="panel-body">
                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>Title</label>
                        <input class="form-control" name="title">
                    </div>
                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="3" name="content"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Writer</label>
                        <input class="form-control" name="writer">
                    </div>
                    <button type="submit" class="btn btn-default">Submit Button</button>
                    <button type="reset" class="btn-default btn">Reset Button</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">File Attach</div>
            <div class="form-group uploadDiv">
                <input type="file" name="uploadFile" multiple>
            </div>

            <div class="uploadResult">
                <ul>
                </ul>
            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function (e){
        var formObg = $("form[rome='form']")

        $("button[type='submit']").on("click", function (e){
            e.preventDefault()
            console.log("submit clicked")
        })

        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
        var maxSize = 5242880

        function checkExtension(fileName, fileSize) {
            if (fileSize >= maxSize) {
                alert("파일 사이즈 초과")
                return false
            }

            if (regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드 할 수 없습니다.")
                return false
            }
            return true
        }

        $("input[type='file']").change(function (e){
            var formData = new FormData()
            var inputFile = $("input[name='uploadFile']")
            var files = inputFile[0].files
            console.log(files)

            for(var i = 0; i < files.length ; i++){
                console.log("Checks")
                if(!checkExtension(files[i].name, files[i].size))
                    return false
                formData.append("uploadFile", files[i])
            }

            $.ajax({
                url : "/uploadAjaxAction",
                processData : false,
                contentType : false,
                data : formData,
                type : 'POST',
                success : function (result){
                    console.log(result)
                    showUploadedFile(result)
                    $(".uploadDiv").html(cloneObj.html())

                }
            })
        })



    })
</script>

<%@ include file="../includes/footer.jsp" %>