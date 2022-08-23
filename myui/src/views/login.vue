<template>
  <!--登录模块-->
  <div class="login-box">
    <el-form class="login-form" :model="loginInfo" ref="loginInfo" :rules="loginRules">
      <h3 class="title">后台管理系统</h3>

      <!--用户名-->
      <el-form-item prop="userName">
        <el-input prefix-icon="iconfont icon-yonghu" v-model="loginInfo.userName" placeholder="请输入账号"></el-input>
      </el-form-item>

      <!--密码-->
      <el-form-item prop="password">
        <el-input prefix-icon="iconfont icon-mima" v-model="loginInfo.password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <!--验证码-->
      <el-form-item prop="code">
        <el-input prefix-icon="iconfont icon-yanzhengma" class="login-code-input" v-model="loginInfo.code"
                  placeholder="请输入验证码"></el-input>
        <div class="login-code-div">
          <img src=""/>
        </div>

      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="loginBtn">登录</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import {request} from '../network/request'
import axios from "axios";

export default {
  name: "login",
  data() {
    return {
      loginInfo: {
        // 用户名
        userName: '',
        // 密码
        password: '',
        // 验证码
        code: '',
        // 随机数，用于获取redis中code
        uuid: '',
      },

      loginRules: {
        userName: [
          {required: true, trigger: "blur", message: "请输入您的账号"}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"}
        ],
        code: [
          {required: true, trigger: "change", message: "请输入验证码"}
        ]
      },
    }
  },

  created() {
    this.getCode();
  },

  methods: {
    // 点击登录按钮
    loginBtn() {
      this.$refs.loginInfo.validate(valid => {
        // 验证不符合格式
        if (!valid) {
          return;
        }
      })
    },

    //获取验证码
    getCode() {
      request({url: '/api/captchaImage', method: 'GET'}).then(result => {

      })
    }
  }
}
</script>

<style lang="scss" scoped>

.login-box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: url("../assets/images/login-bg.jpg");
  background-size: cover;

  h3 {
    margin-bottom: 24px;
    font-weight: normal;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;

    .el-form-item {
      width: 80%;
      margin: 0px auto 25px auto;
    }

    .login-code-input {
      float: left;
      width: 63%;
    }

    .login-code-div {
      float: right;
      width: 33%;
    }

    .el-button {
      width: 100%;
    }
  }
}


</style>