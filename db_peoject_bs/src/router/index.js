import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";


Vue.use(VueRouter)

const routes = [
  {
    path: '/manage',
    component: () => import("../views/Manage.vue"),
    redirect: "/manage/home",
    children:[
      {path: 'user', name: '首页' , component: () => import('../views/User.vue')},
      {path: 'home', name: '用户管理' , component: () => import('../views/Home.vue')},
      {path:'person', name: '个人信息', component:() => import('../views/Person.vue')},
      {path:'file', name: '文件管理', component:() => import('../views/File.vue')}

    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  }
  , {
    path:'/login',
    name: 'Login',
    component:() => import('../views/Login.vue')
  },
  , {
    path:'/register',
    name: 'Register',
    component:() => import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  //console.log(from)
  localStorage.setItem("currentPathName",from.name)  //设置当前路由名称,为了在header组件中使用
  store.commit("setPath")  //触发store数据更新
  next()   //路由放行
})
export default router
