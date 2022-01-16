import { http } from '@/utils/http/axios';

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
    url: '/menu/list',
    method: 'GET',
    params,
  });
}


function convertToMenuItems(items) {
   let menus = [];
   let currentActiveMenu = true;
	 items.map((item,index)=>{
     let menu = {};
     menu.path = item.router;
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
   //console.log('-----------------\n' + JSON.stringify(menus) + '\n-----------------');
   return menus;
}
