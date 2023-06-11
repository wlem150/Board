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
					<input type='hidden' name='amount' value='<c:out value = "${cri.amount }"/>'>
					<input type='hidden' name='type' value='<c:out value = "${cri.type }"/>'>
					<input type='hidden' name='keyword' value='<c:out value = "${cri.keyword}"/>'>
					
				</form>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
				<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New Reply</button>
			</div>
			<div class="panel-body">
				<ul class="chat">
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">
									user00
									<small class="pull-right text-muted">2023-06-10 11:20</small>
								</strong>
							</div>
							<p>Good job!</p>
						</div>
					</li>
				</ul>
			</div>
			<div class="panel-footer">
			</div>
		</div>
	</div>
</div>



<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label>
					<input class="form-control" name="reply" value="New Reply!!!">
				</div>
				
				<div class="form-group">
					<label>Replyer</label>
					<input class="form-control" name="replyer" value="Replyer">
				</div>
				
				<div class="form-group">
					<label>Reply Date</label>
					<input class="form-control" name="replyDate" value="">
				</div>
				
			</div>
			<div class="modal-footer">
					<button id="modalModBtn" type="button" class="btn btn-warning">
						Modify
					</button>
					
					<button id="modalRemoveBtn" type="button" class="btn btn-danger" >
						Remove
					</button>
					
					<button id="modalRegisterBtn" type="button" class="btn btn-primary" data-dismiss="modal">
						Register
					</button>
					<button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">
						Close
					</button>
			</div>
		</div>
	</div>
</div>



<script src="\resources\js\reply.js"></script>
<script>
$(document).ready(function(){

	var bnoValue = '<c:out value="${board.bno}"/>';
	var replyUL = $(".chat");

		showList(1);
		function showList(page){
			replyService.getList({bno:bnoValue, page:page||1}, function(replyCnt,list){
				if(page == -1){
					pageNum =Math.ceil(replyCnt/10.0)
					showList(pageNum);
					return;
				}
				
				var str="";
				if(list == null || list.length == 0){
					replyUL.html("");
					return ;
				}
				for(var i = 0, len = list.length || 0; i < len; i++){
					str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
					str += " <div><div class='header'><string class='primary-font'>"+list[i].replyer+"</string>";
					str += "<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str += "<p>"+list[i].reply+"</p></div></li>";
					}
				replyUL.html(str);
				showReplyPage(replyCnt);
			})
		} // end showList
		

		var modal = $(".modal");
		var modalInputReply = modal.find("input[name = 'reply']");
		var modalInputReplyer = modal.find("input[name = 'replyer']");
		var modalInputReplyDate = modal.find("input[name = 'replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		$("#addReplyBtn").on("click", function(e){
			modal.find("input").val("");
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id != 'modalCloseBtn']").hide();
			
			modalRegisterBtn.show();
			
			$(".modal").modal("show");
			
		}); // addReplyBtn end
		
		$(".chat").on("click","li" ,function(e){
			var rno = $(this).data("rno");
			replyService.get(rno, function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
				modal.data("rno", reply.rno);
				
				modal.find("button[id != 'modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				
				$(".modal").modal("show");
				
				modalModBtn.on("click",function(e){
					var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
					replyService.update(reply, function(result){
						alert(result);
						modal.modal("hide");
						showList(pageNum);
					})
				})
				
				modalRemoveBtn.on("click",function(e){
					var rno = modal.data("rno");
					replyService.remove(rno, function(result){
						alert(result);
						modal.modal("hide");
						showList(pageNum);
					})
				})
			});
		}); // chat end
		
		$("#modalRegisterBtn").on("click",function(result){
			var reply = {
					reply: modalInputReply.val(),
					replyer:modalInputReplyer.val(),
					bno:bnoValue
			};
			
			replyService.add(reply, function(result){
				alert(result);
				modal.find("input").val("");
				modal.modal("hide");
				
				showList(1);
			});
		}); // modalRegisterBtn end
		
		var pageNum = 1;
		var replyPageFooter = $(".panel-footer");

		function showReplyPage(replyCnt) {
		  var endNum = Math.ceil(pageNum / 10.0) * 10;
		  var startNum = endNum - 9;

		  var prev = startNum != 1;
		  var next = false;

		  if (endNum * 10 >= replyCnt) {
		    endNum = Math.ceil(replyCnt / 10.0);
		  }

		  if (endNum * 10 < replyCnt) {
		    next = true;
		  }

		  var str = "<ul class='pagination pull-right'>";

		  if (prev) {
		    str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'>Previous</a></li>";
		  }

		  for (var i = startNum; i <= endNum; i++) {
		    var active = pageNum == i ? "active" : "";
		    str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
		  }

		  if (next) {
		    str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>Next</a></li>";
		  }
		  str += "</ul></div>";

		  console.log(str);
		  replyPageFooter.html(str);
		  replyPageFooter.on("click", "li a", function(e){
				e.preventDefault();
				console.log("page click");
				
				var targetPageNum = $(this).attr("href");
				console.log("targetPageNum : " + targetPageNum);
				pageNum = targetPageNum;
				showList(pageNum);
			});
		}
		
});



</script>
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