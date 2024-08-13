import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/login.vue'
import Register from '../components/login/register.vue'
import Home from "../components/home/home.vue";
import Admin from  "../components/admin/admin.vue"
import Stu from "../components/stu/stu.vue";
import Wellcom from "../components/wellcom/wellcom.vue";
import info from "../components/info/info.vue";
import Grade from "../components/grade/GradeManagement.vue"
import Class from "../components/class/class.vue"
import Teacher from "../components/teacher/teacher.vue"

Vue.use(Router)


const routes= [
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      title: "登录"
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      title: "注册"
    }
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: {
      title: "主页"
    },
    redirect: "/wellcom",
    children: [
      {
        path: "/wellcom",
        component: Wellcom,
        meta: {
          title: "欢迎页"
        }
      },

      // 数据管理路由start
      {
        path: "/infoManage",
        component: {render: (e) => e("router-view")},
        meta: {
          title: "数据管理"
        },
        children: [
          {
            path: "/admin",
            component: Admin,
            meta: {
              title: "管理员管理",
              requiresAdmin: true,
            },
          },
          {
            path: "/stu",
            component: Stu,
            meta: {
              title: "学生管理"
            }
          },
          {
            path: "/grade",
            component: Grade,
            meta: {
              title: "年级管理"
            }
          },
          {
            path: "/class",
            component: Class,
            meta: {
              title: "班级管理"
            }
          },
          {
            path: "/teacher",
            component: Teacher,
            meta: {
              title: "教师管理"
            }
          }
        ]
      },
      // 数据管理路由end


      // 个人信息管理路由start
      {
        path: "/personal",
        component: {render: (e) => e("router-view")},
        meta: {
          title: "个人信息"
        },
        children: [
          {
            path: "/info",
            component: info,
            meta: {
              title: "个人信息管理"
            },
          },
        ]
      },
      // 个人信息管理路由end
    ]
  },
]



const router = new Router({
  mode: "history",
  routes
})

router.beforeEach((to, from, next)=> {
  if (to.path === "/" || to.path === "/register") {
    next();
  }else if(to.path === "/admin"){ // 验证是否是管理如果不是没有权限访问/admin页面
    const UserRole = sessionStorage.getItem("role");
    if (to.meta.requiresAdmin && UserRole !== "ADMIN") {
      alert("你没有权限访问该页面")
      next({path: "/wellcom"})
    } else {
      next();
    }
  }else  {
    let  token = sessionStorage.getItem("token");
    if (token === null || token === "") {
      next({path: "/"})
    } else {
      next();
    }
  }



})

export default router; // 发布路由

// export default new Router({
//   mode: "history",
//   routes: [
//     {
//       path: '/',
//       name: 'Login',
//       component: Login,
//       meta: {
//         title: "登录"
//       }
//     },
//     {
//       path: '/register',
//       name: 'Register',
//       component: Register,
//       meta: {
//         title: "注册"
//       }
//     },
//     {
//       path: '/home',
//       name: 'Home',
//       component: Home,
//       meta: {
//         title: "主页"
//       },
//       redirect: "/wellcom",
//       children: [
//         {path: "/admin", component: Admin,},
//         {path: "/stu", component: Stu,},
//         {path: "/wellcom", component: Wellcom,},
//       ]
//     },
//
//
//   ]
// })






