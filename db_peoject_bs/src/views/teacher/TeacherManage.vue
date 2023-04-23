<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41 / 35%);" >
      <Aside :isCollapse="isCollapse"/>  <!-- 组件引用，传参-->
    </el-aside>

    <el-container>
      <el-header style=" border-bottom: 1px solid #ccc">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
      </el-header>


      <el-main>
        <!-- 表示当前页面子路由会在router-view里面展示-->
        <router-view />
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/teacherComponent/TeacherAside.vue";
import Header from "@/components/teacherComponent/TeacherHeader.vue";

export default {
  name: 'Home',
  data() {
    return{
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,
    }
  },
  components: {
    Aside,
    Header
  },
  methods:{  //收缩按钮触发
    collapse(){  //收缩
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=64
        this.collapseBtnClass="el-icon-s-unfold"
      }else{  //展开
        this.sideWidth=200
        this.collapseBtnClass="el-icon-s-fold"
      }
    }
  }
}
</script>

