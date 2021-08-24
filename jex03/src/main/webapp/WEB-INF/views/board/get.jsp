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
            <div class="panel-heading">Board Read Page</div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label>
                    <input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
                </div>
                <div class="form-group">
                    <label>Title</label>
                    <input class="form-control" name="title" value='<c:out value="${board.title}"/>' readonly="readonly">
                </div>
                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="3" name="content" readonly="readonly"><c:out value="${board.content}"/></textarea>
                </div>
                <div class="form-group">
                    <label>Writer</label>
                    <input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly">
                </div>
                <button data-oper="modify"
                        class="btn btn-default">
                    Modify</button>
                <button data-oper="list"
                        class="btn-info btn">
                    List</button>
                <form id="operForm" action="/board/modify" method="get">
                    <input type="hidden" id="bno" name="bno" value="<c:out value='${board.bno}'/>">
                    <input type="hidden" name="pageNum" value="<c:out value='${criteria.pageNum}'/>">
                    <input type="hidden" name="amount" value="<c:out value='${criteria.amount}'/>">
                    <input type="hidden" name="keyword" value="<c:out value='${criteria.keyword}'/>">
                    <input type="hidden" name="type" value="<c:out value='${criteria.type}'/>">
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="panel-default panel">
<%--        <div class="panel-heading">--%>
<%--            <i class="fa fa-comments fa-fw"></i> Reply--%>
<%--        </div>--%>

        <div class="panel-heading">
            <i class="fa fa-comments fa-fw"></i> Reply
            <button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New Reply</button>
        </div>


        <div class="panel-body">
            <ul class="chat">
                <li class="left clearfix" data-rno="12">
                    <div>
                        <div class="header">
                            <strong class="primary-font">user00</strong>
                            <small class="pull-right text-muted">2018-01-01 13:13</small>
                        </div>
                        <p>Good Job!</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div lcass="modal-body">
                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name="reply" value="New Reply">
                </div>
                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name="replyer" value="replyer">
                </div>
                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name="replyDate" value="">
                </div>
            </div>
            <div class="modal-footer">
                <button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
                <button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
                <button id="modalRegisterBtn" type="button" class="btn btn-success">Register</button>
                <button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/reply.js"></script>
<script>
    $(document).ready(function (){
        var bnoValue = '<c:out value="${board.bno}"/>'
        var replyUL = $(".chat")

            showList(1)

            function showList(page){
                replyService.getList({bno:bnoValue, page : page || 1}, function (list){
                    var str=""
                    console.log("going?")
                    if(list == null || list.length == 0){
                        replyUL.html("")
                        return
                    }
                    console.log("going??")
                    for(var i = 0, len = list.length || 0; i < len; i++){
                        str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>"
                        str += "<div><div class='header'> <strong class='primary-font'>"+ list[i].replyer + "</strong>"
                        str += "<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small>"
                        str += "</div><p>" + list[i].reply + "</p></div></li>"
                    }
                    replyUL.html(str)
                })
            }

        replyUL.on("click", "li", function (e){
            var rno = $(this).data("rno")

            replyService.get(rno, function (reply){
                modalInputReply.val(reply.reply)
                modalInputReplyer.val(reply.replyer)
                modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly")

                modal.find("button[id != 'modalCloseBtn']").hide()
                modalModBtn.show()
                modalRemoveBtn.show()

                $(".modal").modal("show")
            })
        })

        var modal = $(".modal")
        var modalInputReply = modal.find("input[name='reply']")
        var modalInputReplyer = modal.find("input[name='replyer']")
        var modalInputReplyDate = modal.find("input[name='replyDate']")

        var modalModBtn = $("#modalModBtn")
        var modalRemoveBtn = $("#modalRemoveBtn")
        var modalRegisterBtn = $("#modalRegisterBtn")

        $("#addReplyBtn").on("click", function(e){
            modal.find("input").val("")
            modalInputReplyDate.closest("div").hide()
            modal.find("button[id != 'modalCloseBtn']").hide()

            modalRegisterBtn.show()

            $(".modal").modal("show")
        })

        modalRegisterBtn.on("click", function (e){
            var reply = {
                reply : modalInputReply.val(),
                replyer : modalInputReplyer.val(),
                bno : bnoValue
            }

            replyService.add(reply, function (result){
                alert(result)
                modal.find("input").val("")
                modal.modal("hide")
                showList(1)
            })
        })
    })
</script>
<%--<script>--%>
<%--    console.log("=====================")--%>
<%--    console.log("JS TEST")--%>

<%--    replyService.add({--%>
<%--        bno : <c:out value="${board.bno}"/>,--%>
<%--        reply : "test comment",--%>
<%--        replyer : "tester"--%>
<%--        },--%>
<%--        function (success){--%>
<%--            alert("result : " + success)--%>
<%--        }--%>
<%--    )--%>

<%--    var bnoValue = '<c:out value="${board.bno}"/>'--%>
<%--    console.log(bnoValue)--%>
<%--    replyService.getList({bno : bnoValue, page:1}, function (list){--%>
<%--        for(var i = 0, len = list.length || 0; i < len; i++ ){--%>
<%--            console.log(list[i])--%>
<%--        }--%>
<%--    })--%>

<%--    replyService.remove(23, function (count){--%>
<%--        console.log(count);--%>
<%--        if(count === "Success")--%>
<%--            alert("removed")--%>
<%--        }, function (err){--%>
<%--            alert("Error during delete.")--%>
<%--        }--%>
<%--    )--%>

<%--    replyService.update({rno : 24, bno : bnoValue, reply : "Update Text Test"},--%>
<%--        function (success){--%>
<%--            alert("Successful update")--%>
<%--        }--%>

<%--    replyService.get(24, console.log)--%>

<%--</script>--%>
<script>
    $(document).ready(
        function (){
            var operForm = $("#operForm")

            $("button[data-oper='modify']").on("click", function (e){
                operForm.attr("action", "/board/modify").submit()
            })

            $("button[data-oper='list']").on('click', function (e){
                operForm.find("#bno").remove()
                operForm.attr("action", "/board/list")
                operForm.submit()
            })
        }
    )
</script>

<%@ include file="../includes/footer.jsp" %>