<template>

  <div style="display: flex; height: 95%">
    <!--个人信息start-->
    <div style="background-color: #CBF0F9; padding: 0px; margin: 0px; width: 65%">
      <div style="width: 100%; height: 60px">
        <h3 style="float: left; margin-left: 10px">
          <i class="icon iconfont icon-gerenxinxishezhi"/>
          个人信息设置
        </h3>
      </div>

      <div class="divider"></div>

      <!--个人信息修改start-->
      <div style="width: 100% ;height: 60px">
        <h4 style="float: left; margin-left: -130px">
          <i class="icon iconfont icon-gerenxinxishezhi"/>
          个人信息设置
        </h4>
      </div>
      <div style="display: flex; justify-content: center">

        <el-form style="display: flex; margin-bottom: 30px" :model="infoData">
          <el-input
            v-model="infoData.phone"
            type="text"
            prefix-icon="icon iconfont icon-shoujihao"
            style="margin-right: 15px"
            placeholder="手机号"
            onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          />

          <el-input
            v-model="infoData.email"
            type="text"
            prefix-icon="icon iconfont icon-youxiang"
            style="margin-right: 15px"
            placeholder="邮箱"
            onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          />

          <el-radio
            v-model="infoData.gender"
            label="1"
            style="margin-top: 13px"

          >男</el-radio>
          <el-radio
            v-model="infoData.gender"
            label="0"
            style="margin-top: 13px"

          >女</el-radio>

          <el-button @click="saveInfo()">提交</el-button>
          <el-button @click="resetInfo()">重制</el-button>

        </el-form>
      </div>
      <!--个人信息修改end-->

      <div class="divider"></div>

      <!--密码修改start-->
      <div style="width: 100% ;height: 60px">
        <h4 style="float: left; margin-left: 15px">
          <i class="icon iconfont icon-mima"/>
          密码修改
        </h4>
      </div>


      <div style="display: flex; width: 100%; justify-content: left; margin-left: 35px;">

        <el-form style="display: flex" :model="passwordData" id="myform2">
          <el-input
            type="password"
            prefix-icon="icon iconfont icon-mima"
            placeholder="旧密码"
            style="margin-right: 15px"
            v-model="passwordData.oldPassword"
            show-password
            onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          />
          <el-input
            type="password"
            prefix-icon="icon iconfont icon-mima"
            placeholder="新密码"
            style="margin-right: 15px"
            v-model="passwordData.newPassword"
            show-password
            onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,''); this.value=this.value.replace(/[\u4E00-\u9FA5]/g,'')"
          />

          <el-button @click="savePassword()">提交</el-button>
          <el-button @click="myReset()">重制</el-button>
        </el-form>

      </div>
      <div style="text-align: left; margin-left: 35px">
        <p>
          <i class="icon iconfont icon-a-iconzhihuixiaoyuan"/>
          密码6~18为字母、数字、特殊字符组成
        </p>
        <p>
          <i class="icon iconfont icon-a-iconzhihuixiaoyuan"/>
          安全提示：新密码请勿与旧密码过于相似
        </p>
      </div>


      <!--密码修改end-->
      <div class="divider"></div>
      <!--头像上传start-->
      <div>
        <div style="width: 100% ;height: 60px">
          <h4 style="float: left; margin-left: 15px">
            <i class="icon iconfont icon-touxiang_shangchuantouxiang"/>
            头像上传
          </h4>
        </div>
        <!--头像上传start-->
        <div class="update_img">
          <el-upload
            :action="`${img}/user/img`"
            :headers="headersObj"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
          >
            <!--            action="http://localhost:8989/user/img" -->
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </div>
        <!--头像上传end-->
      </div>
      <!--头像上传end-->

    </div>
    <!--个人信息end-->

    <!--个人信息展示start-->
    <div style="background-color: #E1F9F6; padding: 10px; width: 30%; height: 80%;margin-left: 15px">
      <div style="width: 100% ;height: 60px">
        <h4 style="float: left; margin-left: 15px">
          <i class="icon iconfont icon-gerenxinxishezhi"/>
          个人信息展示
        </h4>
        <el-button style="float: right" @click="refresh()">刷新</el-button>
      </div>



      <!--头像和个人信息展示start-->
      <div style="width: 100%;">
        <div style="display: flex">
          <img :src="`${img}/${userInfoList.data.avatar}`"  alt="" class="img_css">
        </div>
        <div style="display: block; margin-bottom: 35px">
          <h2 style="margin-left: 20px">Hi {{userInfoList.data.username}} 管理员你好 ！</h2>
          <span style="display: block; margin-bottom: 5px">手机号： {{userInfoList.data.phone}}</span>
          <span>邮箱是： {{userInfoList.data.email}}</span>
        </div>
      </div>

      <div class="divider"></div>

      <!--账号类型start-->
      <div style="width: 100% ;height: 60px">
        <h4 style="float: left; margin-left: 15px">
          <i class="icon iconfont icon-zhanghaojieshao"/>
          账号类型介绍
        </h4>
      </div>
      <div style="margin-bottom: 20px">
        <br>
        <div style="display: flex">
          <span style="display: block">账号类型分为：管理员、老师、学生, 管理可对本站数据、成绩、系统等进行管理, 普通用户可以进行查询</span>
        </div>
        <br>
        <span>站长邮箱：ziheng870@gmail.com</span>

      </div>
      <div class="divider"></div>
      <!--账号类型end-->



      <!--头像和个人信息展示end-->
    </div>
    <!--个人信息展示end-->

  </div>

</template>


<script>

export default {
  name: "info",
  data(){
    return {
      img: this.$axios.defaults.baseURL,
      // 头像上传方法
      headersObj: {
        Authorization:sessionStorage.getItem("token")
      },
      dialogVisible: false,

      // 信息列表
      infoData: {
        phone: "",
        email: "",
        gender: null,
      },
      // 密码列表
      passwordData: {
        oldPassword: "",
        newPassword: "",
      },

      // 用户信息保存
      userInfoList: [],

    }

  },
  created() {
    this.getUserInfo();
  },

  methods: {
    // 用户信息展示数据获取
    getUserInfo() {
      this.$axios({
        url: "/user",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")}
      }).then(msg => {
        if (msg.data.state === 200) {
          this.userInfoList = msg.data;
        } else {
          alert(msg.data.message)
        }

      })
    },

    // 头像上传方法
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 上传成功处理
    handleSuccess(response,file,fileList){
      console.log(response);
      if (response.state === 200) {
        alert(response.message);
        this.refresh();
      } else {
        alert(response.message);
        this.refresh();
      }
    },

    // 信息提交方法
    saveInfo() {
      if (this.infoData.phone === null || this.infoData.gender === null || this.infoData.email === null) {
        this.$message({
          showClose: true,
          message: 'nmd是不是瞎，个人信息还有没有填写完的数据是没有看见吗！',
          type: 'error'
        })
        return false; // 中断执行
      }
      const reg = /^1[3456789]\d{9}$/
      if (!reg.test(this.infoData.phone)){
        alert("手机格式错误")
        return false;
      }
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(this.infoData.email)){
        alert("邮箱格式错误")
        return false;
      }
      this.$axios({
        url: "user/info",
        method: "put",
        data: this.infoData,
        headers: {Authorization:sessionStorage.getItem("token")}
      }).then(msg => {
        if (msg.data.state === 200) {
          alert("数据更新成功")
          this.refresh()
        } else {
          alert(msg.data.message)
        }
      })

    },

    // 信息重制方法
    resetInfo() {
      this.infoData = {
        phone: "",
        email: "",
        gender: null,
      }
    },

    // 根据ID重置表单中的数据
    myReset() {
      document.getElementById('myform2').reset();
    },

    // 刷新
    refresh() {
      location.reload();
    },

    //密码修改提交方法
    savePassword() {
      if (!this.passwordData.oldPassword || !this.passwordData.newPassword ||
        !this.passwordData.oldPassword.trim() || !this.passwordData.newPassword.trim()) {
        this.$message({
          showClose: true,
          message: '旧密码和新密码不能为空！',
          type: 'error'
        })
        return false;
      } else  {
        this.$axios({
          url: "/user",
          method: "put",
          data: this.passwordData,
          headers: {
            Authorization:sessionStorage.getItem("token")
          }
        }).then(msg => {
          if (msg.data.state === 200) {
            alert(msg.data.message)
            sessionStorage.clear();
            this.$router.push('/');
          } else  {
            alert(msg.data.message)
          }
        })

      }

    }

  }
}
</script>

<style scoped>

.divider{
  width: 100%;
  height: 1px;
  border-top: solid #6cd2c3 1px;
}

.update_img {
  width: 100%;
  display: flex;
  margin-left: 35px;
}

.img_css {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

</style>
