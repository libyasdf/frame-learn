
$(document).ready(function () {
    // 显示右侧箭头
    $('.showSelect').removeClass('glyphicon glyphicon-plus').addClass('iconfont  icon-jiantouyou')
    $('.panel-heading').unbind('click').bind('click',function(){
        if ($(this).find('.showSelect').hasClass('glyphicon')) {
            $(this).find('.showSelect').removeClass('glyphicon  glyphicon-plus')
        }
        if($(this).find('.showSelect').hasClass('icon-jiantouxia')){
            $('.panel-body').hide();
            $(this).find('.showSelect').removeClass('iconfont icon-jiantouxia').addClass('iconfont icon-jiantouyou');
        }else{
            $('.panel-body').show();
            $(this).find('.showSelect').removeClass('iconfont icon-jiantouyou').addClass(' iconfont icon-jiantouxia');
        }
    })
})