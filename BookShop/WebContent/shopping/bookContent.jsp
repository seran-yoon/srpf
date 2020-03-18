<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bookshop.master.ShopBookDBBean"%>
<%@ page import="bookshop.master.ShopBookDataBean"%>
<%@ page import="java.text.NumberFormat"%>

<%
	String book_id = request.getParameter("book_id");
	String book_kind = request.getParameter("book_kind");
	String id = "";
	int buy_price = 0;
	String realFolder = "";
	String saveFolder ="/imageFile";
	
	ServletContext context = getServletContext();
	realFolder = context.getRealPath(saveFolder);
	realFolder = "http://localhost:8888/BookShop/imageFile";
	
	//세션이 있는 사람과 없는 사람에 따라 버튼을 다르게 보이기 위해서
	if(session.getAttribute("id") == null) {
	   id = "not";
	} else {
	   id = (String)session.getAttribute("id");
	}
	
	ShopBookDataBean bookList = null;
	String book_kindName = "";
	
	ShopBookDBBean bookProcess = ShopBookDBBean.getInstance();
	bookList = bookProcess.getBook(Integer.parseInt(book_id));
	
	if(book_kind.equals("100")){
		book_kindName ="문학";
	}else if(book_kind.equals("200")){
		book_kindName ="외국어";
	}else if(book_kind.equals("300")){   
		book_kindName ="컴퓨터";
	}

%>

<!DOCTYPE html>
<html>
<head>
   	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<link href="../css/main.css" rel="stylesheet" type="text/css">
	<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>
	<title><%=bookList.getBook_title()%></title>
</head>
<body>

	<jsp:include page="../module/top.jsp" flush="false" />
	<br><br><br><br>
	
	<form name="inform" method="post" action="cartInsert.jsp">
	   <table class="table">
	      <tr>
	         <td rowspan="8" width="150">
	            <center><img src="<%=realFolder%>\<%=bookList.getBook_image()%>" border="0" width="210" height="320"></center>
	         </td>
	         <td width="500">
	            <font size="+2"><b><%=bookList.getBook_title()%></b></font>
	         </td>
	      </tr>
	      <tr>
	         <td width="500">저  자 : <%=bookList.getAuthor()%></td>
	      </tr>
	      <tr>
	         <td width="500">출판사 : <%=bookList.getPublishing_com()%></td>
	      </tr>
	      <tr>
	         <td width="500">출판일 : <%=bookList.getPublishing_date()%></td>
	      </tr>
	      <tr>
	         <td width="500">정  가 : <%=NumberFormat.getInstance().format(bookList.getBook_price())%>원</td>
	      </tr>
	      <tr>
	         <td width="500">판매가 : <b><font color="red"><%buy_price = (int)(bookList.getBook_price()*((double)(100-bookList.getDiscount_rate())/100));%>  
	                  									<%=NumberFormat.getInstance().format((int)(buy_price))%>원</font></b> 
	                   &nbsp; / <font color="blue">할인율 <%=bookList.getDiscount_rate()%>%</font>  
	         </td>
	      </tr>
	      <tr>
	         <td width="500">
	         	재고수량 : <%=bookList.getBook_count()%>권  
	         	구매수량 : <input type="number" id="buyCount" size="4" name="buy_count" value="1" min="1" max="<%=bookList.getBook_count()%>">권
	            <%
	            	if(bookList.getBook_count() <= 0) { 
	            %>
	              		<b>일시품절</b>
	            <% 
	            	}else { 
	               		if(!id.equals("not")) {
	            %>
			               <b><font color="red"><label id="totalAmount"></label></font></b>
			               <input type="hidden" name="book_id" value="<%=book_id%>">
			               <input type="hidden" name="book_image" value="<%=bookList.getBook_image()%>">
			               <input type="hidden" name="book_title" value="<%=bookList.getBook_title()%>">
			               <input type="hidden" name="buy_price" value="<%=buy_price%>">
			               <input type="hidden" name="book_kind" value="<%=book_kind%>">
			               <input type="submit" value="장바구니에 담기">
	            <%   
	            		}//end - if문 
	            	}//end - if/else문
	            %>
	         </td>
	      </tr>
	      <tr>
	         <td width="500">
	            <input type="button" value="목록으로" onclick="javascript:window.location='list.jsp?book_kind=<%=book_kind%>'">
	            <input type="button" value="메인으로" onclick="javascript:window.location='shopMain.jsp'">
	         </td>
	      </tr>
	      <tr>
	         <td colspan="2" align="left">
	            <%=bookList.getBook_content() %>
	         </td>
	      </tr>
	   </table>
	</form>
	
	<script>
	var $input = $("#buyCount"); 
	$("#buyCount").on('input', function() {
		//$('#totalAmount').text("총구매가 :" + parseInt(<%=buy_price%>) * parseInt($('#buyCount').val()) + "원");
		$('#totalAmount').text("총구매가 :" + Number(<%=buy_price%>) * Number($('#buyCount').val()) + "원");
	    //console.log("Input text changed!" + $(this).val());
	});
	/* (function ($) {
	    var originalVal = $.fn.val;
	    $.fn.val = function (value) {
	        var res = originalVal.apply(this, arguments);
	 
	        if (this.is('input:text') && arguments.length >= 1) {
	            // this is input type=text setter
	            this.trigger("input");
	        }
	        return res;
	    };
	})(jQuery); */
	
	/*
	$("#buyCount").change(function() {
		alert("id name 값이 변경되었습니다.");
	});
	*/
	</script>


</body>
</html>
