
//----------------------------------------------------------------
//아이디 중복 확인
function confirmId(){
	if(document.memInsForm.id.value == ""){
		alert("아이디를 입력하세요.");
		return;
	}
	
	url = "confirmId.jsp?id=" + document.memInsForm.id.value;
	
	window.open(url, "confirm", "toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no, width=500, height=200");
}//end - function confirmId()

//----------------------------------------------------------------
//숫자 검사
function isNumberCheck(inputVal){
	var var_normalize = /[^0-9]/gi; //정규식, 0~9번까지의 숫자만 검사한다는 뜻
	
	if(var_normalize.test(inputVal) == true)
		return false;
	else
		return true;
}//end - function isNumberCheck(inputVal)

//----------------------------------------------------------------
//최상위 체크 로직(chars로 넘긴 값이 있다면 false)
function containsChars(input, chars){
	for(var inx=0;inx<input.lenght;inx++){
		if(chars.indexOf(input.charAt(inx)) != -1)
			return true;
	}
	
	return false;
}//end - function containsChars(input, chars)

//----------------------------------------------------------------
//최상위 체크 로직(chars로 넘긴 값이 없다면 false)
function containsCharsOnly(input, chars){
	for(var inx=0;inx<input.lenght;inx++){
		if(chars.indexOf(input.charAt(inx)) == -1)
			return false;
	}
	
	return true;
}//end - function containsCharsOnly(input, chars)

//----------------------------------------------------------------
//숫자 체크
function isNum(input){
	var chars = "0123456789";
	
	return containsCharsOnly(input, chars);
}//end- function isNum(input)

//----------------------------------------------------------------
//영숫자 체크
function isAlphaNumCheck(input){
	var chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	return containsCharsOnly(input, chars);
}//end - function isAlphaNumCheck(input)

//----------------------------------------------------------------
//이름 체크
function isNameCheck(input){
	var chars = "0123456789~!@#$%^&*()_-+=|{}[],./?";
	
	return containsChars(input, chars);
}//end - function isNameCheck(input)

//----------------------------------------------------------------
//회원등록 검사
function memberInsertCheckForm(memInsForm){
	//아이디 검사
	if(!memInsForm.id.value){
		alert("아이디를 입력하십시오.");
		document.memInsForm.id.focus();
		return false;
	}
	if(isAlphaNumCheck(memInsForm.id.value) == false){
		alert("아이디는 숫자와 영문자만 사용 가능합니다.");
		document.memInsForm.id.focus();
		return false;
	}
	if((memInsForm.id.value.length < 8) || (memInsForm.id.value.length > 10)){
		alert("\n아이디는 8자리에서 10자리 사이만 가능합니다.");
		memInsForm.id.focus();
		return false;
	}
	
	//비밀번호 검사
	if(!memInsForm.passwd.value){
		alert("비밀번호를 입력하십시오.");
		document.memInsForm.passwd.focus();
		return false;
	}
	if(isAlphaNumCheck(memInsForm.passwd.value) == false){
		alert("비밀번호는 숫자와 영문자만 사용 가능합니다.");
		document.memInsForm.passwd.focus();
		return false;
	}
	if((memInsForm.passwd.value.length < 8) || (memInsForm.passwd.value.length > 10)){
		alert("\n비밀번호는 8자리에서 10자리 사이만 가능합니다.");
		memInsForm.passwd.focus();
		return false;
	}
	
	//비밀번호 확인 검사
	if(!memInsForm.repasswd.value){
		alert("비밀번호 확인을 입력하십시오.");
		document.memInsForm.repasswd.focus();
		return false;
	}
	if(memInsForm.passwd.value != memInsForm.repasswd.value){
		alert("비밀번호가 맞지않습니다! \n다시 입력하세요");
		document.memInsForm.repasswd.focus();
		return false;
	}
	
	//이름 확인
	if(!memInsForm.name.value){
		alert("이름을 입력하십시오.");
		document.memInsForm.name.focus();
		return false;
	}
	if(isNameCheck(memInsForm.name.value) == true){
		alert("이름에는 숫자나 특수문자를 입력할 수 없습니다");
		document.memInsForm.name.focus();
		return false;
	}
	
	//주소 확인
	if(!memInsForm.address.value){
		alert("주소를 입력하십시오.");
		document.memInsForm.address.focus();
		return false;
	}
	
	//전화번호 확인
	if(!memInsForm.tel2.value){
		alert("전화번호를 입력하십시오.");
		memInsForm.tel2.focus();
		return false;
	}
	if(!memInsForm.tel3.value){
		alert("전화번호를 입력하십시오.");
		memInsForm.tel3.focus();
		return false;
	}
	if(!isNumberCheck(memInsForm.tel2.value)){
		alert("전화번호는 숫자만 입력 가능합니다.")
		memInsForm.tel2.focus();
		return false;
	}
	if(!isNumberCheck(memInsForm.tel3.value)){
		alert("전화번호는 숫자만 입력 가능합니다.")
		memInsForm.tel3.focus();
		return false;
	}
	
	//동의 확인
	if(memInsForm.registerYn[0].checked == false){
		alert("회원가입을 하시려면 회원가입에 동의해주셔야 합니다.");
		memInsForm.registerYn.focus();
		return false;
	}
	if(memInsForm.is_subscribed.checked == false){
		alert("개인정보 수집 및 이용에 동의을 하여야 회원가입이 완료됩니다.");
		memInsForm.is_subscribed.focus();
		return false;
	}
	
	memInsForm.action = "memberInsertPro.jsp";
	memInsForm.submit();
}//end - function memberInsertCheckForm(memInsForm)

//----------------------------------------------------------------
//사용하기 클릭 시 부모창으로 값 전달
//function sendCheckValue(){
//	//중복체크 결과인 idCheck값을 전달 //opener은 부모창
//	opener.document.memInsForm.idDuplication.value = "idcheck";
//	self.close();	
//}

//----------------------------------------------------------------
//회원 정보 수정/삭제 검사
function memberUpDelCheckForm(memUpDelForm, selectVal){
	//비밀번호 검사
	if(!memUpDelForm.passwd.value){
		alert("비밀번호를 입력하십시오.");
		document.memUpDelForm.passwd.focus();
		return false;
	}
	if(isAlphaNumCheck(memUpDelForm.passwd.value) == false){
		alert("비밀번호는 숫자와 영문자만 사용 가능합니다.");
		document.memUpDelForm.passwd.focus();
		return false;
	}
	if((memUpDelForm.passwd.value.length < 8) || (memUpDelForm.passwd.value.length > 10)){
		alert("\n비밀번호는 8자리에서 10자리 사이만 가능합니다.");
		memUpDelForm.passwd.focus();
		return false;
	}
	
	//비밀번호 확인 검사
	if(!memUpDelForm.repasswd.value){
		alert("비밀번호 확인을 입력하십시오.");
		document.memUpDelForm.repasswd.focus();
		return false;
	}
	if(memUpDelForm.passwd.value != memUpDelForm.repasswd.value){
		alert("비밀번호가 맞지않습니다! \n다시 입력하세요");
		document.memUpDelForm.repasswd.focus();
		return false;
	}

	//수정일 경우만 검사한다.
	if(selectVal == "UP") {
		//이름 확인
		if(!memUpDelForm.name.value){
			alert("이름을 입력하십시오.");
			document.memUpDelForm.name.focus();
			return false;
		}
		if(isNameCheck(memUpDelForm.name.value) == true){
			alert("이름에는 숫자나 특수문자를 입력할 수 없습니다");
			document.memUpDelForm.name.focus();
			return false;
		}
		
		//주소 확인
		if(!memUpDelForm.address.value){
			alert("주소를 입력하십시오.");
			document.memUpDelForm.address.focus();
			return false;
		}
		
		//전화번호 확인
		if(!memUpDelForm.tel2.value){
			alert("전화번호를 입력하십시오.");
			memUpDelForm.tel2.focus();
			return false;
		}
		if(!memUpDelForm.tel3.value){
			alert("전화번호를 입력하십시오.");
			memUpDelForm.tel3.focus();
			return false;
		}
		if(!isNumberCheck(memUpDelForm.tel2.value)){
			alert("전화번호는 숫자만 입력 가능합니다.")
			memUpDelForm.tel2.focus();
			return false;
		}
		if(!isNumberCheck(memUpDelForm.tel3.value)){
			alert("전화번호는 숫자만 입력 가능합니다.")
			memUpDelForm.tel3.focus();
			return false;
		}
	}
	
	memUpDelForm.action = "memberUpDelModalForm.jsp?mode=" + selectVal;
	memUpDelForm.submit();
}//end - function memberUpDelCheckFrom(memUpDelForm, selectVal)
