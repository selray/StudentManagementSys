<template>
  <div>
  <el-card style="width: 500px">
<!--    根据实际表格情况，进行增删-->
    <el-form label-width="80px" size="small">
      <el-upload
          style="text-align: center"
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
<!--        暂时没有头像，因为新增头像需要添加数据库里面-->
        <img v-if="form.avatarurl" :src="form.avatarurl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>



      <el-form-item label="学号">
        <el-input v-model="form.studentid" autocomplete="off" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex" autocomplete="off" />
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确认</el-button>
      </el-form-item>
    </el-form>

  </el-card>
  </div>
</template>

<script>


export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")) : {}
    }
  },

    // 这里暂时写的是学生，后面需要更换成别的端口
    created() {
      this.getUser().then(res =>{
        this.form = res
      })
    },
  methods:{
    async getUser(){
      return  (await this.request.get("/student/studentid/" + this.user.studentid)).data
    },
    save(){
      //发送数据到后端
      //this.$message.success("保存信息："+this.form.avatarUrl)
      this.request.post("/student",this.form).then(res => {
            if(res){
              this.$message.success("保存成功")
              //触发父级更新user的方法
              this.$emit("refreshUser")
              //保存之后，触发manage的父级，通过父级中的功能来实现更新以及右上角头像的更新


              //更新浏览器存储信息
              this.getUser().then(res =>{
                res.token  = JSON.parse(localStorage.getItem("loguserinfo")).token
                //localStorage.removeItem("loguserinfo")
                localStorage.setItem("loguserinfo",JSON.stringify(user))
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
<style>
.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload{
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
  width: 158px;
  height: 158px;
  line-height: 158px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}


</style>