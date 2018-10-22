export const initMenu = (datas) => {
  const menus = []

  const menu = {
    path: '/form',
    component: 'Layout',
    children: [
      {
        path: 'index',
        name: 'Form2',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form2', icon: 'form' }
      }
    ]
  }

  menus.push(menu)

  return menus
}

