<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.CustomerDBBean"%>
<%@ page import="java.sql.Timestamp"%>

<%
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");
	
	String buyer = (String)session.getAttribute("buyer");
	
	System.out.println("memberUpdelPro 진입");

	String mode = request.getParameter("mode");
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	String name = request.getParameter("name");
	String tel = request.getParameter("tel");
	String address = request.getParameter("address");
%>

<jsp:useBean id="customer" scope="page" class="bookshop.shopping.CustomerDataBean"></jsp:useBean>

<%
	customer.setId(id);
	customer.setPasswd(passwd);
	customer.setName(name);
	customer.setTel(tel);
	customer.setAddress(address);
	customer.setReg_date(new Timestamp(System.currentTimeMillis()));
	
	CustomerDBBean customerProcess = CustomerDBBean.getInstance();
	
	if(mode.equals("UP")){ //수정일 경우 모든 정보를 넘겨준다
		customerProcess.updateMember(customer);
	}else if(mode.equals("DEL")){ //탈퇴일 경우 아이디와 비밀번호만 있으면 된다
		customerProcess.deleteMember(id, passwd);
		session.invalidate();
%>
		alert("탈퇴가 완료되었습니다!")
<%
	}else{ //로그아웃일 경우
		response.sendRedirect("../logout.jsp");
	}
	
	response.sendRedirect("../shopMain.jsp");
%>

