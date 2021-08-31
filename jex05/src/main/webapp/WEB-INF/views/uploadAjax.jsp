<%--
  Created by IntelliJ IDEA.
  User: jisoo
  Date: 21. 8. 31.
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Upload with AJAX</h1>

<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>

<button id="uploadBtn">Upload</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js" crossorigin="anonymous"></script>

<script>
    $(document).ready(function(){
        $("#uploadBtn").on("click", function (e){
            var formData = new FormData()
            var inputFile = $("input[name='uploadFile']")
            var files = inputFile[0].files
            console.log(files)

            for(var i = 0; i < files.length ; i++){
                formData.append("uploadFile", files[i])
            }

            $.ajax({
                url : "/uploadAjaxAction",
                processData : false,
                contentType : false,
                data : formData,
                type : 'POST',
                success : function (result){
                    alert("Uploaded")
                }
            })
        })
    })
</script>
</body>
</html>
