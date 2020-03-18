<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="/doArtShow/resourcesImages/Jeon_invisible.ico" type="image/x-icon" />
  <title>전시:해 - 1:1 답변</title>

  <jsp:include page="/module/manager_ModifyExh_Header.html"></jsp:include>

</head>

<body id="page-top">

  <jsp:include page="/module/manager_TopNav.jsp"></jsp:include>

  <div id="wrapper">

    <!-- Sidebar -->
    <jsp:include page="/module/manager_Sidebar.html"></jsp:include>

    <div id="content-wrapper">

      <div class="container-fluid">


      <div><!-- Modal Start -->
        <div>
          <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header breadcrumb">
              <h5 class="modal-title breadcrumb-item">
              	<a href="admin.do">Home</a>
              	&nbsp;&nbsp;/&nbsp;&nbsp;
              	<span class="breadcrumb-item active">1:1 문의 답변</span>
              </h5>
            </div>



			<form id="responseEmailForm" action="responseEmail.do" method="post">
			
	            <!-- Modal body -->
	            <div class="modal-body">
	
	              <div class="input-group mb-3">
	                <div class="input-group-prepend">
	                    <span class="input-group-text">제목</span>
	                </div>
	                <input type="text" class="form-control" name="respSubject" />
	                <input type="hidden" name="reqNo" value="${prDto.reqNo}">
	              </div>
	              
	              <div class="input-group mb-3">
	                  <div class="input-group-prepend">
	                      <span class="input-group-text">받는 사람</span>
	                  </div>
	                  <div class="form-control">${prDto.email}</div>
	                  <input type="hidden" class="form-control" name="respEmail" value="${prDto.email}" />
	              </div>
	              
	              <div class="input-group mb-3">
					<div class="input-group-prepend">
	                	<span class="input-group-text">문의 내용</span>
	                </div>
	                <div class="form-control" style="height: auto;">${prDto.reqMessage}</div>
	              </div>
	
	              <div class="input-group mb-3">
					<div class="input-group-prepend">
	                	<span class="input-group-text">답변 내용</span>
	                </div>
	                <textarea id="respMessage" name="respMessage" class="form-control" rows="15" cols="40" style="width: 90%; height: 250px;"></textarea>
	              </div>
	
	            </div><!-- Modal Body End -->
	
	            <!-- Modal footer -->
	            <div class="modal-footer">
	              <button type="submit" class="btn btn-success">전송</button>
	              <button type="button" class="btn btn-danger" onclick="cancelResponse()">취소</button>
	            </div>

			</form>


          </div>
        </div>
      </div><!-- Modal End -->


      </div>
      <!-- /.container-fluid -->


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


  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="js/responseEmail.js"></script>

</body>

</html>