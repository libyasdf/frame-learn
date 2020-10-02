// var Text = React.createClass({
//   render: function() {
//     return ( <div className = "a" >大家好， 我是用react渲染出来的！ </div> );
//   }
// });
//
// ReactDOM.render( < h1 > Hello, world! < /h1>,
//   document.getElementById('like_button_container')
// );
const PI = 3.1415926;
const columns = [
  {
    title: '姓名',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '年龄',
    dataIndex: 'age',
    key: 'age',
  },
  {
    title: '住址',
    dataIndex: 'address',
    key: 'address',
  },
];
function sum(...args) {
  log('sum', args);
  return args.reduce((num, tot) => tot + num);
}

function mult(...args) {
  log('mult', args);
  return args.reduce((num, tot) => tot * num);
}

// private function
function log(...msg) {
  console.log(...msg);
}

export { columns, PI, sum, mult };
