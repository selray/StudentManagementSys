<template>
    <div class="wrapper">
      <div style="margin: 100px auto; background-color: #fff;width: 350px;height: 400px;padding: 20px;border-radius:10px ">
        <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>注 册</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="username">
            <el-input placeholder="请输入账号"size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input placeholder="请输入密码"size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="comfirmPassword">
            <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
          </el-form-item>
          <div style="margin: 10px 0;text-align: center">
            <el-button type="primary" size="small" autocomplete="off" @click="login">确定</el-button>
            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
          </div>
        </el-form>

        </div>
      </div>

</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      user:{},
      rules:{
        username:[
          {required:true, message:'请输入账号', trigger:'blur'},
          // {min:3,max:5,message: '长度在3到5个字符之间',trigger:'blur'}
        ],
        password:[
          {required:true, message:'请输入密码', trigger:'blur'},
        ],
        comfirmPassword:[
          {required:true, message:'请输入密码', trigger:'blur'},
        ]
      }
    }
  },
  methods:{
    login(){
      //进行输入数据的校验，如果数据不合法就不会向后端发送请求
      this.$refs['userForm'].validate((valid) => {
        if (valid){ //表单校验合法
          //校验两个密码是否一致
            if (this.user.password !== this.user.comfirmPassword){
              this.$message.error("两次密码输入不一致")
              return false
            }
          this.request.post("/student/register",this.user).then(res =>{
            if(res.code == '200'){   //判断是否是自己定义的异常处理
              this.$message.success("注册成功")
            }
            else{
              this.$message.error(res.msg)
            }
          })
        }
      })

    }
  }

}
</script>

<style>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC466B , #3F5EFB);
    overflow: hidden;
  }

</style>