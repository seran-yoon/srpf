<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "bookshop.master.ShopBookDBBean" %>
<%@ page import = "bookshop.master.ShopBookDataBean" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.NumberFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	
	String managerId = "";
	try{
		managerId = (String)session.getAttribute("managerId");
		
		//세션값이 없으면 로그인 화면으로 이동한다
		if(managerId == null || managerId.equals("")){
			response.sendRedirect("logon/managerLoginForm.jsp?useSSL=false");
		}else{ //로그인이 되어있으면 화면을 보여줌
			String book_kind = "all";
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../css/main.css" rel="stylesheet" type="text/css">
	<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<title>세라딘 관리자 페이지</title>
	<style>
		body{
			position: relative;
		}
		.affix{
			top: 0;
			width: 100%;
			z-index: 9999 important;
		}
		.navbar{
			margin-bottom: 0px;
		}
		.affix ~ .container-fluid{
			position: relative;
			top: 50px;
		}
	</style>
</head>
<body data-spy="scroll" data-target=".navbar" data-offset="50">

	<div class="container-fluid" style="background-color:#ededed; color:#7d7d7d; height:310px">
	<center>
		<img src="../imageFile/세라딘.png" height="200" width="200">
		<h3>도서쇼핑몰, 세라딘 관리자 페이지</h3>
		<p>관리자만 접근 가능합니다.</p>
	</center>
	</div>
	
	<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar"> <!-- 화면의 크기가 작아지면 메뉴가 한 화면에 다 안나오기 때문에 더 볼 수 있는 버튼을 만든것 -->
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">세라딘</a>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 상품 관리  <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="productProcess/bookRegisterForm.jsp">상품등록</a></li>
								<li><a href="productProcess/bookList.jsp?book_kind=all">상품수정/삭제</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 판매 관리  <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="orderProduct/orderList.jsp">판매 리스트</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 배송 관리  <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="delivery/deliveryList.jsp">배송 리스트</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 통계 관리  <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="statistics/monthStatsForm.jsp">월별 판매리스트(꺾은선)</a></li>
                		  		<li><a href="statistics/monthBarStatsForm.jsp">월별 판매리스트(막대)</a></li>
                  				<li><a href="statistics/bookKindStatsForm.jsp">도서종류별 연간 판매비율(도너츠)</a></li>
							</ul>
						</li>
						<li><a href="logon/managerLogout.jsp">로그아웃</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
		<div class="container-fluid">
			<div class="col-sm-9 col-sm-offset-3 col-md-8 col-md-offset-2 main">
				<br><br><br>
				<table  class="table table-bordered table-striped nanum table-hover">
					<tr>
						<td width="700">
					<%
						List<ShopBookDataBean> bookLists = null;
						ShopBookDataBean bookList = null;
						String book_kindName = "";
						
						String realFolder = ""; //웹 어플리케이션 상의 절대 경로
						String saveFolder = "/imageFile"; //파일이 업로드되는 폴더
						
						ServletContext context = getServletContext();
						realFolder = context.getRealPath(saveFolder);
						realFolder = "http://localhost:8888/BookShop/imageFile";
						
						ShopBookDBBean bookProcess = ShopBookDBBean.getInstance();
					    int count = bookProcess.getBookCount();
					    
					%>
						<div align="center">
							<h3><b>세라딘에 등록된 도서</b></h3>
						</div>
					<%
						if (count<=0) {
					%>
							<table class="table table-bordered table-striped nanum table-hover">
								<tr>
									<td align="center">
										<h1>등록된 책이 없습니다.</h1>
									</td>
								</tr>
							</table>
						
					<%
						}else{
						//책 종류에 대한 모든 책의 자료를 가져온다.
							bookLists = bookProcess.getBooks(book_kind);
						
							for(int i=0;i<bookLists.size();i++) {
								bookList = (ShopBookDataBean)bookLists.get(i);
					%>
							<table class="table table-bordered table-striped nanum table-hover">
								<tr>	
									<td rowspan="4" width="100">
										<center><img src="<%=realFolder%>\<%=bookList.getBook_image()%>" border="0" width="125" height="180"></center>
									</td>
									<td width="350">
										<font size="+1">
											<b><%=bookList.getBook_title()%></b>
										</font>
									</td>
									<td rowspan="4" width="100" align="center" valign="middle"> <!-- valign = 높이 지정할때 -->
										<%
											if(bookList.getBook_count() == 0){ 
										%>
												<h5 align="center"><b><font color="red">판매 종료(재고소진)</font></b></h5>
										<%
											}else{
										%>
												<div align="center"><b><font color="gray">판매 중</font></b></div>
										<%	
											}
										%>
									</td>
								</tr>
								<tr>
									<td>출판사 : <%=bookList.getPublishing_com() %></td>
								</tr>
								<tr>
									<td>저자 : <%=bookList.getAuthor() %></td>
								</tr>
								<tr>
									<td>
										정가 : <%=NumberFormat.getInstance().format(bookList.getBook_price())%>원<br>
										판매가 : <b><font color="red"><%=NumberFormat.getInstance().format((int)(bookList.getBook_price()*((double)(100-bookList.getDiscount_rate())/100)))%></font></b>원
									</td>
								</tr>
							</table>	
							
					<% 
							} // End - for문
						} // End - if/else문
					%>
						</td>
					</tr>
				</table>
			</div>
		</div>

</body>
</html>

<%
		}//end - if/else문
	}catch(Exception e){
		e.printStackTrace();
	}//end - try/catch문
%>