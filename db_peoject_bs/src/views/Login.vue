<template>

    <div class="login_container">
      <div class="login_box">
        <div class="login_form">
      <div style="margin: 20px auto; width: 350px;height: 300px;padding: 20px;border-radius:10px ">
        <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>登 录</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="username">
            <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
          </el-form-item>
          <div style="margin: 10px 0;text-align: center">
            <el-button type="primary" size="medium" autocomplete="off" @click="login">登录</el-button>
            <el-button type="warning" size="medium" autocomplete="off" @click="$router.push('/register')">注册</el-button>
          </div>
        </el-form>
      </div>
        </div>
        </div>
      <vue-particles
          color="#DCDCDC"
          :particleOpacity="0.7"
          :particlesNumber="60"
          shapeType="circle"
          :particleSize="6"
          linesColor="#DCDCDC"
          :linesWidth="1"
          :lineLinked="true"
          :lineOpacity="0.4"
          :linesDistance="150"
          :moveSpeed="3"
          :hoverEffect="true"
          hoverMode="grab"
          :clickEffect="true"
          clickMode="push">
      </vue-particles>
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
        ]
      }
    }
  },
  methods:{
    login(){
      //进行输入数据的校验，如果数据不合法就不会向后端发送请求
      this.$refs['userForm'].validate((valid) => {
        if (valid){ //表单校验合法

          //第一次判断是否是学生登录
          this.request.post("/student/login",this.user).then(res =>{
            if(res.code == '200'){   //判断是否是自己定义的异常处理，这里是指数据库验证成功
              localStorage.setItem("loguserinfo",JSON.stringify(res.data)) //登录成功后，将用户信息存放在浏览器中，以便于其他操作
              this.$router.push("/studentmanage") //跳转到学生登录界面
              this.$message.success("学生登录成功")
            }
            else{ //验证同学身份失败，转而验证老师
              //this.$message.error(res.msg)
              this.request.post("/teacher/login",this.user).then(res =>{
                if(res.code == '200'){   //判断是否是自己定义的异常处理，这里是指数据库验证成功
                  localStorage.setItem("loguserinfo",JSON.stringify(res.data)) //登录成功后，将用户信息存放在浏览器中，以便于其他操作
                  this.$router.push("/teachermanage")  //跳转到老师登录界面
                  this.$message.success("教师登录成功")
                }
                else{ //验证老师身份失败，转而验证管理员
                  //this.$message.error(res.msg)
                  this.request.post("/manager/login",this.user).then(res =>{
                    if(res.code == '200'){   //判断是否是自己定义的异常处理，这里是指数据库验证成功
                      localStorage.setItem("loguserinfo",JSON.stringify(res.data)) //登录成功后，将用户信息存放在浏览器中，以便于其他操作
                      this.$router.push("/manage") //跳转到管理员登录界面
                      this.$message.success("管理员登录成功")
                    }
                    else{ //验证失败
                      this.$message.error(res.msg)
                      //this.$message.success("用户名或密码错误")
                    }
                  })
                }
              })
            }
          })
        }
      })

    }
  }

}
</script>

<style>
.login_container {
  /*background-image: linear-gradient(-180deg, #1a1454 0%, #0e81a5 100%);*/
  background-image: url('~@/assets/images/BackGround2.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  overflow: hidden;
  height: 100vh;
}
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC466B , #3F5EFB);
    overflow: hidden;
  }
.login_box {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  box-shadow: 10px 10px 20px 0px rgba(0, 0, 0, 0.7);
  background-color: rgba(255, 255, 255, 0.3);
}
.login_form{
  /*background-color: #fff;*/
  /*width: 600px;*/
  /*height: 320px;*/
  /*padding: 20px;*/
  /*border-radius: 10px;*/
  /*position: absolute;*/
  /*background-color: rgba(0, 0, 0, 0.3);*/
  /*left: 50%;*/
  /*transform: translate(-50%, -50%);*/
  /*z-index: 2;*/
  /*box-shadow: 10px 10px 20px 0px rgba(0, 0, 0, 0.7);*/
  /*text-align: center;*/
}
</style>