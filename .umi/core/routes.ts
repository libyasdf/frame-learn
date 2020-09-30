// @ts-nocheck
import React from 'react';
import { ApplyPluginsType } from '/Users/baiyueli/.npm/_npx/5588/lib/node_modules/dumi/node_modules/@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": (props) => require('react').createElement(require('../../../../.npm/_npx/5588/lib/node_modules/dumi/node_modules/@umijs/preset-dumi/lib/themes/default/layout.js').default, {
      ...{"menus":{"*":{"*":[{"path":"/","title":"目录","meta":{"order":1}},{"title":"Code style","path":"/code style","meta":{"order":2},"children":[{"path":"/code style/javascript","title":"javascript 代码风格规范","meta":{"order":2}},{"path":"/code style/typescript","title":"typescript 代码风格规范","meta":{}}]},{"title":"Theme","path":"/theme","meta":{"order":3},"children":[{"path":"/theme/theme","title":"主题","meta":{"order":1}},{"path":"/theme/color","title":"Color","meta":{"order":2}}]},{"title":"Typography","path":"/typography","meta":{},"children":[{"path":"/typography/typography","title":"排版","meta":{}}]}]}},"locales":[],"navs":{},"title":"framework-learn","mode":"doc","repoUrl":"https://github.com/libyasdf/frame-learn"},
      ...props,
    }),
    "routes": [
      {
        "path": "/",
        "component": require('../../docs/index.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/index.md",
          "updatedTime": 1601481520000,
          "order": 1,
          "slugs": [
            {
              "depth": 2,
              "value": "目录",
              "heading": "目录"
            }
          ],
          "title": "目录"
        },
        "title": "目录"
      },
      {
        "path": "/code style/javascript",
        "component": require('../../docs/code style/javascript.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/code style/javascript.md",
          "updatedTime": 1601481296552,
          "order": 2,
          "group": {
            "order": 2,
            "path": "/code style",
            "title": "Code style"
          },
          "slugs": [
            {
              "depth": 2,
              "value": "javascript 代码风格规范",
              "heading": "javascript-代码风格规范"
            },
            {
              "depth": 3,
              "value": "避免使用默认导出",
              "heading": "避免使用默认导出"
            },
            {
              "depth": 3,
              "value": "使用 index.js 统一文件夹下的导出。",
              "heading": "使用-indexjs-统一文件夹下的导出。"
            },
            {
              "depth": 3,
              "value": "同类处理函数包装成对象",
              "heading": "同类处理函数包装成对象"
            },
            {
              "depth": 3,
              "value": "跨层级太多尽量使用别名",
              "heading": "跨层级太多尽量使用别名"
            },
            {
              "depth": 3,
              "value": "布尔值变量方法需要以 is 开头",
              "heading": "布尔值变量方法需要以-is-开头"
            },
            {
              "depth": 3,
              "value": "常量变量总是大写",
              "heading": "常量变量总是大写"
            },
            {
              "depth": 3,
              "value": "使用少量变量让可读性更好",
              "heading": "使用少量变量让可读性更好"
            },
            {
              "depth": 3,
              "value": "尽量将对象解构出来使用",
              "heading": "尽量将对象解构出来使用"
            },
            {
              "depth": 3,
              "value": "合理使用简短的三元表达式替代 if",
              "heading": "合理使用简短的三元表达式替代-if"
            },
            {
              "depth": 3,
              "value": "合理构造对象来代替多个 if",
              "heading": "合理构造对象来代替多个-if"
            },
            {
              "depth": 3,
              "value": "使用 async / await 替代 then / catch",
              "heading": "使用-async--await-替代-then--catch"
            }
          ],
          "title": "javascript 代码风格规范"
        },
        "title": "javascript 代码风格规范"
      },
      {
        "path": "/code style/typescript",
        "component": require('../../docs/code style/typescript.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/code style/typescript.md",
          "updatedTime": 1601481296553,
          "slugs": [
            {
              "depth": 2,
              "value": "typescript 代码风格规范",
              "heading": "typescript-代码风格规范"
            },
            {
              "depth": 3,
              "value": "所有的 interface 起始为 I,而 types 起始为 T",
              "heading": "所有的-interface-起始为-i而-types-起始为-t"
            },
            {
              "depth": 3,
              "value": "class 或 组件的 interface 应该包含它的名字",
              "heading": "class-或-组件的-interface-应该包含它的名字"
            },
            {
              "depth": 3,
              "value": "interface 尽量进行 extends 而不是从零写",
              "heading": "interface-尽量进行-extends-而不是从零写"
            },
            {
              "depth": 3,
              "value": "如果可以的话，尽量将类型分的更细一点",
              "heading": "如果可以的话，尽量将类型分的更细一点"
            },
            {
              "depth": 3,
              "value": "使用？来替代 undefined 类型",
              "heading": "使用？来替代-undefined-类型"
            },
            {
              "depth": 3,
              "value": "在 class 中总是使用 private public protected 明确",
              "heading": "在-class-中总是使用-private-public-protected-明确"
            }
          ],
          "title": "typescript 代码风格规范",
          "group": {
            "path": "/code style",
            "title": "Code style"
          }
        },
        "title": "typescript 代码风格规范"
      },
      {
        "path": "/theme/color",
        "component": require('../../docs/theme/color.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/theme/color.md",
          "updatedTime": 1601481520000,
          "order": 2,
          "group": {
            "order": 3,
            "path": "/theme",
            "title": "Theme"
          },
          "slugs": [],
          "title": "Color"
        },
        "title": "Color"
      },
      {
        "path": "/theme/theme",
        "component": require('../../docs/theme/theme.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/theme/theme.md",
          "updatedTime": 1601481520000,
          "order": 1,
          "group": {
            "order": 3,
            "path": "/theme",
            "title": "Theme"
          },
          "slugs": [
            {
              "depth": 2,
              "value": "主题",
              "heading": "主题"
            }
          ],
          "title": "主题"
        },
        "title": "主题"
      },
      {
        "path": "/typography/typography",
        "component": require('../../docs/typography/typography.md').default,
        "exact": true,
        "meta": {
          "filePath": "docs/typography/typography.md",
          "updatedTime": 1601481520000,
          "slugs": [
            {
              "depth": 2,
              "value": "排版",
              "heading": "排版"
            },
            {
              "depth": 3,
              "value": "字体",
              "heading": "字体"
            },
            {
              "depth": 4,
              "value": "font-family",
              "heading": "font-family"
            },
            {
              "depth": 4,
              "value": "字号与行高",
              "heading": "字号与行高"
            },
            {
              "depth": 4,
              "value": "字重",
              "heading": "字重"
            },
            {
              "depth": 4,
              "value": "字体颜色",
              "heading": "字体颜色"
            },
            {
              "depth": 3,
              "value": "阴影",
              "heading": "阴影"
            }
          ],
          "title": "排版",
          "group": {
            "path": "/typography",
            "title": "Typography"
          }
        },
        "title": "排版"
      },
      {
        "path": "/code style",
        "meta": {
          "order": 2
        },
        "exact": true,
        "redirect": "/code style/javascript"
      },
      {
        "path": "/theme",
        "meta": {
          "order": 3
        },
        "exact": true,
        "redirect": "/theme/theme"
      },
      {
        "path": "/typography",
        "meta": {},
        "exact": true,
        "redirect": "/typography/typography"
      }
    ],
    "title": "framework-learn"
  }
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
