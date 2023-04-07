<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入课程号" suffix-icon="el-icon-search" v-model="lnumber"></el-input>
<!--      <el-input style="width: 200px" placeholder="请输入课程名称" class="ml-5" suffix-icon="el-icon-position" v-model="classname"></el-input>-->

      <el-input style="width: 200px" placeholder="请输入教师号" class="ml-5" suffix-icon="el-icon-message" v-model="tnumber"></el-input>
      <!--      <el-input style="width: 200px" placeholder="请输入开课学院号" class="ml-5" suffix-icon="el-icon-position" v-model="lcollege"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
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
      <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
    </div>

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
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text="确认"
                    cancel-button-text="取消"
                    :icon="InfoFilled"
                    icon-color="#626AEF"
                    title="是否确认选课?"
                    @confirm="selectCourse(scope.row.lnumber,scope.row.tnumber,scope.row.semester,scope.row.lessontime)"
                    @cancel="cancelEvent"
                >
                  <template #reference>
                    <el-button type="success" @click="handleEdit(scope.row)">选课<i class="el-icon-edit"></i></el-button>
                  </template>

<!--                  <template #reference>-->
<!--                    <el-button type="danger">删除<i class="el-icon-remove-outline"></i></el-button>-->
<!--                  </template>-->
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
      timeData:"",
      StringSplit:[],
      WillUpdateSplit:[],

      StringIndex:"",
      StringIndex2:"",
      StringIndex3:"",
      numberSplit:[],
      numberSplit2:[],
      number1:0,
      number2:0,
      number3:0,
      number4:0,
      Flag:0,
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
          })

    },
    //选课，在lesson choose表中，添加一个记录
    selectCourse(lnumber,tnumber,semester,lessontime){
      //this.$message.success("==============选课点击事件：传入数据："+lnumber+"  "+tnumber+"  "+semester+"  "+JSON.parse(localStorage.getItem("loguserinfo")).studentid);
      //先通过已选课程判断，选课是否冲突
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
            //console.log(res)
            // this.tableData
            this.tableData2 = res.records
            //this.$message.success("传入前tableData2的数据:"+this.tableData2[0].lessontime)
            //this.$message.success("传入前tableData2的数据:"+this.tableData2[1].lessontime)

            //console.log(this.tableData)
            //this.$message.success("tabledata信息："+this.tableData[0].lessontime)
            //this.$message.success("tabledata长度："+this.tableData.length)
            //this.FilledTable()
            //this.total = res.total
            this.Flag = 0;
            //this.$message.success("进入外层循环之前："+this.tableData2.length)
            for(let k = 0; k < this.tableData2.length ;k++){//遍历datatable数据
              //this.$message.success("进入外层循环：")

              this.timeData = this.tableData2[k].lessontime;
              //获取到了某一个已选课程的上课时间
              //进行分析判断是否冲突
              this.StringSplit = this.timeData.split("，");
              this.WillUpdateSplit = lessontime.split("，")

              //this.$message.success("获取到的时间信息："+lessontime  +"需要判断的信息："+this.timeData)
              for(let o = 0;o< this.WillUpdateSplit.length;o++) {  //遍历需要选择课程的时间

                this.StringIndex = this.WillUpdateSplit[o];
                //先判断星期
                for(let x = 0;x < this.StringSplit.length;x++){ //遍历已选课程的时间
                  this.StringIndex2 = this.StringSplit[x];
                  //this.$message.success("判断星期是否相等"+this.StringIndex.charAt(0)+"   "+this.StringIndex2.charAt(0))
                  if(this.StringIndex.charAt(0) == this.StringIndex2.charAt(0)){ //星期相等,判断节数
                    //需要选的课程时间数字拆分
                    this.numberSplit = this.StringIndex.split("-");
                    this.numberSplit2 = this.StringIndex2.split("-");
                    this.numberSplit[0] = this.numberSplit[0].substring(1);
                    this.number1 = this.numberSplit[0];
                    this.number2 = this.numberSplit[1];
                    //现在需要选的课程是 number1-number2
                    this.numberSplit2[0] = this.numberSplit2[0].substring(1);
                    this.number3 = this.numberSplit2[0];
                    this.number4 = this.numberSplit2[1];
                    //当前查询到了已经选的课程是 number3-number4
                    // this.$message.success("1："+this.number1+"2："+this.number2+"3："+this.number3+"4："+this.number4)

                    //进行判断
                    if(this.number3 <= this.number1 && this.number4 >= this.number1  ){
                      //产生冲突
                      //this.$message.error("选课时间冲突，选课失败")
                      this.Flag = 1;
                    }
                    else if (this.number3 <= this.number2 && this.number4 >= this.number2){
                      //this.$message.error("选课时间冲突，选课失败")
                      this.Flag = 1;
                    }
                  }
                }
              }
            }
            if(this.Flag == 0) { //如果没有冲突，才会进入
              //发送数据到后端
              //this.$message.success("没有出现时间冲突！")
              this.request.get("/lessonchoose/xk", {
                params: {
                  lnumber: lnumber,
                  tnumber: tnumber,
                  semester: semester,
                  snumber: localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")).studentid : ""
                }
              })
                  .then(res => {
                    if (res) {
                      this.$message.success("选课成功")
                      this.dialogFormVisible = false
                      this.load()
                    } else {
                      this.$message.error("选课失败，重复选课")
                    }
                  })
            }
            else {
              this.$message.error("选课失败，时间冲突")
            }
          })
      //这里的this.tableData2.length比正常的少一

      //this.$message.success("出循环:")
      //this.$message.error("Flag值："+this.Flag)
      //添加课程

    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    JudgeTimeConflicts(lessontime){
      //判断
      //lessontime代表需要选择的课程时间
      for(let k = 1; k <= this.tableData2.length ;k++){//遍历datatable数据
        this.timeData = this.tableData2[k-1].lessontime;

        //获取到了某一个已选课程的上课时间
        //进行分析判断是否冲突
        this.StringSplit = this.timeData.split("，");
        this.WillUpdateSplit = this.lessontime.split("，")
        //this.$message.success("获取到的时间信息："+lessontime)
        for(let o = 0;o< this.WillUpdateSplit.length;o++) {  //遍历需要选择课程的时间
          this.StringIndex = this.WillUpdateSplit[o];
          //先判断星期
          for(let x = 0;x < this.StringSplit.length;x++){ //遍历已选课程的时间
            this.StringIndex2 = this.StringSplit[x];
            if(this.StringIndex.charAt(0) == this.StringIndex2.charAt(0)){ //星期相等,判断节数
              //需要选的课程时间数字拆分
              this.numberSplit = this.StringIndex.split("-");
              this.numberSplit2 = this.StringIndex2.split("-");
              this.number1 = this.numberSplit[0];
              this.number2 = this.numberSplit[1];
              //现在需要选的课程是 number1-number2

              this.number3 = this.numberSplit2[0];
              this.number4 = this.numberSplit2[1];
              //当前查询到了已经选的课程是 number3-number4

              //进行判断
              if(this.number3 <= this.number1 && this.number4 >= this.number1 || this.number3 <= this.number2 && this.number4 >= this.number2 ){
                //产生冲突
                this.$message.error("选课时间冲突，选课失败")
                return false;
              }
            }
          }
        }
      }
      return true;
      //this.$message.success("传入后参数:"+lnumber+"  "+tnumber+"  "+semester)

    },

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    save(){
      //发送数据到后端
      this.request.post("/department",this.form)
          .then(res => {
            if(res){
              this.$message.success("选课成功")
              this.dialogFormVisible = false
              this.load()
            }else {
              this.$message.error("选课失败")
            }
          })
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