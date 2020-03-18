<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.DeliveryDataBean"%>
<%@ page import="bookshop.shopping.BuyDBBean"%>
<%@ page import="bookshop.shopping.BuyDataBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<%
	request.setCharacterEncoding("utf-8");
	String managerId = "";
	managerId = (String)session.getAttribute("managerId");
	
	if(managerId == null || managerId.equals("")){
		response.sendRedirect("../logon/managerLoginForm.jsp");
	}
	
	List<BuyDataBean> buyLists = null;
	BuyDataBean buyList = null;
	DeliveryDataBean deliveryList = null;
	
	int count = 0;
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<title>배송 관리</title>
	<script type="text/javascript">
		function openUser(buyId, sanction, bookTitle){
			window.open('deliveryUpModalForm.jsp?buyId=' + buyId + '&sanction=' + sanction + '&bookTitle=' + bookTitle, '', 'left=400, top=100, width=900, height=600 scrollbar=no, status=no, resizable=no, fullscreen=no, channelmode=no');
			
			return false;			
		}
	</script>
</head>
<body>

<%
	BuyDBBean buyProcess = BuyDBBean.getInstance();
	count = buyProcess.getListCount();
	
	if(count == 0){ //데이터가 없는 경우
%>
	<div align="center">
		<h3><b>배송 목록</b></h3><br>
		<a href="../managerMain.jsp">관리자 메인으로</a>
	</div>
	<br><br>
	
	<table class="table table-bordered table-striped nanum table-hover">
		<tr class="info">
			<td align="center">배송 목록 내역이 없습니다</td>
		</tr>
	</table>
<%
	}else{ //데이터가 있는 경우
		buyLists = buyProcess.getBuyList();
%>
	<div align="center">
		<h3><b>배송 목록</b></h3>
		<a href="../managerMain.jsp">관리자 메인으로</a>
	</div>
	<br><br>
	
	<form action="deliveryList.jsp" class="form-horizontal" role="form" method="post" name="deliverList">
		<table class="table table-bordered table-striped nanum table-hover">
			<tr class="info">
				<td width="130" align="center">주문 번호</td>
				<td width="100" align="center">배송 상황</td>
				<td width="100" align="center">주문 고객</td>
				<td width="200" align="center">책 이름</td>
				<td width="70" align="center">주문 가격</td>
				<td width="70" align="center">주문 수량</td>
				<td width="150" align="center">주문 일</td>
				<td width="200" align="center">결제 계좌</td>
				<td width="70" align="center">배송지 명</td>
				<td width="100" align="center">배송지 전화번호</td>
				<td width="150" align="center">배송지 주소</td>
			</tr>
		<%
			for(int i=0; i<buyLists.size();i++){
				buyList = (BuyDataBean)buyLists.get(i);
		%>
			<tr>
				<td width="130"><a href="#" onclick="return openUser('<%=buyList.getBuy_id()%>','<%=buyList.getSanction()%>', '<%=buyList.getBook_title()%>');"><%=buyList.getBuy_id()%></a></td>
				<!-- 배송상황에 따라 이미지와 글자의 색상을 변경한다 -->
				<td width="100">
					<%if(buyList.getSanction().equals("상품준비중")){ %>
						<span class="glyphicon glyphicon-gift"></span>
						<p class="text-success"><%=buyList.getSanction() %></p>
					<%}else if(buyList.getSanction().equals("상품배송중")){ %>
						<span class="glyphicon glyphicon-send"></span>
						<p class="text-warning"><%=buyList.getSanction() %></p>
					<%}else if(buyList.getSanction().equals("배송완료")){ %>
						<span class="glyphicon glyphicon-home"></span>
						<p class="text-danger"><%=buyList.getSanction() %></p>
					<%} %>
				</td>
				<td width="100"><%=buyList.getBuyer() %></td>
				<td width="200"><%=buyList.getBook_title() %></td>
				<td width="70"><%=NumberFormat.getInstance().format(buyList.getBuy_price()) %></td>
				<td width="70"><%=buyList.getBuy_count() %></td>
				<td width="150"><%=buyList.getBuy_date().toString() %></td>
				<td width="200"><%=buyList.getAccount() %></td>
				<td width="70"><%=buyList.getDeliveryName() %></td>
				<td width="100"><%=buyList.getDeliveryTel() %></td>
				<td width="150"><%=buyList.getDeliveryAddress() %></td>
			</tr>
		<% 
			}//end - for문
		%>
		</table>
	</form>
<%
	}//end - if/else문
%>

</body>
</html>