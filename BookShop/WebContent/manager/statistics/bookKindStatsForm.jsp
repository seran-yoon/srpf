<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.BuyBookKindDataBean"%>
<%@ page import="bookshop.shopping.BuyDBBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<%
	request.setCharacterEncoding("utf-8");
	
	String managerId = "";
	
	try{
		managerId = (String)session.getAttribute("managerId");
		if(managerId == null || managerId.equals("")){
			response.sendRedirect("../logon/managerLoginFrom.jsp");
		}else{
			String year = request.getParameter("year");
			
			BuyBookKindDataBean buyBookKindList = null;
			BuyDBBean buyProcess = BuyDBBean.getInstance();
			buyBookKindList = buyProcess.buyBookKindYear(year);
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="../../css/morris.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<script src="../../js/morris.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
	<title>도서종류별 연간 판매비율(도너츠)</title>
</head>
<body>

	<div class="container">
		<h2 align="center"><b>도서종류별 연간 판매비율(도너츠)</b></h2><hr><br>
		<form class="form-horizontal" role="form" method="post" name="bookKindStatsForm" action="bookKindStatsForm.jsp">
			<div class="form-group">
				<div class="col-sm-1">
					<h4><span class="label label-info">검색 년도</span></h4>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" id="year" name="year" maxlength="8" placeholder="Enter Year">
				</div>
				<div class="col-sm-2">
					<input type="submit" class="btn btn-danger btn-sm" value="검색하기" action="javascript:window.location=bookKindStatsForm.jsp">
					<!-- 어짜피 버튼 타입이 submit이라 뒤에 action을 안써도 전송은 되지만 저렇게 쓸 수 있다는 걸 보여주기 위함 -->
					<input type="button" class="btn btn-info btn-sm" value="메인으로" onclick="javascript:window.location='../managerMain.jsp'">
				</div>
			</div>
			<br>
			<table class="table table-bordered" border="1" width="700" cellspacing="0" cellpadding="0" align="center">
				<thead>
					<tr class="info">
						<td align="center"><h4><b>문학</b></h4>
						<td align="center"><h4><b>외국어</b></h4>
						<td align="center"><h4><b>컴퓨터</b></h4>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="center"><h4><%=buyBookKindList.getBookQty100()%></h4></td>
						<td align="center"><h4><%=buyBookKindList.getBookQty200()%></h4></td>
						<td align="center"><h4><%=buyBookKindList.getBookQty300()%></h4></td>
					</tr>
					<tr class="danger">
						<td align="right" colspan="3">
							<h4><p class="bg-danger">총 판매 수량 : <%=buyBookKindList.getTotal()%> 권</p></h4>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div id="myfirstchart" style="height:300px;"></div>
	</div>
	
	
	<script>
	//(문학의 총 수량  * 100) / 책 전체 수량
		var q1 = Math.floor(Number(<%=buyBookKindList.getBookQty100()%>) * 100 / (<%=buyBookKindList.getTotal()%>));
		var q2 = Math.floor(Number(<%=buyBookKindList.getBookQty200()%>) * 100 / (<%=buyBookKindList.getTotal()%>));
		var q3 = Math.floor(Number(<%=buyBookKindList.getBookQty300()%>) * 100 / (<%=buyBookKindList.getTotal()%>));
		
		new Morris.Donut({
			element: 'myfirstchart', //그래프를 표시하기 위한 객체의 id
			data: [ //그래프의 데이터. 각 요소가 하나의 그래프 상의 값에 해당한다
					{value: q1, label: '문학'},
					{value: q2, label: '외국어'},
					{value: q3, label: '컴퓨터'}
				  ],
			backgroundColor: '#ccc',
			labelColor: '#060',
			colors: ['#0BA462', '#39B580', '#67C69D'],
			formatter: function(x){
						return x + "%"
					   }
		});
	</script>
	
</body>
</html>

<%
		}//end - if/else문
	}catch(Exception e){
		e.printStackTrace();
	}//end - try/catch
%>