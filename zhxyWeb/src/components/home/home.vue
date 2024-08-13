<template>

  <el-container class="home">
    <!--header start-->
    <el-header class="header">
      <div style="display: flex; justify-content: space-between;">

        <div style="width: 190px; height: 100%; margin-top: -5px; flex-direction: row-reverse">
          <span style="margin-top: 20px; font-size: 30px">智慧校园</span>
          <i class="icon iconfont icon-a-iconzhihuixiaoyuan"></i>
        </div>

        <div class="link-box" style="display: flex; align-items: center;">
          <div style="display: flex; margin-top: 5px">
            <img :src="avatar ? `${img}/${avatar}` : ''" class="img_css">
          </div>
          <span>
            <i class="icon iconfont icon-084tuichu" @click="exitLogin()"/>
          </span>

        </div>
      </div>


    </el-header>
    <!--header end-->
    <el-container>
      <!--aside start-->
      <el-aside width="200px" style="background-color: #2F2222">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#2F2222"
          text-color="#fff"
          active-text-color="#ffd04b"
          :unique-opened="true"
          :router="true"
        >
          <el-submenu index="1" >
            <!--1导航栏模板-->
            <template slot="title">
              <i class="icon iconfont icon-xueshengguanli"></i>
              <span>数据管理</span>
            </template>
            <!--2-->
            <el-menu-item index="/stu">
              <i class="icon iconfont icon-yonghu"></i>
              <span slot="title">学生管理</span>
            </el-menu-item>

            <el-menu-item index="/teacher">
              <i class="icon iconfont icon-jiaoshishezhi"></i>
              <span slot="title">教师管理</span>
            </el-menu-item>

            <el-menu-item index="/class">
              <i class="icon iconfont icon-banjiguanli"></i>
              <span slot="title">班级管理</span>
            </el-menu-item>

            <el-menu-item index="/grade">
              <i class="icon iconfont icon-nianjiguanli"></i>
              <span slot="title">年级管理</span>
            </el-menu-item>

            <el-menu-item index="/admin" >
              <i class="icon iconfont icon-guanliyuan_jiaoseguanli"></i>
              <span>管理员管理</span>
<!--              <router-link to="/admin">管理员管理</router-link>-->
            </el-menu-item>

          </el-submenu>

          <el-submenu index="2">
            <template slot="title">

              <i class="icon iconfont icon-xueshengguanli"></i>
              <span>个人信息</span>
            </template>

            <el-menu-item index="/info">
              <i class="icon iconfont icon-yonghu"></i>
              <span slot="title">个人信息修改</span>
            </el-menu-item>

          </el-submenu>

        </el-menu>
      </el-aside>
      <!--aside end-->

      <!--main start-->
      <el-main class="main">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
          <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.path">
            {{item.meta.title}}
          </el-breadcrumb-item>
        </el-breadcrumb>

        <router-view></router-view>
      </el-main>
      <!--main end-->
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "home",
  data() {
    return {
      avatar: "",
      img: this.$axios.defaults.baseURL
    }
  },

  created() {
    this.getUserInfo()

  },

  // 面包屑实现
  computed: {
    breadcrumbList() {
      return this.$route.matched;
    },
  },


  methods: {
    // 用户信息展示数据获取
    getUserInfo() {
      // console.log(this.img)
      // console.log("傻逼")
      this.$axios({
        url: "/user",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")}
      }).then(msg => {
        this.avatar = msg.data.data.avatar;
        // console.log(this.avatar);
      })
    },

    // 退出登录
    exitLogin() {
      sessionStorage.clear();
      this.$router.push("/");
    }
  }
}
</script>


<style scoped >
.img_css {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 20px;
}

.home {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.header {
  background-color: #51bcf3;
  height: 60px;

}
.icon-a-iconzhihuixiaoyuan{
  font-size: 60px;
}

.link-box {

}



</style>
