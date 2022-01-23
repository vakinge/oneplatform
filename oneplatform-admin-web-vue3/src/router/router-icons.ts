import { renderIcon } from '@/utils/index';
import { DashboardOutlined,SettingOutlined,AlertOutlined } from '@vicons/antd';

//前端路由图标映射表
export const constantRouterIcon = {
  DashboardOutlined: renderIcon(DashboardOutlined),
  SettingOutlined: renderIcon(SettingOutlined),
  AlertOutlined: renderIcon(AlertOutlined),
};
