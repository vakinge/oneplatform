import { http } from '@/utils/http/axios';

/**
 * @description: 角色列表
 */
export function getRoleList(params) {
  return http.request({
    url: '/perm/role/list',
    method: 'POST',
    params,
  });
}
