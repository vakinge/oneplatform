import { http } from '@/utils/http/axios';

const apiPrefix = '/perm/role';
export function GetList(query = {}) {
  query.example.roleType = 'function';
  return http.request({
    url: apiPrefix + '/users',
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


export function DelObj(id) {
  return http.request({
    url: apiPrefix + '/delete',
    method: 'post',
    params: { id },
  });
}
