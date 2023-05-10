<template>
<div>
  <div style="margin: 10px 0">
    <el-input style="width: 200px" placeholder="请输入课程号" suffix-icon="el-icon-search" v-model="lnumber"></el-input>
    <el-input style="width: 200px" placeholder="请输入课程名" class="ml-5" suffix-icon="el-icon-message" v-model="lname"></el-input>
    <el-input style="width: 200px" placeholder="请输入学院名" class="ml-5" suffix-icon="el-icon-message" v-model="deptname"></el-input>
    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    <el-button type="warning" @click="reset">重置</el-button>
    <el-button type="danger" @click="handleAdd">新增</el-button>
    <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
  </div>
  <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
    <el-table-column prop="lnumber" label="课程号" width="200">
    </el-table-column>
    <el-table-column prop="lname" label="课程名称" width="250">
    </el-table-column>
    <el-table-column prop="lcredit" label="学分"width="200">
    </el-table-column>
    <el-table-column prop="deptname" label="学院" width="250">
    </el-table-column>
    <el-table-column label="操作" width="200">
      <template slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text="确认"
            cancel-button-text="取消"
            :icon="InfoFilled"
            icon-color="#626AEF"
            title="是否删除?"
            @confirm="del(scope.row.lnumber)"
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
  <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%" >
    <el-form label-width="80px" size="small">
      <el-form-item label="课程号">
        <el-input v-model="form.lnumber" autocomplete="off" />
      </el-form-item>
      <el-form-item label="课程名">
        <el-input v-model="form.lname" autocomplete="off" />
      </el-form-item>
      <el-form-item label="学分">
        <el-input v-model="form.lcredit" autocomplete="off" />
      </el-form-item>
      <el-form-item label="学院号">
        <el-input v-model="form.lcollege" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
    </template>
  </el-dialog>
</div>
</template>
<script>
export default {
  name: "CourseManage",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      lnumber:'',
      lname:'',
      lcredit:'',
      lcollege:'',
      deptname:'',
      multipleSelection: [],
      dialogFormVisible: false,
      form: {},
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/lesson/pagewithname",{
        params:{
          pageNum:this.pageNum,
          pageSize: this.pageSize,
          lnumber:this.lnumber,
          lname:this.lname,
          lcredit:this.lcredit,
          deptname:this.deptname,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
      this.lname=""
      this.lnumber=""
      this.deptname=""
      this.load()
    },
    save(){
      //发送数据到后端
      this.request.post("/lesson",this.form)
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
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/lesson/export")
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)); //将数据赋予弹窗
      this.dialogFormVisible = true //显示弹窗
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
handleSizeChange(pageSize){
  this.pageSize = pageSize
  this.load()
},
  }
}
</script>
