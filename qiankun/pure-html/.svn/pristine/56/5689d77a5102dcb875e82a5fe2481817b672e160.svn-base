/**
 * 常用辅助小组件
 */


/**
 * 全屏组件
 * @return {[type]} [description]
 */
var fullScreen = function(){

    /**
     * 初始化
     * @param  {[type]} id [description]
     * @return {[type]}    [description]
     */
    var init = function(id){
        $("#"+id).unbind('click').bind('click',function(){
            if(isFullscreen()){
                closeFullScreen();
            }else{
                fullScreen();
            }
        })
    }

    /**
     * 判断是否全屏
     * @return {Boolean} [description]
     */
    var isFullscreen = function(){
        return document.fullscreenElement    ||
           document.msFullscreenElement  ||
           document.mozFullScreenElement ||
           document.webkitFullscreenElement || false;
    }

    /**
     * 关闭全屏
     * @return {[type]} [description]
     */
    var closeFullScreen = function(){
        if (document.exitFullscreen) {  
            document.exitFullscreen();  
        }  
        else if (document.mozCancelFullScreen) {  
            document.mozCancelFullScreen();  
        }  
        else if (document.webkitCancelFullScreen) {  
            document.webkitCancelFullScreen();  
        }  
        else if (document.msExitFullscreen) {  
            document.msExitFullscreen();  
        }  
    }

    /**
     * 全屏事件
     * @return {[type]} [description]
     */
    var fullScreen = function (){  
        var docElm = document.documentElement;  
        //W3C   
        if (docElm.requestFullscreen) {  
            docElm.requestFullscreen();  
        }  
        //FireFox   
        else if (docElm.mozRequestFullScreen) {  
            docElm.mozRequestFullScreen();  
        }  
        //Chrome等   
        else if (docElm.webkitRequestFullScreen) {  
            docElm.webkitRequestFullScreen();  
        }  
        //IE11   
        else if (elem.msRequestFullscreen) {  
            elem.msRequestFullscreen();  
        }  
    }

    return {
        init:function(id){
            init(id);
        }
    }
}();


/**
 * 加载中组件
 * 注意需要引入 Loading.css
 */
var Loading = function(){

	/**
	 * 打开
	 * @return {[type]} [description]
	 */
    var open = function(){
        var html = joinHtml();
        $('body').append(html);
    }

    /**
     * 拼接html
     * @return {[type]} [description]
     */
    var joinHtml = function(){
        var html = [];
        html.push('<div class="Loading">');
        html.push('<div class="spinner">');
        html.push('<div class="rect1"></div>');
        html.push('<div class="rect2"></div>');
        html.push('<div class="rect3"></div>');
        html.push('<div class="rect4"></div>');
        html.push('<div class="rect5"></div>');
        html.push('</div>')
        html.push('</div>')
        return html.join('');
    }

    /**
     * 关闭
     * @return {[type]} [description]
     */
    var clear = function(){
        //$('.Loading').addClass('hidden');
        $('.Loading').remove();
    }

    return {
        open:function(){
            open();
        },
        clear:function(){
            clear();
        }
    }
}();


/**
 * 滚动到顶部组件
 * @return {[type]} [description]
 */
var scrollTop = function (){
    var init = function(){
        if($("#scrollTop").length == 0 ){
            var html = joinHtml();
            $('body').append(html);
            bindFun();
        }
    }

    /**
     * 拼接Html
     * @return {[type]} [description]
     */
    var joinHtml = function(){
        var html = [];
        html.push('<div id="scrollTop" style="position: fixed;right: 40px;bottom: 55px;">');
        html.push('<span class="glyphicon glyphicon-circle-arrow-up" style="cursor:pointer;font-size: 28px;"></span>');
        html.push('</div>');
        return html.join('');
    }

    /**
     * 绑定事件
     * @return {[type]} [description]
     */
    var bindFun = function(){
        $("#scrollTop > .glyphicon-circle-arrow-up").unbind('click').bind('click',function(){
            $('html,body').animate({scrollTop:0},'slow');
        })

        // 隐藏go to top按钮
        $("#scrollTop").hide();

        // 判断是否出现
        $(window).scroll(function(){
            //当window的scrolltop距离大于1时，scrollTop按钮淡出，反之淡入
            if($(this).scrollTop()>1){
                $("#scrollTop").fadeIn();
            } else {
                $("#scrollTop").fadeOut();
            }
        });
    }
    return {
        init:function(){
            init();
        }
    }
}();

/**
 * 数据字典组件
 * 注：当type=0，初始化字典类型时，若要用字典类型查询字典项，
 * 	此时查询条件不可直接取字典类型控件的value值，而是取控件的data-mark值；
 *  例如：var mark = $("#sheng option:selected").attr("data-mark");
 * 	   	var mark = $("#sheng:checked").attr("data-mark");
 * @type {{init}}
 */
var Dictionary = function(){

    var argument = {
        position:"",    //控件位置，DIV或select的ID
        domType:"",     //控件类型：radio;checkbox;select
        mark:"",        //字典类型唯一码值
        type:"",		//要初始化的数据类型：1字典项；0字典类型
        name:"",        //控件name属性
        resId:"",		//资源ID
        hasAll:false,	//是否初始化“全部”选项（只对radio,checkbox有效）,默认为false，没有
        checkAllId:"",	//全选框的ID属性（只有当hasAll为true是有用）
        hasSelect:true  //是否初始化“--请选择--”下拉框，默认为true，
    };

    var init = function(json){

        var _arg = $.extend(argument, json);
        if(!_arg.resId){
    		_arg.resId = $("#resId").val();
    	}
        if(_arg.mark){
            $.ajax({
                type:"get",
                url:"/system/config/dictionary/getListByMark",
                data:{
                	"mark":_arg.mark,
                	"type":_arg.type,
                	"resId":$("#resId").val()
                },
                dataType:"json",
                async:false,
                success:function(data){
                    if(data.flag == "1"){
                        $("#"+_arg.position).empty().append(joinHtml(data.data,_arg));
                    }else{
                    	if(data.msg){
                    		layer.msg("获取字典项失败！" + data.msg,{icon:2});
                    	}else{
                    		layer.msg("获取字典项异常！请刷新重试！",{icon:2});
                    	}
                    }
                },
                error:function(){
                	layer.msg("加载字典项异常！请刷新重试！",{icon:2});
                }
            })
        }else{
        	$("#"+_arg.position).empty().append("");;
        }
    }

    /**
     * 拼接字符串
     * @param data
     */
    var joinHtml = function(data,arg){
        var html = "",val="";
        if(arg.type == '0'){//字典类型，作为查询条件，查询字典项列表时需用到
    		$("#"+arg.position).before("<input type='hidden' name='type' value='1'>");
    	}
        if(arg.domType == "radio"){
        	if(arg.hasAll){
        		html += "<label class='radio-inline'>";
        		html += "<input type='radio' id='" + arg.checkAllId + "' checked name='" + arg.name + "' value=''>全部";
        		html += "</label>";
        	}
        	$.each(data, function (i, n) {
            	html += "<label class='radio-inline'>";
            	if(arg.type == '0'){
            		html += "<input type='radio' data-mark='" + n.mark + "' name='" + arg.name + "' value='" + n.code + "'>";
            	}else if(arg.type == '1'){
            		html += "<input type='radio' name='" + arg.name + "' value='" + n.code + "'>";
            	}
                html += n.name;
                html += "</label>";
            })
        }else if(arg.domType == "checkbox") {
        	if(arg.hasAll){
        		html += "<label class='checkbox-inline'>";
        		html += "<input type='checkbox'style ='height :20px' id='" + arg.checkAllId + "' name='" + arg.name + "' value=''>全部";
        		html += "</label>";
        	}
            $.each(data, function (i, n) {
            	html += "<label class='checkbox-inline'>";
            	if(arg.type == '0'){
            		html += "<input type='checkbox' style ='height :20px' data-mark='" + n.mark + "' name='" + arg.name + "' value='" + n.code + "'>";
            	}else if(arg.type == '1'){
            		html += "<input type='checkbox'style ='height :20px' name='" + arg.name + "' value='" + n.code + "'>";
            	}
                html += n.name;
                html += "</label>";
            })
        }else if(arg.domType == "select"){
            if(arg.hasSelect){
                html += "<option data-mark='' value=''>--请选择--</option>";
            }
            $.each(data, function (i, n) {
            	if(arg.type == '0'){
            		html += "<option data-mark='" + n.mark + "' value='" + n.code + "'>" + n.name + "</option>";
            	}else if(arg.type == '1'){
            		html += "<option value='" + n.code + "'>" + n.name + "</option>";
            	}
            })
        }
        return html;
    }
    
    var arg = {
    	mark:"",	//唯一码值
    	type:""		//数据类型	0:字典类型；1：字典项
    	//,resId:""	//资源ID
    }
    
    /**
     * 根据唯一码值，获取一个以code、name为键值对的map集合
     * @param json
     * @return json
     */
    var getNameAndCode = function(json){
    	var _arg = $.extend(arg, json);
    	var jsonObj = {};
    	/*if(!_arg.resId){
    		_arg.resId = $("#resId").val();
    	}*/
        if(_arg.mark){
        	$.ajax({
                type:"get",
                url:"/system/config/dictionary/getMapByMark",
                data:{
                	"mark":_arg.mark,
                	"type":_arg.type
                	//, "resId":_arg.resId
                },
                dataType:"json",
                async:false,
                success:function(data){
                	console.log(data);
                    if(data.flag == "1"){
                    	jsonObj = data.data;
                    }else{
                    	if(data.msg){
                    		layer.msg("获取字典项失败！" + data.msg,{icon:2});
                    	}else{
                    		layer.msg("获取字典项异常！请刷新重试！",{icon:2});
                    	}
                    }
                },
                error:function(){
                	layer.msg("获取字典项异常！请刷新重试！",{icon:2});
                }
            })
        }
        return jsonObj;
    }

    return{
        init:function(json){
            init(json);
        },
        getNameAndCode:function(json){
        	return getNameAndCode(json);
        }
    }
}();

/**
 * 职务、职级、涉密等级
 * 下拉框初始化方法、及根据value回显name的方法
 */
var userLevel = function(){

    var argument = {
        position:"",    //控件位置，DIV或select的ID
        type:""		//要初始化的数据类型：1职务；2职级；3涉密等级
    };
	
	var init = function(json){

        var _arg = $.extend(argument, json);
        if(_arg.type){
            $.ajax({
                type:"get",
                url:"/user/getUserLevel",
                data:{
                	"type":_arg.type
                },
                dataType:"json",
                async:false,
                success:function(data){
                    if(data.flag == "1"){
                    	var html = "";
                        $.each(data.data, function (i, n) {
                        	html += "<option value='" + n.occupation_level + "'>" + n.occupation_name + "</option>";
                        })
                        $("#"+_arg.position).empty().append(html);
                    }else{
                    	if(data.msg){
                    		layer.msg("获取字典项失败！" + data.msg,{icon:2});
                    	}else{
                    		layer.msg("获取字典项异常！请刷新重试！",{icon:2});
                    	}
                    }
                },
                error:function(){
                	layer.msg("加载字典项异常！请刷新重试！",{icon:2});
                }
            })
        }else{
        	$("#"+_arg.position).empty().append("<option value=''>--请选择--</option>");;
        }
    }
	
	var arg = {
    	type:""		//1职务；2职级；3涉密等级
    }
	
	var getNameAndLevel = function(json){
		var jsonObj = {};
		var _arg = $.extend(arg, json);
        if(_arg.type){
        	$.ajax({
                type:"get",
                url:"/user/getUserLevel",
                data:{
                	"type":_arg.type
                },
                dataType:"json",
                async:false,
                success:function(data){
                    if(data.flag == "1"){
                    	$.each(data.data, function (i, n) {
                        	jsonObj[n.occupation_level] = n.occupation_name;
                        })
                    }
                },
                error:function(){
                }
        	})
        }
		return jsonObj;
	}
	
	return{
        init:function(json){
            init(json);
        },
        getNameAndLevel:function(json){
        	return getNameAndLevel(json);
        }
    }
}();