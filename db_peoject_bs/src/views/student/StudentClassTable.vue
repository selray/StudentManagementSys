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

    <div style="margin-bottom:45px;">
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange" :row-style="{height:'25px'}" :cell-style="{padding:'0'}">
      <!--          多选框-->

      <el-table-column prop="id" label="编号" width="50">
        <template slot-scope="scope">
          <span>
            {{ String.fromCharCode(65 + scope.$index)}}
            </span>
        </template>
      </el-table-column>
      <el-table-column prop="lnumber" label="课程号" >
      </el-table-column>
      <el-table-column prop="classname" label="课程名" width="150">
      </el-table-column>
      <el-table-column prop="lcredit" label="学分" width="50">
      </el-table-column>
      <el-table-column prop="tnumber" label="老师号">
      </el-table-column>
      <el-table-column prop="semester" label="学期">
      </el-table-column>
      <el-table-column prop="lessontime" label="时间" width="200">
      </el-table-column>
      <el-table-column prop="classroom" label="教室">
      </el-table-column>
      <el-table-column prop="maxsize" label="最大人数">
      </el-table-column>
      <el-table-column prop="currentsize" label="当前人数">
      </el-table-column>


    </el-table>
    </div>
    <div class="font_div">
      课 程 表
    </div>
    <el-table :data="ClassTable" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange" :row-style="{height:'35px'}" :cell-style="{padding:'0'}">
      <!--          多选框-->

      <el-table-column fixed prop="firstCol" label="" width="50px">
      </el-table-column>
      <el-table-column fixed prop="tabletime" label="上课时间" >
      </el-table-column>
      <el-table-column prop="lnumber" label="星期一" >
      </el-table-column>
      <el-table-column prop="tnumber" label="星期二">
      </el-table-column>
      <el-table-column prop="semester" label="星期三">
      </el-table-column>
      <el-table-column prop="lessontime" label="星期四" >
      </el-table-column>
      <el-table-column prop="classroom" label="星期五">
      </el-table-column>



    </el-table>

  </div>
</template>

<script>

import index from "vuex";

export default {
  name: "User",
  computed: {
    index() {
      return index
    }
  },
  data(){
    return{

      ClassTable:[],
      ClassTimeTable:[],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      CourseTableDis:[[]],
      tnumber: "",
      lnumber: "",
      semester: "",
      lessontime: "",
      classroom: "",
      maxsize: "",
      currentsize: "",
      classname: "",
      lcredit: "",
      id: "",
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
            this.$message.success("加载成功")
            console.log(res)
            // this.tableData
            this.tableData = res.records
            //this.$message.success("tabledata信息："+this.tableData.findIndex((role) => role.id === 0).lessontime)
            this.FilledTable()
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

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    FilledTable(){
      this.CourseTableDis[12]= new Array();

      // for(var i =1;i<=12;i++){
      //   for(var j =1;j<=5;j++){ //判断星期几
      //     //判断tabledata的数据
      //     this.tableData.
      //     this.CourseTableDis[x][i] = dataArray[i];
      //   }
      // } //判断第几节

      this.ClassTable = [
        {
          firstCol:'一',
          tabletime:'8:00-8:45',
        },
        {
          firstCol: '二',
          tabletime:'8:55-9:40'
        },
        {
          firstCol:'三',
          tabletime:'10:00-10:45'
        },
        {
          firstCol: '四',
          tabletime:'10:55-11:40'
        },
        {
          firstCol:'五',
          tabletime:'13:00-13:45'
        },
        {
          firstCol: '六',
          tabletime:'13:55-14:40'
        },
        {
          firstCol:'七',
          tabletime:'15:00-15:45'
        },
        {
          firstCol: '八',
          tabletime:'15:55-16:40'
        },
        {
          firstCol:'九',
          tabletime:'18:00-18:45'
        },
        {
          firstCol: '十',
          tabletime:'18:55-19:40'
        },
        {
          firstCol:'十一',
          tabletime:'20:00-20:45'
        },
        {
          firstCol: '十二',
          tabletime:'20:55-21:40'
        },
      ]
    },
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

.font_div{
  font-weight:bold;/*加粗*/
  text-align:center;   /* 水平居中 */

}
</style>