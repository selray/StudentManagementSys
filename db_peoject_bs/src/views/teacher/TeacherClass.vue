<template>
  <div>

    <div style="margin: 10px 0">
      <el-input suffix-icon="el-icon-search" style="width:200px" placeholder="请输入学号" v-model="snumber"></el-input>
      <el-input suffix-icon="el-icon-search" style="width:200px" placeholder="请输入姓名" class="ml-5" v-model="sname"></el-input>
      <el-input suffix-icon="el-icon-search" style="width:200px" placeholder="请输入课号" class="ml-5" v-model="lnumber"></el-input>
      <el-button  class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button  type="warning" @click="reset">重置</el-button>
    </div>


    <div style="margin: 10px 0">
      <template>
        <el-select v-model="lname" clearable placeholder="请选择课程" @change="load">
          <el-option
              v-for="item in uniqueData"
              :key="item.lnumber"
              :value="item.lname">
          </el-option>
        </el-select>
      </template>
      <!--      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>-->
      <!--      <el-popconfirm-->
      <!--          class="ml-5"-->
      <!--          confirm-button-text='确定'-->
      <!--          cancel-button-text='我再想想'-->
      <!--          icon="el-icon-info"-->
      <!--          icon-color="red"-->
      <!--          title="您确定批量删除这些数据吗？"-->
      <!--          @confirm="delBatch"-->
      <!--      >-->
      <!--        <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>  &lt;!&ndash;slot="reference"使按钮显示&ndash;&gt;-->
      <!--      </el-popconfirm>-->
<!--      <el-upload action="http://localhost:9090/teacher/import" :show-file-list="false" accept=".xlsx" :on-success="handleExcelImportSucess" style="display: inline-block">-->
<!--        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i></el-button>-->
<!--      </el-upload>-->
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55"></el-table-column>  &lt;!&ndash;选择框&ndash;&gt;-->
      <el-table-column prop="snumber" label="学生学号" width="140" align="center"></el-table-column>
      <el-table-column prop="sname" label="学生姓名" width="140" align="center"></el-table-column>
      <el-table-column prop="lnumber" label="课程号" width="140" align="center"></el-table-column>
      <el-table-column prop="lname" label="课程名" width="140" align="center"></el-table-column>
      <el-table-column prop="semester" label="学期" align="center"></el-table-column>
      <el-table-column prop="psgrade" label="平时成绩" align="center"></el-table-column>
      <el-table-column prop="ksgrade" label="考试成绩" align="center"></el-table-column>
      <el-table-column prop="totalgrade" label="总成绩" align="center"></el-table-column>
      <el-table-column prop="gpa" label="绩点" align="center"></el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <!--          <el-popconfirm-->
          <!--              class="ml-5"-->
          <!--              confirm-button-text='确定'-->
          <!--              cancel-button-text='我再想想'-->
          <!--              icon="el-icon-info"-->
          <!--              icon-color="red"-->
          <!--              title="您确定删除吗？"-->
          <!--              @confirm="del(scope.row.deptid)"-->
          <!--          >-->
          <!--            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>-->
          <!--          </el-popconfirm>-->
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2,5,10,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!--编辑框-->
    <el-dialog title="班级信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px"  size="small">
        <el-form-item label="学号" >
          <el-input v-model="form.snumber" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.sname" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="平时成绩">
          <el-input v-model="form.psgrade" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试成绩">
          <el-input v-model="form.ksgrade" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "Class",
  data(){
    return {
      tableData: [],
      courseData:[],
      total:0,
      pageNum:1,
      pageSize:10,
      snumber:"",
      sname:"",
      lnumber:"",
      lname:"",
      semester:"",
      form:{},
      dialogFormVisible:false,
      multipleSelection: [],
      headerBg:'headerBg'
    }
  },
  created() {
    this.load()
  },
  computed:{
    uniqueData() {
      const set = new Set();
      return this.courseData.filter((item) => {
        const key = `${item.lname}-${item.lnumber}`;
        if (set.has(key)) {
          // 如果集合中已经存在相同的key，则删除当前对象
          return false;
        } else {
          set.add(key);
          return true;
        }
      });
    },
  },
  methods: {
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)) //深拷贝，解决没点确定表格数据变化问题
      // console.log(this.form)
      this.dialogFormVisible=true
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    load(){
      this.request.get("/lessonchoose/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          snumber:this.snumber,
          sname:this.sname,
          lnumber:this.lnumber,
          lname:this.lname,
          semester:this.semester
        }
      }).then(res=>{
        // console.log(res)
        // console.log(this.tableData)
        this.tableData=res.records
        // console.log("1==="+this.tableData + Date.now())
        console.log(this.tableData)
        this.total=res.total
        if (this.courseData.length === 0) {
          this.courseData = this.tableData;
        }
      })
    },
    save(){
      this.request.post("/lessonchoose",this.form).then(res=>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    reset(){
      this.snumber=""
      this.sname=""
      this.lnumber=""
      this.lname=""
      this.semester=""
      this.load()
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    exp(){
      // 构建URL参数字符串
      window.open(`http://124.71.166.37:9090/lessonchoose/export?tableData=${encodeURIComponent(JSON.stringify(this.tableData))}`)
    }
  }

}
</script>

<style>
.headerBg{
  background: #eee !important;
}
</style>