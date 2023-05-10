<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入教师号" suffix-icon="el-icon-search" v-model="tnumber"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" class="ml-5" suffix-icon="el-icon-message" v-model="tname"></el-input>
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
      <el-upload action="http://124.71.166.37:9090/teacher/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入<i class = "el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--          多选框-->
      <el-table-column type="selection" width="55" />


      <el-table-column prop="tnumber" label="工号" width="250">
      </el-table-column>
      <el-table-column prop="tname" label="姓名" width="250">
      </el-table-column>
      <el-table-column prop="tsex" label="性别" width="120">
      </el-table-column>
      <el-table-column prop="tstatus" label="职位" width="250">
      </el-table-column>
      <el-table-column label="操作"width="313">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确认"
              cancel-button-text="取消"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="是否删除?"
              @confirm="del(scope.row.tnumber)"
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
    <el-dialog title="教师信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="教师号">
          <el-input v-model="form.tnumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.tname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.tsex" autocomplete="off" />
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form.tstatus" autocomplete="off" />
        </el-form-item>
        <el-form-item label="账户">
          <el-input v-model="form.tusername" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.tpassword" autocomplete="off" />
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
    <el-dialog title="教师信息" :visible.sync="dialogFormVisible2" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="教师号">
          <el-input v-model="form.tnumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.tname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.tsex" autocomplete="off" />
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form.tstatus" autocomplete="off" />
        </el-form-item>
        <el-form-item label="账户">
          <el-input v-model="form.tusername" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.tpassword" autocomplete="off" />
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
      tnumber: "",
      tname: "",
      tsex: "",
      tstatus: "",
      tusername:"",
      tpassword:"",
      dialogFormVisible: false,
      dialogFormVisible2: false,
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
      this.request.get("/teacher/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          tnumber:this.tnumber,
          tname:this.tname,
          tsex: this.tsex,
          tstatus:this.tstatus,
          tusername:this.tusername,
          tpassword:this.tpassword
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
      this.request.post("/teacher/update",this.form)
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
      //发送数据到后端
      this.request.post("/teacher/save",this.form)
          .then(res => {
            if(res){
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            }else {
              this.$message.error("保存失败")
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
      this.request.delete("/teacher/" + id)
          .then(res => {
            if(res){
              this.$message.success("删除成功")
              this.load()
            }else {
              this.$message.error("删除失败")
            }
          })
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.tnumber)   //把一个对象的数组变成一个纯数组
      this.request.post("/teacher/del/batch",ids).then(res => {
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
      this.tnumber = ""
      this.tname = ""
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
      window.open("http://localhost:9090/teacher/export")
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