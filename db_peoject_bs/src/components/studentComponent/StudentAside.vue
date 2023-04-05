<template>
  <el-menu :default-openeds="['1', '3']" style = "min-height : 100%; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px">

      <b style="color: white" v-show="logoTextShow">学生教务管理系统</b>
    </div>
    <el-menu-item index="/studentmanage/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">学生选课</span>
      </template>
      <el-menu-item index="/studentmanage/selecourse">
        <i class="el-icon-s-custom"></i>
        <span slot="title">选课</span>
      </el-menu-item>
      <!--    后面针对其他角色需要更改路由  -->
      <el-menu-item index="/studentmanage/cancelclass">
        <i class="el-icon-document"></i>
        <span slot="title">退课</span>
      </el-menu-item>
      <el-menu-item index="/studentmanage/classtable">
        <i class="el-icon-document"></i>
        <span slot="title">课表查询</span>
      </el-menu-item>
      <el-menu-item index="/studentmanage/courseinquire">
        <i class="el-icon-document"></i>
        <span slot="title">课程查询</span>
      </el-menu-item>
      <el-menu-item index="/studentmanage/file">
        <i class="el-icon-document"></i>
        <span slot="title">文件管理</span>
      </el-menu-item>
    </el-submenu>
    <!--    <el-submenu index="3">-->
    <!--      <template slot="title"><i class="el-icon-setting"></i>-->
    <!--        <span slot="title">导航三</span>-->
    <!--      </template>-->
    <!--      <el-menu-item-group>-->
    <!--        <template slot="title">分组一</template>-->
    <!--        <el-menu-item index="3-1">选项1</el-menu-item>-->
    <!--        <el-menu-item index="3-2">选项2</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-menu-item-group title="分组2">-->
    <!--        <el-menu-item index="3-3">选项3</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-submenu index="3-4">-->
    <!--        <template slot="title">选项4</template>-->
    <!--        <el-menu-item index="3-4-1">选项4-1</el-menu-item>-->
    <!--      </el-submenu>-->
    <!--    </el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods:{
    handleSelect(){
      // console.log(this.$router.options.routes)
      //this.$router.history.fullPath.split("/")
    }
  }
}
</script>

<style scoped>

</style>