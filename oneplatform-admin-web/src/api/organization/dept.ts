import { http } from '@/utils/http/axios';

/**
 * 获取tree菜单列表
 * @param params
 */
export function getList(params?) {
  return http.request({
    url: '/org/dept/list',
    method: 'POST',
    params,
  });
}


export function add(params) {
  return http.request({
    url: '/org/dept/add',
    method: 'POST',
    params,
  });
}
