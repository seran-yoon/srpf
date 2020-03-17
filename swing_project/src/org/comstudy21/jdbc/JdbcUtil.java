package org.comstudy21.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

	public static Connection getConnection() {
		
		//mysql�� �����ϱ� ���� Ŀ�ؼ� ���� �Է� (url, user, password)
		String url = "jdbc:oracle:thin:@localhost:1521/xe"; //oracle�� ��Ʈ��ȣ 1521
		String user = "comstudy21";
		String password = "comstudy21";
		
		Connection conn = null; //mysql ���� ��ü�� Connection�� ���� ���� conn ����
		
		try {
			//����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver"); //Class.forName() �޼��带 ȣ���Ͽ� mysql���� �����ϴ� DriverŬ������ JVM method area�� �ε���Ų�� -> �̷��� �ؾ� ���� Driver ��ü�� �ٷ�� DriverManager�� static �޼ҵ带 ����� �� �ְ� �ȴ�
			System.out.println("����̹� �˻� ����!");
			//�����ϱ�
			conn = DriverManager.getConnection(url, user, password); //�ؿ� catch (SQLException e)���༭ ����ó�� ����� ��
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �˻� ����!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB ���� ����!");
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
		close(rs); //�����Ǵ°��� �������� ȣ��Ǵ°��� ����
		close(stmt);
		close(conn);
	}

}

