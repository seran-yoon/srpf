<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../../js/jquery-3.4.1.js"></script> <!-- jquery파일을 쓰기 위한 코드 -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>
	<script src="../../js/function3.js"></script>
	<title>회원 가입</title>
</head>
<body>

	<div class="container">
		<form class="form-horizontal" method="post" name="memInsForm" action="memberInsertPro.jsp">
			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-6">
					<h2 align="center">회원 가입</h2>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">아이디</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="id" name="id" maxlength="16" placeholder="Enter ID">
				</div>
				<input class="btn btn-danger btn-sm" type="button" name="confirm_id" value="ID 중복확인" Onclick="confirmId(document.memInsForm.id.value)">
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">비밀번호</label>
				<div class="col-sm-3">
					<input type="password" class="form-control" id="passwd" name="passwd" maxlength="16" placeholder="Enter Password">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">비밀번호 확인</label>
				<div class="col-sm-3">
					<input type="password" class="form-control" id="repasswd" name="repasswd" maxlength="16" placeholder="Enter Password Check">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">이		름</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="name" name="name" maxlength="16" placeholder="Enter Name">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">주		소</label>
				<div class="col-sm-7">
					<input type="text" class="form-control" id="address" name="address" maxlength="100" placeholder="Enter Address">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">전화번호</label>
				<div class="col-sm-2">
					<select class="form-control" name="tel1">
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="017">017</option>
						<option value="018">018</option>
						<option value="019">019</option>
					</select>
				</div>
				<div class="input-group col-sm-3">
					<div class="input-group-addon">-</div>
					<div>
						<input type="text" class="form-control col-sm-1" name="tel2" maxlength="4" placeholder="Tel">
					</div>
					<div class="input-group-addon">-</div>
					<div>
						<input type="text" class="form-control col-sm-1" name="tel3" maxlength="4" placeholder="Tel">
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">회원가입 동의</label>
				<div class="col-sm-10">
					<label class="radio-inline">
						<input type="radio" id="registerYn" name="registerYn" value="Y" checked/>동의합니다. 
					</label>
					<label class="radio-inline">
						<input type="radio" id="registerYn" name="registerYn" value="N"/>동의하지 않습니다.
					</label>
				</div>
			</div>
			<div class="alert alert-info fade in col-sm-offset-2 col-sm-10">
				<strong>[ 도서쇼핑몰 '세라딘'의 개인 정보 수집 및 이용 안내 ]</strong>
				<p>
					<h5>
						개인 정보 제3자 제공 동의
						<br>① 개인 정보를 제공받는 자 : 도서쇼핑몰 세라딘 (이하 세라딘)
						<br>② 개인 정보를 제공 받는 자의 개인 정보 이용 목적 : 영업관리, 설문조사 및 프로모션, 이벤트, 경품 제공, eDM발송, 행사 관련 마케팅
						<br>③ 제공하는 개인 정보 항목 : 이름, 주소, 전화번호
						<br>④ 개인 정보를 제공받는 자의 개인 정보 보유 및 이용 기간 : 개인 정보 취급 목적을 달성하여 더이상 개인 정보가 불필요하게 된 경우이거나 5년이 지나면 지체없이 해당 정보를 파기할 것 입니다.
						<br><br> 귀하는 위와 같은 '세라딘'의 개인정보 수집 및 이용정책에 동의하지 않을 수 있으나, '세라딘'으로부터 신간 안내, 경품 추첨, 행사 초청 안내 등의 유용한 정보를 제공받지 못 할 수 있습니다.
					</h5>
				</p>
				<div class="checkbox">
					<label>
						<input type="checkbox" id="is_subscribed" name="is_subscribed" value="0"/>
					</label> '세라딘'의 개인정보 수집 및 이용에 동의합니다.
				</div>
			</div>  
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-primary" onclick="memberInsertCheckForm(this.form)">회원가입</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>