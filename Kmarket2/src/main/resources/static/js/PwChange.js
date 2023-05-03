	let regPass	 = /^.*(?=^.{5,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;

	
	let isPassOk	 = false;
	
	$(function() {
		
		// 비밀번호 일치여부 확인
		$('input[name=pass2]').on("change keyup paste",function(){
			
			let pass1 = $('input[name=pass1]').val();
			let pass2 = $(this).val();
			
			if(pass1 == pass2){
				
				if(pass2.match(regPass)){
					isPassOk = true;
					$('.resultPass').css('color','green').text('비밀번호가 일치합니다.');
				}else{
					isPassOk = false;
					$('.resultPass').css('color','red').text('영문, 숫자, 특수문자 조합에 최소 5자 이상이어야 함.');
				}
			}else{
				isPassOk = false;
				$('.resultPass').css('color','red').text('비밀번호가 일치하지 않습니다.');
			}
		});
		// 폼 전송이 시작될 때 실행되는 폼 이벤트(폼 전송 버튼을 클릭했을때)
		$('.form').submit(function(e) {
			
			// 폼 데이터 유효성 검증 Validation
			// 1.비밀번호 검증
			if(!isPassOk){
				alert('새 비밀번호가 유효하지 않습니다.');
				return false;
			}
			
			alert("비밀번호를 수정했습니다.\n다시 로그인 해주세요.")
			// 최종 전송
			return true;
		});
	});