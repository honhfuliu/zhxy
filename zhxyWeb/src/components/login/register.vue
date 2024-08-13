<template>
  <div class="form-login">
    <el-form class="centered" ref="my_login" :model="loginData" :rules="rules">
      <h2>注册</h2>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginData.username" style="width: 300px" prefix-icon="icon iconfont icon-yonghu" placeholder="请输入用户名"
                  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginData.password" style="width: 300px" prefix-icon="icon iconfont icon-mima" placeholder="请输密码"
                  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password1">
        <el-input type="password" v-model="loginData.password1" style="width: 300px" prefix-icon="icon iconfont icon-mima" placeholder="再次输入密码"
                  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
        ></el-input>
      </el-form-item>
      <el-form-item style="margin-top: -15px" prop="role">
        <el-radio v-model="loginData.role" label="TEACHER" border="true">老师</el-radio>
        <el-radio v-model="loginData.role" label="STUDENT" border="true">学生</el-radio>
      </el-form-item>
      <el-form-item class="centered" style="margin: 0 auto; margin-top: -1px">
        <el-button style="width: 200px" @click="submitRegister()">注册</el-button>
      </el-form-item>
      <router-link class="router-link" to="/">已有账号？</router-link>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      loginData: {
        username: "",
        password: "",
        password1: "",
        role: ""
      },


      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 6, max: 18, message: "长度在6-18个字符之间", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 25, message: "长度在6-25个字符之间", trigger: "blur" }
        ],
        password1: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          { min: 6, max: 25, message: "长度在6-25个字符之间", trigger: "blur"},
          {  validator: this.passwordValidation, required: true }
        ],
        role: [{ required: true, message: "请选择角色", trigger: "change" }]
      },

    }
  },

  methods: {
    submitRegister() {
      // console.log(this.loginData);
      this.$refs.my_login.validate((valid) => {
        if (valid) {
          const requestData = Object.assign({}, this.loginData);
          delete requestData.password1;
          this.$axios({
            url: "user/reg",
            method: "post",
            data: requestData,
          }).then(reg => {
            console.log(reg.status);
            console.log(reg.message);
            if (reg.data.state === 200) {
              alert(reg.data.message)
              location.reload()
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
    },

    passwordValidation(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.loginData.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }



  },


}
</script>

<style scoped>
.form-login {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 350px;
  height: 380px;
  transform: translate(-50%, -50%);
  background-color: #ADD8E6;
  box-shadow: 5px 5px 10px #7e858d;
  border-radius: 20px;


}

.centered {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.router-link {
  margin-left: 230px;
  margin-top: 10px;
}

</style>
