import { get, post } from '/@/utils/http/axios';
import { Ref } from 'vue';

enum URL {
  list = '/api/order/list',
  create = '/api/order/create',
  update = '/api/order/update',
  delete = '/api/order/delete',
  cancel = '/api/order/cancelOrder',
  cancelUserOrder = '/api/order/cancelUserOrder',
  userOrderList = '/api/order/userOrderList',
  successUserOrder = '/api/order/successUserOrder',
  userOrderListBySellerId = '/api/order/userOrderListBySellerId',
  updateOrderExpressAndStatus = '/api/order/updateOrderExpressAndStatus',
  updateOrderPayStatus = '/api/order/updateOrderPayStatus',
  updateUserOrderStatus5 = '/api/order/updateUserOrderStatus5',
}

const listApi = async (params: any) => get<any>({ url: URL.list, params: params, data: {}, headers: {} });
const userOrderListApi = async (params: any) => get<any>({ url: URL.userOrderList, params: params, data: {}, headers: {} });

const createApi = async (data: any) =>
  post<any>({
    url: URL.create,
    params: {},
    data: data,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });
const updateApi = async (params: any, data: any) =>
  post<any>({
    url: URL.update,
    params: params,
    data: data,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params: params, headers: {} });

const cancelApi = async (params: any) => post<any>({ url: URL.cancel, params: params, headers: {} });

const cancelUserOrderApi = async (params: any) => post<any>({ url: URL.cancelUserOrder, params: params, headers: {} });

const successUserOrderApi = async (params: any) => post<any>({ url: URL.successUserOrder, params: params, headers: {} });
const userOrderListBySellerIdApi = async (params: any) =>
  get<any>({ url: URL.userOrderListBySellerId, params: params, data: {}, headers: {} });
const updateOrderExpressAndStatusApi = async (params: any) =>
  post<any>({
    url: URL.updateOrderExpressAndStatus,
    params: params,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });
const updateOrderPayStatusApi = async (data: any) =>
  post<any>({
    url: URL.updateOrderPayStatus,
    data: data,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });
const updateUserOrderStatus5Api = async (data: any) =>
  post<any>({
    url: URL.updateUserOrderStatus5,
    data: data,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });

export {
  listApi,
  userOrderListApi,
  createApi,
  updateApi,
  deleteApi,
  cancelApi,
  cancelUserOrderApi,
  userOrderListBySellerIdApi,
  updateOrderExpressAndStatusApi,
  updateOrderPayStatusApi,
  updateUserOrderStatus5Api,
};
