import { http } from '@/utils/http/axios';

const apiPrefix = '/org/position';
export function GetList(query) {
  return http.request({
    url: apiPrefix + '/list',
    method: 'post',
    data: query,
  });
}

export function AddObj(obj) {
  return http.request({
    url: apiPrefix + '/add',
    method: 'post',
    data: obj,
  });
}

export function UpdateObj(obj) {
  return http.request({
    url: apiPrefix + '/update',
    method: 'post',
    data: obj,
  });
}

export function DelObj(id) {
  return http.request({
    url: apiPrefix + '/delete',
    method: 'post',
    params: { id },
  });
}

export function GetObj(id) {
  return http.request({
    url: apiPrefix + '/' + id,
    method: 'get',
  });
}

export function BatchDelete(ids) {
  return http.request({
    url: apiPrefix + '/batchDelete',
    method: 'post',
    data: { ids },
  });
}
