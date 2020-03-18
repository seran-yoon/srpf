<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.Timestamp"%>
<% 
	String managerId = "";
	
	try{
		managerId = (String)session.getAttribute("managerId");
		
		if(managerId == null || managerId.equals("")){
			//세션값이 없으면 정상적으로 로그인을 하지 않은 경우이므로 쫓아낸다
			response.sendRedirect("../logon/managerLoginForm.jsp");
		}else{
			//정상적으로 로그인을 하고 들어온 경우 웹페이지를 보여준다
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../etc/script.js"></script> <!-- 밑에서 버튼 onclick시에 사용할 script문 --><!-- script.js파일을 가져와서 이곳에 붙여놓은것과 같음!!! 여기 파일에서 script태그안에 자바스크립트 코드를 작성해도 되지만, 코드가 너무 길 경우 보기 복잡하므로 js파일을 따로 만들어서 코드 써주고 파일링크를 가져옴 -->
	<title>상품 등록</title>
</head>
<body>

	<div class="container">
		<form method="post" name="writeform" action="bookRegisterPro.jsp" enctype="multipart/form-data">
			<h2>책 등록</h2>
			<table class="table table-bordered table-striped nanum table-hover">
				<colgroup>
					<col class="col-sm-1">
					<col class="col-sm-3">
				</colgroup>
				<tbody>
					<tr class="success">
						<td align="right" colspan="2">
							<a href="../managerMain.jsp">관리자 메인으로</a>
						</td>
					</tr>
					<tr>
						<td>분류 선택</td>
						<td align="left">
							<select name="book_kind">
								<option value="100">문학</option>
								<option value="200">외국어</option>
								<option value="300">컴퓨터</option>								
							</select>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">제	목</td>
						<td align="left">
							<input type="text" size="50" maxlength="50" name="book_title"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">가	격</td>
						<td align="left">
							<input type="text" size="10" maxlength="10" name="book_price"/>원
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">수	량</td>
						<td align="left">
							<input type="text" size="5" maxlength="5" name="book_count"/>권
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">저	자</td>
						<td align="left">
							<input type="text" size="20" maxlength="20" name="author"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">출판사</td>
						<td align="left">
							<input type="text" size="50" maxlength="50" name="publishing_com"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">출판일</td>
						<td align="left">
								<select name="publishing_year">
								<%	Timestamp nowTime = new Timestamp(System.currentTimeMillis());
									int lastYear = Integer.parseInt(nowTime.toString().substring(0, 4));
								
									for(int i=lastYear;i>=2010;i--){ %>
									<option value="<%=i%>"><%=i%></option>
								<%	} %>
								</select>년
								<select name="publishing_month">
								<%	for(int i=1;i<=12;i++){	%>
									<option value="<%=i%>"><%=i%></option>
								<%	} %>
								</select>월
								<select name="publishing_day">
								<%	for(int i=1;i<=31;i++){	%>
									<option value="<%=i%>"><%=i%></option>
								<%	} %>
								</select>일
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">이미지</td>
						<td align="left">
							<input type="file" name="book_image"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">책내용</td>
						<td align="left">
							<textarea name="book_content" rows="12" cols="100"></textarea>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B3E6FF">할인율</td>
						<td align="left">
							<input type="text" size="5" maxlength="2" name="discount_rate"/>%
						</td>
					</tr>
					<tr class="info">
						<td colspan=2 align="center">
							<input type="button" class="btn btn-primary" value="책등록" onclick="checkForm(this.form)"> <!-- checkForm함수를 실행하라는 뜻인데, js파일이 따로 밖에있다고 생각하지말고 이 파일 내에 같이 붙어있다고 생각하면 됨 -->
							<input type="reset" class="btn btn-warning" value="다시 작성">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

</body>
</html>
<%
		}//end - if/else문
	}catch(Exception e){
		e.printStackTrace();
	}//end - try/catch문
%>