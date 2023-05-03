$(function(){

    // 판매자 정보 팝업 띄우기
    $('.latest .company > a').click(function(e){
        e.preventDefault();
        let company = $(this).text();
        let jsonData = {
			"company":company
		}
        $.ajax({
			url:"/Kmarket2/my/home/seller",
			method:"get",
			data:jsonData,
			dataType:"json",
			success:function(data){
				$("#popSeller .level").text(data.level);
				$("#popSeller .company").text(data.company);
				$("#popSeller .ceo").text(data.ceo);
				$("#popSeller .tel").text(data.tel);
				$("#popSeller .fax").text(data.fax);
				$("#popSeller .email").text(data.email);
				$("#popSeller .bizNum").text(data.bizNum);
				$("#popSeller .address").text(data.addr1+" "+data.addr2);
			},
		})
        
        
        $('#popSeller').addClass('on');
    });

    // 문의하기 팝업 띄우기
    $('.btnQuestion').click(function(e){
        e.preventDefault();
        $('.popup').removeClass('on');
        $('#popQuestion').addClass('on');
    });

    // 주문상세 팝업 띄우기
    $('.latest .orderNo > a').click(function(e){
        e.preventDefault();
        $('#popOrder').addClass('on');
    });

    // 수취확인 팝업 띄우기
    $('.latest .confirm > .receive').click(function(e){
        e.preventDefault();
        $('#popReceive').addClass('on');
    });

    // 상품평 작성 팝업 띄우기
    $('.latest .confirm > .review').click(function(e){
        e.preventDefault();
        $('#popReview').addClass('on');
        let prodName = $(this).data("prodname");
        let prodNo = $(this).data("prodno");
        $('#popReview .productName').text(prodName);
        $('#popReview input[name=prodNo]').val(prodNo);
    });

    // 팝업 닫기
    $('.btnClose, .btnNegative').click(function(){                
        $(this).closest('.popup').removeClass('on');                
    });

    // 상품평 작성 레이팅바 기능
    $(".my-rating").starRating({
        starSize: 20,
        useFullStars: true,
        strokeWidth: 0,
        useGradient: false,
        minRating: 1,
        ratedColors: ['#ffa400', '#ffa400', '#ffa400', '#ffa400', '#ffa400'],
        callback: function(currentRating, $el){
            alert('별점을 ' + currentRating + "개 주셨습니다.");
            $('input[name=rating]').val(currentRating);
            console.log('DOM element ', $el);
        }
    });

});