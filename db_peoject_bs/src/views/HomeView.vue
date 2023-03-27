<template>
  <el-container style="min-height: 100vh;">

    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);min-height: 100%; box-shadow: 2px 0 6px rgb(0 21 41 /35%)">
      <el-menu :default-openeds="['1', '3']" style = "min-height : 100%; overflow-x: hidden"
               background-color="rgb(48,65,86)"
               text-color="#fff"
               active-text-color="#ffd04b"
               :collapse-transition="false"
               :collapse="isCollapse"
      >
        <div style="height: 60px;line-height: 60px;text-align: center">
          <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px">

          <b style="color: white" v-show="logoTextShow">学生教务管理系统</b>
        </div>
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-message"></i>
            <span slot="title">导航一</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="2-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-setting"></i>
            <span slot="title">导航三</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="3-1">选项1</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="3-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="3-4">
            <template slot="title">选项4</template>
            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
        <div style="flex: 1;font-size: 20px">
          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        </div>

        <el-dropdown style="width: 70px; cursor: pointer">
          <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>

      <el-main>
        <template>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>

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
            <el-button type="danger" class="ml-5">导入<i class = "el-icon-bottom"></i></el-button>
          <el-button type="danger">导出<i class = "el-icon-top"></i></el-button>
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
        <div style="padding: 10px 0">
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
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utls/request";

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  },
  data() {

    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,

      deptname: "",
      deptid: "",
      address: "",
      phonecode: "",
      dialogFormVisible: false,
      multipleSelection: [],
      form: {},

      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true
    }
  },
  created() {
    this.load()
  },
  methods: {
    collapse(){ //点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
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
      request.get("/department/page",{
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
      request.post("/department",this.form)
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
      request.delete("/department/" + id)
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
      request.post("/department/del/batch",ids).then(res => {
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
    }
  }
}
</script>
<style>
.headerBg{
  background: #ccc !important;
}
</style>