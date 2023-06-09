import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";


Vue.use(VueRouter)

const routes = [
  {
    path: '/manage',
    component: () => import("@/views/manager/Manage.vue"),
    redirect: "/manage/home",
    children:[
      {path: 'home', name: '主页' , component: () => import('@/views/Home.vue')},
      {path: 'user', name: '学院信息管理' , component: () => import('@/views/manager/DepartmentManage.vue')},
      {path:'information', name: '管理员个人信息', component:() => import('@/views/manager/ManagerInformation.vue')},
      {path:'file', name: '文件管理', component:() => import('@/views/manager/File.vue')},
      {path:'student',name:'学生信息管理',component:() => import('@/views/manager/StudentManage.vue')},
      {path:'teacher',name:'教师信息管理',component:() => import('@/views/manager/TeatherManage.vue')},
      {path:'lesson',name:'课程信息', component:()=>import ('@/views/manager/LessonManage.vue')},
      {path:'sclass',name:'开课信息',component : () => import('@/views/manager/SclassManage.vue')}
    ],
  },
  {
    path: '/teachermanage',
    component: () => import("@/views/teacher/TeacherManage.vue"),
    redirect: "/teachermanage/home",
    children:[
      {path: 'home', name: '首页' , component: () => import('@/views/teacher/TeacherHome.vue')},
      {path: 'RegistScore', name: '成绩录入' , component: () => import('@/views/teacher/TeacherRegistScore.vue')},
      {path:'person', name: '个人信息', component:() => import('@/views/teacher/TeacherPerson.vue')},
      {path:'file', name: '文件管理', component:() => import('@/views/manager/File.vue')},
      {path:'classtable', name: '课表查询', component:() => import('@/views/teacher/TeacherClassTable.vue')},
      {path:'myClass', name: '我的课程', component:() => import('@/views/teacher/TeacherClass.vue')},
    ]
  },

  {
    path: '/studentmanage',
    component: () => import("@/views/student/StudentManage.vue"),
    redirect: "/studentmanage/home",
    children:[
      {path: 'selecourse', name: '选课' , component: () => import('@/views/student/StudentSeleceCourse.vue')},
      {path: 'home', name: '用户管理' , component: () => import('@/views/student/StudentHome.vue')},
      {path:'personinfo', name: '个人信息', component:() => import('@/views/student/StudentPerson.vue')},
      {path:'file', name: '文件管理', component:() => import('@/views/student/StudentFileManage.vue')},
      {path:'courseinquire', name: '课程查询', component:() => import('@/views/student/StudentCourseInquire.vue')},
      {path:'cancelclass', name: '退课', component:() => import('@/views/student/StudentCancelClass.vue')},
      {path:'classtable', name: '课表查询', component:() => import('@/views/student/StudentClassTable.vue')}

    ],

  },
  {
    path: '/about',
    name: 'about',
    component: () => import('@/views/AboutView.vue')
  }
  , {
    // path: '/',
    path:'/login',
    name: 'Login',
    component:() => import('@/views/Login.vue'),
  }
  , {
    path:'/register',
    name: 'Register',
    component:() => import('@/views/Register.vue')
  },
  {
    path: '/',
    redirect: '/login'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  //console.log(from)
  localStorage.setItem("currentPathName",to.name)  //设置当前路由名称,为了在header组件中使用
  store.commit("setPath")  //触发store数据更新
  next()   //路由放行
})
export default router
