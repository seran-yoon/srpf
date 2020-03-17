package org.comstudy21.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

	public static Connection getConnection() {
		
		//mysql에 연결하기 위해 커넥션 정보 입력 (url, user, password)
		String url = "jdbc:oracle:thin:@localhost:1521/xe"; //oracle은 포트번호 1521
		String user = "comstudy21";
		String password = "comstudy21";
		
		Connection conn = null; //mysql 연결 객체인 Connection을 위한 변수 conn 생성
		
		try {
			//드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver"); //Class.forName() 메서드를 호출하여 mysql에서 제공하는 Driver클래스를 JVM method area에 로딩시킨다 -> 이렇게 해야 여러 Driver 객체를 다루는 DriverManager의 static 메소드를 사용할 수 있게 된다
			System.out.println("드라이버 검색 성공!");
			//연결하기
			conn = DriverManager.getConnection(url, user, password); //밑에 catch (SQLException e)써줘서 예외처리 해줘야 함
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 접속 실패!");
			e.printStackTrace();
		}
		
		return conn;
		
	}

	public static void close(Connection conn){
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void close(Statement stmt){
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void close(ResultSet rs){
		if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs){
		close(rs); //생성되는것의 역순으로 호출되는것이 좋다
		close(stmt);
		close(conn);
	}

}

