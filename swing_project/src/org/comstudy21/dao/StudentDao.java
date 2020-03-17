package org.comstudy21.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.comstudy21.dto.StudentDto;
import org.comstudy21.jdbc.JdbcUtil;

public class StudentDao {

	private Connection conn = JdbcUtil.getConnection();
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private final String SELECT_ALL = "select * from student order by id asc"; // 전체보기
	private final String INSERT = "insert into student (id, name, email, phone, bday, gender, resume, salary, area, type, memo) values(?,?,?,?,?,?,?,?,?,?,?)"; // 추가
	private final String UPDATE = "update student set id = ?, email = ?, phone = ?, bday = ?, gender = ?, resume = ?, salary = ?, area = ?, type = ?, memo = ? where name = ?"; // 수정
	private final String DELETE = "delete from student where id = ?"; // 삭제
	private final String SELECT = "select * from student where name = ?"; // 검색

	public StudentDao() {

	}

	public StudentDao(Connection conn) {
		this.conn = conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// 테이블에 출력구성 가져오는 메소드
	public Vector getStudentList(StudentDto dto) {

		Vector<Vector> list = new Vector<>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String bday = rs.getString("bday");
				String gender = rs.getString("gender");
				String resume = rs.getString("resume");
				String salary = rs.getString("salary");
				String area = rs.getString("area");
				String type = rs.getString("type");
				String memo = rs.getString("memo");

				Vector<String> row = new Vector<>();
				row.add(id);
				row.add(name);
				row.add(email);
				row.add(phone);
				row.add(bday);
				row.add(gender);
				row.add(resume);
				row.add(salary);
				row.add(area);
				row.add(type);
				row.add(memo);
				
				list.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return list;
	}

	//전체보기
	public Vector selectAll() {

		Vector<Vector> list = new Vector<>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String bday = rs.getString("bday");
				String gender = rs.getString("gender");
				String resume = rs.getString("resume");
				String salary = rs.getString("salary");
				String area = rs.getString("area");
				String type = rs.getString("type");
				String memo = rs.getString("memo");

				Vector<String> row = new Vector<>();
				row.add(id);
				row.add(name);
				row.add(email);
				row.add(phone);
				row.add(bday);
				row.add(gender);
				row.add(resume);
				row.add(salary);
				row.add(area);
				row.add(type);
				row.add(memo);
				
				list.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return list;
		
	}

	//추가
	public void insert(StudentDto getdto) {

		try {
			pstmt = conn.prepareStatement(INSERT);

			pstmt.setString(1, getdto.getId());
			pstmt.setString(2, getdto.getName());
			pstmt.setString(3, getdto.getEmail());
			pstmt.setString(4, getdto.getPhone());
			pstmt.setString(5, getdto.getBday());
			pstmt.setString(6, getdto.getGender());
			pstmt.setString(7, getdto.getResume());
			pstmt.setString(8, getdto.getSalary());
			pstmt.setString(9, getdto.getArea());
			pstmt.setString(10, getdto.getType());
			pstmt.setString(11, getdto.getMemo());

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	//수정
	public void update(StudentDto getdto) {

		try {
			pstmt = conn.prepareStatement(UPDATE);

			pstmt.setString(11, getdto.getName());
			pstmt.setString(1, getdto.getId());
			pstmt.setString(2, getdto.getEmail());
			pstmt.setString(3, getdto.getPhone());
			pstmt.setString(4, getdto.getBday());
			pstmt.setString(5, getdto.getGender());
			pstmt.setString(6, getdto.getResume());
			pstmt.setString(7, getdto.getSalary());
			pstmt.setString(8, getdto.getArea());
			pstmt.setString(9, getdto.getType());
			pstmt.setString(10, getdto.getMemo());

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	//삭제
	public void delete(StudentDto getdto) {

		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, getdto.getId());

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	//검색
	public Vector select(StudentDto getdto) {
			
		Vector<Vector> list = new Vector<>();
		
		try {
			pstmt = conn.prepareStatement(SELECT);
			pstmt.setString(1, getdto.getName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String bday = rs.getString("bday");
				String gender = rs.getString("gender");
				String resume = rs.getString("resume");
				String salary = rs.getString("salary");
				String area = rs.getString("area");
				String type = rs.getString("type");
				String memo = rs.getString("memo");
				
				Vector<String> row = new Vector<>();
				row.add(id);
				row.add(name);
				row.add(email);
				row.add(phone);
				row.add(bday);
				row.add(gender);
				row.add(resume);
				row.add(salary);
				row.add(area);
				row.add(type);
				row.add(memo);
				
				list.add(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	
		return list;
		
	}

}

