<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.shopping.CartDBBean"%>
<%@ page import="bookshop.master.ShopBookDBBean"%>

<%
	String cart_id = request.getParameter("cart_id");
	String buy_count = request.getParameter("buy_count");
	String buy_countOld = request.getParameter("buy_count");
	String book_kind = request.getParameter("book_kind");
	String book_id = request.getParameter("book_id");
	
	if(session.getAttribute("id") == null){
		response.sendRedirect("shopMain.jsp");
	}else{
		String buyer = (String)session.getAttribute("id");
		
		//재고보다 책의 수량을 더 많이 주문하면 안된다
		//현재 책방에 있는 재고수량을 구한다
		int rtnBookCount = 0;
		ShopBookDBBean bookprocess = ShopBookDBBean.getInstance();
		rtnBookCount = bookprocess.getBookCount(Integer.parseInt(book_id));
		
		//장바구니에 담겨있는 책의 수량을 구한다
		byte cartBookCount = 0;
		CartDBBean cartProcess = CartDBBean.getInstance();
		cartBookCount = cartProcess.getBookIdCounter(buyer, Integer.parseInt(book_id));
		
		//수량에 대한 검사를 통과해야 다음 페이지로 넘어갈 수 있다
		if(rtnBookCount < 1){
%>
			<script>
				alert('현재 재고가 없습니다.');
				history.go(-1);
			</script>
<%
		}else if(Integer.parseInt(buy_count) < 1){ //최소주문수량과 최대 주문수량은 bookContent.jsp에서 <input>안에 지정을 해놨기 때문에 굳이 안써도 됨
%>
			<script>
				alert('주문은 최소 1권 이상 해야합니다.');
				history.go(-1);
			</script>
<%	
		}else if(Integer.parseInt(buy_count) > rtnBookCount){ //최소주문수량과 최대 주문수량은 bookContent.jsp에서 <input>안에 지정을 해놨기 때문에 굳이 안써도 됨
%>
			<script>
				alert('주문하신 수량이 재고수량보다 많습니다.');
				history.go(-1);
			</script>
<%
		}else if(cartBookCount > rtnBookCount){
%>
			<script>
				alert('장바구니에 담겨져있는 수량이 재고수량보다 많습니다.');
				history.go(-1);
			</script>
<%
		}else if((Integer.parseInt(buy_count) + cartBookCount - Integer.parseInt(buy_countOld)) > rtnBookCount){
%>
			<script>
				alert('장바구니에 담겨져있는 수량 + 구매수량이 재고수량보다 많습니다.');
				history.go(-1);
			</script>
<%
		}else{
			cartProcess.updateCount(Integer.parseInt(cart_id), Byte.parseByte(buy_count));
			response.sendRedirect("cartList.jsp?book_kind=" + book_kind);
		}//end - 내부 if/else문
	}//end - if/else문
%>