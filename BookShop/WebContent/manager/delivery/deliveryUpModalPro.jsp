<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bookshop.shopping.BuyDBBean"%>

<%
	request.setCharacterEncoding("utf-8");

	if(session.getAttribute("managerId") == null){
		response.sendRedirect("../managerMain.jsp");
	}else{
		String buy_id = request.getParameter("buyId");
		String sanction = request.getParameter("sanction"); //라디오버튼은 같은 이름이 여러개라도 선택된 값만 전송되기 때문에 getParameter로 받으면 된다
		String bookTitle = request.getParameter("bookTitle");
		
		BuyDBBean buyProcess = BuyDBBean.getInstance();
		buyProcess.updateDelivery(buy_id, sanction, bookTitle);		
	}
%>
