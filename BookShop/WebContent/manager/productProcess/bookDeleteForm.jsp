<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
%> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<title>책 삭제</title>
</head>
<body>
	
	<h2>책 삭제</h2>
	<br>
	<form action="bookDeletePro.jsp?book_id=<%=book_id%>&book_kind=<%=book_kind%>" method="post" name="delForm" onsubmit="return deleteSave()">
		<table>
			<tr>
				<td align="right"><a href="../managerMain.jsp">관리자 메인으로</a>&nbsp;
					<a href="bookList.jsp?book_kind=<%=book_kind%>">목록으로</a>
				</td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="삭제"></td>
			</tr>
		</table>
	</form>

</body>
</html>
<%
		}//end - if/else문
	}catch(Exception e){
		e.printStackTrace();
	}//end - try/catch문
%>