<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="bookshop.master.ShopBookDBBean"%>

<%

	request.setCharacterEncoding("utf-8");
	
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	
	//System.out.println(id + " : " + passwd); //아이디와 비밀번호를 확인하기 위해
	
	ShopBookDBBean manager = ShopBookDBBean.getInstance(); //연결이 됨
	
	int check = manager.managerCheck(id, passwd);
	
	if(check == 1){ //아이디와 비밀번호가 맞아서 성공적으로 로그인 됨
		//아이디와 비밀번호가 맞으면 세션을 발급한다
		session.setAttribute("managerId", id);
		response.sendRedirect("../managerMain.jsp");
	}else if(check == 0){ //비밀번호가 틀림
%>
		<script>
			alert("비밀번호가 맞지 않습니다!");
			history.go(-1); /* 전 페이지로 돌아간다 */
		</script>
<%
	}else{ //check=-1 -> 아이디 자체가 없을 경우
%>
		<script>
			alert("아이디가 없습니다!");
			history.go(-1); /* 전 페이지로 돌아간다 */
		</script>
<%
	}
	

%>
