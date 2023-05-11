<template>
  <el-container style="min-height: 100vh;">

    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);min-height: 100%; box-shadow: 2px 0 6px rgb(0 21 41 /35%)">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow " :user="user"/>
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" :user="user"></Header>
      </el-header>

      <el-main>
<!--        //表示当前页面的子路由会在rooter-view中展示-->
<!--        通过这个刷新函数来实现，header头像中的及时刷新-->
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>


import Aside from "@/components/managerComponent/Aside.vue";
import Header from "@/components/managerComponent/Header.vue";
export default {
  name: 'HomeView',

  data() {

    return{

      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}

    }
  },
  components:{
    Aside,
    Header
  },
  created() {
    //从后台获取新数据
    this.getUser()
  },
  methods: {
    collapse(){ //点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser(){
      //从后台获取数据
      let mnumber = localStorage.getItem("loguserinfo")?JSON.parse(localStorage.getItem("loguserinfo")).mnumber:""
      this.request.get("/manager/" + mnumber).then(res =>{
      //重新复制后台的最新数据
        this.user = res
      })
    }

  }
}
</script>
