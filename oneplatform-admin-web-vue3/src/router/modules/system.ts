import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import { SettingOutlined } from '@vicons/antd';
import { renderIcon } from '@/utils/index';

/**
 * @param name 路由名称, 必须设置,且不能重名
 * @param meta 路由元信息（路由附带扩展信息）
 * @param redirect 重定向地址, 访问这个路由时,自定进行重定向
 * @param meta.disabled 禁用整个菜单
 * @param meta.title 菜单名称
 * @param meta.icon 菜单图标
 * @param meta.keepAlive 缓存该路由
 * @param meta.sort 排序越小越排前
 *
 * */
const routes: Array<RouteRecordRaw> = [
  {
    path: '/system',
    name: 'System',
    redirect: '/system/menu',
    component: Layout,
    meta: {
      title: '系统管理',
      icon: renderIcon(SettingOutlined),
      sort: 1,
    },
    children: [
      {
        path: 'account',
        name: 'system_account',
        meta: {
          title: '账号管理',
        },
        component: () => import('@/views/system/account/index.vue'),
      },
      {
        path: 'menu',
        name: 'system_menu',
        meta: {
          title: '菜单管理',
        },
        component: () => import('@/views/system/menu/index.vue'),
      },
      {
        path: 'role',
        name: 'system_role',
        meta: {
          title: '角色管理',
        },
        component: () => import('@/views/system/role/index.vue'),
      },
      {
        path: 'datarole',
        name: 'system_datarole',
        meta: {
          title: '数据角色',
        },
        component: () => import('@/views/system/datarole/index.vue'),
      },
      {
        path: 'dict',
        name: 'system_dict',
        meta: {
          title: '字典管理',
        },
        component: () => import('@/views/system/dict/index.vue'),
      },
      {
        path: 'config',
        name: 'system_config',
        meta: {
          title: '系统配置管理',
        },
        component: () => import('@/views/system/config/index.vue'),
      },
    ],
  },
];

export default routes;
