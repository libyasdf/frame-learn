/*********************
 * 通过后台设置的cookie在同服务器下的项目可以共同使用
 * 通过js设置的cookie在同服务器下的项目可以共同使用
 * 
 *********************/           

			
			/*获取js 设置的cookie值 
			 *key cookie的name，
			 *返回 key对应的value值，没有对应的cookie时返回null
			*/
			function getCookie(objname){
				if( objname == "rolesNo" || objname == "sysRoleIds" || objname=="sysRoleNos" ){
					return getRolesInfo(objname);
				}
				var arrstr = document.cookie.split("; ");
				for(var i = 0;i < arrstr.length;i ++){
					var temp = arrstr[i].split("=");
					if(temp[0] == objname){
						if (objname == "deptnm") {
							var deptnm = decodeURI(temp[1]);
							return deptnm.replace(/%2F/g, "/");
						}else{
							return decodeURI(temp[1]);	
						}
					}
				}
			}
			
			/**
			 * 与后台交互获取当前登录的用户角色信息
			 */
			function getRolesInfo(objname) { 
				var _now_userinfo;
				$.ajax({
					   type: "GET",
					   async: false,
					   url: "/user/userRolesInfo",
					   data: {
						   infoName: objname
					   },
					   dataType: "json",
					   success: function(data){
						   _now_userinfo = data.info;
					   },
					   error : function(error) {
						   _now_userinfo = "get role info error！";
					   }
				});
				return _now_userinfo; 
			}
            /* js设置cookie 
             * key cookie的名字，取cookie时通过key找对应的value
             *value  cookie中要存的值 
             *extime  cookie存活时间 以分钟为单位
            */
			function setCookie(key,value,extime){
				var exp=new Date();
				exp.setTime(exp.getTime()+(extime*60 * 1000));
				document.cookie = key + "=" + escape(value) + ";expires=" + exp.toGMTString()+";path=/";
			}
			
			/* 清除js cookie */
			function clearCookie(key){
				setCookie(key, "", -1);
			}