<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入课程号" suffix-icon="el-icon-search" v-model="lnumber"></el-input>
      <!--      <el-input style="width: 200px" placeholder="请输入课程名称" class="ml-5" suffix-icon="el-icon-position" v-model="classname"></el-input>-->

      <el-input style="width: 200px" placeholder="请输入教师号" class="ml-5" suffix-icon="el-icon-message" v-model="tnumber"></el-input>
      <!--      <el-input style="width: 200px" placeholder="请输入开课学院号" class="ml-5" suffix-icon="el-icon-position" v-model="lcollege"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
      <el-button type="danger" @click="handleAdd">新增</el-button>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" :default-sort = "{prop: 'lnumber', order: 'descending'}">
      <el-table-column prop="lnumber" label="课程号" width="100"sortable>
      </el-table-column>
      <el-table-column prop="classname" label="课程名称" width="150">
      </el-table-column>
      <el-table-column prop="lcredit" label="学分">
      </el-table-column>
      <el-table-column prop="tnumber" label="教师号"sortable>
      </el-table-column>
      <el-table-column prop="semester" label="学期">
      </el-table-column>
      <el-table-column prop="lessontime" label="时间" width="140">
      </el-table-column>
      <el-table-column prop="classroom" label="教室">
      </el-table-column>
      <el-table-column prop="maxsize" label="最大人数">
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
    <!--  编辑-->
    <el-dialog title="开课信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="课程号">
          <el-input v-model="form.lnumber" autocomplete="off" :disabled="true"/>
        </el-form-item>
        <el-form-item label="教师号">
          <el-input v-model="form.tnumber" autocomplete="off" :disabled="true"/>
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="form.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="时间">
          <el-input v-model="form.lessontime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地点">
          <el-input v-model="form.classroom" autocomplete="off" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input v-model="form.maxsize" autocomplete="off" />
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
      <!--      新增-->
    </el-dialog>
    <el-dialog title="开课信息" :visible.sync="dialogFormVisible2" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="课程号">
          <el-input v-model="form.lnumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="教师号">
          <el-input v-model="form.tnumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="form.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="时间">
          <el-input v-model="form.lessontime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地点">
          <el-input v-model="form.classroom" autocomplete="off" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input v-model="form.maxsize" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取消</el-button>
        <el-button type="primary" @click="save2">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SclassManage",
  data() {
    return {
      tableData: [],
      tableData2:[],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      tnumber: "",
      lnumber: "",
      semester: "",
      snumber:"",
      lessontime: "",
      classroom: "",
      maxsize: "",
      currentsize: "",
      classname: "",
      lcredit: "",
      dialogFormVisible: false,
      dialogFormVisible2:false,
      multipleSelection: [],
      form: {},
      tabSort: 0, //该字段代表升序还是降序 假设：0正序 1倒序
      tabProp: '' //prop绑定的字段名
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/sclass/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          tnumber: this.tnumber,
          lnumber: this.lnumber,
          semester: this.semester,
          lessontime: this.lessontime,
          classroom: this.classroom,
          maxsize: this.maxsize
        }
      })
          .then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total
              }
          )
      this.res.sort(function (a,b){
        return b.lnumber-a.lnumber;
      })
    },
    reset(){
      this.lnumber = ""
      this.tnumber = ""
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/sclass/export")
    },
    save(){
      //发送数据到后端
      this.request.post("/sclass/save",this.form)
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
    save2(){
      //发送数据到后端
      this.request.post("/sclass/new",this.form).then(res=>{
        if(res==0){
          this.$message.success("新增成功")
          this.dialogFormVisible2 = false
          this.load()
        }
        else if(res==-1){this.$message.error("新增失败,该教师已经开授此课程")}
        else if(res==-2){this.$message.error("新增失败，该时间段此教室已被占用")}
        else if(res==-3){this.$message.error("新增失败，该课程不存在")}
        else if(res==-4){this.$message.error("新增失败，该教师不存在")}
      })
    },
    del(id){
      this.request.delete("/sclass/" + id)
          .then(res => {
            if(res){
              this.$message.success("删除成功")

            }else {
              this.$message.error("删除失败")
              this.load()
            }
          })
    },
    handleAdd(){
      this.dialogFormVisible2 = true
      this.form = {}
    },
    handleEdit(row){
      this.form = row //将数据赋予弹窗
      this.dialogFormVisible = true //显示弹窗

    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<style scoped>

</style>