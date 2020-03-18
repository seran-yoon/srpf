<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "bookshop.master.ShopBookDBBean" %>
<%@ page import = "bookshop.master.ShopBookDataBean" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.NumberFormat" %>
<% String book_kind = request.getParameter("book_kind"); %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../css/main.css" rel="stylesheet" type="text/css">
	<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<title>세라딘</title>
</head>
<body bgcolor="#cceeff">

	<jsp:include page="../module/top.jsp" flush="false"/> 
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<jsp:include page="../module/left.jsp" flush="false"/>
				</ul>
			</div>
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
					    int count = bookProcess.getBookCount2(book_kind);
					    
						if(book_kind.equals("100")){
							book_kindName = "문학";
						}else if(book_kind.equals("200")){
							book_kindName = "외국어";
						}else if(book_kind.equals("300")){
							book_kindName = "컴퓨터";
						}else if(book_kind.equals("all")){
							book_kindName = "전체";
						}
					%>
						<div align="center">
							<h3><b><%=book_kindName %> 분류의 목록</b></h3>
							<a href="shopMain.jsp">메인으로</a><hr>
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
										<a href="bookContent.jsp?book_id=<%=bookList.getBook_id()%>&book_kind=<%=book_kind%>">
											<center><img src="<%=realFolder%>\<%=bookList.getBook_image()%>" border="0" width="125" height="180"></center>
										</a>
									</td>
									<td width="350">
										<font size="+1">
											<b>
											<a href="bookContent.jsp?book_id=<%=bookList.getBook_id()%>&book_kind=<%=book_kind%>"><%=bookList.getBook_title()%></a>
											</b>
										</font>
									</td>
									<td rowspan="4" width="100" align="center" valign="middle"> <!-- valign = 높이 지정할때 -->
										<%
											if(bookList.getBook_count() == 0){ 
										%>
												<h5 align="center"><b><font color="red">일시품절</font></b></h5>
										<%
											}else{
										%>
												<div align="center"><b><font color="gray">구매가능</font></b></div>
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
	</div>

</body>
</html>