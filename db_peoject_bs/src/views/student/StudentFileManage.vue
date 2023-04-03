<template>
  <div>
    <div style="margin: 10px 0;">
      <el-input style="width: 200px" placeholder="请输入需要查询的文件" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" accept="xlsx" :on-success="handleFileUploadSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">上传文件<i class = "el-icon-top"></i></el-button>
      </el-upload>
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
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <!--          多选框-->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="序号">
      </el-table-column>
      <el-table-column prop="name" label="文件名称">
      </el-table-column>
      <el-table-column prop="type" label="文件类型">
      </el-table-column>
      <el-table-column prop="size" label="文件大小kb">
      </el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope" t>
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change = "changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!--          注意！！这里的row是指的是每一行的数据，不同情形下，每一行数据不一样！！-->
          <el-popconfirm
              class="ml-5"
              confirm-button-text="确认"
              cancel-button-text="取消"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="是否删除?"
              @confirm="del(scope.row.id)"
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
      <el-pagination>
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      </el-pagination>

    </div>
  </div>
</template>

<script>
export default {
  name: "File.vue",
  data(){
    return {
      tableData:[],
      name:'',
      multipleSelection:[],
      pageNum: 1,
      pageSize: 10,
      total: 0
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
      this.request.get("/file/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    changeEnable(row){
      this.request.post("/file/update", row).then(res =>{
        if (res.code === '200'){
          this.$message.success("操作成功")
        }
      })
    },
    del(id){
      this.request.delete("/file/" + id).then(res => {
        if(res.code === '200'){
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
      // 这里也需要修改V.id，根据具体每一行的哪些属性来确定
      let ids = this.multipleSelection.map(v => v.id)   //把一个对象的数组变成一个纯数组
      this.request.post("/file/del/batch",ids).then(res => {
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset(){
      this.name = ""
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
    handleFileUploadSuccess(res){
      this.load()
      console.log(res)
    },
    download(url){

      window.open(url)
    }
  }
}
</script>

<style scoped>

</style>