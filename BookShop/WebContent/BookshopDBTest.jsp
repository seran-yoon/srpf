<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- JSP에서 JDBC의 객체를 사용하기 위해 java.sql 패키지를 import한다 -->
<%@ page import = "java.sql.*" %>


<!-- ***DB연동이 된다는것을 확인하기 위한 연습코드!*** -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>BookShopDB TEST</h1><br>
	<table width="550" border="1">
	
	<%
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			//사용하려는 데이터베이스명을 포함한 URL을 정의
			String url = "jdbc:mysql://localhost:3306/bookshopdb";
			String id = "bookmaster";
			String pw = "1111";
			
			//데이터베이스와 연동하기 위해 DriverManager에 등록한다
			Class.forName("com.mysql.jdbc.Driver");
			
			//DriverManager객체로부터 Connection 객체를 얻어온다
			conn = DriverManager.getConnection(url, id, pw);
			
			//질문을 준비한다
			String sql = "SELECT * FROM MANAGER WHERE MANAGERID = ?";
			
			//prepareStatement에서 해당 sql을 미리 컴파일 한다
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "bookmaster");
			
			//쿼리를 실행하고 결과를 ResultSet 객체에 담는다
			rs = pstmt.executeQuery();
			
			//결과를 한 행씩 돌아가면서 가져온다
			while(rs.next()){
				String gid = rs.getString("managerid");
				String gpw = rs.getString("managerpasswd");
				
	%>
	
				<tr>
					<td width="100"><%=gid %></td>
					<td width="100"><%=gpw %></td>
				</tr>
	
	<%
			}
			
		}catch(Exception e){
			//예외가 발생하면 예외상황을 처리한다
			e.printStackTrace();
			e.getMessage();
			out.println("member Table 호출에 실패했습니다.");
			out.println(e.getMessage());
		}finally{
			//쿼리가 성공 혹은 실패에 상관 없이 사용한 자원을 해제
			//순서는 열린 순서의 역순으로 닫는다
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException sqle){
					
				}
			}
			if(pstmt != null){
				try{
					rs.close();
				}catch(SQLException sqle){
					
				}
			}
			if(conn != null){
				try{
					rs.close();
				}catch(SQLException sqle){
					
				}
			}
		}
	%>
	
	</table>

</body>
</html>