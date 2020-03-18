<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "bookshop.shopping.CustomerDataBean" %>
<%@ page import = "bookshop.shopping.CustomerDBBean" %>

<%
CustomerDataBean customerList = null;

if(session.getAttribute("id") == null) {
   response.sendRedirect("../shopMain.jsp");
} else {
   String buyer = (String)session.getAttribute("id");
   
   //세션id에 해당하는 정보를 DB에서 가져온다.
   CustomerDBBean customerProcess = CustomerDBBean.getInstance();
   customerList = customerProcess.getMember(buyer);
   
   //전화번호를 화면에 맞게 나눈다.
   String tel1   = "";
   String tel2   = "";
   String tel3   = "";
   
   tel1 = customerList.getTel().substring(0, 3);
   if(customerList.getTel().length() == 12) { //가운데 자리가 3자리인 경우
      tel2 = customerList.getTel().substring(4,  7); 
      tel3 = customerList.getTel().substring(8, 12);
   } else { //가운데 자리가 4자리인 경우
      tel2 = customerList.getTel().substring(4,  8);
      tel3 = customerList.getTel().substring(9, 13);
   }
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<script src="../../js/function3.js"></script>
	<title>회원 정보 수정/탈퇴</title>
</head>
<body>
	
	<div class="container">
		<form class="form-horizontal" method="post" name="memUpDelForm" action="memberUpDelModalForm.jsp">
			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-6">
					<h2 align="center">고객 정보 수정/탈퇴</h2>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">아이디</label>
				<div class="col-sm-3"><h4><%=buyer %></h4></div>
				<!-- 아이디는 수정하면 안되므로 화면에는 보이기만 하고 값을 숨겨놓는다 -->
				<input type="hidden" name="id" value="<%=buyer %>">
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">비밀번호</label>
				<div class="col-sm-3">
					<input type="password" name="passwd" class="form-control" id="passwd" maxlength="10">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">비밀번호 확인</label>
				<div class="col-sm-3">
					<input type="password" name="repasswd" class="form-control" id="repasswd" maxlength="10">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">이름</label>
				<div class="col-sm-3">
					<input type="text" name="name" class="form-control" id="name" maxlength="10" value="<%=customerList.getName() %>">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">주소</label>
				<div class="col-sm-7">
					<input type="text" name="address" class="form-control" id="address" maxlength="100" value="<%=customerList.getAddress() %>">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">전화번호</label>
				<div class="col-sm-2">
					<select class="form-control" id="tel1" name="tel1">
						<option value="010" <%if(tel1.equals("010")) {%>selected<%}%>>010</option>
						<option value="011" <%if(tel1.equals("011")) {%>selected<%}%>>011</option>
						<option value="017" <%if(tel1.equals("017")) {%>selected<%}%>>017</option>
						<option value="018" <%if(tel1.equals("018")) {%>selected<%}%>>018</option>
						<option value="019" <%if(tel1.equals("019")) {%>selected<%}%>>019</option>
					</select>
				</div>
				<div class="input-group col-sm-3">
					<div class="input-group-addon">-</div>
					<div><input type="text" class="form-control col-sm-1" name="tel2" id="tel2" value="<%=tel2%>" maxlength="4"></div>
					<div class="input-group-addon">-</div>
					<div><input type="text" class="form-control col-sm-1" name="tel3" id="tel3" value="<%=tel3%>" maxlength="4"></div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-success" onclick="memberUpDelCheckForm(this.form, 'UP')">회원정보 수정</button>
					<button type="button" class="btn btn-danger" onclick="memberUpDelCheckForm(this.form, 'DEL')">회원 탈퇴</button>
				</div>
			</div>
		</form>
	</div>
	
</body>
</html>

<%
	}//end - if/else문
%>