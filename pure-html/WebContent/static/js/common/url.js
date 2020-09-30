/**
 * url模块的构造函数
 * @protocol 请求协议，小写
 * @host URL主机名已全部转换成小写, 包括端口信息
 * @port 主机的端口号部分
 * @hostname 主机的主机名部分, 已转换成小写
 * @hash URL 的 “#” 后面部分
 * @search URL 的“查询字符串”部分，包括开头的问号
 * @query 查询字符串中的参数部分（问号后面部分字符串）
 * @pathname URL的路径部分,位于主机名之后请求查询之前
 * @path pathname 和 search 连在一起。
 * @href 所解析的完整原始 URL。协议名和主机名都已转为小写。
 */
function Url(){
    this.protocol = null;
	  this.host = null;
	  this.port = null;
	  this.hostname = null;
	  this.hash = null;
	  this.search = null;
	  this.query = null;
	  this.pathname = null;
	  this.path = null;
	  this.href = null;
};

 Url.prototype.protocolPattern = /(\w+:)\/\/([^/:]+)(:\d*)?([^# ]*)/;

/**
 *输入 URL 字符串，返回一个对象。
 */
Url.prototype.parse=function(urlStr){
  if (typeof urlStr !== 'string') {
    throw new TypeError('Parameter "url" must be a string, not ' + typeof urlStr);
  }
   var _array=this.protocolPattern.exec(urlStr);
   if(!_array){
      throw new TypeError('url解析错误！');
      return false;
   }

   this.href=_array[0];
   this.protocol =_array[1].toLowerCase();
   this.hostname=_array[2].toLowerCase();
   var _port=_array[3];
   if(_port){
	  if (_port !== ':') {
	     this.port = _port.slice(1);
	  }
	  this.host=this.hostname+_port;
   }else{
   	  this.host=this.hostname;
   }
   var _path=_array[4];
   if(_path){
   	this.path=_path;
   	if(_path.split("?")[0]!="?"){
   		this.pathname=_path.split("?")[0];
   	}
   	if(_path.split("?").length>=2 && _path.split("?")[1]){
		this.search=_path.slice(this.pathname.length);
    	var _query_array=this.search.slice(1).split("&");
    	var _this_obj=new Object();
    	for(var i=0;i<_query_array.length;i++){ 
    		var _obj=_query_array[i].split("=");
    		_this_obj[_obj[0]]=_obj[1];
    	}
    	this.query=_this_obj;
   	}
   
   }
   return this;
};


/**
 *输入一个对象，返回一个url。
 */
Url.prototype.format=function(urlObj){
   var url="";
   if(urlObj.protocol){
     url+=urlObj.protocol+"//";
   }
   if(urlObj.hostname){
     url+=urlObj.hostname;
   }
   if(urlObj.port){
     url+=":"+urlObj.port;
   }
   if(urlObj.pathname){
     url+=urlObj.pathname;
   }
   if(urlObj.query){
     var i=0;
     var _obj=urlObj.query;
     for(var item in _obj){
        if(i==0){
           url+="?"+item+"="+_obj[item];
        }else{
           url+="&"+item+"="+_obj[item];
        }
        i++;
     }
   }
   return url;
};

Url.prototype.base64EncodeChars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
Url.prototype.base64DecodeChars=new Array( 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, 
    -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, 
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
);

/**
 *base64 加密方法
 */
Url.prototype.base64encode=function(str){
    var out, i, len; 
    var c1, c2, c3; 

    len = str.length; 
    i = 0; 
    out = ""; 
    while(i < len) { 
    c1 = str.charCodeAt(i++) & 0xff; 
    if(i == len) 
    { 
        out += this.base64EncodeChars.charAt(c1 >> 2); 
        out += this.base64EncodeChars.charAt((c1 & 0x3) << 4); 
        out += "=="; 
        break; 
    } 
    c2 = str.charCodeAt(i++); 
    if(i == len) 
    { 
        out += this.base64EncodeChars.charAt(c1 >> 2); 
        out += this.base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4)); 
        out += this.base64EncodeChars.charAt((c2 & 0xF) << 2); 
        out += "="; 
        break; 
    } 
    c3 = str.charCodeAt(i++); 
    out += this.base64EncodeChars.charAt(c1 >> 2); 
    out += this.base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4)); 
    out += this.base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6)); 
    out += this.base64EncodeChars.charAt(c3 & 0x3F); 
    } 
    return out; 

};

/**
 *base64 解密方法
 */
Url.prototype.base64decode=function(str){
   var c1, c2, c3, c4; 
    var i, len, out; 

    len = str.length; 
    i = 0; 
    out = ""; 
    while(i < len) { 
    /* c1 */ 
    do { 
        c1 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff]; 
    } while(i < len && c1 == -1); 
    if(c1 == -1) 
        break; 

    /* c2 */ 
    do { 
        c2 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff]; 
    } while(i < len && c2 == -1); 
    if(c2 == -1) 
        break; 

    out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4)); 

    /* c3 */ 
    do { 
        c3 = str.charCodeAt(i++) & 0xff; 
        if(c3 == 61) 
        return out; 
        c3 = this.base64DecodeChars[c3]; 
    } while(i < len && c3 == -1); 
    if(c3 == -1) 
        break; 

    out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2)); 

    /* c4 */ 
    do { 
        c4 = str.charCodeAt(i++) & 0xff; 
        if(c4 == 61) 
        return out; 
        c4 = this.base64DecodeChars[c4]; 
    } while(i < len && c4 == -1); 
    if(c4 == -1) 
        break; 
    out += String.fromCharCode(((c3 & 0x03) << 6) | c4); 
    } 
    return out; 
};

/**
 *把字符编码utf-16转换成utf-8
 */
Url.prototype.utf16to8=function(str) { 
    var out, i, len, c; 
    out = ""; 
    len = str.length; 
    for(i = 0; i < len; i++) { 
    c = str.charCodeAt(i); 
    if ((c >= 0x0001) && (c <= 0x007F)) { 
        out += str.charAt(i); 
    } else if (c > 0x07FF) { 
        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F)); 
        out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F)); 
        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F)); 
    } else { 
        out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F)); 
        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F)); 
    } 
    } 
    return out; 
}; 

/**
 *把字符编码utf-8转换成utf-16
 */
Url.prototype.utf8to16=function(str) { 
    var out, i, len, c; 
    var char2, char3; 

    out = ""; 
    len = str.length; 
    i = 0; 
    while(i < len) { 
    c = str.charCodeAt(i++); 
    switch(c >> 4) 
    { 
      case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: 
        out += str.charAt(i-1); 
        break; 
      case 12: case 13: 
        char2 = str.charCodeAt(i++); 
        out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F)); 
        break; 
      case 14: 
        char2 = str.charCodeAt(i++); 
        char3 = str.charCodeAt(i++); 
        out += String.fromCharCode(((c & 0x0F) << 12) | 
                       ((char2 & 0x3F) << 6) | 
                       ((char3 & 0x3F) << 0)); 
        break; 
    } 
    } 

    return out; 
};

Url.prototype.hexTable = new Array(256);
for (var i = 0; i < 256; ++i){
    Url.prototype.hexTable[i] = '%' + ((i < 16 ? '0' : '') + i.toString(16)).toUpperCase();
}
 
/**
 *加密中文传参乱码
 */
Url.prototype.escape=function(str){
  if (typeof str !== 'string') {
    if (typeof str === 'object')
      str = String(str);
    else
      str += '';
  }
  var out = '';
  var lastPos = 0;
  for (var i = 0; i < str.length; ++i) {
    var c = str.charCodeAt(i);

    if (c === 0x21 || c === 0x2D || c === 0x2E || c === 0x5F || c === 0x7E ||
        (c >= 0x27 && c <= 0x2A) ||
        (c >= 0x30 && c <= 0x39) ||
        (c >= 0x41 && c <= 0x5A) ||
        (c >= 0x61 && c <= 0x7A)) {
      continue;
    }

    if (i - lastPos > 0)
      out += str.slice(lastPos, i);

    if (c < 0x80) {
      lastPos = i + 1;
      out += this.hexTable[c];
      continue;
    }

    if (c < 0x800) {
      lastPos = i + 1;
      out += this.hexTable[0xC0 | (c >> 6)] + this.hexTable[0x80 | (c & 0x3F)];
      continue;
    }
    if (c < 0xD800 || c >= 0xE000) {
      lastPos = i + 1;
      out += this.hexTable[0xE0 | (c >> 12)] +
             this.hexTable[0x80 | ((c >> 6) & 0x3F)] +
             this.hexTable[0x80 | (c & 0x3F)];
      continue;
    }
    ++i;
    var c2;
    if (i < str.length)
      c2 = str.charCodeAt(i) & 0x3FF;
    else
      throw new URIError('加密错误！');
    lastPos = i + 1;
    c = 0x10000 + (((c & 0x3FF) << 10) | c2);
    out += this.hexTable[0xF0 | (c >> 18)] +
           this.hexTable[0x80 | ((c >> 12) & 0x3F)] +
           this.hexTable[0x80 | ((c >> 6) & 0x3F)] +
           this.hexTable[0x80 | (c & 0x3F)];
  }
  if (lastPos === 0)
    return str;
  if (lastPos < str.length)
    return out + str.slice(lastPos);
  return out;
};

/**
 *解密中文传参乱码
 */
Url.prototype.unescape=function(s, decodeSpaces){
  try {
    return decodeURIComponent(s);
  } catch (e) {
    //return this.unescapeBuffer(s, decodeSpaces).toString();
  }
};

/**
 *object 转 string
 *@argument 要转的对象
 *@join 连接符
 */
Url.prototype.objToString=function(argument,join){
     var data="";
     for(var item in argument){
       if(argument[item]){
          if(data){
            data+= join+item+"="+argument[item];
          }else{
            data+= item+"="+argument[item];
          }
       }
     }
     return data;
}

/**
 *String 转 object
 *@argument 要转的对象
 *@join 分割符
 */
Url.prototype.strToObject=function(argument,join){
  var obj={};
  var _obj=argument.split(join);
  for(var i=0;i<_obj.length;i++){
  	obj[_obj[i].split("=")[0]]=_obj[i].split("=")[1];
  }
  return obj;
}