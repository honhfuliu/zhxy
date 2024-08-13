<template>
  <div style="background-color: #f7f2f2; padding: 0px; height: 95%; border-radius: 10px">
    <!--头部start-->
    <div style="height: 60px; margin-bottom: 15px">
      <!--标题start-->
      <div style="width: 100%">
        <h3 style="float: left; margin-left: 10px">
          <i class="icon iconfont icon-nianjiguanli"/>
          年级管理
        </h3>
      </div>
      <!--标题end-->

      <div style="display: flex; justify-content: center; align-items: center; float: right; height: 70px">
        <el-row>
          <el-col :span="10">
            <el-input placeholder="班级名称搜索(回车)" v-model="queryDto.name" @keyup.enter.native="searchGrade()"></el-input>
          </el-col>

          <el-col :span="5" style="margin-left: 15px">
            <el-button @click="add">年级添加</el-button>
          </el-col>

          <el-col :span="5" style="margin-left: 20px; margin-top: 11px">
            <i class="el-icon-refresh-right" @click="refresh()"/>
          </el-col>
        </el-row>
      </div>
    </div>

    <div class="divider"></div>
    <!--头部end-->
    <!--body start-->
    <div style="margin-top: 15px ">
      <el-table
        :header-cell-style="{ fontSize: '15px', background: '#60c1b9', color: 'white', textAlign: 'center'}"
        style="width: 100%"
        :data="GradeList"
      >
        <el-table-column label="序号" width="80" align="center" type="index" :index="indexMethod">
        </el-table-column>
        <el-table-column label="id" width="100" prop="gid" align="center"></el-table-column>
        <el-table-column label="年级名称" prop="gName" align="center"></el-table-column>
        <el-table-column label="开始时间" align="center" prop="startYear"></el-table-column>
        <el-table-column label="结束时间" prop="endYear" align="center"></el-table-column>
        <el-table-column label="状态" prop="isDelete" align="center">
          <template #default="scope">
            <span v-if="scope.row.isDelete == 0">正常</span>
            <span v-if="scope.row.isDelete == 1">删除</span>
          </template>
        </el-table-column>
        <el-table-column label="操作人" prop="modifiedUser" align="center"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="deleteGrade(scope.row.gid)">删除</el-button>
            <el-button @click="updateGrade(scope.row.gid)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页start-->
      <el-pagination
        :total="this.total"
        :page-size="queryDto.PageSize"
        :current-page="queryDto.PageNo"
        layout="total,sizes, prev, pager, next, jumper"
        :page-sizes="[5,10,15]"
        @current-change="currentChange"
        @size-change="sizeChange"
      ></el-pagination>
      <!--分页end-->
    </div>

    <!--新增 start-->
    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div>
        <el-form :model="AddGradeData" ref="grade" :rules="rules">
          <el-form-item prop="gName">
            <el-input placeholder="班级名称" v-model="AddGradeData.gName"
                      onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g,'')"
            ></el-input>
          </el-form-item>

          <el-form-item prop="startYear">
            <el-date-picker
              style="width: 100%"
              format="yyyy-MM-dd HH:mm:ss"
              v-model="AddGradeData.startYear"
              type="datetime"
              placeholder="开始日期时间">
            </el-date-picker>
          </el-form-item>

          <el-form-item prop="endYear">
            <el-date-picker
              style="width: 100%"
              v-model="AddGradeData.endYear"
              type="datetime"
              placeholder="结束日期时间">
            </el-date-picker>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="ConditionalOperation()">确 定</el-button>
      </div>
    </el-dialog>
    <!--新增 end-->
    <!--body end-->
  </div>
</template>

<script>
export default {
  name: "GradeManagement",

  data() {
    return{
      //表单验证规则
      rules: {
        gName: [
          { required: true, message: "请输入年级名称", trigger: "blur" },
        ],
        startYear:[
          { required: true, message: "请选择开始时间", trigger: "blur" },
        ],
        endYear:[
          { required: true, message: "请选择结束时间", trigger: "blur" },
        ]
      },

      //新增信息存储
      AddGradeData: {
        gid: null,
        gName: "",
        startYear: null,
        endYear: null,
      },


      // 新增弹框控制
      dialogVisible: false,

      // 班级信息存储列表
      GradeList: [],

      // 分页
      queryDto: {
        name: "",
        PageNo: 1,
        PageSize: 5,
      },
      total: 0,
    }
  },

  created() {
    this.getGradeList();
  },

  methods: {
    add(){
      if (sessionStorage.getItem("role") !== "ADMIN") {
        alert("你无权进行操作");
        return false;
      }
      this.dialogVisible = true
    },

    // 关闭实现
    close() {
      this.dialogVisible = false
      this.refresh()
    },

    // 序号
    indexMethod(index) {
      const page = this.queryDto.PageNo
      const size= this.queryDto.PageSize
      return index + 1 + (page - 1) * size
    },

    // 修改年级信息获取
    updateGrade(id){
      if (sessionStorage.getItem("role") !== "ADMIN") {
        alert("你无权进行操作");
        return false;
      }
      this.dialogVisible = true
      this.$axios({
        url: `/grade/${id}`,
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
      }).then(msg => {
        if (msg.data.state === 200) {
          this.AddGradeData.gid = msg.data.data.gid
          this.AddGradeData.gName = msg.data.data.gName
          this.AddGradeData.startYear = msg.data.data.startYear
          this.AddGradeData.endYear = msg.data.data.endYear
          console.log(this.AddGradeData)
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 修改年级信息
    getByIdUpdateGrade(){
      this.$refs.grade.validate((valid) => {
        if (valid) {
          this.$axios({
            url :"/grade",
            method: "put",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.AddGradeData
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

    // 删除方法
    deleteGrade(id) {
      this.$axios({
        url: `/grade/${id}`,
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

    // 新增方法
    AddGrade(){
      this.$refs.grade.validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/grade",
            method: "post",
            headers: {Authorization:sessionStorage.getItem("token")},
            data: this.AddGradeData
          }).then(msg => {
            if (msg.data.state === 200) {
              alert(msg.data.message)
              this.dialogVisible = false
              this.refresh()
            }else {
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

    //新增更新操作判断
    ConditionalOperation(){
      if (this.AddGradeData.gid !== null) {
        this.getByIdUpdateGrade()

      }else {
        this.AddGrade()
      }
    },

    // 刷新
    refresh() {
      location.reload();
    },

    // 捕获页数切换之后将其赋值给queryDto的相关属性。
    currentChange(page) {
      // console.log(page)
      this.queryDto.PageNo = page;
      this.getGradeList()
    },

    // 捕获页面显示总数，之后将其赋值给queryDto中的相关属性
    sizeChange(size){
      this.queryDto.PageSize = size;
      this.getGradeList()
    },

    // 班级信息获取方法
    getGradeList() {
      this.$axios({
        url: "grade",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryDto
      }).then(msg => {
        if (msg.data.state ===200) {
          this.GradeList = msg.data.data.list;
          this.total = msg.data.data.total;
        } else {
          alert(msg.data.message)
        }
      })
    },

    // 搜索获取班级信息
    searchGrade(){
      this.queryDto.PageNo = 1
      this.$axios({
        url: "grade",
        method: "get",
        headers: {Authorization:sessionStorage.getItem("token")},
        params: this.queryDto
      }).then(msg => {
        if (msg.data.state ===200) {
          this.GradeList = msg.data.data.list;
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
.divider{
  width: 100%;
  height: 1px;
  border-top: solid #6cd2c3 1px;
}

</style>
