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
                </form>
            </div>
        </div>
    </div>
</div>

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