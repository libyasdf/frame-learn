const webpack = require("webpack");
const path = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  entry: {
    main: './index.js'
  },
  devServer: {
    index: "index.html",
    publicPath: "/", //此路径下的打包文件可在浏览器中访问。
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, 'dist'),
    chunkFilename: "[hash].js"
  },
  module: {
    rules: [{
      test: /\.js$/,
      use: ["babel-loader"]
    }, {
      test: /\.vue$/,
      use: ["vue-loader"]
    }, {
      test: /\.css$/,
      use: ["vue-style-loader", "css-loader"]
    }, {
      test: /\.less$/,
      use: ["vue-style-loader", "css-loader", "less-loader"]
    },
    // {
    //   test: /\.(png|jpg|gif)$/,
    //   use: [{
    //     loader: 'file-loader',
    //     options: {
    //       name: '[path][name].[ext]'
    //     }
    //   }]
    // },
     {
      test: /\.(png|jpg|gif)$/,
      use: [{
        loader: "url-loader",
        options: { //指定处理后的文件的「路径」「名字」转化base-64的「大小」限制
          name: '[path][name].[ext]', //这里指定了文件的路径为相对路径，名字为文件的名字
          limit: 8192 //小于8192字节的图片都被转化为了base-64地址
        }
      }]
    }]
  },
  plugins: [
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true
    }),
    new VueLoaderPlugin(),
    new webpack.DefinePlugin({
      'env': JSON.stringify("environment"),
    }),
  ],
  resolve: {
    alias: {
      'vue': "vue/dist/vue.esm.js" //设置别名，引入自带编译版本的vue,这时我们就不用手写render函数，可以使用templete语法了
    }
  }

};
