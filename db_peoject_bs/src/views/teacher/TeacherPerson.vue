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
        <el-input v-model="form.tbirthday" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model="form.tusername" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.tpassword" autocomplete="off" />
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
      //console.log(res)
    })
  },
  methods:{
    async getUser(){
      //console.log((await this.request.get('/teacher/id/'+this.user.tnumber)).data)
      return  (await this.request.get('/teacher/id/'+this.user.tnumber)).data
    },
    save(){
      this.request.post("/teacher/update",this.form).then(res=>{
        if(res){
          this.$message.success("保存成功")

          //触发父级更新user的方法
          this.$emit("refreshUser")
          //保存之后，触发manage的父级，通过父级中的功能来实现更新以及右上角头像的更新



          //更新浏览器存储信息
          this.getUser().then(res =>{
            //console.log(res)
            res.token  = JSON.parse(localStorage.getItem("loguserinfo")).token
            //console.log(localStorage.getItem("loguserinfo"))
            //console.log(JSON.parse(localStorage.getItem("loguserinfo")))
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

      this.request.get("/file/teaavartarinfo",{
        params:{
          avartarurl: res,
          tnumber: JSON.parse(localStorage.getItem("loguserinfo")).tnumber
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