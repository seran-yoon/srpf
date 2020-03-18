<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.DeliveryDataBean"%>
<%@ page import="bookshop.shopping.BuyDBBean"%>
<%@ page import="bookshop.shopping.BuyDataBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<%
	request.setCharacterEncoding("utf-8");
	String sanction = request.getParameter("sanction");
	String buyId = request.getParameter("buyId");
	
	String managerId = "";
	managerId = (String)session.getAttribute("managerId");
	
	if(managerId == null || managerId.equals("")){
		response.sendRedirect("../logon/managerLoginForm.jsp");
	}
	
	int rtnVal = 0;
	
	BuyDBBean buyProcess = BuyDBBean.getInstance();
	rtnVal = buyProcess.getDeliveryStatus(buyId);
	
	String bookTitle = request.getParameter("bookTitle");
	
	//배송완료 : 1(상품준비중), 2(상품배송중), 3(배송완료)
	String[] deliveryName = {"상품준비중", "상품배송중", "베송완료"};
	String[] deliveryRadio = {"40", "40", "20"};
	String[] deliveryColor = {"success", "warning", "danger"};
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<title>배송 상태 수정</title>
</head>
<body>

	<div class="container">
		<form class="form-horizontal" role="form" method="post" name="deliveryUpModalPro" action="deliveryUpModalPro.jsp">
			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-6"><h2 align="center">배송 상태 수정</h2></div>
			</div>
			<div class="form-group">
				<div class="col-sm-6">
					<label class="radio-inline">
						<input type="radio" id="sanction" name="sanction" value="1" <%if(rtnVal==1){%>checked<%}%>>상품준비중
					</label>
					<label class="radio-inline">
						<input type="radio" id="sanction" name="sanction" value="2" <%if(rtnVal==2){%>checked<%}%>>상품배송중
					</label>
					<label class="radio-inline">
						<input type="radio" id="sanction" name="sanction" value="3" <%if(rtnVal==3){%>checked<%}%>>배송완료
					</label>
					<input type="hidden" id="buyId" name="buyId" value="<%=buyId%>">
					<input type="hidden" id="bookTitle" name="bookTitle" value="<%=bookTitle%>">
				</div>
			</div>
			<div class="form-group">
				<div class="progress">
					<%for(int i=0;i<=rtnVal-1;i++){ %>
					<div class="progress-bar progress-bar-<%=deliveryColor[i]%> progress-bar-striped" style="width:<%=deliveryRadio[i]%>%; height=100px"><%=deliveryName[i]%></div>
					<%}%>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-5">
					<button type="submit" class="btn btn-primary" data-dismiss="modal">수정</button>
					<button type="reset" class="btn btn-danger">취소</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>