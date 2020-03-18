<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.BuyDataBean"%>
<%@ page import="bookshop.shopping.BuyDBBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<%
	if(session.getAttribute("id") == null){
		response.sendRedirect("shopMain.jsp");
	}

	String buyer = (String)session.getAttribute("id");
	
	List<BuyDataBean> buyLists = null;
	BuyDataBean buyList = null;
	
	int count = 0; //총 구매 건수
	int total = 0; //소계금액
	int sum = 0; //총 합계 금액
	long compareId = 0; //buy_id를 비교하기 위한 변수
	
	String realFolder = "";
	String saveFolder = "";
	ServletContext context = getServletContext();
	realFolder = context.getRealPath(saveFolder);
	realFolder = "http://localhost:8888/BookShop/imageFile";
	
	BuyDBBean buyProcess = BuyDBBean.getInstance();
	
	count = buyProcess.getListCount(buyer);
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<title>구매 목록</title>
	<style type="text/css">
		.gi_2x{
			font-size: 2m;
		}
	</style>
</head>
<body>

	<jsp:include page="../module/top.jsp" flush="false"/>
	<br><br><br>
	
<%
	if(count <= 0){ //구매내역이 하나도 없는 경우
%>	
		<table class="table table-bordered table-striped nanum table-hover">
			<tr class="info">
				<td align="center"><h2>구매하신 내역이 없습니다</h2></td>
			</tr>
		</table>
<%
	}else{ //구매내역이 있는 경우
%>
		<div class="col-sm-offset-1 col-sm-10 col-sm-offset-1">
			<table class="table table-bordered table-striped nanum table-hover">
			<tr>
				<td colspan="6">
					<h3><span class="label label-success">구 매 목 록</span></h3>
				</td>
			</tr>
			<tr class="info">
				<td width="100" align="center">번호</td>
				<td width="300" align="center">제목</td>
				<td width="100" align="center">배송상태</td>
				<td width="70" align="center">판매가격</td>
				<td width="50" align="center">구매수량</td>
				<td width="70" align="center">구매금액</td>
			</tr>
		<%
			buyLists = buyProcess.getBuyList(buyer);
		
			for(int i=0;i<buyLists.size();i++){
				buyList = buyLists.get(i);
					
				if(buyList.getBook_image() == null){
					buyList.setBook_image("nothing.jpg");
				}
					
				//현재 한 건 가져온 데이터를 출력해 준 후, 다음 buy_id와 비교할 값을 구하기
				//다음 buy_id를 구하는데, 현재 buy_id가 마지막 데이터면 다음 데이터를 구할 수 없으므로 -1일때까지만 구한다
				if(i<buyLists.size()-1){
					BuyDataBean compare = buyLists.get(i+1);
					compareId = compare.getBuy_id();
				}
		%>
			<tr>
				<td width="100"><%=buyList.getBuy_id() %></td>
				<td width="300">
					<img src="<%=realFolder%>/<%=buyList.getBook_image()%>" border="0" width="50" height="70" align="middle">
					<%=buyList.getBook_title() %>
				</td>
				<td width="100">
					<%if(buyList.getSanction().equals("상품준비중")){ %>
						<span class="glyphicon glyphicon-gift gi-2x"></span>
					<%}else if(buyList.getSanction().equals("상품배송중")){ %>
						<span class="glyphicon glyphicon-send gi-2x"></span>  
					<%}else if(buyList.getSanction().equals("배송완료")){ %>
						<span class="glyphicon glyphicon-home gi-2x"></span>
					<%}//end - if/else문 %>	
					<%=buyList.getSanction() %>
				</td>
				<td width="70"><%=NumberFormat.getInstance().format(buyList.getBuy_price()) %>원</td>
				<td width="50"><%=buyList.getBuy_count() %></td>
				<td width="70">
					<%total += buyList.getBuy_count() * buyList.getBuy_price(); %>
					<%=NumberFormat.getInstance().format(buyList.getBuy_count() * buyList.getBuy_price()) %>원
				</td>
			</tr>
			
			<%
				//한 건의 데이터를 출력
				//현재 buy_id와 다음 buy_id를 비교해서 값이 다르면 소계를 출력한다
				//또는 마지막 데이터이면 무조건 소계를 출력한다
				if((buyList.getBuy_id() != compareId) || (i == buyLists.size() -1)){
					sum += total;
			%>
			<tr class="danger">
				<td colspan="6" align="right">
					<h4>금액 : <%=NumberFormat.getInstance().format(total)%>&nbsp;원</h4>
				</td>
			</tr>
		<%			total = 0;
					compareId = buyList.getBuy_id();
				}//end - if문
			}//end - for문
		%>
			<tr>
				<td colspan="6" align="right" class="">
					<h3>총 금액 : <%=NumberFormat.getInstance().format(sum) %>&nbsp;원</h3>
				</td>
			</tr>
		</table>
		</div>
<%
	}//end - if/else문
%>
	
</body>
</html>