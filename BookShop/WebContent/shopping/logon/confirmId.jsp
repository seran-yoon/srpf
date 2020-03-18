<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@	page import="bookshop.shopping.CustomerDataBean"%>
<%@ page import="bookshop.shopping.CustomerDBBean"%>
<%@ page import="java.sql.Timestamp"%>

<script src="../../js/function3.js"></script>

<%
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("id");
	
	int rtnVal = 0;
	CustomerDBBean customerProcess = CustomerDBBean.getInstance();
	rtnVal = customerProcess.confirmId(id);
	
	if(rtnVal == -1){
%>
	<center><h3>사용할 수 있는 아이디 입니다.</h3></center>
	<center>
		<!-- <input type="button" value="use" onclick="sendCheckValue()">  -->
		<input type="button" value="close" onclick="javascript:self.close()">
	</center>
<%
	}else{
%>
	<center><h3>이미 사용중인 아이디 입니다.</h3></center>
	<center>
		<input type="button" value="close" onclick="javascript:self.close()">
	</center>
<%
	}
%>

	
