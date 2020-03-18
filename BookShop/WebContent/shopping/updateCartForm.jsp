<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.CartDBBean"%>

<%
	String cart_id = request.getParameter("cart_id");
	String buy_count = request.getParameter("buy_count");
	String buy_countOld = request.getParameter("buy_countOld");
	String book_kind = request.getParameter("book_kind");
	String book_id = request.getParameter("book_id");
	
	if(session.getAttribute("id") == null){
		response.sendRedirect("shopMain.jsp");
	}else{
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<title>도서 수량 변경</title>
	<script>
		function cartUpdate(){
			var rtnVal = confirm("구매수량을 수정하시겠습니까?");
			if(rtnVal == true){
				updateForm.action = "./updateCartPro.jsp";
				updateForm.submit();
			}
		}
	</script>
</head>
<body>

	<div>
		<h2>구매 수량 변경</h2>
		<form class="form-inline" method="post" name="updateForm">
		<div class="form-group">
			<label class="sr-only">변경 할 수량</label>
			<input type="text" class="form-control" name="buy-count" size="4" value="<%=buy_count%>" placeholder="구매 수량">
			<input type="hidden" class="form-control" name="buy-countOld" value="<%=buy_countOld%>">
			<input type="hidden" class="form-control" name="cart_id" value="<%=cart_id%>">
			<input type="hidden" class="form-control" name="book_kind" value="<%=book_kind%>">
			<input type="hidden" class="form-control" name="book_id" value="<%=book_id%>">
		</div>
		<input type="submit" align="center" class="btn btn-danger btn-submit" value="수정" onclick="cartUpdate(); return false;" onFocus="this.blur()">
		</form>	
	</div>

</body>
</html>

<%
	}//end - if/else문
%>
