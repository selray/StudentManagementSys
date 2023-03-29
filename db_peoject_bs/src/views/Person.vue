<template>
  <div>
  <el-card style="width: 500px">
<!--    根据实际表格情况，进行增删-->
    <el-form label-width="80px" size="small">
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
    created() {
    this.request.get("/student/studentid/" + this.user.studentid).then(res => {
      if(res.code === '200'){
        this.form = res.data
      }
    })
    },
  methods:{
    save(){
      //发送数据到后端
      this.request.post("/student",this.form)
          .then(res => {
            if(res){
              this.$message.success("保存成功")
            }else {
              this.$message.error("保存失败")
            }
          })
    }
  }
}
</script>

<style scoped>

</style>