<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入学号" suffix-icon="el-icon-search" v-model="studentid"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" class="ml-5" suffix-icon="el-icon-message" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon-color="#626AEF"
          title="是否批量删除?"
          @confirm="delBatch"
          @cancel="cancelEvent"
      >
        <el-button type="danger" slot="reference">批量删除<i class = "el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://124.71.166.37:9090/student/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入<i class = "el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--          多选框-->
      <el-table-column type="selection" width="55" />


      <el-table-column prop="studentid" label="学号" width="200">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="200">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="120">
      </el-table-column>
      <el-table-column prop="mobilephone" label="电话" width="200">
      </el-table-column>
      <el-table-column prop="sprofession" label="专业" width="200">
      </el-table-column>
      <el-table-column label="操作"width="263">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确认"
              cancel-button-text="取消"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="是否删除?"
              @confirm="del(scope.row.studentid)"
              @cancel="cancelEvent"
          >
            <template #reference>
              <el-button type="danger">删除<i class="el-icon-remove-outline"></i></el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0" >
      <el-pagination
          v-model:current-page="currentPage4"
          v-model:page-size="pageSize4"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

    </div>
    <!--        “编辑”弹窗-->
    <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="学号">
          <el-input v-model="form.studentid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.mobilephone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.sprofession" autocomplete="off" />
        </el-form-item>
        <el-form-item label="账户">
          <el-input v-model="form.logn" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.pswd" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="update">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>
      <!--        “新建”弹窗-->
      <el-dialog title="学生信息" :visible.sync="dialogFormVisible2" width="30%" >
        <el-form label-width="80px" size="small">
          <el-form-item label="学号">
            <el-input v-model="form.studentid" autocomplete="off" />
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.sex" autocomplete="off" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="form.mobilephone" autocomplete="off" />
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="form.sprofession" autocomplete="off" />
          </el-form-item>
          <el-form-item label="账户">
            <el-input v-model="form.logn" autocomplete="off" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.pswd" autocomplete="off" />
          </el-form-item>
        </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      studentid: "",
      name: "",
      sex: "",
      dataofbirth: "",
      mobilephone:"",
      Status:"",
      logn:"",
      pswd:"",
      Sprofession:"",
      age:"",
      nativeplace:"",
      dialogFormVisible: false,
      dialogFormVisible2:false,
      multipleSelection: [],
      form: {},
      headerBg: 'headerBg'

    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/student/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          studentid:this.studentid,
          name:this.name,
          sex: this.sex,
          mobilephone:this.mobilephone,
          logn:this.logn,
          pswd:this.pswd,
          Sprofession:this.Sprofession,
          age:this.age,
          nativeplace:this.nativeplace
        }
      })
          .then(res => {
            console.log(res)
            this.tableData = res.records
            this.total = res.total
          })

    },
    update(){
      //发送数据到后端
      this.request.post("/student/update",this.form)
          .then(res => {
            if(res){
              this.$message.success("编辑成功")
              this.dialogFormVisible = false
              this.load()
            }else {
              this.$message.error("编辑失败")
            }
          })
    },
    save(){
      this.request.post("/student/save",this.form)
          .then(res => {
            if(res){
              this.$message.success("新增成功")
              this.dialogFormVisible2 = false
              this.load()
            }else {
              this.$message.error("新增失败")
            }
          })
    },
    handleAdd(){
      this.dialogFormVisible2 = true
      this.form = {}
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)); //将数据赋予弹窗
      this.dialogFormVisible = true //显示弹窗

    },
    del(id){
      this.request.delete("/student/" + id)
          .then(res => {
            if(res){
              this.$message.success("删除成功")

            }else {
              this.$message.success("删除成功")
              this.load()
            }
          })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.studentid)   //把一个对象的数组变成一个纯数组
      this.request.post("/student/del/batch",ids).then(res => {
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else {
          this.$message.error("批量删除失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    reset(){
      this.studentid = ""
      this.name = ""
      this.mobilephone = ""
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/student/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("文件上传成功！")
      this.load()
    }
  }
}
</script>

<style>
.headerBg{
  background: #ccc !important;
}
</style>