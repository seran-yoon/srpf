<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.BuyMonthDataBean"%>
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
			
			BuyMonthDataBean buyMonthList = null;
			BuyDBBean buyProcess = BuyDBBean.getInstance();
			buyMonthList = buyProcess.buyMonth(year);
			
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
	<title>월별 판매리스트(막대)</title>
</head>
<body>

	<div class="container">
		<h2 align="center"><b>월별 판매리스트(막대)</b></h2><hr><br>
		<form class="form-horizontal" role="form" method="post" name="monthBarStatsForm" action="monthBarStatsForm.jsp">
			<div class="form-group">
				<div class="col-sm-1">
					<h4><span class="label label-info">검색 년도</span></h4>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" id="year" name="year" maxlength="8" placeholder="Enter Year">
				</div>
				<div class="col-sm-2">
					<input type="submit" class="btn btn-danger btn-sm" value="검색하기" action="javascript:window.location=monthBarStatsForm.jsp">
					<!-- 어짜피 버튼 타입이 submit이라 뒤에 action을 안써도 전송은 되지만 저렇게 쓸 수 있다는 걸 보여주기 위함 -->
					<input type="button" class="btn btn-info btn-sm" value="메인으로" onclick="javascript:window.location='../managerMain.jsp'">
				</div>
			</div>
			<br>
			<table class="table table-bordered" border="1" width="700" cellspacing="0" cellpadding="0" align="center">
				<thead>
					<tr class="info">
						<td align="center"><h4><b>1월</b></h4>
						<td align="center"><h4><b>2월</b></h4>
						<td align="center"><h4><b>3월</b></h4>
						<td align="center"><h4><b>4월</b></h4>
						<td align="center"><h4><b>5월</b></h4>
						<td align="center"><h4><b>6월</b></h4>
						<td align="center"><h4><b>7월</b></h4>
						<td align="center"><h4><b>8월</b></h4>
						<td align="center"><h4><b>9월</b></h4>
						<td align="center"><h4><b>10월</b></h4>
						<td align="center"><h4><b>11월</b></h4>
						<td align="center"><h4><b>12월</b></h4>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="center"><h4><%=buyMonthList.getMonth01()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth02()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth03()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth04()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth05()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth06()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth07()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth08()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth09()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth10()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth11()%></h4></td>
						<td align="center"><h4><%=buyMonthList.getMonth12()%></h4></td>
					</tr>
					<tr class="danger">
						<td align="right" colspan="12">
							<h4><p class="bg-danger">총 판매 수량 : <%=buyMonthList.getTotal()%> 권</p></h4>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div id="mybarChart" style="height:400px;"></div>
	</div>
	
	<script>
		var m01 = <%=year%> + "-01";
		var m02 = <%=year%> + "-02";
		var m03 = <%=year%> + "-03";
		var m04 = <%=year%> + "-04";
		var m05 = <%=year%> + "-05";
		var m06 = <%=year%> + "-06";
		var m07 = <%=year%> + "-07";
		var m08 = <%=year%> + "-08";
		var m09 = <%=year%> + "-09";
		var m10 = <%=year%> + "-10";
		var m11 = <%=year%> + "-11";
		var m12 = <%=year%> + "-12";
		
		new Morris.Bar({
			element: 'mybarChart',
			data: [
					{month: m01, y: <%=buyMonthList.getMonth01()%>},
					{month: m02, y: <%=buyMonthList.getMonth02()%>},
					{month: m03, y: <%=buyMonthList.getMonth03()%>},
					{month: m04, y: <%=buyMonthList.getMonth04()%>},
					{month: m05, y: <%=buyMonthList.getMonth05()%>},
					{month: m06, y: <%=buyMonthList.getMonth06()%>},
					{month: m07, y: <%=buyMonthList.getMonth07()%>},
					{month: m08, y: <%=buyMonthList.getMonth08()%>},
					{month: m09, y: <%=buyMonthList.getMonth09()%>},
					{month: m10, y: <%=buyMonthList.getMonth10()%>},
					{month: m11, y: <%=buyMonthList.getMonth11()%>},
					{month: m12, y: <%=buyMonthList.getMonth12()%>},
			      ],
			xkey: 'month', //그래프 데이터에서 x축에 해당하는 값의 이름
			ykeys: ['y'], //그래프 데이터에서 y축에 해당하는 값의 이름
			labels: ['판매권수'],
			bgColors: function(row, series, type){
						if(type === 'bar'){
							var red = Math.ceil(255 * row.y / this.ymax);
							return 'rgb(' + red + ',0,0)';
						}else{
							return '#000';
						}
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