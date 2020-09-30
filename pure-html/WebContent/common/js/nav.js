$(function(){
    // nav1收缩展开
    $('.nav1-item>a').on('click',function(){
        if (!$('.nav1').hasClass('nav1-mini')) {
            if ($(this).next().css('display') == "none") {
                //展开未展开
                $('.nav1-item').children('ul').slideUp(300);
                $(this).next('ul').slideDown(300);
                $(this).parent('li').addClass('nav1-show').siblings('li').removeClass('nav1-show');
            }else{
                //收缩已展开
                $(this).next('ul').slideUp(300);
                $('.nav1-item.nav1-show').removeClass('nav1-show');
            }
        }
    });
    //nav1-mini切换
    $('#mini').on('click',function(){
        if (!$('.nav1').hasClass('nav1-mini')) {
			$('#mini img').attr('src','static/images/right.png')
            $('.nav1-item.nav1-show').removeClass('nav1-show');
            $('.nav1-item').children('ul').removeAttr('style');
            $('.nav1').addClass('nav1-mini');
            $('.col-sm-10').addClass('contxt');
        }else{
            $('.nav1').removeClass('nav1-mini');
            $('.col-sm-10').removeClass('contxt');
			$('#mini img').attr('src','static/images/left.png')
        }
    });
	
	//list2点击
	$('.list2 a').click(function(){
		$('.list2 a').css('background','none')
		$(this).css('background','#4171dc')
		})
});