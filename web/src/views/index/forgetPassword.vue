<template>
  <div class="container">
    <div class="tel-regist-page pc-style">
      <img :src="Logo1" alt="logo" class="logo-icon" />
      <div class="login-tab">
        <div class="tab-selected">
          <span>忘记密码</span>
          <span class="tabline tabline-width"></span>
        </div>
      </div>

      <div class="regist-padding">
        <div class="common-input">
          <img :src="UserIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入账号" v-model="tData.loginForm.username" type="text" class="input" />
            <p class="err-view"> </p>
          </div>
        </div>
      </div>
      <div class="regist-padding">
        <div class="common-input">
          <img :src="MailIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入邮箱" v-model="tData.loginForm.email" type="text" class="input" />
            <p class="err-view"> </p>
          </div>
        </div>
      </div>
      <div class="regist-padding">
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入新密码" v-model="tData.loginForm.password" type="password" class="input" />
            <p class="err-view"> </p>
          </div>
        </div>
      </div>
      <div class="regist-padding">
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请再次输入新密码" v-model="tData.loginForm.repassword" type="password" class="input" />
            <p class="err-view"> </p>
          </div>
        </div>
      </div>
      <div class="tel-login">
        <div class="next-btn-view">
          <button class="next-btn" @click="handleRegister">重置</button>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <button class="next-btn" @click="back">返回</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import MailIcon from '/@/assets/images/mail-icon.svg';
  import PwdIcon from '/@/assets/images/pwd-icon.svg';
  import UserIcon from '/@/assets/images/user-icon.svg';
  import { getUserDetailByUsernameAndEmailApi, updateUserPwdApi } from '/@/api/user';
  import Logo1 from '/@/assets/images/logo1.png';

  const router = useRouter();

  const tData = reactive({
    loginForm: {
      username: '',
      password: '',
      repassword: '',
      email: '',
    },
  });
  const back = () => {
    router.push({ name: 'login' });
  };

  const handleRegister = () => {
    console.log('login');
    if (
      tData.loginForm.username === '' ||
      tData.loginForm.password === '' ||
      tData.loginForm.repassword === '' ||
      tData.loginForm.email === ''
    ) {
      message.warn('不能为空！');
      return;
    }
    if (tData.loginForm.password !== tData.loginForm.repassword) {
      message.warn('密码不一致！');
      return;
    }
    getUserDetailByUsernameAndEmailApi({
      username: tData.loginForm.username,
      email: tData.loginForm.email,
    })
      .then((res) => {
        console.log(res.data.id);
        if (res.data != null) {
          updateUserPwdApi({
            userId: res.data.id,
            password: tData.loginForm.password,
            newPassword: tData.loginForm.repassword,
          })
            .then((res) => {
              message.success('修改成功');
              router.push({ name: 'login' });
            })
            .catch((err) => {
              message.error(err.msg);
            });
        } else {
          message.warn('修改失败');
        }
      })
      .catch((err) => {
        message.warn(err.msg || '重置失败');
      });
  };
</script>

<style scoped lang="less">
  div {
    display: block;
  }

  *,
  :after,
  :before,
  img {
    border-style: none;
  }

  *,
  :after,
  :before {
    border-width: 0;
    border-color: #dae1e7;
  }

  .container {
    max-width: 100%;
    //background: #142131;
    background-image: url('../images/admin-login-bg.jpg');
    background-size: cover;
    object-fit: cover;
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .pc-style {
    position: relative;
    width: 400px;
    height: 464px;
    background: #fff;
    -webkit-box-shadow: 2px 2px 6px #aaa;
    box-shadow: 2px 2px 6px #aaa;
    border-radius: 4px;
  }

  .tel-regist-page {
    overflow: hidden;

    .regist-title {
      font-size: 14px;
      color: #1e1e1e;
      font-weight: 500;
      height: 24px;
      line-height: 24px;
      margin: 40px 0;
      padding: 0 28px;

      .toWxLogin {
        color: #3d5b96;
        float: right;
        cursor: pointer;
      }
    }

    .regist-padding {
      padding: 0 28px;
      margin-bottom: 8px;
    }
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
        padding: 0;
        display: block;
        width: 100%;
        letter-spacing: 1.5px;
        outline: none; // 去掉边框线
      }

      .err-view {
        margin-top: 4px;
        height: 16px;
        line-height: 16px;
        font-size: 12px;
        color: #f62a2a;
      }
    }
  }

  .tel-login {
    padding: 0 28px;
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

  .next-btn-view {
    display: flex;
    justify-content: space-between; /* 让按钮靠两边 */
  }

  //.next-btn {
  //  padding: 5px 10px; /* 按钮内边距 */
  //}

  .logo-icon {
    margin-top: 20px;
    //margin-left: 175px;
    //width: 48px;
    //height: 48px;
    width: 200px;
    height: 65px;
    margin-left: 100px;
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
    margin-bottom: 10px;
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
  }

  tab-selected {
    color: #1e1e1e;
    font-weight: 500;
  }
</style>
