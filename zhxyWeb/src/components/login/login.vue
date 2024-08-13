<template>
  <div class="waibu">
    <div class="form-login" >
      <el-form class="centered" ref="my_login" :model="loginData" :rules="rules">
        <h2>登录页面</h2>
        <el-form-item prop="username">
          <el-input type="text" style="width: 300px" prefix-icon="icon iconfont icon-yonghu" v-model="loginData.username"
                    onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'',); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" style="width: 300px" prefix-icon="icon iconfont icon-mima" v-model="loginData.password"
                    show-password
                    onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-top: -15px" prop="role">
          <el-radio style="width: 90px" v-model="loginData.role" label="ADMIN" >管理员</el-radio>
          <el-radio v-model="loginData.role" label="TEACHER" >老师</el-radio>
          <el-radio v-model="loginData.role" label="STUDENT" >学生</el-radio>
        </el-form-item>

        <el-form-item class="centered" style="margin: 0 auto; margin-top: -1px">
          <el-button style="width: 140px" @click="submitLogin">登录</el-button>
          <el-button style="width: 140px">
            <router-link to="/register" style="text-decoration: none !important; color: cornflowerblue;">注册</router-link>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>




</template>

<script>
// import imgUrl from 'src/assets/1.jpg';
export default {
  name: "login",
  data() {
    return {
      // imgUrl : imgUrl,
      loginData: {
        username: "",
        password: "",
        role: ""
      },

      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 18, message: "长度在6-18个字符之间", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 25, message: "长度在6-25个字符之间", trigger: "blur" }
        ],
        role: [{ required: true, message: "请选择角色", trigger: "change" }]
      },
    }
  },

  methods: {

    submitLogin() {
      // console.log(this.loginData);
      this.$refs.my_login.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "user/login",
            method: "post",
            data: this.loginData
          }).then(reg => {
            if (reg.data.state === 200) {
              sessionStorage.setItem("token", reg.data.data.token)
              sessionStorage.setItem("role", this.loginData.role)
              // console.log(reg.data.data.token)
              alert(reg.data.message)
              this.$router.push('/wellcom');
            }else {
              alert(reg.data.message)
            }
          })
        }else {
          this.$message({
            showClose: true,
            message: 'nmd是不是瞎，还有没有填写完的数据是没有看见吗！',
            type: 'error'
          })
          return false; // 中断执行
        }
      })
    }
  }
}
</script>

<style scoped>



.form-login {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 350px;
  height: 310px;
  transform: translate(-50%, -50%);
  background-color: #e3b788;
  box-shadow: 5px 5px 10px #b3d4fc;
  border-radius: 20px;



}

.centered {
  display: flex;
  flex-direction: column;
  align-items: center;
}


</style>
