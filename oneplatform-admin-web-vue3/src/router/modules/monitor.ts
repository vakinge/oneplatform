import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import { AlertOutlined } from '@vicons/antd';
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
    path: '/monitor',
    name: 'monitor',
    redirect: '/monitor/log',
    component: Layout,
    meta: {
      title: '系统监控',
      icon: renderIcon(AlertOutlined),
      sort: 3,
    },
    children: [
      {
        path: 'log',
        name: 'monitor_log',
        meta: {
          title: '审计日志',
        },
        component: () => import('@/views/monitor/log/index.vue'),
      },
      {
        path: 'scheduler',
        name: 'monitor_scheduler',
        meta: {
          title: '定时任务管理',
        },
        component: () => import('@/views/monitor/scheduler/index.vue'),
      },
    ],
  },
];

export default routes;
