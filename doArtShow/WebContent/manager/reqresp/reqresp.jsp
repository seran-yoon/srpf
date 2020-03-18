<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="/doArtShow/resourcesImages/Jeon_invisible.ico" type="image/x-icon" />

	<jsp:include page="/module/manager_ExhList_Header.html"></jsp:include>
	
	<title>전시:해 - 1:1 문의</title>
</head>
<body>

  <jsp:include page="/module/manager_TopNav.jsp"></jsp:include>

  <div id="wrapper">

    <!-- Sidebar -->
    <jsp:include page="/module/manager_Sidebar.html"></jsp:include>

    <div id="content-wrapper">

      <div class="container-fluid">

        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="admin.do">Home</a>
          </li>
          <li class="breadcrumb-item active">1:1 문의 요청</li>
        </ol>

        <!-- DataTables Example -->
        <div class="card mb-3">
          <div class="card-header">
			<i class="fas fa-file-alt"></i>
            	1:1 문의 요청 목록
          </div>
          <div class="card-body">
			<div class="table-responsive">
              <table class="table table-bordered table-hover nowrap" id="dataTable" cellspacing="0" style="width: 100%">
                <thead>
                  <tr>
                    <th>문의 번호</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th style="display: none;"></th>
                    <th>문의 내용</th>
                    <th>답변 내용</th>
                    <th>문의 시간</th>
                    <th>답변 시간</th>
                    <th style="display: none;"></th>
                    <th>답변 여부</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="list" items="${personalRequestList}">
                  	<tr>
                  		<td>${list.reqNo}</td>
                  		<td>${list.name}</td>
                  		<td>${list.email}</td>
                  		<td style="display: none;">${list.reqMessage}</td>
                  		<td>
                  			<c:choose>
                  				<c:when test="${fn:length(list.reqMessage) > 15}">
                  					${fn:substring(list.reqMessage, 0, 15)}...
                  				</c:when>
                  				<c:otherwise>
                  					${list.reqMessage}
                  				</c:otherwise>
                  			</c:choose>
                  		</td>
                  		<td>${list.respMessage}</td>
                  		<td>
                  			${fn:substring(list.reqTime, 0, 16)}
	                    	<c:choose>
		                    	<c:when test="${fn:substring(list.reqTime, 11, 13) >= 0 && fn:substring(list.reqTime, 11, 13) < 12}">
		                    		AM
		                    	</c:when>
		                    	<c:otherwise>
		                    		PM
		                    	</c:otherwise>
	                    	</c:choose>
                  		</td>
                  		<td>
                  			${fn:substring(list.respTime, 0, 16)}
                  			<c:choose>
                  				<c:when test="${fn:substring(list.respTime, 0, 16) eq ''}">
                  					&nbsp;
                  				</c:when>
		                    	<c:when test="${fn:substring(list.respTime, 11, 13) >= 0 && fn:substring(list.respTime, 11, 13) < 12}">
		                    		AM
		                    	</c:when>
		                    	<c:otherwise>
		                    		PM
		                    	</c:otherwise>
	                    	</c:choose>
                  		</td>     
                  		<td style="display: none;">${list.reqFlag}</td>		
                  		<td>
                  			<c:choose>
                  				<c:when test="${list.reqFlag eq 'N'}">
                  					<button type="button" class="btn btn-primary">답변하기</button>
                  				</c:when>
                  				<c:otherwise>
                  					<button type="button" class="btn btn-danger">답변완료</button>
                  				</c:otherwise>
                  			</c:choose>
                  		</td>
                  	</tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="card-footer small text-muted">Updated today at <span id="nowTime"></span></div>
        </div>

      </div>
      <!-- /.container-fluid -->


	  <!-- The Modal 1 -->
      <div class="modal" id="myModal1">
        <div class="modal-dialog">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">상세 정보</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">

              <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">문의 번호</span>
                </div>
                <div class="form-control reqNo">${list.reqNo}</div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">이름</span>
                  </div>
                  <div class="form-control name"></div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">이메일</span>
                  </div>
                  <div class="form-control email"></div>
              </div>

              <div class="input-group mb-3">
				<div class="input-group-prepend">
                	<span class="input-group-text">문의 내용</span>
                </div>
                <div class="form-control reqMessage" style="height: auto;"></div>
              </div>

              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">문의 시간</span>
                  </div>
                  <div class="form-control reqTime"></div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">답변 여부</span>
                  </div>
                  <div class="form-control" id="reqFlag"></div>
              </div>

            </div><!-- Modal Body End -->

            <!-- Modal footer -->
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" id="responseBtn">답변하기</button>
              <button type="button" class="btn btn-danger" data-dismiss="modal">취소하기</button>
            </div>

          </div>
        </div>
      </div><!-- Modal 1 End -->


	  <!-- The Modal 2 -->
      <div class="modal" id="myModal2">
        <div class="modal-dialog">
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">상세 정보</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">

              <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">문의 번호</span>
                </div>
                <div class="form-control reqNo">${list.reqNo}</div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">이름</span>
                  </div>
                  <div class="form-control name"></div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">이메일</span>
                  </div>
                  <div class="form-control email"></div>
              </div>

              <div class="input-group mb-3">
				<div class="input-group-prepend">
                	<span class="input-group-text">문의 내용</span>
                </div>
                <div class="form-control reqMessage" style="height: auto;"></div>
              </div>
              
              <div class="input-group mb-3">
				<div class="input-group-prepend">
                	<span class="input-group-text">답변 내용</span>
                </div>
                <div class="form-control" id="respMessage" style="height: auto;"></div>
              </div>

              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">문의 시간</span>
                  </div>
                  <div class="form-control reqTime"></div>
              </div>
              
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                      <span class="input-group-text">답변 시간</span>
                  </div>
                  <div class="form-control" id="respTime"></div>
              </div>

            </div><!-- Modal Body End -->

            <!-- Modal footer -->
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
            </div>

          </div>
        </div>
      </div><!-- Modal 2 End -->


      <!-- Sticky Footer -->
      <footer class="sticky-footer">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright © 전시:해 2020</span>
          </div>
        </div>
      </footer>

    </div>
    <!-- /.content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <script src="vendor/datatables/jquery.dataTables.js"></script>
  <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
  
  <!-- Demo scripts for this page-->
  <script src="js/demo/datatables-demo_PersonalRequest.js"></script>

</body>
</html>