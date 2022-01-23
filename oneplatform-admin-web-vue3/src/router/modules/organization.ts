import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import { OptionsSharp } from '@vicons/ionicons5';
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
    path: '/organization',
    name: 'Organization',
    redirect: '/organization/dept',
    component: Layout,
    meta: {
      title: '组织架构',
      icon: renderIcon(OptionsSharp),
      sort: 2,
    },
    children: [
      {
        path: 'dept',
        name: 'organization_dept',
        meta: {
          title: '部门管理',
        },
        component: () => import('@/views/organization/dept/index.vue'),
      },
      {
        path: 'position',
        name: 'organization_position',
        meta: {
          title: '职位管理',
        },
        component: () => import('@/views/organization/position/index.vue'),
      },
      {
        path: 'staff',
        name: 'organization_staff',
        meta: {
          title: '员工管理',
        },
        component: () => import('@/views/organization/staff/index.vue'),
      },
    ],
  },
];

export default routes;
