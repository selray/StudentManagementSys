<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden;"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <div style="height: 60px;line-height: 60px;text-align: center;">
      <img src="../../assets/logo.png" alt="" style="width: 20px;position: relative;top:5px;margin-right: 5px;">
      <b style="color: white;" v-show="!isCollapse">选课管理系统</b>
    </div>

    <el-menu-item index="/teachermanage/home">
      <template >
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/teachermanage/class">
        <i class="el-icon-s-custom"></i>
        <span slot="title">成绩录入</span>
      </el-menu-item>
      <el-menu-item index="/teachermanage/classtable">
        <i class="el-icon-document"></i>
        <span slot="title">课表查询</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean
  },
  methods:{
    handleSelect(index){
      // console.log(index)
      // alert(this.$route.name)
    }
  }
}
</script>

<style scoped>

</style>