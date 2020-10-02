
function showMsg(){
	console.log("change window size");
}
function showCoords(evq){
	var x1 = window.pageXOffset;//evq.clientX;
	var y1 = window.pageYOffset;//evq.clientY;
	console.log(x1 + ":" + y1);
}

function coordinates(event){
	var x=event.screenX;
	var y=event.screenY;
	console.log("X=" + x + " Y=" + y);
}
function leftBox(){
	var x = document.getElementById("fname").value;
	document.getElementById("fname").value = x.toUpperCase();
}
function setStyle(id){
	document.getElementById(id).style.background = "yellow";
	
}
function leftChange(age){
	alert(age);
}
function submitForm(){
	console.log("submit!");
	alert("submit");
}
function keyDownF(){
	var x;
	if (window.event) {
		x = event.keyCode;
	}else if (event.which) {
		x = event.which;
	}
	var truecode = String.fromCharCode(x);//将 Unicode 编码转为一个字符
	console.log("result:",truecode);
}
function keyUpF(){
	var x = document.getElementById("fnameupk");//在最后.value会有神奇的效果
	x.value = x.value.toUpperCase();
	// var x=document.getElementById("fname");
	// x.value=x.value.toUpperCase();
}
function moveon(x){
	x.style.height = "100px";
	x.style.width = "100px";
}
function mouseout(x){
	x.style.height = "50px";
	x.style.width = "200px";
}
function showDetail(animal){
	var a = animal.getAttribute("data-type");
	var b = animal.innerHTML;
	b = b.replace(/[\r\n]/g,"");//回车
	b = b.replace(/\ +/g,"");//空格
	console.log( b + "is" + a);
	alert(b + "is" + a);
}

// 拖动
function allowDrop(ev){
	// 默认地，数据/元素无法被放置到其他元素中。为了实现拖放，我们必须阻止元素的这种默认的处理方式。
	ev.preventDefault();//取消事件的默认动作。
	// 该方法将通知 Web 浏览器不要执行与事件关联的默认动作（如果存在这样的动作）。
	// 例如，如果 type 属性是 "submit"，在事件传播的任意阶段可以调用任意的事件句柄，通过调用该方法，可以阻止提交表单。
	// 注意，如果 Event 对象的 cancelable 属性是 fasle，那么就没有默认动作，或者不能阻止默认动作。无论哪种情况，调用该方法都没有作用。
}

function drag(ev){//规定拖动什么数据
	console.log("drag:",ev);
	ev.dataTransfer.setData("Text",ev.target.id);//要添加的数据类型.+ 要添加的数据.
	// DataTransfer 对象用来保存，通过拖放动作，拖动到浏览器的数据
	// setData为一个给定的类型设置数据。如果该数据类型不存在，它将添加到的末尾，这样类型列表中的最后一个项目将是新的格式。
	// 如果已经存在的数据类型，替换相同的位置的现有数据。就是，当更换相同类型的数据时，不会更改类型列表的顺序。
}

function drop(ev){
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	console.log("drop",data);//要检索的数据类型。
	ev.target.appendChild(document.getElementById(data));
}
// 调用 preventDefault() 来阻止数据的浏览器默认处理方式（drop 事件的默认行为是以链接形式打开）
// 通过 dataTransfer.getData() 方法获得被拖的数据。该方法将返回在 setData() 方法中设置为相同类型的任何数据
// 被拖数据是被拖元素的 id ("drag1")
// 把被拖元素追加到放置元素中


