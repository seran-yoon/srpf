package bookshop.shopping;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BuyDBBean {

	private static BuyDBBean instance = new BuyDBBean();
	
	public static  BuyDBBean getInstance(){
		return instance;
	}
	
	//생성자
	private BuyDBBean(){
		
	}
	
	//connection pool로부터 커넥션 객체를 얻어내는 메소드
	private Connection getConnection() throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/bookshopdb");
		
		return ds.getConnection();
	}
	
	//------------------------------------------------------------------------------------
	//bank테이블에 있는 계좌정보 전체를 구하는 메소드
	public List<String> getAccount() throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		List<String> accountList = null;
		
		try{
			conn = getConnection();
			sql = "SELECT * FROM bookshopdb.bank";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			accountList = new ArrayList<String>();
			
			while(rs.next()){
				String account = new String(rs.getString("account") + " " + rs.getString("bank") + " " + rs.getString("name"));
						
				accountList.add(account);
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
		
		return accountList;
		
	}//end - public List<String> getAccount() throws Exception
	
	//------------------------------------------------------------------------------------
	//구매확정을 하면 발생하는 트랜잭션
	public void insertBuy(List<CartDataBean> lists, String buyer, String account, String deliveryName, String deliveryTel, String deliveryAddress) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		Timestamp reg_date = null;
		String maxDate = "";
		String number = "";
		String todayDate = "";
		String compareDate = "";
		long buyId = 0;
		short nowCount = 0;
		
		try {
			conn = getConnection();
			
			//-----------------------------------------------------------------------------------------------
			//구매 테이블에 넣을 buyId를 만든다
			reg_date = new Timestamp(System.currentTimeMillis());
			todayDate = reg_date.toString();
			compareDate = todayDate.substring(0, 4) + todayDate.substring(5, 7) + todayDate.substring(8, 10);
			
			pstmt = conn.prepareStatement("SELECT MAX(buy_id) FROM bookshopdb.buy");
			
			rs = pstmt.executeQuery(); //실행
			pstmt.clearParameters(); //종료(비워주기)
			
			rs.next();
			
			if(rs.getLong(1) > 0){
				Long val = new Long(rs.getLong(1));
				maxDate = val.toString().substring(0, 8);
				number = val.toString().substring(8);
				
				//오늘 날짜와 데이터중 가장 큰 날짜를 비교한다
				if(compareDate.equals(maxDate)){
					if(Integer.parseInt(number)+1 < 10000){
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number)+1+10000));
					}else{
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number)+1));
					}
				}else{ //데이터가 없으면 오늘날짜(yyyMMdd)뒤에 00001을 붙여서 buyId를 만든다
					compareDate += "00001";
					buyId = Long.parseLong(compareDate);
				}
			}else{ //오늘 날짜와 구매 테이블에 처음으로 데이터가 기록되는 경우 오늘날짜(yyyMMdd)뒤에 00001을 붙여서 buyId를 만든다
				compareDate += "00001";
				buyId = Long.parseLong(compareDate);
			}
			//end - buyId만들기
			
			//-----------------------------------------------------------------------------------------------
			//Transction 시작
			//MySQL은 AutoCommit을 비활성화 시킨다
			conn.setAutoCommit(false);
			
			//해당 아이디에 대한 cart테이블 레코드를 가져온 후 buy테이블에 추가
			for(int i=0;i<lists.size();i++){
				CartDataBean cart = lists.get(i);
				
				sql = "INSERT INTO bookshopdb.buy (buy_id, buyer, book_id, book_title, buy_price, buy_count, book_image, buy_date, account, deliveryName, deliveryTel, deliveryAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, buyId);
				pstmt.setString(2, buyer);
				pstmt.setInt(3, cart.getBook_id());
				pstmt.setString(4, cart.getBook_title());
				pstmt.setInt(5, cart.getBuy_price());
				pstmt.setByte(6, cart.getBuy_count());
				pstmt.setString(7, cart.getBook_image());
				pstmt.setTimestamp(8, reg_date);
				pstmt.setString(9, account);
				pstmt.setString(10, deliveryName);
				pstmt.setString(11, deliveryTel);
				pstmt.setString(12, deliveryAddress);
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
				pstmt.close();
				
				//카트에 있는 상품이 구매되었으므로 book테이블에 있는 책의 수량을 재조정 해야 함
				pstmt = conn.prepareStatement("SELECT book_count FROM book WHERE book_id=?");
				pstmt.setInt(1, cart.getBook_id());
				rs = pstmt.executeQuery();
				rs.next();
				
				nowCount = (short)(rs.getShort(1) - cart.getBuy_count());
				sql = "UPDATE bookshopdb.book SET book_count=? WHERE book_id=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setShort(1, nowCount);
				pstmt.setInt(2, cart.getBook_id());
				
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
			
			//카트에 있는 물품들에 대한 계산이 모두 끝나면 카트를 비운다
			pstmt = conn.prepareStatement("DELETE FROM bookshopdb.cart WHERE buyer=?");
			pstmt.setString(1, buyer);
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			//모든 테이블에 대한 작업이 끝났으므로 이때 commit()을 실행한다
			conn.commit();
			conn.setAutoCommit(true);
			
			//-----------------------------------------------------------------------------------------------
			//Transction 종료
			
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
		
	}//end - public void insertBuy(List<CartDataBean> lists, String buyer, String account, String deliveryName, String deliveryTel, String deliveryAddress) throws Exception
	
	//------------------------------------------------------------------------------------
	//buy_id에 해당하는 레코드의 건수를 구하는 메소드
	public int getListCount(String buyer) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		int rtnCount = 0;
		
		try{
			conn = getConnection();
			sql = "SELECT COUNT(*) FROM bookshopdb.buy WHERE buyer=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, buyer);
			
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
		
	}//end - public int getListCount(String buyer) throws Exception
	
	//------------------------------------------------------------------------------------
	//구매자 id에 해당하는 구매목록을 구하는 메소드
	public List<BuyDataBean> getBuyList(String buyer) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		List<BuyDataBean> lists = null;
		BuyDataBean buy = null;

		try {
			conn  = getConnection();
			sql   = "SELECT * FROM bookshopdb.buy WHERE buyer=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, buyer);
			rs    = pstmt.executeQuery();
			
			lists = new ArrayList<BuyDataBean>();
			
			while(rs.next()) {
				buy = new BuyDataBean();
				
				buy.setBuy_id(rs.getLong("buy_id"));
				buy.setBook_id(rs.getInt("book_id"));
				buy.setBook_title(rs.getString("book_title"));
				buy.setBuy_price(rs.getInt("buy_price"));
				buy.setBuy_count(rs.getByte("buy_count"));
				buy.setBook_image(rs.getString("book_image"));
				buy.setSanction(rs.getString("sanction"));
				
				lists.add(buy);
			}
		} catch(Exception e) {
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
		
		return lists;
	}
	
	//------------------------------------------------------------------------------------
	//buy테이블의 전체 레코드 건수를 구하는 메소드
	public int getListCount() throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int rtnCount = 0;
		
		try{
			conn = getConnection();

            pstmt = conn.prepareStatement("select count(*) from bookshopdb.buy");
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	rtnCount= rs.getInt(1);
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
		
	}//end - public int getListCount() throws Exception
	
	
	//------------------------------------------------------------------------------------
	//
	public List<BuyDataBean> getBuyList() throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		BuyDataBean buy = null;
		List<BuyDataBean> lists = null;
		
		try{
			conn = getConnection();

			sql = "select * from bookshopdb.buy";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        lists = new ArrayList<BuyDataBean>();
	        
	        while (rs.next()) {
	        	buy = new BuyDataBean();

	           	buy.setBuy_id(rs.getLong("buy_id"));
	           	buy.setBuyer(rs.getString("buyer"));
	           	buy.setBook_id(rs.getInt("book_id"));
	           	buy.setBook_title(rs.getString("book_title"));
	            buy.setBuy_price(rs.getInt("buy_price"));
	           	buy.setBuy_count(rs.getByte("buy_count"));
	            buy.setBook_image(rs.getString("book_image"));
	           	buy.setBuy_date(rs.getTimestamp("buy_date"));
	           	buy.setAccount(rs.getString("account"));
	           	buy.setDeliveryName(rs.getString("deliveryName"));
	            buy.setDeliveryTel(rs.getString("deliveryTel"));
	            buy.setDeliveryAddress(rs.getString("deliveryAddress"));
	            buy.setSanction(rs.getString("sanction"));
	            
	           	lists.add(buy);
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
		
		return lists;
		
	}//end - public List<BuyDataBean> getBuyList() throws Exception
	
	//------------------------------------------------------------------------------------
	//
	public int getDeliveryStatus(String buyId) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		int rtnVal = 0;
		String status = null;
		
		try{
			conn = getConnection();
			sql = "SELECT sanction FROM bookshopdb.buy WHERE buy_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Long.parseLong(buyId));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				//배송완료 : 1(상품준비중), 2(상품배송중), 3(배송완료)
				status = rs.getString("sanction");
				if(status.contentEquals("상품준비중")){
					rtnVal = 1;
				}else if(status.contentEquals("상품배송중")){
					rtnVal = 2;
				}else if(status.contentEquals("배송완료")){
					rtnVal = 3;
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
		
		return rtnVal;
		
	}//end - public int getDeliveryStatus(String buyId) throws Exception
	
	//------------------------------------------------------------------------------------
	//구매번호에 해당하는 배송상태를 수정한다
	public void updateDelivery(String buyId, String status, String bookTitle) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
		String sanction = "";
				
		if(Integer.parseInt(status) == 1){
			sanction = "상품준비중";
		}else if(Integer.parseInt(status) == 2){
			sanction = "상품배송중";
		}else if(Integer.parseInt(status) == 3){
			sanction = "배송완료";
		}
		
		try{
			conn = getConnection();
			sql = "UPDATE bookshopdb.buy SET sanction=? WHERE buy_id=? AND book_title=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sanction);
			pstmt.setLong(2, Long.parseLong(buyId));
			pstmt.setString(3, bookTitle);
		
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
		
	}//end - public void updateDelivery(String buyId, String status, String bookTitle) throws Exception
	
	//------------------------------------------------------------------------------------
	//도서 종류별 연간 판매 비율
	public BuyBookKindDataBean buyBookKindYear(String year) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		BuyBookKindDataBean buyKind = null;
		
		try{
			conn = getConnection();
			sql  = "SELECT ";
			for(int i=100;i<=300;i+=100){
				sql += "IFNULL(SUM(CASE bk.book_kind WHEN '"+i+"' THEN bu.buy_count END), 0) AS 'qty"+i+"', ";
			}
//			sql += 		"IFNULL(SUM(CASE bk.book_kind WHEN '100' THEN bu.buy_count END), 0) AS 'qty100', ";
//			sql += 		"IFNULL(SUM(CASE bk.book_kind WHEN '200' THEN bu.buy_count END), 0) AS 'qty200', ";
//			sql += 		"IFNULL(SUM(CASE bk.book_kind WHEN '300' THEN bu.buy_count END), 0) AS 'qty300', ";
			sql += 		"IFNULL(SUM(bu.buy_count), 0) AS 'tot' ";
			sql += "FROM book bk, buy bu ";
			sql += "WHERE bk.book_id = bu.book_id AND DATE_FORMAT(bu.buy_date, '%Y') = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, year);
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				buyKind = new BuyBookKindDataBean();
				
				buyKind.setBookQty100(rs.getInt("qty100"));
				buyKind.setBookQty200(rs.getInt("qty200"));
				buyKind.setBookQty300(rs.getInt("qty300"));
				buyKind.setTotal(rs.getInt("tot"));
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
		
		return buyKind;
		
	}//end - public BuyBookKindDataBean buyBookKindYear(String year) throws Exception
	
	//------------------------------------------------------------------------------------
	//월간 판매 수량
	public BuyMonthDataBean buyMonth(String year) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		BuyMonthDataBean buyMonth = null;
		
		try{
			conn = getConnection();
			sql  = "SELECT ";
			for(int i=1;i<=12;i++){
				if(i<10){
					sql += "IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '0"+i+"' THEN buy_count END), 0) AS 'm0"+i+"', ";
				}else if(i>=10){
					sql += "IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '"+i+"' THEN buy_count END), 0) AS 'm"+i+"', ";
				}
			}
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '01' THEN buy_count END), 0) AS 'm01', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '02' THEN buy_count END), 0) AS 'm02', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '03' THEN buy_count END), 0) AS 'm03', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '04' THEN buy_count END), 0) AS 'm04', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '05' THEN buy_count END), 0) AS 'm05', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '06' THEN buy_count END), 0) AS 'm06', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '07' THEN buy_count END), 0) AS 'm07', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '08' THEN buy_count END), 0) AS 'm08', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '09' THEN buy_count END), 0) AS 'm09', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '10' THEN buy_count END), 0) AS 'm10', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '11' THEN buy_count END), 0) AS 'm11', ";
//			sql += 		"IFNULL(SUM(CASE DATE_FORMAT(buy_date, '%m') WHEN '12' THEN buy_count END), 0) AS 'm12', ";
			sql += 		"IFNULL(SUM(buy_count), 0) AS 'tot' ";
			sql += "FROM buy ";
			sql += "WHERE DATE_FORMAT(buy_date, '%Y') = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, year);
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				buyMonth = new BuyMonthDataBean();
				
				buyMonth.setMonth01(rs.getInt("m01"));
				buyMonth.setMonth02(rs.getInt("m02"));
				buyMonth.setMonth03(rs.getInt("m03"));
				buyMonth.setMonth04(rs.getInt("m04"));
				buyMonth.setMonth05(rs.getInt("m05"));
				buyMonth.setMonth06(rs.getInt("m06"));
				buyMonth.setMonth07(rs.getInt("m07"));
				buyMonth.setMonth08(rs.getInt("m08"));
				buyMonth.setMonth09(rs.getInt("m09"));
				buyMonth.setMonth10(rs.getInt("m10"));
				buyMonth.setMonth11(rs.getInt("m11"));
				buyMonth.setMonth12(rs.getInt("m12"));
				buyMonth.setTotal(rs.getInt("tot"));
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
		
		return buyMonth;
		
	}//end - public BuyMonthDataBean buyMonthYear(String year) throws Exception
	
	
	
}
