<template>
  <el-card style="width:500px;">
    <el-form label-width="80px"  size="small">
      <el-upload
          class="avatar-uploader"
          action="http://124.71.166.37:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <!--        暂时没有头像，因为新增头像需要添加数据库里面-->
        <img v-if="form.avatarurl" :src="form.avatarurl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="姓名">
        <el-input v-model="form.mname" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="账户">
        <el-input v-model="form.musername" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.mpassword" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      form:{},
      user:localStorage.getItem("loguserinfo")? JSON.parse(localStorage.getItem("loguserinfo")):{}
    }
  },
  created() {
    this.getUser().then(res =>{
      this.form = res
    })
  },
  methods:{
    async getUser(){
      // console.log("123======="+(await this.request.get('/manager/mnumber/'+this.user.mnumber)).data)
      return  (await this.request.get('/manager/mnumber/'+this.user.mnumber)).data
    },
    save(){
      this.request.post("/manager",this.form).then(res=>{
        if(res){
          this.$message.success("保存成功")

          //触发父级更新user的方法
          this.$emit("refreshUser")
          //保存之后，触发manage的父级，通过父级中的功能来实现更新以及右上角头像的更新


          //更新浏览器存储信息
          this.getUser().then(res =>{
            res.token  = JSON.parse(localStorage.getItem("loguserinfo")).token
            //localStorage.removeItem("loguserinfo")
            localStorage.setItem("loguserinfo",JSON.stringify(res))
          })



        }else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res){
      this.form.avatarurl = res

      this.request.get("/file/manavartarinfo",{
        params:{
          avartarurl: res,
          mnumber: JSON.parse(localStorage.getItem("loguserinfo")).mnumber
        }
      })
          .then(res => {
            this.$message.success("保存成功")
          })
      //this.$message.success("路径"+this.form.avatarUrl)
      //this.$message.success("路径"+res)
    }
  }
}
</script>

<style scoped>
.avatar-uploader{
  text-align: center;
  padding-bottom:10px
}


.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 158px;
  height: 158px;
  display: block;
}
</style>