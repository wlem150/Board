<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../includes/header.jsp"%>

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
			<div class="panel-heading">Board Read Page</div>
			<div class="panel-body">
				<form action="/board/modify" role="form" method="post">
					<div class="form-group">
						<label>Bno</label> <input class="form-control" name='bno'
							value='<c:out value="${board.bno }"/>' readonly>
					</div>
					<div class="form-group">
						<label>Title</label> <input class="form-control" name='title'
							value='<c:out value="${board.title }"/>'>
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea class="form-control" name='content' rows="3"><c:out
								value="${board.content}" /></textarea>
					</div>
					<div class="form-group">
						<label>Writer</label> <input class="form-control" name='writer'
							value='<c:out value="${board.writer }"/>'>
					</div>
					<%-- 	<div class="form-group">
					<label>RegDate</label> <input class="form-control" name='regDate'
						value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regDate }"/>' readonly>
				</div>
				<div class="form-group">
					<label>UpdateDate</label> <input class="form-control" name='updateDate'
						value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate }"/>' readonly>
				</div>			
				 --%>
					<button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
					<button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
					<button type="submit" data-oper='list' class="btn btn-info">List</button>
				</form>
			</div>

		</div>

	</div>
</div>
<script>
	$(document).ready(function() {
		var formObj = $("form");

		$("button").on("click", function(e) {
			e.preventDefault();

			var operation = $(this).data("oper");

			console.log(operation);

			if (operation === "remove") {
				formObj.attr("action", "/board/remove");
			} else if (operation === "list") {
				formObj.attr("action", "/board/list").attr("method", "get");
				formObj.empty();
			}
			formObj.submit();
		});
	});
</script>

<%@ include file="../includes/footer.jsp"%>