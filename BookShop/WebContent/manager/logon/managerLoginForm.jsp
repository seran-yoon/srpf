<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 부트스트랩 쓰기 위한 코드 추가 -->
	<meta name="viewport" content="width=device-width, initial!-scale=1">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<!--  -->
	<title>managerLoginForm.jsp</title>
	<style>
		.modal-header, h4, .close{
			background-color: #5cb85c;
			color: white !important; /* 뒤에 !important를 써주면 중요한거다 꼭 적용하라 라는 뜻 */
			text-align: center;
			font-size: 30px;
		}
		.modal-footer{
			background-color: #f9f9f9;
		}
	</style>
</head>
<body>
	
	<div class="container">
		<h2>Manager Login</h2>
		<button type="button" class="btn btn-default btn-lg" id="myBtn">Login</button>
		
		<!-- Modal --> <!-- modal은 pop up같은 개념 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			
				<!-- Modal Content -->
				<div class="modal-content">
					<div class="modal-header" style="padding:35px 50px;">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4><span class="glyphicon glyphicon-lock"></span> Manager Login</h4>
					</div>
					<div class="modal-body" style="padding:40px 50px;">
						<form class="form-horizontal" role="form" method="post" action="managerLoginPro.jsp">
							<div class="form-group">
								<label for="username"><span class="glyphicon glyphicon-user"></span> Manager ID</label>
								<input type="text" class="form-control" id="id" name="id" maxlength="10" placeholder="관리자 ID를 입력하십시오">
							</div>
							<div class="form-group">
								<label for="pwd"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
								<input type="password" class="form-control" id="pwd" name="passwd" maxlength="10" placeholder="비밀번호를 입력하십시오">
							</div>
							<div>
								<button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span> Login</button>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span>Cancel</button>
					</div>
				</div>
			
			</div>
		</div>
		
	</div>

<script>
$(document).ready(function(){
	$("#myBtn").click(function(){
		$("#myModal").modal();
	})
});
</script>
<script>
$(function(){
	$('#myModal').modal({
		keyboard: true
	})
});
</script>

</body>
</html>