<template>
  <div class="content-list">
    <div class="list-title">我的订单</div>
    <!--    default-active-key="1"-->
    <a-tabs v-model="activeKey" @change="onTabChange">
      <a-tab-pane key="1" tab="全部" />
      <a-tab-pane key="2" tab="待付款" />
      <a-tab-pane key="3" tab="已支付" />
      <a-tab-pane key="4" tab="我收到的订单" />
    </a-tabs>
    <div v-if="activeKey !== '4'">
      <div class="list-content">
        <div class="order-item-view" v-for="(item, index) in orderData" :key="index">
          <div class="header flex-view">
            <div class="left">
              <span class="text">订单号</span>
              <span class="num mg-4">#</span>
              <span class="num">{{ item.orderNumber }}</span>
              <span class="time">{{ getFormatTime(item.orderTime, true) }}</span>
            </div>
            <div class="right">
              <a-popconfirm v-if="item.status === '1'" title="确定取消订单？" ok-text="是" cancel-text="否" @confirm="handleCancel(item)">
                <a-button type="primary" size="small" style="margin-right: 24px">取消</a-button>
              </a-popconfirm>
              <span class="text">订单状态</span>
              <span class="state">{{ item.status === '1' ? '待支付' : item.status === '2' ? '已支付' : '已取消' }}</span>
            </div>
          </div>
          <div class="content flex-view">
            <div class="left-list">
              <div class="list-item flex-view">
                <img :src="item.cover" class="thing-img" />
                <div class="detail flex-between flex-view">
                  <div class="flex-between flex-top flex-view">
                    <h2 class="name">{{ item.title }}</h2>
                    <span class="count">x{{ item.count }}</span>
                  </div>
                  <div class="flex-between flex-center flex-view">
                    <span class="type"></span>
                    <span class="price">¥{{ item.price }}</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="right-info">
              <p class="title">收货信息</p>
              <p class="name">{{ item.receiverName }} {{ item.receiverPhone }} </p>
              <p class="text mg">{{ item.receiverAddress }} </p>
              <p class="title">快递单号：</p>
              <p class="text"> </p>
              <p class="title">备注信息：</p>
              <p class="text">{{ item.remark }} </p>
            </div>
          </div>
          <div class="bottom flex-view">
            <div class="left">
              <span class="text">共{{ item.count }}件商品</span>
              <span class="open" @click="handleDetail(item.thingId)">商品详情</span>
            </div>
            <div class="right flex-view">
              <span class="text">总计</span>
              <span class="num">¥ {{ item.price * item.count }}</span>
              <span class="text">优惠</span>
              <span class="num">¥0</span>
              <span class="text">实际支付</span>
              <span class="money">¥ {{ item.price * item.count }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="list-content">
        <div class="order-item-view" v-for="(item, index) in orderData" :key="index">
          <div class="header flex-view">
            <div class="left">
              <span class="text">订单号</span>
              <span class="num mg-4">#</span>
              <span class="num">{{ item.orderNumber }}</span>
              <span class="time">{{ getFormatTime(item.orderTime, true) }}</span>
            </div>
            <div class="right">
              <a-popconfirm v-if="item.status === '1'" title="确定取消订单？" ok-text="是" cancel-text="否" @confirm="handleCancel(item)">
                <a-button type="primary" size="small" style="margin-right: 24px">取消</a-button>
              </a-popconfirm>
              <a-popconfirm v-if="item.status === '2'" ok-text="确定" cancel-text="取消" @confirm="sendThing(item)">
                <a-button type="primary" size="small" style="margin-right: 24px">发货</a-button>
                <template #icon>
                  <span class="title">快递单号</span>
<!--                  <a-input v-model="expressNumber" placeholder="请输入快递单号" />-->
                  <a-input v-model:value="expressNumber" @update:model-value="updateExpressNumber" placeholder="请输入快递单号" />
                </template>
              </a-popconfirm>
              <span class="text">订单状态</span>
              <span class="state">{{
                item.status === '1'
                  ? '待支付'
                  : item.status === '2'
                  ? '已支付'
                  : item.status === '3'
                  ? '已发货'
                  : item.status === '4'
                  ? '待收货'
                  : item.status === '5'
                  ? '已收货'
                  : '已取消'
              }}</span>
            </div>
          </div>
          <div class="content flex-view">
            <div class="left-list">
              <div class="list-item flex-view">
                <img :src="item.cover" class="thing-img" />
                <div class="detail flex-between flex-view">
                  <div class="flex-between flex-top flex-view">
                    <h2 class="name">{{ item.title }}</h2>
                    <span class="count">x{{ item.count }}</span>
                  </div>
                  <div class="flex-between flex-center flex-view">
                    <span class="type"></span>
                    <span class="price">¥{{ item.price }}</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="right-info">
              <p class="title">收货信息</p>
              <p class="name">{{ item.receiverName }} {{ item.receiverPhone }} </p>
              <p class="text mg">{{ item.receiverAddress }} </p>
              <p class="title">快递单号：</p>
              <p class="text">{{ item.express }} </p>
              <p class="text"> </p>
              <p class="title">备注信息：</p>
              <p class="text">{{ item.remark }} </p>
            </div>
          </div>
          <div class="bottom flex-view">
            <div class="left">
              <span class="text">共{{ item.count }}件商品</span>
              <span class="open" @click="handleDetail(item.thingId)">商品详情</span>
            </div>
            <div class="right flex-view">
              <span class="text">总计</span>
              <span class="num">¥ {{ item.price * item.count }}</span>
              <span class="text">优惠</span>
              <span class="num">¥0</span>
              <span class="text">实际支付</span>
              <span class="money">¥ {{ item.price * item.count }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { message } from 'ant-design-vue';
  import { getFormatTime } from '/@/utils/';
  import { updateOrderExpressAndStatusApi, userOrderListApi, userOrderListBySellerIdApi } from '/@/api/order';
  import { cancelUserOrderApi } from '/@/api/order';
  import { BASE_URL } from '/@/store/constants';
  import { useUserStore } from '/@/store';

  const router = useRouter();
  const route = useRoute();
  const userStore = useUserStore();

  const loading = ref(false);
  const orderData = ref([]);
  const orderStatus = ref('');
  const sellerId = ref('');
  const activeKey = ref('1');
  const expressNumber = ref('');

  onMounted(() => {
    getOrderList();
  });

  const onTabChange = (key) => {
    console.log(key);
    if (key === '1') {
      orderStatus.value = '';
      sellerId.value = '';
    }
    if (key === '2') {
      orderStatus.value = '1';
      sellerId.value = '';
    }
    if (key === '3') {
      orderStatus.value = '2';
      sellerId.value = '';
    }
    if (key === '4') {
      sellerId.value = userStore.user_id;
      orderStatus.value = '';
    }
    activeKey.value = key;
    getOrderList();
  };
  const getOrderList = () => {
    loading.value = true;
    let userId = userStore.user_id;
    if (sellerId.value === userId) {
      userOrderListBySellerIdApi({ sellerId: sellerId.value })
        .then((res) => {
          res.data.forEach((item, index) => {
            if (item.cover) {
              item.cover = BASE_URL + '/api/staticfiles/image/' + item.cover;
            }
          });
          orderData.value = res.data;
          loading.value = false;
        })
        .catch((err) => {
          console.log(err);
          loading.value = false;
        });
    } else {
      userOrderListApi({ userId: userId, status: orderStatus.value })
        .then((res) => {
          res.data.forEach((item, index) => {
            if (item.cover) {
              item.cover = BASE_URL + '/api/staticfiles/image/' + item.cover;
            }
          });
          orderData.value = res.data;
          loading.value = false;
        })
        .catch((err) => {
          console.log(err);
          loading.value = false;
        });
    }
  };
  const handleDetail = (thingId) => {
    // 跳转新页面
    let text = router.resolve({ name: 'detail', query: { id: thingId } });
    window.open(text.href, '_blank');
  };
  const handleCancel = (item) => {
    cancelUserOrderApi({
      id: item.id,
    })
      .then((res) => {
        message.success('取消成功');
        getOrderList();
      })
      .catch((err) => {
        message.error(err.msg || '取消失败');
      });
  };

  const sendThing = (item) => {
    // 将 expressNumber.value 中的快递单号写入数据库的逻辑
    // 这里可以调用接口或方法将数据保存到数据库
    console.log('输入的快递单号为:', expressNumber.value);
    updateOrderExpressAndStatusApi({
      id: item.id,
      express: expressNumber.value,
    })
      .then((res) => {
        message.success('发货成功');
        getOrderList();
      })
      .catch((err) => {
        message.error(err.msg || '发货失败');
      });
    // 清空输入框
    expressNumber.value = '';
  };

  const updateExpressNumber = (value) => {
    expressNumber.value = value; // 更新数据
  };
</script>
<style scoped lang="less">
  .flex-view {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
  }

  .content-list {
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;

    .list-title {
      color: #152844;
      font-weight: 600;
      font-size: 18px;
      line-height: 24px;
      height: 24px;
      margin-bottom: 4px;
    }
  }

  .order-item-view {
    background: #f7f9fb;
    border-radius: 4px;
    padding: 16px;
    margin-top: 12px;

    .header {
      border-bottom: 1px solid #cedce4;
      padding-bottom: 12px;
      -webkit-box-pack: justify;
      -ms-flex-pack: justify;
      justify-content: space-between;
      font-size: 14px;

      .text {
        color: #6f6f6f;
      }

      .mg-4 {
        margin-left: 4px;
      }

      .num {
        font-weight: 500;
        color: #152844;
      }

      .num {
        font-weight: 500;
        color: #152844;
      }

      .time {
        margin-left: 16px;
        color: #a1adc5;
      }

      .state {
        color: #ff7b31;
        font-weight: 600;
        margin-left: 10px;
      }
    }

    .content {
      padding: 12px 0;
      overflow: hidden;

      .left-list {
        overflow: hidden;
        height: 132px;
        -webkit-box-flex: 2;
        -ms-flex: 2;
        flex: 2;
        padding-right: 16px;

        .list-item {
          height: 60px;
          margin-bottom: 12px;
          overflow: hidden;
          cursor: pointer;
        }

        .thing-img {
          width: 48px;
          height: 100%;
          margin-right: 12px;
        }

        .detail {
          -webkit-box-flex: 1;
          -ms-flex: 1;
          flex: 1;
          -webkit-box-orient: vertical;
          -webkit-box-direction: normal;
          -ms-flex-direction: column;
          flex-direction: column;
        }

        .flex-between {
          -webkit-box-pack: justify;
          -ms-flex-pack: justify;
          justify-content: space-between;
        }

        .flex-between {
          -webkit-box-pack: justify;
          -ms-flex-pack: justify;
          justify-content: space-between;
        }

        .flex-top {
          -webkit-box-align: start;
          -ms-flex-align: start;
          align-items: flex-start;
        }

        .name {
          color: #152844;
          font-weight: 600;
          font-size: 14px;
          line-height: 18px;
        }

        .count {
          color: #484848;
          font-size: 12px;
        }

        .flex-between {
          -webkit-box-pack: justify;
          -ms-flex-pack: justify;
          justify-content: space-between;
        }

        .flex-center {
          -webkit-box-align: center;
          -ms-flex-align: center;
          align-items: center;
        }

        .type {
          color: #6f6f6f;
          font-size: 12px;
        }

        .price {
          color: #ff7b31;
          font-weight: 600;
          font-size: 14px;
        }
      }

      .right-info {
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        border-left: 1px solid #cedce4;
        padding-left: 12px;
        line-height: 22px;
        font-size: 14px;

        .title {
          color: #6f6f6f;
        }

        .name {
          color: #152844;
        }

        .text {
          color: #484848;
        }

        .mg {
          margin-bottom: 4px;
        }
      }
    }

    .bottom {
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      border-top: 1px solid #cedce4;
      -webkit-box-pack: justify;
      -ms-flex-pack: justify;
      justify-content: space-between;
      font-size: 14px;
      padding-top: 14px;

      .text {
        color: #6f6f6f;
      }

      .open {
        color: #4684e2;
        margin-left: 8px;
        cursor: pointer;
      }

      .right {
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
      }

      .text {
        color: #6f6f6f;
      }

      .num {
        color: #152844;
        margin: 0 40px 0 8px;
      }

      .money {
        font-weight: 600;
        font-size: 18px;
        color: #ff7b31;
        margin-left: 8px;
      }
    }
  }

  .order-item-view:first-child {
    margin-top: 16px;
  }
</style>
