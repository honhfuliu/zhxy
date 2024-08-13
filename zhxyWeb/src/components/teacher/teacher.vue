<template>
  <div style="height: 95%; width: 100%; background-color: #f7f2f2; border-radius: 10px">
    <!--头部start-->
    <div>
      <div style="height: 60px; margin-bottom: 10px">
        <h3 style="float: left; margin-left: 10px">
          <i class="icon iconfont icon-jiaoshishezhi"/>
          教师管理
        </h3>
        <div style="display: flex; justify-content: center; align-items: center; float: right; height: 70px">
          <el-row>
            <el-col :span="10">
              <el-input placeholder="教师名称(回车)" v-model="queryData.name" @keyup.enter.native="searchGetTeacher"></el-input>
            </el-col>

            <el-col :span="5" style="margin-left: 15px">
              <el-button @click="add">教师添加</el-button>
            </el-col>

            <el-col :span="5" style="margin-left: 15px;  margin-top: 11px">
              <i class="el-icon-refresh-right" @click="refresh()"/>
            </el-col>

          </el-row>
        </div>
      </div>
    </div>
    <!--头部end-->
    <div class="divider"></div>
    <!--body start-->
    <div style="margin-top: 15px">
      <el-table
        :header-cell-style="{ fontSize: '15px', background: '#60c1b9', color: 'white', textAlign: 'center'}"
        style="width: 100%"
        :data="teacherList"
      >
        <el-table-column label="序号" align="center" width="70" type="index" :index="Nindex"></el-table-column>
        <el-table-column label="id" align="center" width="80" prop="tid"></el-table-column>
        <el-table-column label="教学科目" align="center" prop="course"></el-table-column>
        <el-table-column label="用户名" align="center" prop="username"></el-table-column>
        <el-table-column label="教师名称" align="center" prop="tname"></el-table-column>
        <el-table-column label="入职时间" align="center" prop="entryTime"></el-table-column>
        <el-table-column label="学历" align="center" prop="degree"></el-table-column>
        <el-table-column label="邮箱" align="center" prop="email"></el-table-column>
        <el-table-column label="手机号" align="center" prop="phone"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template #default="scope">
            <el-button @click="getByIdDelete(scope.row.tid)">删除</el-button>
            <el-button @click="getByIdUpdate(scope.row.tid)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页start-->
      <el-pagination
        :total="total"
        :page-size="queryData.PageSize"
        :current-page="queryData.PageNo"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[5,10,15]"
        @current-change="currentChange"
        @size-change="sizeChange"></el-pagination>
      <!--分页end-->

      <!--新增start-->
      <el-dialog :visible.sync="dialogVisible" width="30%">
        <el-form :model="addTeacherData" ref="teacher" :rules="rules">
          <el-form-item prop="tname">
            <el-input placeholder="教师名称" v-model="addTeacherData.tname"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="username">
            <el-input placeholder="用户名称" v-model="addTeacherData.username"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="course">
            <el-input placeholder="教学科目" v-model="addTeacherData.course"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="degree">
            <el-select placeholder="学历" v-model="addTeacherData.degree" style="width: 100%">
              <el-option
                v-for="i in degreeSelectOptions"
                :key="i.label"
                :label="i.label"
                :value="i.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item prop="entryTime">
            <el-date-picker
              style="width: 100%"
              v-model="addTeacherData.entryTime"
              format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="入职时间">
            </el-date-picker>
          </el-form-item>

        </el-form>
        <div slot="footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="ConditionalOperation()">确 定</el-button>
        </div>
      </el-dialog>
      <!--新增end-->
    </div>
    <!--body end-->

  </div>
</template>

<script>
export default {
  name: "teacher",
  data(){
    return{
      // 表单框验证
      rules: {
        tname: [
          { required: true, message: "请输入教师名称", trigger: "blur" },
        ],
        course: [
          { required: true, message: "请输入教学科目", trigger: "blur" },
        ],
        username: [
          { required: true, message: "请输入用户名称", trigger: "blur" },
        ],
        entryTime: [
          { required: true, message: "请输入创建时间", trigger: "blur" },
        ],
        degree:[
          { required: true, message: "请输入学历", trigger: "blur" },
        ]


      },

      // 学历选择框
      degreeSelectOptions: [{
        value: '专科',
        label: '专科'
      }, {
        value: '本科',
        label: '本科'
      }, {
        value: '研究生',
        label: '研究生'
      }, {
        value: '硕士',
        label: '硕士'
      }, {
        value: '博士',
        label: '博士'
      }],

      // 新增老师信息存储
      addTeacherData:{
        tid: null,
        course: "",
        username:"",
        tname:"",
        entryTime: null,
        degree: "",
      },

      // 新增弹框开关控制
      dialogVisible: false,


      // 老师信息获取存储
      teacherList: [],

      // 分页条件
      queryData:{
        name: "",
        PageSize: 5,
        PageNo: 1
      },
      total: 0,
    }
  },
  created() {
    this.getTeacherInfo()
  },
  methods: {
    add(){
      if (sessionStorage.getItem("role") !== "ADMIN") {
        alert("你无权进行操作")
        return false;
      }
      this.dialogVisible = true
    },

    // 关闭实现
    close() {
      this.dialogVisible = false
      this.refresh()
    },

    // 修改教师信息获取
    getByIdUpdate(id) {
      if (sessionStorage.getItem("role") !== "ADMIN"){
        alert("你无权操作")
        return false;
      }
      this.$axios({
        url: `/teacher/${id}`,
        method:"get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.dialogVisible = true
          this.addTeacherData.tid = msg.data.data.tid
          this.addTeacherData.degree = msg.data.data.degree
          this.addTeacherData.course = msg.data.data.course
          this.addTeacherData.username = msg.data.data.username
          this.addTeacherData.entryTime = msg.data.data.entryTime
          this.addTeacherData.tname = msg.data.data.tname

        } else {
          alert(msg.data.message)
        }
      })
    },

    // 删除
    getByIdDelete(id){
      this.$axios({
        url: `/teacher/${id}`,
        method:"delete",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          alert(msg.data.message)
          this.refresh()
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 新增老师数据提交
    addTeacher() {
      this.$refs.teacher.validate((valid) =>{
        if (valid) {
          this.$axios({
            url: "/teacher",
            method: "post",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addTeacherData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.refresh()
              this.dialogVisible = false
            } else {
              alert(msg.data.message)
              this.refresh()
            }
          })
        } else {
          this.$message({
            showClose: true,
            message: 'nmd是不是瞎，还有没有填写完的数据是没有看见吗！',
            type: 'error'
          })
          return false; // 中断执行
        }
      })
    },

    // 修改老师信息
    updateTeacher(){
      this.$refs.teacher.validate((valid) =>{
        if (valid){
          this.$axios({
            url :"/teacher",
            method: "put",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addTeacherData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.dialogVisible = false
              this.refresh()
            } else {
              alert(msg.data.message)
              this.refresh()
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

    // 新增提交还是修改提交判断
    ConditionalOperation(){
      if (this.addTeacherData.tid !== null){
        this.updateTeacher()
      } else {
        this.addTeacher()
      }
    },

    // 序号实现
    Nindex(index) {
      const page = this.queryData.PageNo
      const size= this.queryData.PageSize
      return index + 1 + (page - 1) * size
    },

    // 搜索获取信息
    searchGetTeacher(){
      this.queryData.PageNo = 1
      this.$axios({
        url: "/teacher",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.teacherList = msg.data.data.list
          this.total = msg.data.data.total
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 请求信息获取
    getTeacherInfo(){
      // console.log(this.queryData)
      // if (this.queryData.name !== "") {
      //   this.queryData.PageNo = 1
      //   this.queryData.PageSize = 5
      // }
      this.$axios({
        url: "/teacher",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.teacherList = msg.data.data.list
          this.total = msg.data.data.total
        } else {
          alert(msg.data.message)
        }
      })
    },


    // 刷新
    refresh(){
      location.reload()
    },

    // 捕获页数切换之后将其赋值给queryDto的相关属性。
    currentChange(page){
      this.queryData.PageNo = page;
      this.getTeacherInfo()
    },

    // 捕获页面显示总数，之后将其赋值给queryDto中的相关属性
    sizeChange(size){
      this.queryData.PageSize = size;
      this.getTeacherInfo()
    },
  }

}
</script>

<style scoped>
.divider{
  width: 100%;
  height: 1px;
  border-top: solid #6cd2c3 1px;
}

</style>
