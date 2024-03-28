<template>
  <div class="container">
    <div class="login-page pc-style">
      <img :src="Logo1" alt="logo" class="logo-icon" />
      <div class="login-tab">
        <div class="tab-selected">
          <span>欢迎登录</span>
          <span class="tabline tabline-width"></span>
        </div>
      </div>
      <div class="mail-login" type="login">
        <div class="common-input">
          <img :src="UserIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入账号" v-model="pageData.loginForm.username" type="text" class="input" />
            <p class="err-view"> </p>
          </div>
          <!---->
        </div>
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入密码" v-model="pageData.loginForm.password" type="password" class="input" />
            <p class="err-view"> </p>
          </div>
        </div>
        <div class="common-input">
          <img :src="Yanzheng" class="left-icon" />
          <input placeholder="请输入验证码" v-model="sidentifyMode" type="text" class="input" />
          <!-- 使用验证码组件 -->
          <div class="code" @click="refreshCode">
            <SIdentify :identifyCode="identifyCode" />
          </div>
        </div>
        <div class="next-btn-view">
          <button class="next-btn btn-active" style="margin: 16px 0" @click="handleLogin">登录</button>
        </div>
      </div>
      <div class="operation">
        <a @click="handleCreateUser" class="forget-pwd" style="text-align: left">注册新帐号</a>
        <a class="forget-pwd" style="text-align: right" @click="forgetPassword">忘记密码</a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useUserStore } from '/@/store';
  import { message } from 'ant-design-vue';
  import LogoIcon from '/@/assets/images/k-logo.png';
  import Logo12 from '/@/assets/images/logo12.png';
  import Logo1 from '/@/assets/images/logo1.png';
  import PwdIcon from '/@/assets/images/pwd-icon.svg';
  import Yanzheng from '/@/assets/images/yanzheng.svg';
  import UserIcon from '/@/assets/images/user-icon.svg';

  import SIdentify from '/@/views/index/components/Sidentify.vue';
  let sidentifyMode = ref(''); //输入框验证码
  let identifyCode = ref(''); //图形验证码
  let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz'); //验证码出现的数字和字母

  const router = useRouter();
  const userStore = useUserStore();

  const pageData = reactive({
    loginForm: {
      username: '',
      password: '',
    },
  });

  onMounted(() => {
    identifyCode.value = '';
    makeCode(identifyCodes.value, 4);
  });

  // 生成随机数
  const randomNum = (min, max) => {
    max = max + 1;
    return Math.floor(Math.random() * (max - min) + min);
  };
  // 随机生成验证码字符串
  const makeCode = (o, l) => {
    for (let i = 0; i < l; i++) {
      identifyCode.value += o[randomNum(0, o.length)];
    }
  };
  // 更新验证码
  const refreshCode = () => {
    identifyCode.value = '';
    makeCode(identifyCodes.value, 4);
  };

  const handleLogin = () => {
    //验证验证码不为空
    if (!sidentifyMode.value) {
      message.error('验证码不能为空');
      console.log('验证码为空');
      return;
    }
    //验证验证码是否正确
    if (sidentifyMode.value != identifyCode.value) {
      message.error('验证码错误');
      console.log('验证码错误，输入为：' + identifyCode.value);
      refreshCode();
      return;
    } else {
      userStore
        .login({
          username: pageData.loginForm.username,
          password: pageData.loginForm.password,
        })
        .then((res) => {
          if (userStore.user_status == '0') {
            loginSuccess();
            console.log('success==>', userStore.user_name);
            console.log('success==>', userStore.user_id);
            console.log('success==>', userStore.user_token);
            console.log('账号状态' + userStore.user_status);
            console.log('用户头像图片：' + userStore.user_avatar);
          } else {
            console.log('账号状态' + userStore.user_status);
            message.warn('账号被封禁，请联系管理员');
          }
        })
        .catch((err) => {
          message.warn(err.msg || '登录失败');
        });
    }
  };

  const handleCreateUser = () => {
    router.push({ name: 'register' });
  };
  const forgetPassword = () => {
    router.push({ name: 'forgetPassword' });
  };

  const loginSuccess = () => {
    router.push({ name: 'portal' });
    message.success('登录成功！');
  };
</script>
<style scoped lang="less">
  div {
    display: block;
  }

  .container {
    //background-color: #f1f1f1;
    background-image: url('../images/admin-login-bg.jpg');
    background-size: cover;
    object-fit: cover;
    height: 100%;
    max-width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .new-content {
    position: absolute;
    left: 0;
    right: 0;
    margin: 80px auto 0;
    width: 980px;
  }

  .logo-img {
    width: 125px;
    display: block;
    margin-left: 137.5px;
  }

  .login-page {
    overflow: hidden;
    background: #fff;

    .logo-icon {
      margin-top: 20px;
      //margin-left: 175px;
      //width: 48px;
      //height: 48px;
      width: 200px;
      height: 65px;
      margin-left: 100px;
    }
  }

  .pc-style {
    position: relative;
    width: 400px;
    height: 464px;
    background: #fff;
    border-radius: 4px;
    -webkit-box-shadow: 2px 2px 6px #aaa;
    box-shadow: 2px 2px 6px #aaa;
  }

  .login-tab {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    color: #1e1e1e;
    font-size: 14px;
    color: #1e1e1e;
    font-weight: 500;
    height: 46px;
    line-height: 44px;
    margin-bottom: 40px;
    border-bottom: 1px solid #c3c9d5;

    div {
      position: relative;
      -webkit-box-flex: 1;
      -ms-flex: 1;
      flex: 1;
      text-align: center;
      cursor: pointer;
    }

    .tabline {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      margin: 0 auto;
      display: inline-block;
      width: 0;
      height: 2px;
      background: #3d5b96;
      -webkit-transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
      transition: width 0.5s cubic-bezier(0.46, 1, 0.23, 1.52);
    }

    tab-selected {
      color: #1e1e1e;
      font-weight: 500;
    }

    .mail-login,
    .tel-login {
      padding: 0 28px;
    }
  }

  .mail-login {
    margin: 0px 24px;
  }

  .common-input {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: start;
    -ms-flex-align: start;
    align-items: flex-start;

    .left-icon {
      margin-right: 12px;
      width: 24px;
    }

    .input-view {
      -webkit-box-flex: 1;
      -ms-flex: 1;
      flex: 1;

      .input {
        font-weight: 500;
        font-size: 14px;
        color: #1e1e1e;
        height: 26px;
        line-height: 26px;
        border: none;
        padding: 0;
        display: block;
        width: 100%;
        letter-spacing: 1.5px;
      }

      err-view {
        margin-top: 4px;
        height: 16px;
        line-height: 16px;
        font-size: 12px;
        color: #f62a2a;
      }
    }
  }

  .next-btn {
    background: #3d5b96;
    border-radius: 4px;
    color: #fff;
    font-size: 14px;
    font-weight: 500;
    height: 40px;
    line-height: 40px;
    text-align: center;
    width: 100%;
    outline: none;
    cursor: pointer;
  }

  button {
    background: transparent;
    padding: 0;
    border-width: 0px;
  }

  button,
  input,
  select,
  textarea {
    margin: 0;
    padding: 0;
    outline: none;
  }

  .operation {
    display: flex;
    flex-direction: row;
    margin: 0 24px;
  }

  .forget-pwd {
    //text-align: center;
    display: block;
    overflow: hidden;
    flex: 1;
    margin: 0 auto;
    color: #3d5b96;
    font-size: 14px;
    cursor: pointer;
  }
</style>
