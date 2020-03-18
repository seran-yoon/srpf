package bookshop.master;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopBookDBBean {
	
	private static ShopBookDBBean instance = new ShopBookDBBean();
	
	public static  ShopBookDBBean getInstance(){
		return instance;
	}
	
	//생성자
	private ShopBookDBBean(){
		
	}
	
	//connection pool로부터 커넥션 객체를 얻어내는 메소드
	private Connection getConnection() throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/bookshopdb");
		
		return ds.getConnection();
	}
	
	//------------------------------------------------------------------------------------
	//관리자 인증 메서드
	public int managerCheck(String id, String passwd) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String dbpasswd = "";
		int rtnVal = -1;
		String sql = "";
		
		try {
			conn = getConnection();
			
			sql = "SELECT managerPasswd FROM manager WHERE managerId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){ //해당 아이디 정보가 있으면
				dbpasswd = rs.getString("managerPasswd");
				if(dbpasswd.equals(passwd)){ //비밀번호가 맞으면
					rtnVal = 1; //인증 성공
				}else{ 
					rtnVal = 0; //비밀번호 틀림
				}
			}else{ //문의한 아이디에 대한 정보가 없으면 
				rtnVal = -1; //해당 아이디가 없음
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return rtnVal;
		
	}//end - public int managerCheck(String id, String passwd) throws Exception
	
	//------------------------------------------------------------------------------------
	//책 등록 메소드
	public void insertBook(ShopBookDataBean book) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "INSERT INTO bookshopdb.book VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, book.getBook_id());
			pstmt.setString(2, book.getBook_kind());
			pstmt.setString(3, book.getBook_title());
			pstmt.setInt(4, book.getBook_price());
			pstmt.setShort(5, book.getBook_count());
			pstmt.setString(6, book.getAuthor());
			pstmt.setString(7, book.getPublishing_com());
			pstmt.setString(8, book.getPublishing_date());
			pstmt.setString(9, book.getBook_image());
			pstmt.setString(10, book.getBook_content());
			pstmt.setByte(11, book.getDiscount_rate());
			pstmt.setTimestamp(12, book.getReg_date());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
	}//end - public void insertBook(ShopBookDBBean book) throws Exception
	
	//------------------------------------------------------------------------------------
	//(도서 종류에 관계없이)등록 된 책의 전체 권수
	public int getBookCount() throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int rtnCnt = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM bookshopdb.book");
			rs = pstmt.executeQuery();
			if(rs.next()){
				rtnCnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return rtnCnt;
		
	}//end - public void getBookCount() throws Exception

	//-------------------------------------------------------------------------------
	// 도서종류에 따른 등록된 책의 전체 권수를 구하는 메서드
	public int getBookCount2(String book_kind) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt	= null;
		ResultSet rs = null;
		
		int rtnCnt = 0;		
		
		try {
			conn = getConnection();
			String sql1 = "SELECT COUNT(*) FROM bookshopdb.book ";
			String sql2 = "WHERE book_kind=?";
					
			if(book_kind.equals("all")) {
				pstmt	= conn.prepareStatement(sql1);
			} else {
				pstmt	= conn.prepareStatement(sql1+sql2);
				pstmt.setString(1, book_kind);
			}
			
			rs		= pstmt.executeQuery();
			
			if(rs.next()){
				rtnCnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return rtnCnt;
		
	} // End - public int getBookCount2() throws Exception
	
	//------------------------------------------------------------------------------------
	//분류별 또는 전체 등록된 책의 정보를 구하는 메소드
	public List<ShopBookDataBean> getBooks(String book_kind) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ShopBookDataBean> bookList = null;
				
		try {
			conn = getConnection();
			String sql1 = "SELECT * FROM bookshopdb.book ";
			String sql2 = "WHERE book_kind=? ORDER BY reg_date DESC";
			
			if(book_kind.equals("all")){
				pstmt = conn.prepareStatement(sql1);
			}else{
				pstmt = conn.prepareStatement(sql1+sql2);
				pstmt.setString(1, book_kind);
			}
			
			rs = pstmt.executeQuery();
			
			bookList = new ArrayList<ShopBookDataBean>();
			
			if(rs.next()){
				//찾아온 책의 자료를 보내줄 곳에 담는다. 자료가 없을때 까지
				do{
					ShopBookDataBean book = new ShopBookDataBean();
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
//					book.setBook_content(rs.getString("book_content")); //책의 내용은 자료를 선택한 상세화면에서 보여주기 위해서 지금은 안씀
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));
					
					bookList.add(book);
				}while(rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return bookList;
		
	}//end - public List<ShopBookDataBean> getBooks(String book_kind) throws Exception
	
	//------------------------------------------------------------------------------------
	//책의 정보를 삭제
	public void deleteBook(int bookId) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int rtnCnt = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("DELETE FROM bookshopdb.book where book_id=?");
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("!!!삭제 오류!!!");
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
	}//end - public void deleteBook(int bookId) throws Exception
	
	//------------------------------------------------------------------------------------
	//책의 정보를 구하는 메소드
	public ShopBookDataBean getBook(int bookId) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ShopBookDataBean book = null;
				
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM bookshopdb.book WHERE book_id=?");
			pstmt.setInt(1, bookId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				book = new ShopBookDataBean();
				
				book.setBook_id(rs.getInt("book_id"));
				book.setBook_kind(rs.getString("book_kind"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_count(rs.getShort("book_count"));
				book.setAuthor(rs.getString("author"));
				book.setPublishing_com(rs.getString("publishing_com"));
				book.setPublishing_date(rs.getString("publishing_date"));
				book.setBook_image(rs.getString("book_image"));
				book.setBook_content(rs.getString("book_content"));
				book.setDiscount_rate(rs.getByte("discount_rate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return book;
		
	}//end - public ShopBookDataBean getBook(int bookId) throws Exception
	
	//------------------------------------------------------------------------------------
	//책 수정 메소드
	public void updateBook(ShopBookDataBean book, int bookId) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		try {
			conn = getConnection();
			sql = "UPDATE bookshopdb.book SET ";
			sql += "book_kind=?, book_title=?, book_price=?, book_count=?, ";
			sql += "author=?, publishing_com=?, publishing_date=?, book_image=?, book_content=?, discount_rate=? ";
			sql += "WHERE book_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setShort(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setByte(10, book.getDiscount_rate());
			
			pstmt.setInt(11, book.getBook_id());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
	}//end - public void updateBook(ShopBookDataBean book, int bookId) throws Exception

	//------------------------------------------------------------------------------------
	//책 종류에 따른 책의 정보를 건수에 맞춰 추출한다 (getBooks라는 같은 이름을 가진 메소드가 하나 더있는것 -> 오버로딩)
	public ShopBookDataBean[] getBooks(String book_kind, int count) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ShopBookDataBean bookList[] = null;
		String sql = "";
		int rtnCount = 0;
		int i = 0;
		
		try {
			conn = getConnection();
			//limit의 조회건수보다 실제데이터가 적으면 NULL문제가 발생하므로 먼저 해당자료의 건수를 구한다
			//조회할 건수보다 실제 데이터가 적으면 적은만큼만 limit으로 검색한다
			sql = "SELECT COUNT(*) FROM bookshopdb.book WHERE book_kind=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book_kind);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				rtnCount = rs.getInt(1);
			}	
			
			//검색하려는 데이터 건수가 실제 데이터보다 많으면 실제 데이터 건수만큼만 추출한다
			if(rtnCount > count){
				rtnCount = count;
			}
			pstmt.close();
			rs.close();
			
			//데이터가 없으면 검색할 필요가 없다
			if(rtnCount > 0){
				sql = "";
				sql += "SELECT * FROM book WHERE book_kind=? ";
				sql += "ORDER BY reg_date DESC limit ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, book_kind);
				pstmt.setInt(2, 0);
				pstmt.setInt(3, rtnCount);
			
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					bookList = new ShopBookDataBean[count];
					do{
						ShopBookDataBean book = new ShopBookDataBean();
						
						book.setBook_id(rs.getInt("book_id"));
						book.setBook_kind(rs.getString("book_kind"));
						book.setBook_title(rs.getString("book_title"));
						book.setBook_price(rs.getInt("book_price"));
						book.setBook_count(rs.getShort("book_count"));
						book.setAuthor(rs.getString("author"));
						book.setPublishing_com(rs.getString("publishing_com"));
						book.setPublishing_date(rs.getString("publishing_date"));
						book.setBook_image(rs.getString("book_image"));
						book.setDiscount_rate(rs.getByte("discount_rate"));
						book.setReg_date(rs.getTimestamp("reg_date"));
						
						bookList[i] = book;
						i++;
					}while(rs.next());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return bookList;
		
	}//end - public ShopBookDataBean[] getBooks(String book_kind, int count) throws Exception

	//------------------------------------------------------------------------------------
	//책 id에 해당하는 재고수량을 추출
	public int getBookCount(int bookId) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		int rtnCount = 0;
		
		try{
			conn = getConnection();
			sql = "SELECT book_count FROM bookshopdb.book WHERE book_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				rtnCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		
		return rtnCount;
		
	}//end - public int getBookCount(int bookId) throws Exception
	
}//end - public class ShopBookDBBean 
