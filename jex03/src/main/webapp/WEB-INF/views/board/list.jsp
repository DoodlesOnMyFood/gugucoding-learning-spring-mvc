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
                            <td><a class="move" href="<c:out value="${board.bno}"/>"><c:out value="${board.title}"/></a></td>
                            <td><c:out value="${board.writer}"/> </td>
                            <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd"/> </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="row">
                    <div class="col-lg-12">
                        <form id="searchForm" action="/board/list" method="get">
                            <select name="type">
                                <option value=""${pageMaker.criteria.type == null ? 'selected' : ''}>--</option>
                                <option value="T" ${pageMaker.criteria.type eq 'T' ? 'selected' : ''}>제목</option>
                                <option value="C" ${pageMaker.criteria.type eq 'C' ? 'selected' : ''}>내용</option>
                                <option value="W" ${pageMaker.criteria.type eq 'W' ? 'selected' : ''}>작성자</option>
                                <option value="TC" ${pageMaker.criteria.type eq 'TC' ? 'selected' : ''}>제목 or 내용</option>
                                <option value="TW" ${pageMaker.criteria.type eq 'TW' ? 'selected' : ''}>제목 or 작성자</option>
                                <option value="TCW" ${pageMaker.criteria.type eq 'TWC' ? 'selected' : ''}>제목 or 작성자 or 내용</option>
                            </select>
                            <input type="text" name="keyword" value="${pageMaker.criteria.keyword}"/>
                            <input type="hidden" name="pageNum" value="${pageMaker.criteria.pageNum}">
                            <input type="hidden" name="amount" value="${pageMaker.criteria.amount}">
                            <button class="btn btn-default">Search</button>
                        </form>
                    </div>
                </div>
                <div class="pull-right">
                    <ul class="pagination">
                        <c:if test="${pageMaker.prev}">
                            <li class="paginate_button previous"><a href="${pageMaker.startPage - 1}">Previous</a></li>
                        </c:if>
                        <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                            <li class='paginate_button ${pageMaker.criteria.pageNum == num ? "active" : ""}'><a href="${num}">${num}</a> </li>
                        </c:forEach>

                        <c:if test="${pageMaker.next}">
                            <li class="paginate_button next"><a href="${pageMaker.endPage + 1}">Next</a> </li>
                        </c:if>
                    </ul>
                    <form id="actionForm" action="/board/list" method="get">
                        <input type="hidden" name="pageNum" value='${pageMaker.criteria.pageNum}'>
                        <input type="hidden" name="amount" value='${pageMaker.criteria.amount}'>
                        <input type="hidden" name="type" value='${pageMaker.criteria.type}'>
                        <input type="hidden" name="keyword" value='${pageMaker.criteria.keyword}'>
                    </form>
                </div>
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
        var actionForm = $("#actionForm")

        $(".paginate_button a").on("click", function (e){
            e.preventDefault()
            console.log("click")
            actionForm.attr("action", "/board/list")
            $(".bno_val").remove()
            actionForm.find("input[name='pageNum']").val($(this).attr("href"))
            actionForm.submit()
        })

        $(".move").on("click", function(e){
            e.preventDefault()
            actionForm.append("<input class='bno_val' type='hidden' name='bno' value='" + $(this).attr("href") + "'>")
            actionForm.attr("action", "/board/get")
            actionForm.submit();
        })

        var searchForm = $("#searchForm")

        $("#searchForm button").on("click", function (e){
            if(!searchForm.find("option:selected").val()){
                alert("검색 종류 선택하세요")
                return false
            }
            if(!searchForm.find("input[name='keyword']").val()){
                alert("키워드 입력하세요")
                return false
            }
            searchForm.find("input[name='pageNum']").val("1")
            e.preventDefault()
            searchForm.submit()
        })


    })
</script>

<%@ include file="../includes/footer.jsp" %> 