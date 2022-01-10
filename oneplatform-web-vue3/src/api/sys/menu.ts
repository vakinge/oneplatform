import { defHttp } from '/@/utils/http/axios';
import { getMenuListResultModel } from './model/menuModel';

enum Api {
  GetMenuList = '/perm/user/menus',
}

/**
 * @description: Get user menu based on id
 */

export const getMenuList = async () => {
  const data = await defHttp.get<Any>({ url: Api.GetMenuList });
  return convertToMenuItems(data);
};

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
       "hideMenu": !item.isDisplay,
       "hideBreadcrumb": true,
       "title": item.name,
       "icon": item.icon
     };
     if(item.children){
       menu.children = convertToMenuItems(item.children);
     }
	   menus.push(menu);
	 });
   //console.log('-----------------\n' + JSON.stringify(menus) + '\n-----------------');
   return menus;
}

/*

{
		"id": "10001",
		"name": "Workbench",
		"parentId": "10000",
		"checked": false,
		"disabled": false,
		"sort": 99,
		"type": "menu",
		"code": "/dashboard/workbench",
		"clientType": "PC",
    "isDisplay": true,
		"leaf": false
	}
  
{
		"path": "analysis",
		"name": "Analysis",
		"component": "/dashboard/analysis/index",
		"meta": {
			"hideMenu": true,
			"hideBreadcrumb": true,
			"title": "routes.dashboard.analysis",
			"currentActiveMenu": "/dashboard",
			"icon": "bx:bx-home"
		}
	}
*/
