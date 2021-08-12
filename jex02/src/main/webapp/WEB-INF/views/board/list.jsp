<%--
  Created by IntelliJ IDEA.
  User: jisoo
  Date: 21. 8. 10.
  Time: 오후 2:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ include file="../includes/header.jsp" %>
  <div class="row">
      <div class="col-lg-12">
          <h1 class="page-header">Tables</h1>
      </div>
  </div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board List Page
                <button id="regBtn" type="button" class="btn btn-xs pull-right">Register New Board</button>
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>#번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>수정일</th>
                        </tr>
                    </thead>
                    <c:forEach items="${list}" var="board">
                        <tr>
                            <td><c:out value="${board.bno}"/> </td>
                            <td><a href="/board/get/?bno=<c:out value="${board.bno}"/>"><c:out value="${board.title}"/></a></td>
                            <td><c:out value="${board.writer}"/> </td>
                            <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd"/> </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                            </div>
                            <div class="modal-body">
                                처리가 완료가 되었습니다.
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" dat   data-dismiss="modal" >Close</button>
<%--                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
<%--                                <button type="button" class="btn btn-primary">Save changes</button>--%>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function (){
        var result = '<c:out value="${result}" />'

        checkModal(result);

        history.replaceState({}, null, null)

        function checkModal(result){
            if(result === '' || history.state)
                return

            if(parseInt(result) > 0)
                $(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록 되었습니다.")

            $("#myModal").modal("show")
        }
        $("#regBtn").on("click", function (){
            self.location = "/board/register"
        })
    })
</script>

<%@ include file="../includes/footer.jsp" %> 