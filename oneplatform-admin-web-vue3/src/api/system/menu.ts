import { http } from '@/utils/http/axios';
import { Layout } from '@/router/constant';

/**
 * @description: 根据用户id获取用户菜单
 */
export async function currentUserMenus() {
  let menus = await http.request({
    url: '/perm/user/menus',
    method: 'GET',
  });
  return convertToMenuItems(menus);
}

/**
 * 获取tree菜单列表
 * @param params
 */
export function getMenuList(params?) {
  return http.request({
    url: '/perm/functionResource/tree',
    method: 'GET',
    params,
  });
}


export function addMenu(params) {
  return http.request({
    url: '/perm/functionResource/add',
    method: 'POST',
    params,
  });
}

export function updateMenu(params) {
  return http.request({
    url: '/perm/functionResource/update',
    method: 'POST',
    params,
  });
}

function convertToMenuItems(items) {
   let menus = [];
   let currentActiveMenu = true;
	 items.map((item,index)=>{
     let menu = {};
     menu.path = item.router;
     menu.name = item.code;
     menu.component = item.type === 'catalog' ? 'LAYOUT' : item.viewPath;
     if(item.type === 'catalog'){
       menu.component = 'LAYOUT';
       menu.redirect = item.viewPath;
     }else {
       menu.component = item.viewPath;
     }
     menu.meta = {
       hidden: !item.isDisplay,
       title: item.name,
       icon: item.icon
     };
     if(item.children){
       menu.children = convertToMenuItems(item.children);
     }
	   menus.push(menu);
	 });

   return menus;
}
