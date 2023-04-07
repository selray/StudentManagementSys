<template>
  <div>

<!--    <div style="margin: 10px 0">-->
<!--      <el-input style="width: 200px" placeholder="请输入课程号" suffix-icon="el-icon-search" v-model="lnumber"></el-input>-->
<!--      <el-input style="width: 200px" placeholder="请输入教师号" class="ml-5" suffix-icon="el-icon-message" v-model="tnumber"></el-input>-->
<!--      &lt;!&ndash;      <el-input style="width: 200px" placeholder="请输入地址" class="ml-5" suffix-icon="el-icon-position" v-model="lcredit"></el-input>&ndash;&gt;-->
<!--      &lt;!&ndash;      <el-input style="width: 200px" placeholder="请输入开课学院号" class="ml-5" suffix-icon="el-icon-position" v-model="lcollege"></el-input>&ndash;&gt;-->
<!--      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>-->
<!--      <el-button type="warning" @click="reset">重置</el-button>-->
<!--    </div>-->

<!--    <div style="margin: 10px 0">-->
      <!--      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>-->
      <!--      <el-popconfirm-->
      <!--          class="ml-5"-->
      <!--          confirm-button-text="确认"-->
      <!--          cancel-button-text="取消"-->
      <!--          :icon="InfoFilled"-->
      <!--          icon-color="#626AEF"-->
      <!--          title="是否批量删除?"-->
      <!--          @confirm="delBatch"-->
      <!--          @cancel="cancelEvent"-->
      <!--      >-->
      <!--        <el-button type="danger" slot="reference">批量删除<i class = "el-icon-remove-outline"></i></el-button>-->
      <!--      </el-popconfirm>-->
      <!--      <el-upload action="http://localhost:9090/department/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">-->
      <!--        <el-button type="primary" class="ml-5">导入<i class = "el-icon-bottom"></i></el-button>-->
      <!--      </el-upload>-->
<!--      <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>-->
<!--    </div>-->

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--          多选框-->
<!--      <el-table-column type="selection" width="55" />-->


      <el-table-column prop="lnumber" label="课程号" width="100">
      </el-table-column>
      <el-table-column prop="classname" label="课程名称" width="150">
      </el-table-column>
      <el-table-column prop="lcredit" label="学分">
      </el-table-column>
      <el-table-column prop="tnumber" label="老师号">
      </el-table-column>
      <el-table-column prop="semester" label="学期">
      </el-table-column>
      <el-table-column prop="lessontime" label="时间" width="140">
      </el-table-column>
      <el-table-column prop="classroom" label="教室">
      </el-table-column>
      <el-table-column prop="maxsize" label="最大人数">
      </el-table-column>
      <el-table-column prop="currentsize" label="当前人数">
      </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
<!--                <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>-->
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text="确认"
                    cancel-button-text="取消"
                    :icon="InfoFilled"
                    icon-color="#626AEF"
                    title="是否退课?"
                    @confirm="delCourse(scope.row.lnumber,scope.row.tnumber,scope.row.semester)"
                    @cancel="cancelEvent"
                >
                  <template #reference>
                    <el-button type="danger">退课<i class="el-icon-remove-outline"></i></el-button>
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
    <!--        “新建”弹窗-->
    <el-dialog title="学院信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="课程号">
          <el-input v-model="form.lnumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="form.lname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="form.lcredit" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开设学院">
          <el-input v-model="form.lcollege" autocomplete="off" />
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
      lnumber: "",
      semester: "",
      lessontime: "",
      classroom: "",
      maxsize: "",
      currentsize: "",
      classname: "",
      lcredit: "",


      dialogFormVisible: false,
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
      //请求分页查询数据
      //通过axios向后台请求参数

      //通过request.js中的baseurl已经将前面的http://localhost:9090部分省略了
      this.request.get("/sclass/tk/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          tnumber: this.tnumber,
          lnumber: this.lnumber,
          semester: this.semester,
          lessontime: this.lessontime,
          classroom: this.classroom,
          maxsize: this.maxsize,
          snumber: localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")).studentid:""
        }
      })
          .then(res => {
            //this.$message.success("加载成功")
            console.log(res)
            this.tableData = res.records
            this.total = res.total
          })

    },
    save(){
      //发送数据到后端
      this.request.post("/sclass",this.form)
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
    delCourse(lnumber,tnumber,semester){
      //this.$message.success("==============退课点击事件：传入数据："+lnumber+"  "+tnumber+"  "+semester+"  "+JSON.parse(localStorage.getItem("loguserinfo")).studentid);
      //发送数据到后端
      this.request.get("/lessonchoose/tk",{
        params: {
          lnumber: lnumber,
          tnumber: tnumber,
          semester: semester,
          snumber: localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")).studentid:""
        }
      })
          .then(res => {
            if(res){
              this.$message.success("退课成功")
              this.dialogFormVisible = false
              this.load()
            }else {
              this.$message.error("退课失败")
            }
          })
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    // handleEdit(row){
    //   this.form = row //将数据赋予弹窗
    //   this.dialogFormVisible = true //显示弹窗
    //
    // },
    // del(id){
    //   this.request.delete("/lesson/" + id)
    //       .then(res => {
    //         if(res){
    //           this.$message.success("删除成功")
    //           this.load()
    //         }else {
    //           this.$message.error("删除失败")
    //         }
    //       })
    // },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    // delBatch(){
    //   let ids = this.multipleSelection.map(v => v.lnumber)   //把一个对象的数组变成一个纯数组
    //   this.request.post("/department/del/batch",ids).then(res => {
    //     if(res){
    //       this.$message.success("批量删除成功")
    //       this.load()
    //     }else {
    //       this.$message.error("批量删除失败")
    //     }
    //   })
    // },
    reset(){
      this.lnumber = ""
      this.lname = ""
      this.lcredit = ""
      this.lcollege = ""
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
      window.open("http://localhost:9090/sclass/export")
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