<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="bookshop.master.ShopBookDBBean"%>
<%@ page import="bookshop.master.ShopBookDataBean"%>

<% 
	String managerId = "";
	
	try{
		managerId = (String)session.getAttribute("managerId");
		
		if(managerId == null || managerId.equals("")){
			//세션값이 없으면 정상적으로 로그인을 하지 않은 경우이므로 쫓아낸다
			response.sendRedirect("../logon/managerLoginForm.jsp");
		}else{
			//정상적으로 로그인을 하고 들어온 경우 웹페이지를 보여준다
			int book_id = Integer.parseInt(request.getParameter("book_id"));
			String book_kind = request.getParameter("book_kind");
			try{
				ShopBookDBBean bookprocess = ShopBookDBBean.getInstance();
				ShopBookDataBean book = bookprocess.getBook(book_id);
%> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../etc/script.js"></script> <!-- 밑에서 버튼 onclick시에 사용할 script문 -->
	<title>도서 정보 수정</title>
</head>
<body>

	<form method="post" name="writeform" action="bookUpdatePro.jsp" enctype="multipart/form-data"> <!-- enctype="multipart/form-data" => 모든 문자를 인코딩하지 않음을 명시함. 이 방식은 <form> 요소가 파일이나 이미지를 서버로 전송할 때 주로 사용함. -->
<!-- <form> 태그의 enctype 속성은 폼 데이터(form data)가 서버로 제출될 때 해당 데이터가 인코딩되는 방법을 명시함 이 속성은 <form> 요소의 method 속성값이 “post”인 경우에만 사용할 수 있다 -->
		<table>
			<tr>
				<td align="right" colspan="2">
					<a href="../managerMain.jsp">관리자 메인으로</a>&nbsp;
					<a href="bookList.jsp?book_kind=<%=book_kind%>">목록으로</a>
				</td>
			</tr>
			<tr>
				<td width="100">분류 선택</td>
				<td width="400" align="left">
					<select name="book_kind">
						<option value="100" <%if(book.getBook_kind().equals("100")){%>selected<%}%>>
							문학
						</option>
						<option value="200" <%if(book.getBook_kind().equals("200")){%>selected<%}%>>
							외국어
						</option>
						<option value="300" <%if(book.getBook_kind().equals("300")){%>selected<%}%>>
							컴퓨터
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="100">제 목</td>
				<td width="400" align="left">
					<input type="text" size="50" maxlength="50" name="book_title" 
						value="<%=book.getBook_title()%>">
					<input type="hidden" name="book_id" value="<%=book_id %>">
				</td>
			</tr>
			<tr>
				<td width="100">가 격</td>
				<td width="400" align="left">
					<input type="text" size="5" maxlength="5" name="book_price" 
						value="<%=book.getBook_price()%>">원
				</td>				
			</tr>
			<tr>
				<td width="100">수 량</td>
				<td width="400" align="left">
					<input type="text" size="5" maxlength="5" name="book_count" 
						value="<%=book.getBook_count()%>">권
				</td>				
			</tr>
			<tr>
				<td width="100">저 자</td>
				<td width="400" align="left">
					<input type="text" size="20" maxlength="10" name="author" 
						value="<%=book.getAuthor()%>">
				</td>				
			</tr>
			<tr>
				<td width="100">출판사</td>
				<td width="400" align="left">
					<input type="text" size="30" maxlength="30" name="publishing_com" 
						value="<%=book.getPublishing_com()%>">
				</td>				
			</tr>
			<tr>
				<td width="100">출판일</td>
				<td width="400" align="left">
					<select name="publishing_year">
					<% 
						Timestamp nowTime = new Timestamp(System.currentTimeMillis());
						int lastYear = Integer.parseInt(nowTime.toString().substring(0, 4));
						for(int i = lastYear; i >= 2010; i--) {
					%>
						<option value="<%=i %>"<%if(Integer.parseInt(book.getPublishing_date().substring(0, 4))==i) {%>selected<%}%>><%=i %></option>
					<%	
						} 
					%>
					</select>년
					<select name="publishing_month">
					<%
						for(int i = 1; i <= 12; i++) {
					%>
						<option value="<%=i %>"<%if(Integer.parseInt(book.getPublishing_date().substring(5, 7))==i) {%>selected<%} %>><%=i %></option>
					<%
						} 
					%>
					</select>월
					<select name="publishing_day">
					<%
						for(int i = 1; i <= 31; i++) {
					%>
						<option value="<%=i %>"<%if(Integer.parseInt(book.getPublishing_date().substring(8))==i) {%>selected<%} %>><%=i %></option>
					<%
						} 
					%>
					</select>일
				</td>				
			</tr>
			<tr>
				<td width="100">이미지</td>
				<td width="400" align="left">
					<input type="file" name="book_image"><%=book.getBook_image()%>
				</td>				
			</tr>
			<tr>
				<td width="100">내 용</td>
				<td width="400" align="left">
					<textarea name="book_content" rows="12" cols="100"><%=book.getBook_content()%></textarea>
				</td>				
			</tr>
			<tr>
				<td width="100">할인율</td>
				<td width="400" align="left">
					<input type="text" size="5" maxlength="2" name="discount_rate" value="<%=book.getDiscount_rate()%>">%
				</td>				
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="책 수정" onclick="updateCheckForm(this.form)">
					<input type="reset" value="다시 작성">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
<%
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}//end - if/else문
	}catch(Exception e){
		e.printStackTrace();
	}//end - try/catch문
%>