<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入学院号" suffix-icon="el-icon-search" v-model="deptid"></el-input>
      <el-input style="width: 200px" placeholder="请输入学院名" class="ml-5" suffix-icon="el-icon-message" v-model="deptname"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" class="ml-5" suffix-icon="el-icon-position" v-model="address"></el-input>
      <el-input style="width: 200px" placeholder="请输入联系方式" class="ml-5" suffix-icon="el-icon-position" v-model="phonecode"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text="确认"
          cancel-button-text="取消"
          :icon="InfoFilled"
          icon-color="#626AEF"
          title="是否批量删除?"
          @confirm="delBatch"
          @cancel="cancelEvent"
      >
        <el-button type="danger" slot="reference">批量删除<i class = "el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
        <el-upload action="http://localhost:9090/department/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
      <el-button type="primary" class="ml-5">导入<i class = "el-icon-bottom"></i></el-button>
        </el-upload>
          <el-button type="primary" @click="exp" class="ml-5">导出<i class = "el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--          多选框-->
      <el-table-column type="selection" width="55" />


      <el-table-column prop="deptid" label="学院号" width="140">
      </el-table-column>
      <el-table-column prop="deptname" label="学院名称" width="120">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>
      <el-table-column prop="phonecode" label="联系方式">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确认"
              cancel-button-text="取消"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="是否删除?"
              @confirm="del(scope.row.deptid)"
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
    <!--        “新建”弹窗-->
    <el-dialog title="学院信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="学院号">
          <el-input v-model="form.deptid" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学院名">
          <el-input v-model="form.deptname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.phonecode" autocomplete="off" />
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

      deptname: "",
      deptid: "",
      address: "",
      phonecode: "",
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
      // fetch("http://localhost:9090/department/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&deptname="+this.deptname)
      //     .then(res => res.json()).then(res =>{
      //   console.log(res)
      //   this.tableData = res.data
      //   this.total = res.total
      // })

      //通过request.js中的baseurl已经将前面的http://localhost:9090部分省略了
      this.request.get("/department/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          deptid: this.deptid,
          deptname: this.deptname,
          address: this.address,
          phonecode: this.phonecode
        }
      })
          .then(res => {
            console.log(res)
            this.tableData = res.records
            this.total = res.total
          })

    },
    save(){
      //发送数据到后端
      this.request.post("/department",this.form)
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
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row){
      this.form = row //将数据赋予弹窗
      this.dialogFormVisible = true //显示弹窗

    },
    del(id){
      this.request.delete("/department/" + id)
          .then(res => {
            if(res){
              this.$message.success("删除成功")
              this.load()
            }else {
              this.$message.error("删除失败")
            }
          })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.deptid)   //把一个对象的数组变成一个纯数组
      this.request.post("/department/del/batch",ids).then(res => {
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.deptid = ""
      this.deptname = ""
      this.address = ""
      this.phonecode = ""
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
      window.open("http://localhost:9090/department/export")
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