//冒泡排序
function bubbleSort ( data ) {
    var temp = 0;
    for ( var i = data.length ; i > 0 ; i -- ){
        for( var j = 0 ; j < i - 1 ; j++){
           if( data[j] > data[j + 1] ){
               temp = data[j];
               data[j] = data [j+1];
               data[j+1] = temp;
           }
        }
    }
    return data;
}

//选择排序
function selectionSort( data ) {
    for( var i = 0; i< data.length ; i++){
        var min = data[i];
        var temp;
        var index = i;
        for( var j = i + 1; j< data.length; j++){
            if( data[j] < min ){
                min = data[j];
                index = j;
            }
        }

        temp = data[i];
        data[i] = min;
        data[index]= temp;
    }
    return data;
}

var dataStore = [ 72 , 1 , 68 , 95 , 75 , 54 , 58 , 10 , 35 , 6 , 28 , 45 , 69 , 13 , 88 , 99 , 24 , 28 , 30 , 31 , 78 , 2 , 77 , 82 , 72 ];
console.log( '原始数据:' + dataStore );
console.log( '选择排序:' + selectionSort( dataStore) );

// 原始数据:72,1,68,95,75,54,58,10,35,6,28,45,69,13,88,99,24,28,30,31,78,2,77,82,72
// 选择排序:1,2,6,10,13,24,28,28,30,31,35,45,54,58,68,69,72,72,75,77,78,82,88,95,99
function SupeClass(name){
  this.bookList = ["s","f","l"];
}
var Book = function(id,name,price){
  SupeClass.call(this,id);
  this.id = id;
    function check(){
      console.log(this.price);
    };
    this.setname = function(){};
    this.setname(name);
}
Book.prototype = new SupeClass();
var b = new Book(11,"sdaf",50);
// console.log(b.id);
// console.log(b.check());
// console.log(b.setname());
b.bookList.push("k");
var g = new Book(1,"sdf",5);
console.log(g.bookList);
// 原型继承
var objectBook = {
  name:"",
  list:[]
}
function inner(L){
  function F(){}
  F.prototype = L;
  return new F();
}
var oneBook = inner(objectBook);
oneBook.name = "wanng";
oneBook.list.push("liby");
var twoBook = inner(objectBook);
twoBook.name = "ni";
twoBook.list.push("oll");
console.log(oneBook.name,":",oneBook.list);
console.log(twoBook.name,":",twoBook.list);

Object.prototype.mix = function(){
    var i=1,len=arguments.length,arg;
    for (; i < len; i++) {
        arg = arguments[i];
        for (var property in arg) {
          this[property] = arg[property];
        }
    }
  };

twoBook.mix(oneBook);
console.log(twoBook);

//
// //原型式继承
    //原型式继承其实就是类式继承的封装,实现的功能是返回一个实例，改实例的原型继承了传入的o对象
    function inheritObject(o) {
        //声明一个过渡函数对象
        function F() {}
        //过渡对象的原型继承父对象
        F.prototype = o;
        //返回一个过渡对象的实例，该实例的原型继承了父对象
        return new F();
    }
    //寄生式继承
    //寄生式继承就是对原型继承的第二次封装，使得子类的原型等于父类的原型。并且在第二次封装的过程中对继承的对象进行了扩展
    function inheritPrototype(subClass, superClass){
        //复制一份父类的原型保存在变量中，使得p的原型等于父类的原型
        var p = inheritObject(superClass.prototype);
        //修正因为重写子类原型导致子类constructor属性被修改
        p.constructor = subClass;
        //设置子类的原型
        subClass.prototype = p;
    }
    //定义父类
    var SuperClass = function (name) {
        this.name = name;
        this.books = ['javascript','html','css']
    };
    //定义父类原型方法
    SuperClass.prototype.getBooks = function () {
        console.log(this.books)
    };

    //定义子类
    var SubClass = function (name) {
        SuperClass.call(this,name)
    }

    inheritPrototype(SubClass,SuperClass);

    var subclass1 = new SubClass('php')
//
/* 间接继承 */ 
const mixinClass = (base, ...mixins) => {
  const mixinProps = (target, source) => {
    Object.getOwnPropertyNames(source).forEach(prop => {
      if (/^constructor$/.test(prop)) { return; }
      Object.defineProperty(target, prop, Object.getOwnPropertyDescriptor(source, prop));
    })
  };

  let Ctor;
  if (base && typeof base === 'function') {
    Ctor = class extends base {
      constructor(...props) {
        super(...props);
      }
    };
    mixins.forEach(source => {
      mixinProps(Ctor.prototype, source.prototype);
    });
  } else {
    Ctor = class {};
  }
  return Ctor;
};

class A {
  methodA() {}
}
class B {
  methodB() {}
}
class C extends mixinClass(A, B) {
  methodA() { console.log('methodA in C'); }
  methodC() {}
}

let c = new C();
c instanceof C  // true
c instanceof A  // true
c instanceof B  // false
