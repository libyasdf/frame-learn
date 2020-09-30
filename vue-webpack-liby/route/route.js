import routercomp from "../components/routercomp.vue"
import VueRouter from "vue-router"

export default new VueRouter({
  routes: [{
    name: 'routercomp',
    path: '/',
    component:() => import("../components/routercomp.vue")
    // 懒加载，以异步的形式引入组件——这个import方法需要babel插件的支持
    // component: routercomp
  }]
})
