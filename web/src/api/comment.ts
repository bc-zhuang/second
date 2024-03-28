import { get, post } from '/@/utils/http/axios';

enum URL {
  list = '/api/comment/list',
  create = '/api/comment/create',
  delete = '/api/comment/delete',
  listThingComments = '/api/comment/listThingComments',
  listUserComments = '/api/comment/listUserComments',
  like = '/api/comment/like',
  thingAllComment = '/api/comment/thingAllComment',
  deleteOne = '/api/comment/deleteOne',
  listUserAllCommentsByUserAllThing = '/api/comment/listUserAllCommentsByUserAllThing',
}

const listApi = async (params: any) => get<any>({ url: URL.list, params: params, data: {}, headers: {} });
const createApi = async (data: any) =>
  post<any>({
    url: URL.create,
    params: {},
    data: data,
    headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
  });
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params: params, headers: {} });
const listThingCommentsApi = async (params: any) => get<any>({ url: URL.listThingComments, params: params, data: {}, headers: {} });
const listUserCommentsApi = async (params: any) => get<any>({ url: URL.listUserComments, params: params, data: {}, headers: {} });
const likeApi = async (params: any) => post<any>({ url: URL.like, params: params, headers: {} });
const thingAllCommentApi = async (params: any) => get<any>({
    url: URL.thingAllComment,
    params: params,
    headers: {},
  });
const deleteOneApi = async (params: any) => post<any>({ url: URL.deleteOne, params: params, headers: {} });
const listUserAllCommentsByUserAllThingApi = async (params:any)=> get<any>({
    url: URL.listUserAllCommentsByUserAllThing,
    params: params,
    headers: {},
  });

export {
  listApi,
  createApi,
  deleteApi,
  listThingCommentsApi,
  listUserCommentsApi,
  likeApi,
  thingAllCommentApi,
  deleteOneApi,
  listUserAllCommentsByUserAllThingApi,
};
