<template>
  <div style="height: 95%; background-color: #f7f2f2; padding: 0; border-radius: 10px">
    <!--第一行start-->
    <div style="margin-bottom:15px">
      <div style="width: 100%; height: 60px">
        <h3 style="float: left; margin-left: 10px">
          <i class="icon iconfont icon-guanliyuan_jiaoseguanli"/>
          管理员管理
        </h3>

        <!--搜索 添加 刷新start-->
        <div style="display: flex; justify-content: center; align-items: center; float: right; height: 70px">
          <el-row>
            <el-col :span="10">
              <el-input placeholder="用户名搜索(回车)" v-model="queryDto.name" @keyup.enter.native="searchGetAdmin"></el-input>
            </el-col>
            <el-col :span="5" style="margin-left: 20px">
              <el-button @click="dialogVisible = true">添加管理员</el-button>
            </el-col>

            <el-col :span="5" style="margin-left: 20px">
              <i class="el-icon-refresh-right" @click="refresh()"/>
            </el-col>
          </el-row>
        </div>
        <!--搜索 添加 刷新end-->
      </div>

    </div>

    <div class="divider"></div>
    <br>
    <!--第一行end-->
    <div>
      <!--表格start-->
      <el-table class="table_c"
                :data="adminList"
                :header-cell-style="{ fontSize: '15px', background: '#60c1b9', color: 'white', textAlign: 'center'}"
                style="width: 100%"
      >
        <el-table-column label="序号" align="center" width="80" type="index" :index="indexMethod"></el-table-column>
        <el-table-column label="创建时间" prop="createTime" align="center"></el-table-column>
        <el-table-column label="id"  prop="uid" align="center"></el-table-column>
        <el-table-column label="名称" prop="username" align="center"></el-table-column>
        <el-table-column label="手机号" prop="phone" align="center"></el-table-column>
        <el-table-column label="邮箱" prop="email" align="center"></el-table-column>
        <el-table-column label="性别" prop="gender" align="center">
          <template #default="scope">
            <span v-if="scope.row.gender == 0">女</span>
            <span v-if="scope.row.gender == 1">男</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="isDelete" align="center">
          <template #default="scope">
            <span v-if="scope.row.isDelete == 0">正常</span>
            <span v-if="scope.row.isDelete == 1">删除</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button @click="deleteId(scope.row.uid)">删除</el-button>
            <el-button @click="GetByIdSelectUpdateAdmin(scope.row.uid)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--表格end-->
      <el-pagination
        :total="this.total"
        :page-size="queryDto.PageSize"
        :current-page="queryDto.PageNo"
        layout="total,sizes, prev, pager, next, jumper"
        :page-sizes="[5,10,15]"
        @current-change="currentChange"
        @size-change="sizeChange"
      ></el-pagination>
    </div>

    <!--新增start-->
    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
      title="管理员添加"
      >
      <div>
        <el-form label-width="70px" style="width: 70%; margin-left: 25px" :model="AdminData" ref="admin" :rules="rules">
          <el-form-item prop="username">
            <el-input v-model="AdminData.username" type="text" placeholder="请输入管理员用户名" prefix-icon="icon iconfont icon-yonghu"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            > </el-input>
          </el-form-item >
          <el-form-item prop="password">
            <el-input v-model="AdminData.password" type="password" placeholder="请输密码" prefix-icon="icon iconfont icon-mimaxiugai"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            > </el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="AdminData.phone" type="text" placeholder="手机号" prefix-icon="icon iconfont icon-shoujihao"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            > </el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="AdminData.email" type="text" placeholder="邮箱" prefix-icon="icon iconfont icon-youxiang"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            > </el-input>
          </el-form-item>
          <el-form-item>
            <el-radio v-model="AdminData.gender" label="1" border>男</el-radio>
            <el-radio v-model="AdminData.gender" label="0" border>女</el-radio>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="ConditionalOperation()">确 定</el-button>
      </div>
    </el-dialog>
    <!--新增end-->
  </div>
</template>

<script>


export default {
  name: "admin",
  data() {
    return{
      // 管理员新增信息存储
      AdminData: {
        uid: null,
        username: "",
        password: "",
        phone: "",
        email: "",
        gender: null,

      },
      // 新增弹框控制
      dialogVisible: false,

      // 管理员信息存储列表
      adminList: [],

      queryDto: {
        name: "",
        PageNo: 1,
        PageSize: 5,
      },
      total: 0,

      // 表单验证
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 5, max: 18, message: "长度在6-18个字符之间", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 25, message: "长度在6-25个字符之间", trigger: "blur" }
        ],

      }
    }

  },

  // 进入到这个页面之后加载出数据
  created() {
    this.query();
  },

  methods: {
    // 关闭实现
    close() {
      this.dialogVisible = false
      this.refresh()
    },

    //索引实现
    indexMethod(index) {
      const page = this.queryDto.PageNo
      const size= this.queryDto.PageSize
      return index + 1 + (page - 1) * size
    },

    // 判断是添加管理员还是更新管理员信息
    ConditionalOperation() {
      if (this.AdminData.uid !== null) {
        this.updateAdmin();
      } else {
        this.SaveAdmin();
      }
    },

    // 更新管理员操作
    updateAdmin(){
      this.$refs.admin.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/admin",
            method: "put",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.AdminData,
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.dialogVisible = false
              this.refresh();
            } else {
              alert(msg.data.message)
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

    // 更新管理员信息获取
    GetByIdSelectUpdateAdmin(id){
      this.dialogVisible = true
      this.$axios({
        url: `/admin/${id}`,
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.AdminData.uid = msg.data.data.uid
          this.AdminData.username = msg.data.data.username
          this.AdminData.phone = msg.data.data.phone
          this.AdminData.email = msg.data.data.email
          this.AdminData.gender = msg.data.data.gender
        } else {
          alert(msg.data.message)
        }
      })

    },

    // 删除管理员
    deleteId(id) {
      console.log(`/admin/${id}`)
      this.$axios({
        url: `/admin/${id}`,
        method: "delete",
        headers: {Authorization:sessionStorage.getItem("token")},

      }).then(msg => {
          if (msg.data.state === 200) {
            alert(msg.data.message)
            this.refresh();
          } else {
            alert(msg.data.message)
          }
      })
    },

    // 新增管理员
    SaveAdmin() {
      this.$refs.admin.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/admin",
            method: "post",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.AdminData,
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.refresh();
            } else {
              alert(msg.data.message)
              this.refresh();
            }
          })
          this.dialogVisible = false;
        }else {
          this.$message({
            showClose: true,
            message: 'nmd是不是瞎，还有没有填写完的数据是没有看见吗！',
            type: 'error'
          })
        }
      })
    },

    // 刷新功能实现
    // 刷新
    refresh() {
      location.reload();
    },


    // 捕获页数切换之后将其赋值给queryDto的相关属性。
    currentChange(page) {
      // console.log(page)
      this.queryDto.PageNo = page;
      this.query()
    },

    // 捕获页面显示总数，之后将其赋值给queryDto中的相关属性
    sizeChange(size){
      this.queryDto.PageSize = size;
      this.query()
    },

    // 查询请求发送
    query() {
      this.$axios({
        url: "admin/page",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryDto
      }).then(msg=> {
        if (msg.data.state === 200) {
          this.adminList = msg.data.data.list;
          this.total = msg.data.data.total;
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 搜索查询指定管理员
    searchGetAdmin() {
      this.queryDto.PageNo = 1
      this.$axios({
        url: "admin/page",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryDto
      }).then(msg=> {
        if (msg.data.state === 200) {
          this.adminList = msg.data.data.list;
          this.total = msg.data.data.total;
        } else {
          alert(msg.data.message)
        }
      })
    }




  },
}
</script>

<style scoped>
.el-icon-refresh-right {
  font-size: 20px;
  margin-top: 11px;
}

.divider{
  width: 100%;
  height: 1px;
  border-top: solid #6cd2c3 1px;
}

.table_c {

}
</style>
