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
    <style>
        .uploadResult{
            width : 100%;
            background-color: gray;
        }

        .uploadResult ul{
            display:flex;
            flex-flow:row;
            justify-content: center;
            align-items: center;
        }

        .uploadResult ul li{
            list-style: none;
            padding: 10px;
        }

        .uploadResult ul li img{
            width: 20px;
        }

        .uploadResult ul li span{
            color: white;
        }

        .bigPictureWrapper{
            position: absolute;
            display: none;
            justify-content: center;
            align-items: center;
            top:0$;
            width: 100%;
            height: 100%;
            background-color: gray;
            z-index: 100;
            /*background: rgba(255,255,255,0.5);*/
        }

        .bigPicture{
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .bigPicture img{
            width: 500px;
        }
    </style>
</head>
<body>
<h1>Upload with AJAX</h1>

<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>
<div class="uploadResult">
    <ul></ul>
</div>
<div class="bigPictureWrapper">
    <div class="bigPicture"></div>
</div>

<button id="uploadBtn">Upload</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js" crossorigin="anonymous"></script>



<script>

    function showImage(fileCallPath){
        $(".bigPictureWrapper").css("display", "flex").show()

        $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>")
        .animate({width:'100%', height:'100%'}, 1000)

        $(".bigPictureWrapper").on("click", function (e){
            $(".bigPicture").animate({width:"0%", height:"0%"}, 1000)
            setTimeout(() =>{
                $(this).hide()
            }, 1000)
        })
    }

    $(document).ready(function(){

        var cloneObj = $(".uploadDiv").click()
        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
        var maxSize = 5242880
        var uploadResult = $(".uploadResult ul")

        function showUploadedFile(uploadResultArr){
            var str = ""

            $(uploadResultArr).each(function (i, obj){

                if(!obj.image){
                    var fileCallPath = encodeURIComponent(obj.uploadPath + obj.uuid + "_" + obj.fileName)
                    str += "<li><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'>" + obj.fileName + "</li>"
                } else{
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName)
                    var originPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName)
                    str += "<li><a href=\"javascript:showImage('" + originPath + "')\"> <img src='/display?fileName=" + fileCallPath + "'><li>"
                }
            })
            uploadResult.append(str)
        }

        function checkExtension(fileName, fileSize){
            if(fileSize >= maxSize){
                alert("파일 사이즈 초과")
                return false
            }

            if(regex.test(fileName)){
                alert("해당 종류의 파일은 업로드할 수 없습니다.")
                return false
            }
            return true
        }


        $("#uploadBtn").on("click", function (e){
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
</body>
</html>
