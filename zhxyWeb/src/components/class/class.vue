<template>
  <div style="height: 95%; background-color: #f7f2f2; padding: 0px; border-radius: 10px">
    <!--头部start-->
    <div style="height: 60px; margin-bottom: 15px">
      <div style="width: 100%;">
        <h3 style="float: left; margin-left: 10px">
          <i class="icon iconfont icon-banjiguanli"/>
          班级管理
        </h3>

        <!--搜索 添加 刷新start-->
        <div style="display: flex; justify-content: center; align-items: center; float: right; height: 70px">
          <el-row>
            <el-col :span="10">
              <el-input placeholder="搜索班级名称(回车)" v-model="queryData.name"  @keyup.enter.native="searchGetClassInfo"></el-input>
            </el-col>

            <el-col :span="5" style="margin-left: 20px">
              <el-button @click="addClass()" >添加班级</el-button>
            </el-col>

            <el-col :span="5" style="margin-left: 20px; margin-top: 11px">
              <i class="el-icon-refresh-right" @click="refresh()"/>
            </el-col>
          </el-row>
        </div>
        <!--搜索 添加 刷新start-->
      </div>

    </div>

    <!--头部end-->
    <div class="divider"></div>

    <!--body start-->
    <div style="margin-top: 15px">
      <el-table
        :header-cell-style="{ fontSize: '15px', background: '#60c1b9', color: 'white', textAlign: 'center'}"
        style="width: 100%"
        :data="ClassList"
      >
        <el-table-column label="序号" align="center" width="80" type="index" :index="Nindex"></el-table-column>
        <el-table-column label="id" align="center" width="80" prop="cid"></el-table-column>
        <el-table-column label="班级名称" align="center" prop="className"></el-table-column>
        <el-table-column label="班主任" align="center" prop="tname"></el-table-column>
        <el-table-column label="学期" align="center" prop="classTerm"></el-table-column>
        <el-table-column label="所属年级" align="center" prop="gname"></el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button @click="deleteClass(scope.row.cid)">删除</el-button>
            <el-button @click="updateClassInfo(scope.row.cid)">修改</el-button>
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

      <!--新增弹框start-->
      <el-dialog :visible.sync="dialogVisible"  width="30%">
        <el-form :model="addClassData" :rules="rules" ref="class">
          <el-form-item prop="className">
            <el-input placeholder="请输入班级名称" v-model="addClassData.className"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="classTerm">
            <el-input placeholder="学期" v-model="addClassData.classTerm"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="headTeacherId">
            <el-select placeholder="班主任" v-model="addClassData.headTeacherId" style="width: 100%">
              <el-option
                v-for="item in teacherSelectOptions"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="gid">
            <el-select placeholder="年级选择" v-model="addClassData.gid" style="width: 100%">
              <el-option
                v-for="item in gradeSelectOptions"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

        </el-form>

        <div slot="footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="ConditionalOperation()">确 定</el-button>
        </div>
      </el-dialog>
      <!--新增弹框end-->
    </div>
    <!--body end-->
  </div>
</template>

<script>
export default {
  name: "class",
  data() {
    return{
      //表单验证
      rules: {
        className: [
          { required: true, message: "请输入班级名称", trigger: "blur" },
        ],
        classTerm: [
          { required: true, message: "请输入学期", trigger: "blur" },
        ],
        gid: [
          { required: true, message: "请选择年级", trigger: "blur" },
        ],
        headTeacherId:[
          { required: true, message: "请选择班主任", trigger: "blur" },
        ],

      },

      // 新增弹框数据存储
      addClassData: {
        cid: null,
        className: "",
        headTeacherId: null,
        classTerm: "",
        gid: ""
      },

      // 新增年级下拉框中信息获取
      gradeSelectOptions: [],

      // 新增老师下拉框中信息获取
      teacherSelectOptions: [],
      // 新增弹框控制
      dialogVisible: false,

      //班级信息获取存储列表
      ClassList: [],

      // 分页数据
      queryData: {
        name: "",
        PageNo: 1,
        PageSize: 5,
      },
      total: 0,
    }
  },

  created() {
    this.getClassInfo()
  },

  methods: {

    // 关闭实现
    close() {
      this.dialogVisible = false
      this.refresh()
    },
    // 提交新增数据方法
    ConditionalOperation(){
      if (this.addClassData.cid !== null) {
        this.updateClassSubmit()
      } else {
        this.submitClass()
      }
    },

    // 修改班级信息提交
    updateClassSubmit(){
      this.$refs.class.validate((valid) => {
        if (valid) {
          console.log(this.addClassData)
          this.$axios({
            url: "/class",
            method: "put",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addClassData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.dialogVisible = false;
              this.refresh()
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

    // 新增班级
    submitClass() {
      this.$refs.class.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/class",
            method: "post",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.addClassData
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

    // 修改班级信息获取
    updateClassInfo(id) {
      if (sessionStorage.getItem("role") === "STUDENT") {
        alert("你无权操作")
        return false;
      }
      this.dialogVisible = true;
      this.$axios({
        url:`/class/${id}`,
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          console.log(msg.data.data)
          this.addClassData.gid = msg.data.data.gid
          this.addClassData.classTerm = msg.data.data.classTerm
          this.addClassData.cid = msg.data.data.cid
          this.addClassData.className = msg.data.data.className
          this.addClassData.headTeacherId = msg.data.data.headTeacherId
        } else {
          alert(msg.data.message)
        }
      })
      this.getAllSelectGrade()
      this.getAllSelectTeacher()


    },


    // 删除方法
    deleteClass(id) {
      if (sessionStorage.getItem("role") !== "ADMIN"){
        alert("你无权操作")
        return false;
      }
      this.$axios({
        url: `/class/${id}`,
        method: "delete",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          alert(msg.data.message)
          this.refresh()
        }else {
          alert(msg.data.message)
        }
      })
    },


    // 新增弹框的控制和其它操作
    addClass() {
      if (sessionStorage.getItem("role") === "STUDENT"){
        alert("你无权操作")
        return false;
      }
      this.dialogVisible = true
      this.getAllSelectGrade()
      this.getAllSelectTeacher()
    },

    // 请求获取新增年级下拉框中信息获取
    getAllSelectGrade(){
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

    // 请求获取新增班主任下拉框中信息获取
    getAllSelectTeacher() {
      this.$axios({
        url: "/teacher/all",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.teacherSelectOptions = msg.data.data
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 序号实现
    Nindex(index){
      const page = this.queryData.PageNo
      const size= this.queryData.PageSize
      return index + 1 + (page - 1) * size
    },

    // 刷新
    refresh(){
      location.reload()
    },

    // 分页获取班级信息
    getClassInfo(){
      // console.log(this.queryData)
      this.$axios({
        url: "/class",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.ClassList = msg.data.data.list;
          this.total = msg.data.data.total;
        } else {
          alert(msg.data.message)
        }
      })
    },
    // 搜索分页
    searchGetClassInfo() {
        this.queryData.PageNo = 1
      this.$axios({
        url: "/class",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryData
      }).then(msg => {
        if (msg.data.state === 200) {
          this.ClassList = msg.data.data.list;
          this.total = msg.data.data.total;
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 捕获页数切换之后将其赋值给queryDto的相关属性。
    currentChange(page) {
      // console.log(page)
      this.queryData.PageNo = page;
      this.getClassInfo()
    },

    // 捕获页面显示总数，之后将其赋值给queryDto中的相关属性
    sizeChange(size){
      this.queryData.PageSize = size;
      this.getClassInfo()
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
