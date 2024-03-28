<template>
  <div>
    <Header />
    <div class="pay-content">
      <div class="title">订单提交成功</div>
      <!--      <div class="text time-margin">-->
      <!--        <span>请在 </span>-->
      <!--        <span class="time">{{ ddlTime }}</span>-->
      <!--        <span> 之前付款，超时订单将自动取消</span>-->
      <!--      </div>-->
      <div class="text">支付金额</div>
      <div class="price">
        <span class="num">{{ amount }}</span>
        <span>元</span>
      </div>
      <div class="pay-choose-view" style="">
        <div class="pay-choose-box flex-view">
          <!--          <div class="choose-box choose-box-active">-->
          <div class="choose-box" :class="{ 'choose-box-active': selectedPayment === 'wechat' }" @click="selectPayment('wechat')">
            <img :src="WxPayIcon" />
            <span>微信支付</span>
          </div>
          <!--          <div class="choose-box">-->
          <div class="choose-box" :class="{ 'choose-box-active': selectedPayment === 'alipay' }" @click="selectPayment('alipay')">
            <img :src="AliPayIcon" />
            <span>支付宝</span>
          </div>
        </div>
        <div class="tips">请选择任意一种支付方式</div>
        <!--        <div class="tips">userId:{{ userId }}</div>-->
        <!--        <div class="tips">thingId:{{ thingId }}</div>-->
        <!--        <button class="pay-btn pay-btn-active" @click="handlePay()">确认支付</button>-->
        <button class="pay-btn pay-btn-active" @click="showQRCode()">去支付</button>
      </div>

      <!-- 弹窗 -->
      <div class="pay-choose-view" style="">
        <div v-if="showModal" class="modal">
          <div class="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <img :src="paymentQRCode" alt="支付二维码" />
            <div class="button-container">
              <button class="pay-btn pay-btn-left pay-btn-active" @click="handlePay()">确认支付</button>
              <div class="spacer"></div>
              <button class="pay-btn pay-btn-right close-btn" @click="closeModal">关闭弹窗</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import Header from '/@/views/index/components/header.vue';
  import { message } from 'ant-design-vue';
  import WxPayIcon from '/@/assets/images/wx-pay-icon.svg';
  import AliPayIcon from '/@/assets/images/ali-pay-icon.svg';
  import { updateOrderPayStatusApi } from '/@/api/order';
  import router from '/@/router';

  const route = useRoute();

  let ddlTime = ref();
  let amount = ref();
  let userId = ref();
  let thingId = ref();
  let count = ref();

  onMounted(() => {
    amount.value = route.query.amount;
    userId.value = route.query.userId;
    thingId.value = route.query.thingId;
    count.value = route.query.count;
    ddlTime.value = formatDate(new Date().getTime(), 'YY-MM-DD hh:mm:ss');
  });
  const selectedPayment = ref('wechat');
  const icons = {
    wechat: '/@/assets/images/wx-pay-icon.svg',
    alipay: '/@/assets/images/ali-pay-icon.svg',
  };
  const wechatQRCode = '/src/assets/images/logo12.png';
  const aliPayQRCode = '/src/assets/images/logo.png';

  const showModal = ref(false);

  let selectPayment = (paymentMethod) => {
    selectedPayment.value = paymentMethod;
    // 在这里可以添加处理不同支付方式的逻辑
  };

  // 计算属性，根据选择的支付方式返回对应的二维码路径
  const paymentQRCode = computed(() => {
    if (selectedPayment.value === 'wechat') {
      return wechatQRCode;
    } else if (selectedPayment.value === 'alipay') {
      return aliPayQRCode;
    }
  });

  const showQRCode = () => {
    showModal.value = true;
  };

  const closeModal = () => {
    showModal.value = false;
  };

  const handlePay = () => {
    console.log('userId：' + userId.value);
    console.log('thingId：' + thingId.value);
    const data = {
      userId: userId.value,
      thingId: thingId.value,
      count: count.value,
    };
    updateOrderPayStatusApi(data)
      .then((res) => {
        message.success('支付成功');
        router.push({ name: 'orderView' });
      })
      .catch((err) => {
        message.error(err.msg || '支付失败');
      });
  };
  const formatDate = (time, format = 'YY-MM-DD hh:mm:ss') => {
    const date = new Date(time);

    const year = date.getFullYear(),
      month = date.getMonth() + 1,
      day = date.getDate() + 1,
      hour = date.getHours(),
      min = date.getMinutes(),
      sec = date.getSeconds();
    const preArr = Array.apply(null, Array(10)).map(function (elem, index) {
      return '0' + index;
    });

    const newTime = format
      .replace(/YY/g, year)
      .replace(/MM/g, preArr[month] || month)
      .replace(/DD/g, preArr[day] || day)
      .replace(/hh/g, preArr[hour] || hour)
      .replace(/mm/g, preArr[min] || min)
      .replace(/ss/g, preArr[sec] || sec);

    return newTime;
  };
</script>

<style scoped lang="less">
  .flex-view {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
  }

  .pay-content {
    position: relative;
    margin: 120px auto 0;
    width: 500px;
    background: #fff;
    overflow: hidden;

    .title {
      color: #152844;
      font-weight: 500;
      font-size: 24px;
      line-height: 22px;
      height: 22px;
      text-align: center;
      margin-bottom: 11px;
    }

    .time-margin {
      margin: 11px 0 24px;
    }

    .text {
      height: 22px;
      line-height: 22px;
      font-size: 14px;
      text-align: center;
      color: #152844;
    }

    .time {
      color: #f62a2a;
    }

    .text {
      height: 22px;
      line-height: 22px;
      font-size: 14px;
      text-align: center;
      color: #152844;
    }

    .price {
      color: #ff8a00;
      font-weight: 500;
      font-size: 16px;
      height: 36px;
      line-height: 36px;
      text-align: center;

      .num {
        font-size: 28px;
      }
    }

    .pay-choose-view {
      margin-top: 24px;

      .choose-box {
        width: 140px;
        height: 126px;
        border: 1px solid #cedce4;
        border-radius: 4px;
        text-align: center;
        cursor: pointer;
      }

      .pay-choose-box {
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
        max-width: 300px;
        margin: 0 auto;

        img {
          height: 40px;
          margin: 24px auto 16px;
          display: block;
        }
      }

      .tips {
        color: #6f6f6f;
        font-size: 14px;
        line-height: 22px;
        height: 22px;
        text-align: center;
        margin: 16px 0 24px;
      }

      .choose-box-active {
        border: 1px solid #4684e2;
      }

      .tips {
        color: #6f6f6f;
        font-size: 14px;
        line-height: 22px;
        height: 22px;
        text-align: center;
        margin: 16px 0 24px;
      }

      .pay-btn {
        cursor: pointer;
        background: #c3c9d5;
        border-radius: 32px;
        width: 104px;
        height: 32px;
        line-height: 32px;
        border: none;
        outline: none;
        font-size: 14px;
        color: #fff;
        text-align: center;
        display: block;
        margin: 0 auto;
      }

      .pay-btn-active {
        background: #4684e2;
      }
    }
  }
  .choose-box {
    cursor: pointer;
  }

  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* 半透明背景 */
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
  }

  .close {
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
  }
  .pay-btn {
    margin-top: 10px; /* 调整按钮与图片之间的间距 */
  }

  .close-btn {
    margin-top: 10px;
  }
  .pay-btn-right {
    float: right;
  }
  .pay-btn-left {
    float: left;
  }
</style>
