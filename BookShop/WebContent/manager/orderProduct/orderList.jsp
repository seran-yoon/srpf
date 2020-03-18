<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.BuyDBBean"%>
<%@ page import="bookshop.shopping.BuyDataBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<%
	String buyer = (String)session.getAttribute("id");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<title>주문 목록</title>
</head>
<body>
<%
	List<BuyDataBean> buyLists = null;
	BuyDataBean buyList = null;
	
	int count = 0; //총 구매 건수
	
	BuyDBBean buyProcess = BuyDBBean.getInstance();
	count = buyProcess.getListCount();
	
	if(count == 0){ //주문 내역이 없는 경우
%>
	
	<div align="center">
		<h3><b>주문 목록</b></h3><br>
		<a href="../managerMain.jsp">관리자 메인으로</a>
	</div>
	<br><br>
	
	<table class="table table-bordered table-striped nanum table-hover">
		<tr class="info">
			<td align="center">주문 목록 내역이 없습니다</td>
		</tr>
	</table>
	
<%
	}else{ //주문 내역이 있는 경우	
		buyLists = buyProcess.getBuyList();
%>
	<div align="center">
		<h3><b>주문 목록</b></h3>
		<a href="../managerMain.jsp">관리자 메인으로</a>
	</div>
	<br><br>
		
	<table class="table table-bordered table-striped nanum table-hover">
		<tr class="info">
			<td width="90" align="center">주문 번호</td>
			<td width="70" align="center">주문 고객</td>
			<td width="100" align="center">책 이름</td>
			<td width="50" align="center">주문 가격</td>
			<td width="30" align="center">주문 수량</td>
			<td width="90" align="center">주문 일</td>
			<td width="150" align="center">결제 계좌</td>
			<td width="70" align="center">배송지 명</td>
			<td width="70" align="center">배송지 전화번호</td>
			<td width="200" align="center">배송지 주소</td>
			<td width="70" align="center">배송 상황</td>
		</tr>
		<%
			for(int i=0;i<buyLists.size();i++){
				buyList = (BuyDataBean)buyLists.get(i);
		%>
		<tr>
			<td width="90"><%=buyList.getBuy_id() %></td>
			<td width="70"><%=buyList.getBuyer() %></td>
			<td width="100"><%=buyList.getBook_title() %></td>
			<td width="50"><%=NumberFormat.getInstance().format(buyList.getBuy_price())%></td>
			<td width="30"><%=buyList.getBuy_count() %></td>
			<td width="90"><%=buyList.getBuy_date().toString() %></td>
			<td width="150"><%=buyList.getAccount() %></td>
			<td width="70"><%=buyList.getDeliveryName() %></td>
			<td width="70"><%=buyList.getDeliveryTel() %></td>
			<td width="200"><%=buyList.getDeliveryAddress() %></td>
			<td width="70"><%=buyList.getSanction()%></td>
		</tr>
		<% 
			}//end - for문
		%>
	</table>
		
<%	
	}//end - if/else문
%>
</body>
</html>

