import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import Manager from "@/views/Manage";
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/main',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    children: [
      {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      {path: 'Role', name: '角色管理', component: () => import('../views/Role.vue')},
      {path: 'home', name: '首页', component: () => import('../views/Home.vue')},
      {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
      {path: 'file', name: '文件管理',component: () => import('../views/File.vue')},
      {path: 'menu', name: '菜单管理',component: () => import('../views/Menu.vue')},
    ]
  },
  {
    path: '/',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName",to.name)
  store.commit("setPath")
  next()//放行
})

export default router
