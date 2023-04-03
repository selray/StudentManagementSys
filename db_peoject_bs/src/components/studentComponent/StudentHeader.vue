<template>
  <div style=" line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;  font-size: 18px;" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/studentmanage/home'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 200px; cursor: pointer;text-align: right">
      <div style="display: inline-block">
        <!--      头像功能，15节内容-->
        <img :src="user.avatarurl" alt="" style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
        <span>{{ user.studentid }}</span><span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
        <!--      这里的参数是后台.controller.dto中userdto的内容-->

        <!--    <i class="el-icon-setting" style="margin-right: 15px"></i>-->
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <router-link to="/studentmanage/personinfo" >个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: Boolean,
    user: Object,

  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  watch:{  //监听路由变化
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  data(){
    return{
      // user: localStorage.getItem("loguserinfo") ? JSON.parse(localStorage.getItem("loguserinfo")):{}
    }
  },

  methods: {
    //退出登录，清除浏览器中的当前用户数据并且转化路由
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("loguserinfo")
      this.$message.success("退出成功")
    },
    // reload(){
    //   this.$message.success("移出前："+ user.avatarurl)
    //   localStorage.removeItem("loguserinfo")
    //   this.$message.warning("更新后："+ user.avatarurl)
    //   localStorage.getItem("loguserinfo")
    // },
  },





}
</script>

<style scoped>

</style>