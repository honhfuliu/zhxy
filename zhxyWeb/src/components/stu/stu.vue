<template>
  <div style="width: 100%; height: 95%; background-color: #f7f2f2; border-radius: 10px">
    <!--header start-->
    <div style="height: 60px; margin-bottom: 15px">
      <h3 style="float: left; margin-left: 10px">
        <i class="icon iconfont icon-xueshengguanli"/>
        学生管理
      </h3>
      <div  style="display: flex; justify-content: center; align-items: center; float: right; height: 70px">
        <el-row>
          <el-col :span="10">
            <el-input placeholder="学生名称搜索(回车)" v-model="queryData.name" @keyup.enter.native="searchGetStudent"></el-input>
          </el-col>

          <el-col :span="5" style="margin-left: 15px">
            <el-button @click="add">学生添加</el-button>
          </el-col>

          <el-col :span="5" style="margin-left: 15px; margin-top: 11px">
            <i class="el-icon-refresh-right" @click="refresh()"/>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--header end-->

    <div class="divider"></div>
    <!--body start-->
    <div style="margin-top: 15px ">
      <el-table
        :header-cell-style="{ fontSize: '15px', background: '#60c1b9', color: 'white', textAlign: 'center'}"
        style="width: 100%"
        :data="studentList"
      >
        <el-table-column label="序号" align="center" width="80" type="index" :index="Nindex"></el-table-column>
        <el-table-column label="id" align="center" width="70" prop="sid"></el-table-column>
        <el-table-column label="学生名称" align="center" prop="sname"></el-table-column>
        <el-table-column label="账号名称" align="center" prop="username"></el-table-column>
        <el-table-column label="手机号" align="center" prop="phone"></el-table-column>
        <el-table-column label="邮箱" align="center" prop="email"></el-table-column>
        <el-table-column label="性别" align="center" width="80">
          <template #default="scope">
            <span v-if="scope.row.gender == 0">女</span>
            <span v-if="scope.row.gender == 1">男</span>
          </template>
        </el-table-column>
        <el-table-column label="班级" align="center" prop="className"></el-table-column>
        <el-table-column label="年级" align="center" prop="gname"></el-table-column>
        <el-table-column label="入学时间" align="center" prop="enrollmentDate"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template #default="scope">
            <el-button @click="updateStudent(scope.row.sid)">修改</el-button>
            <el-button @click="deleteStudent(scope.row.sid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页start-->
      <el-pagination
        :total="this.total"
        :page-size="queryData.PageSize"
        :current-page="queryData.PageNo"
        layout="total,sizes, prev, pager, next, jumper"
        :page-sizes="[5,10,15]"
        @current-change="currentChange"
        @size-change="sizeChange"
      />
      <!--分页end-->

      <!--新增弹框start-->
      <el-dialog :visible.sync="dialogVisible"  width="30%">
        <el-form :model="addStudentData" ref="stu" :rules="rules">
          <el-form-item prop="sname">
            <el-input placeholder="请输入学生名称" v-model="addStudentData.sname"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="username">
            <el-input placeholder="请输入学生账号" v-model="addStudentData.username"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="enrollmentDate">
            <el-date-picker
              style="width: 100%"
              v-model="addStudentData.enrollmentDate"
              format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="入学时间">
            </el-date-picker>
          </el-form-item>

          <el-form-item prop="gradeId">
            <el-select placeholder="请选择年级" v-model="addStudentData.gradeId" style="width: 100%" @focus="getGradeInfo">
              <el-option
                v-for="item in gradeSelectOptions"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item prop="classId">
            <el-select placeholder="请选择班级" v-model="addStudentData.classId" style="width: 100%" @focus="getClassInfo">
              <el-option
                v-for="item in classSelectOption"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>

        <div slot="footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="ConditionalOperation">确 定</el-button>
        </div>
      </el-dialog>
      <!--新增弹框end-->
    </div>
    <!--body end-->
  </div>
</template>

<script>
export default {
  name: "stu",
  data() {
    return{
      // 表单验证
      rules: {
        sname: [
          { required: true, message: "请输入学生名称", trigger: "blur" },
        ],
        username: [
          { required: true, message: "请输入账号名称", trigger: "blur" },
        ],
        enrollmentDate: [
          { required: true, message: "请选择时间", trigger: "blur" },
        ],
        classId: [
          { required: true, message: "请选择班级", trigger: "blur" },
        ],
        gradeId: [
          { required: true, message: "请选择年级", trigger: "blur" },
        ],
      },




      // 新增学生下拉框中年级信息获取
      gradeSelectOptions: [],

      // 新增学生下拉框中班级信息获取
      classSelectOption: [],

      // 新增数据保存
      addStudentData:{
        sid: null,
        sname: null,
        username: null,
        enrollmentDate: null,
        classId: null,
        gradeId: null
      },
       // 修改时旧数据存储
      cid: null,
      gid: null,

      // 弹框控制
      dialogVisible: false,

      // 学生信息存储列表
      studentList: [],

    // 分页数据存储
      queryData: {
        name: "",
        PageSize: 5,
        PageNo: 1,
      },
      // 条数
      total: 0,
    }
  },

  created() {
    this.getStudentInfo()
  },

  methods:{
    // 新增按钮
    add(){
      if (sessionStorage.getItem("role") === "STUDENT"){
        alert("你无权限操作")
        return false;
      }
      this.dialogVisible = true
    },

    // 修改学生信息获取
    updateStudent(id) {
      if (sessionStorage.getItem("role") === "STUDENT"){
        alert("你无权限操作")
        return false;
      }
      this.dialogVisible = true
      this.$axios({
        url: `/stu/${id}`,
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.addStudentData.sid = msg.data.data.sid
          this.addStudentData.username = msg.data.data.username
          this.addStudentData.sname = msg.data.data.sname
          this.addStudentData.enrollmentDate = msg.data.data.enrollmentDate
          this.addStudentData.classId = msg.data.data.className
          this.addStudentData.gradeId = msg.data.data.gradeName

          this.gid = msg.data.data.gradeId
          this.cid = msg.data.data.classId
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 删除功能实现
    deleteStudent(id){
      this.$axios({
        url: `/stu/${id}`,
        method: "delete",
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

    //判断是新增还是修改
    ConditionalOperation(){
      if (this.addStudentData.sid !== null) {
        this.updateStudentInfoApi()
      } else {
        this.addStudentApi()
      }
    },

    // 更新学生信息
    updateStudentInfoApi() {
      this.$refs.stu.validate((valid) => {
        if (valid) {
          if (typeof this.addStudentData.classId === "string" || typeof this.addStudentData.gradeId === "string"){
            this.addStudentData.classId = this.cid
            this.addStudentData.gradeId = this.gid
          }
          this.$axios({
            url: "/stu",
            method: "put",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addStudentData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.close()
            }else {
              alert(msg.data.message)
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

    // 新增学生实现
    addStudentApi() {
      this.$refs.stu.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/stu",
            method: "post",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addStudentData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.close()
            }else {
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

    //取消
    close() {
      this.dialogVisible = false
      this.refresh()
    },

    // 班级id和名称获取
    getClassInfo(){
      if (this.addStudentData.gradeId === null) {
        return false;
      }
      this.$axios({
        url: `/class/add/${this.addStudentData.gradeId}`,
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.classSelectOption = msg.data.data
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 年级id和名称获取
    getGradeInfo() {
      this.$axios({
        url: "/grade/all",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.gradeSelectOptions = msg.data.data
        } else {
          alert(msg.data.message)
        }
      })
    },

    //序号
    Nindex(index){
      const page = this.queryData.PageNo
      const size= this.queryData.PageSize
      return index + 1 + (page - 1) * size
    },

    // 搜素请求指定学生信息
    searchGetStudent(){
      this.queryData.PageNo = 1;
      this.$axios({
        url:"stu",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.studentList = msg.data.data.list
          this.total = msg.data.data.total
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 发送请求获取学生信息
    getStudentInfo() {
      this.$axios({
        url:"stu",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.studentList = msg.data.data.list
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

    // 页数改变时域分页数据同步
    currentChange(page) {
      this.queryData.PageNo = page
      this.getStudentInfo()
    },

    // 每页数据量发生变换事与分页数据中的参数同步
    sizeChange(size) {
      this.queryData.PageSize = size
      this.getStudentInfo()
    }

  },
}
</script>

<style scoped>

.divider{
  width: 100%;
  height: 1px;
  border-top: solid #6cd2c3 1px;
}

</style>
