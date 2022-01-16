import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import { DesktopOutline } from '@vicons/ionicons5';
import { renderIcon } from '@/utils/index';

const IFrame = () => import('@/views/iframe/index.vue');

const routes: Array<RouteRecordRaw> = [
  {
    path: '/frame',
    name: 'Frame',
    redirect: '/frame/docs',
    component: Layout,
    meta: {
      title: '外部页面',
      sort: 8,
      icon: renderIcon(DesktopOutline),
    },
    children: [
      {
        path: 'docs',
        name: 'frame-docs',
        meta: {
          title: '项目文档(内嵌)',
          frameSrc: 'https://naive-ui-admin-docs.vercel.app',
        },
        component: IFrame,
      },
      {
        path: 'naive',
        name: 'frame-naive',
        meta: {
          title: 'NaiveUi(内嵌)',
          frameSrc: 'https://www.naiveui.com',
        },
        component: IFrame,
      },
    ],
  },
];

export default routes;
