<template>
<div style=" line-height: 60px; display: flex">
  <div style="flex: 1;">
    <span :class="collapseBtnClass" style="cursor: pointer;  font-size: 18px;" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/manage/home'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
  </div>

  <el-dropdown style="width: 200px; cursor: pointer;text-align: right">
    <div style="display: inline-block">
<!--      头像功能，15节内容-->
      <img :src="user.avatarurl" alt="" style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
      <span>{{ user.mname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
<!--      这里的参数是后台.controller.dto中userdto的内容-->
      <!--    <i class="el-icon-setting" style="margin-right: 15px"></i>-->
    </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
        <router-link to="/manage/information" style="text-decoration: none" >个人信息</router-link>
      </el-dropdown-item>
      <span @click="logout">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0" >退出</el-dropdown-item>
      </span>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: '',

  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;      //需要监听的数据
    }
  },
  watch:{  //监听路由变化
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  data(){
    return{
      user:localStorage.getItem("loguserinfo")? JSON.parse(localStorage.getItem("loguserinfo")):{}
    }
  },
  created() {
    this.getUser().then(res =>{
      this.form = res
    })
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("loguserinfo")
      this.$message.success("退出成功")
    }
  }





}
</script>

<style scoped>

</style>