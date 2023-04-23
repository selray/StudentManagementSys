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




      <el-form-item label="工号">
        <el-input v-model="form.tnumber" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.tname" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.tsex" autocomplete="off" />
      </el-form-item>
      <el-form-item label="生日">
        <el-input v-model="form.tbirthday" autocomplete="off" />
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
      return  (await this.request.get('/teacher/id/'+this.user.tnumber)).data
    },
    save(){
      this.request.post("/teacher",this.form).then(res=>{
        if(res){
          this.$message.success("保存成功")

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