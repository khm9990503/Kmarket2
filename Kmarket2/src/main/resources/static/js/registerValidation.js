	// 데이터 검증에 사용할 정규표현식
	let regUid    = /^[a-z]+[a-z0-9]{3,12}$/g;
	let regName   = /^[가-힣]{2,4}$/;
	let regEmail  = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	let regHp	  = /^\d{3}-\d{3,4}-\d{4}$/;
	let regPass   = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	let regTel 	  = /^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]))-(\d{3,4})-(\d{4})$/
	let regComNum = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/
	
	// 폼 데이터 검증 결과 상태변수
	let isUidOk   = false;
	let isPassOk  = false;
	let isPassMatch  = false;
	let isNameOk  = false;
	let isNickOk  = false;
	let isEmailOk = false;
	let isHpOk    = false;
	let isEmailAuthOk = false;
	let isEmailAuthCodeOk = false;
	let receivedCode = 0;	
	let isTelOk = false;
	let isFaxOk = false;
	
	$(function(){
		
		$('input[name=uid]').keydown(function(){
			isUidOk = false;
		});
		
		$('#btnUidCheck').click(function(){
			
			let uid = $('input[name=uid]').val();
			
			if(isUidOk){
				return;
			}	
			
			if(!uid.match(regUid)){
				$('.resultUid').css('color', 'red').text('아이디가 유효하지 않습니다.');
				return;
			}
			
			let jsonData = {"uid":uid};
			
			$('.resultUid').css('color', 'black').text('...');
			console.log('here5');
			
			setTimeout(()=>{
				console.log('here6');
				$.ajax({
					url: '/Kmarket2/member/checkUid',
					method: 'get',
					data: jsonData,
					dataType: 'json',
					success:function(data){
						//console.log(data);
						if(data.result == 0){
							console.log('here7');
							isUidOk = true;
							$('.resultUid').css('color', 'green').text('사용 가능한 아이디 입니다.');
						}else{
							console.log('here8');
							isUidOk = false;
							$('.resultUid').css('color', 'red').text('이미 사용중인 아이디 입니다.');
						}
					}
				});
				
			}, 500);
		});
	// 이메일 유효성 검사
	$('input[name=email]').focusout(function() {
		let email = $(this).val();
		if(!email.match(regEmail)){
			isEmailOk = false;
			$('.resultEmail').css('color','red').text('이메일이 유효하지 않습니다.');
		}else{
			isEmailOk = true;
			$('.resultEmail').text('');
		}
	});
	

	// 이메일 인증코드 발송 클릭
	$('#btnEmail').click(function() {
		
		$(this).hide();
		
		let email = $('input[name=email]').val();
		
		if(email == ''){
			alert('이메일을 입력하세요.')
			return;
		}
		
		if(isEmailAuthOk){
			return;
		}
		
		isEmailAuthOk = true;
		
		$('.resultEmail').text('이메일 전송 중...');
		
		setTimeout(function() {
			
		$.ajax({
			url:'/Kmarket2/member/emailAuth',
			method:'GET',
			data:{"email":email},
			dataType:'json',
			success:function(data){
				//console.log(data);
				if(data.status.length == 2){
					// 메일 전송 성공
					isEmailAuthOk = true;
					$('.resultEmail').text('메일 확인 후 인증코드를 입력하세요.')
					$('.auth').show();
					receivedCode = data.code;
				}else {
					// 메일 전송 실패
					isEmailAuthOk = false;
					alert("이메일 주소를 다시 확인해 주세요.")
				}
			}
		});
		},1000);
	});
	
	// 이메일 인증 코드 확인 버튼
	$('#btnEmailConfirm').click(function() {
		let code = $('input[name=auth]').val();
		
		if(code == ''){
			alert('이메일 확인 후 인증코드를 입력하세요.');
			return;
		}else if(code == receivedCode){
			isEmailAuthCodeOk = true;
			$('input[name=email]').attr('readonly',true);
			$('.resultEmail').text('메일 인증 완료')
			$('.auth').hide();
		}else {
			isEmailAuthCodeOk = false;
			alert('인증코드가 틀립니다. \n 다시 확인 하십시오.');
		}
	});
	

	// 비밀번호 형식검사 일치여부 확인
	$('input[name=pass2]').focusout(function(){			
		let pass1 = $('input[name=pass1]').val();
		let pass2 = $(this).val();

		if(pass1 == pass2){
							
			if(pass2.match(regPass)){
				isPassOk = true;
				$('.resultPass').css('color', 'green').text('비밀번호가 일치합니다.');	
			}else{
				isPassOk = false;
				$('.resultPass').css('color', 'red').text('영문, 숫자, 특수문자 조합 8자~12자 설정해 주세요.');
			}				
			
		}else{
			isPassOk = false;
			$('.resultPass').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
		}	
		
	});
		
	
	// 이름 유효성 검증
	$('input[name=name]').focusout(function(){
		let name = $(this).val();
		
		if(!name.match(regName)){
			isNameOk = false;
			$('.resultName').css('color', 'red').text('이름은 한글 2자 이상 이어야 합니다.');
		}else{
			isNameOk = true;
			$('.resultName').text('');
		}
	});
	
	// 대표자 이름 유효성 검증
	$('input[name=ceo]').focusout(function(){
		let name = $(this).val();
		
		if(!name.match(regName)){
			isNameOk = false;
			$('.resultName').css('color', 'red').text('이름은 한글 2자 이상 이어야 합니다.');
		}else{
			isNameOk = true;
			$('.resultName').text('');
		}
	});
	
	// 담당자 이름 유효성 검증
	$('input[name=manager]').focusout(function(){
		let name = $(this).val();
		
		if(!name.match(regName)){
			isNameOk = false;
			$('.resultName').css('color', 'red').text('이름은 한글 2자 이상 이어야 합니다.');
		}else{
			isNameOk = true;
			$('.resultName').text('');
		}
	});
		
	// 이메일 유효성 검사
	$('input[name=email]').focusout(function(){
		let email = $(this).val();
		
		if(!email.match(regEmail)){
			isEmailOk = false;
			$('.resultEmail').css('color', 'red').text('이메일이 유효하지 않습니다.');
		}else{
			isEmailOk = true;
			$('.resultEmail').text('');
		}			
	});

	
	
	// 휴대폰 유효성 검사
	$('input[name=hp]').focusout(function(){
		let hp = $(this).val();
		
		if(!hp.match(regHp)){
			isHpOk = false;
			$('.resultHp').css('color', 'red').text('휴대폰이 유효하지 않습니다.');
		}else{
			isHpOk = true;
			$('.resultHp').text('');
		}
	});
	
	// 담당자 번호 유효성 검사
	$('input[name=managerHp]').focusout(function(){
		let hp = $(this).val();
		
		if(!hp.match(regHp)){
			isHpOk = false;
			$('.resultHp').css('color', 'red').text('휴대폰이 유효하지 않습니다.');
		}else{
			isHpOk = true;
			$('.resultHp').text('');
		}
	});
	
	// 일반전화 유효성 검사
	$('input[name=tel]').focusout(function(){
		let tel = $(this).val();
		
		if(!tel.match(regTel)){
			isTelOk = false;
			$('.msgTel').css('color', 'red').text('전화번호가 유효하지 않습니다.');
		}else{
			isTelOk = true;
			$('.msgTel').text('');
		}
	});
	
	// 팩스번호 유효성 검사
	$('input[name=fax]').focusout(function(){
		let hp = $(this).val();
		
		if(!hp.match(regTel)){
			isHpOk = false;
			$('.msgFax').css('color', 'red').text('팩스번호가 유효하지 않습니다.');
		}else{
			isHpOk = true;
			$('.msgFax').text('');
		}
	});
	
	// 사업자등록번호 유효성 검사
	$('input[name=comRegNum]').focusout(function(){
		let comRegNum = $(this).val();
		
		if(!comRegNum.match(regComNum)){
			isHpOk = false;
			$('.msgCorp').css('color', 'red').text('사업자등록번호가 유효하지 않습니다.');
		}else{
			isHpOk = true;
			$('.msgCorp').text('');
		}
	});
	
	
	// 폼 전송이 시작될 때 실행되는 폼 이벤트(폼 전송 버튼을 클릭했을 때) 
	$('.register > form').submit(function(){
	
		
		////////////////////////////////////
		// 폼 데이터 유효성 검증(Validation)
		////////////////////////////////////
		
		// 아이디 검증
		if(!isUidOk){
			alert('아이디를 확인 하십시요.');
			return false;
		}
		
		// 비밀번호 검증
		if(!isPassOk){
			alert('비밀번호를 확인 하십시요.');
			return false;
		}
		
		// 이름 검증
		if(!isNameOk){
			alert('이름을 확인 하십시요.');
			return false;
		}
		
		// 이메일 검증
		if(!isEmailOk){
			alert('이메일을 확인 하십시요.');
			return false;
		}
		// 휴대폰 검증
		if(!isHpOk){
			alert('휴대폰을 확인 하십시요.');
			return false;
		}
		
		
		// 최종 전송
		return true;
	});
	
});