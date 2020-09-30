var aggregation = (base, ...mixins) => {

    /*  create aggregation class  */
    let aggregate = class __Aggregate extends base {
        constructor (...args) {
            /*  call base class constructor  */
            super(...args);
            /*  call mixin's initializer  */
            mixins.forEach((mixin) => {
                if (typeof mixin.prototype.initializer === "function")
                    mixin.prototype.initializer.apply(this, args)
            })
        }
    };

    /*  copy properties  */
    let copyProps = (target, source) => {console.log(Object.getOwnPropertyNames(source));
        Object.getOwnPropertyNames(source)
            .concat(Object.getOwnPropertySymbols(source))
            .forEach((prop) => {
            if (prop.match(/^(?:initializer|constructor|prototype|arguments|caller|name|bind|call|apply|toString|length)$/))
                return
                Object.defineProperty(target, prop, Object.getOwnPropertyDescriptor(source, prop));
        })
    }


    /*  copy all properties of all mixins into aggregation class  */
    mixins.forEach((mixin) => {
        copyProps(aggregate.prototype, mixin.prototype)
        copyProps(aggregate, mixin)
    })

    return aggregate
}

//============================================

class Colored {
    initializer ()     { this._color = "white" }
    get color ()       { return this._color }
    set color (v)      { this._color = v }
}

class ZCoord {
    initializer ()     { this._z = 0 }
    get z ()           { return this._z }
    set z (v)          { this._z = v }
}

class Shape {
    constructor (x, y) { this._x = x; this._y = y }
    get x ()           { return this._x }
    set x (v)          { this._x = v }
    get y ()           { return this._y }
    set y (v)          { this._y = v }
}

class Rectangle extends aggregation(Shape, Colored, ZCoord) {
    toString(){
        return this.x+","+this.y+","+this.z+","+this.color;
    }
}

var rect = new Rectangle(7, 42)
rect.z     = 1000
//rect.color = "red"
console.log(rect)

// const curry = require('lodash').curry;
//
//
// const _pipe = (f, g) => (...arg) => {
//   console.log("f"+f);
//   console.log("g"+g);
//   console.log("arg:"+arg);
//   return g.call(null, f.apply(null, arg));
// }
//
// // const compose = (...args) => args.reverse().reduce(_pipe, args.shift());
//
// // const compose = function(...args) {
// //   let init = args.pop()
// //   return function(...arg) {
// //     return args.reverse().reduce(function(sequence, func) {
// //       return sequence.then(function(result) {
// //         return func.call(null, result)
// //       })
// //     }, Promise.resolve(init.apply(null, arg)))
// //   }
// // }
//
// function* iterateSteps(steps) {
//   let n
//   for (let i = 0; i < steps.length; i++) {
//     if (n) {
//       n = yield steps[i].call(null, n)
//     } else {
//       n = yield
//     }
//   }
// }
// const compose = function(...steps) {
//   let g = iterateSteps(steps)
//   return function(...args) {
//     let val = steps.pop().apply(null, args)
//     // 这里是第一个值
//     console.log(val)
//     // 因为无法传参数 所以无所谓执行 就是空耗一个yield
//     g.next()
//     return steps.reverse.reduce((val, val1) => g.next(val).value, val)
//   }
// }
//
//
// let init = (...args) => args.reduce((ele1, ele2) => ele1 + ele2, 0);
// let step2 = (val) => val + 2;
// let step3 = (val) => val + 3;
// let step4 = (val) => val + 4;
// var steps = [step4, step3, step2, init];
// var dc = compose(step4, step3, step2, init);
// console.log(dc(6));
//
//
//
//
//
// var maps = curry(function(f,arr){
//   // console.log(arr);
//   return arr.map(f);
// });
// var getNodes = function(x){
//   return x.valueOf();
// }
// var m = maps(getNodes);
// console.log(m([1,2]));
//
//
// var match = curry(function(what, str) {
//   return str.match(what);
// });
//
//
// match(/\s+/g, "hello world");
// // [ ' ' ]
//
// match(/\s+/g)("hello world");
// // [ ' ' ]
//
// var hasSpaces = match(/\s+/g);
// // function(x) { return x.match(/\s+/g) }
//
// console.log(hasSpaces("hello world"));
//
// // 一个播放器类
// class Player {
//
//   constructor() {
//     // 初始化观察者列表
//     this.watchers = {}
//
//     // 模拟2秒后发布一个'play'事件
//     setTimeout(() => {
//       this._publish('play', true)
//     }, 2000)
//
//     // 模拟4秒后发布一个'pause'事件
//     setTimeout(() => {
//       this._publish('pause', true)
//     }, 4000)
//   }
//
//   // 发布事件
//   _publish(event, data) {
//     if (this.watchers[event] && this.watchers[event].length) {
//       this.watchers[event].forEach(callback => callback.bind(this)(data))
//     }
//   }
//
//   // 订阅事件
//   subscribe(event, callback) {
//     this.watchers[event] = this.watchers[event] || []
//     this.watchers[event].push(callback)
//   }
//
//   // 退订事件
//   unsubscribe(event = null, callback = null) {
//     // 如果传入指定事件函数，则仅退订此事件函数
//     if (callback&&event) {
//       if (this.watchers[event] && this.watchers[event].length) {
//         this.watchers[event].splice(this.watchers[event].findIndex(cb => Object.is(cb, callback)), 1)
//       }
//
//     // 如果仅传入事件名称，则退订此事件对应的所有的事件函数
//     } else if (event) {
//       this.watchers[event] = []
//
//     // 如果未传入任何参数，则退订所有事件
//     } else {
//       this.watchers = {}
//     }
//   }
// }
//
// // 实例化播放器
// const player = new Player()
// console.log(player)
//
// // 播放事件回调函数1
// const onPlayerPlay1 = function(data) {
//   console.log('1: Player is play, the `this` context is current player', this, data)
// }
//
// // 播放事件回调函数2
// const onPlayerPlay2 = data => {
//   console.log('2: Player is play', data)
// }
//
// // 暂停事件回调函数
// const onPlayerPause = data => {
//   console.log('Player is pause', data)
// }
//
// // 加载事件回调函数
// const onPlayerLoaded = data => {
//   console.log('Player is loaded', data)
// }
//
// // 可订阅多个不同事件
// player.subscribe('play', onPlayerPlay1)
// player.subscribe('play', onPlayerPlay2)
// player.subscribe('pause', onPlayerPause)
// player.subscribe('loaded', onPlayerLoaded)
//
// // 可以退订指定订阅事件
// player.unsubscribe('play', onPlayerPlay2)
// // 退订指定事件名称下的所有订阅事件
// player.unsubscribe('play')
// // 退订所有订阅事件
// player.unsubscribe()
//
// // 可以在外部手动发出事件（真实生产场景中，发布特性一般为类内部私有方法）
// player._publish('loaded', true)
