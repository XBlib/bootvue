<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #42b983;width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 10px 0;text-align: right">
        <el-button @click="login" type="primary" size="small" autocomplete="off">登录</el-button>
        <el-button type="warning" size="small" autocomplete="off">注册</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: { },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          console.log("这里")
          this.request.post("/user/login", this.user).then(res => {
            if (!res) {
              this.$message.error("用户名或密码错误")
            } else {
              this.$router.push("/main")
            }
          })
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
  .wrapper {
    height: 100vh;
    /*background-image: linear-gradient(to bottom right,#FC466B,#42b983);*/
    overflow: hidden;

  }
</style>