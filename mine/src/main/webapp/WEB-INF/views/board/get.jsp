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
				<div class="form-group">
					<label>Bno</label> <input class="form-control" name='bno'
						value='<c:out value="${board.bno }"/>' readonly>
				</div>
				<div class="form-group">
					<label>Title</label> <input class="form-control" name='title'
						value='<c:out value="${board.title }"/>' readonly>
				</div>
				<div class="form-group">
					<label>Content</label> <textarea class="form-control" name='content' rows="3"
						 readonly><c:out value="${board.content}"/></textarea>
				</div>
				<div class="form-group">
					<label>Writer</label> <input class="form-control" name='writer'
						value='<c:out value="${board.writer }"/>' readonly>
				</div>
				<button data-oper='modify' class="btn btn-default">
				Modify
				</button>
				<button data-oper='list' class="btn btn-info">List</button>
				<form action="/board/modify" id='operForm' method="get">
					<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno }"/>'>
					<input type='hidden' name='pageNum' value='<c:out value = "${cri.pageNum }"/>'>
					<input type='hidden' name='amout' value='<c:out value = "${cri.amount }"/>'>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		var operForm = $("#operForm");
		
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", function(e){
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list").submit();
		});
	});
	
</script>
<%@ include file="../includes/footer.jsp"%>