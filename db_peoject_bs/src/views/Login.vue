<template>
    <div class="wrapper">
      <div style="margin: 200px auto; background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius:10px ">
        <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>登 录</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="username">
            <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
          </el-form-item>
          <div style="margin: 10px 0;text-align: center">
            <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
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
                  this.$router.push("/manage")  //跳转到老师登录界面
                  this.$message.success("老师登录成功")
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
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC466B , #3F5EFB);
    overflow: hidden;
  }

</style>