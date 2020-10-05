import { defineConfig } from 'dumi';

export default defineConfig({
  title: '框架学习',
  // 文档模式（默认值）
  //  mode: 'doc',
  // 站点模式
  mode: 'site',
  base: "/frame",
  publicPath: "/frame/",
  // exportStatic: {},
  // favicon: "/logo.png",
  // logo: "/logo.png",
  outputPath: "docs-dist"
  // more config: https://d.umijs.org/config
});
