import { login, logout, getInfo, getMenus } from '@/api/login'
import { setLogined, removeLoginedState } from '@/utils/auth'
import { initMenu } from '@/utils/menu'

const user = {
  state: {
    id: 0,
    name: '',
    avatar: '',
    menus: []
  },

  mutations: {
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          commit('SET_ID', data.id)
          commit('SET_NAME', data.username)
          commit('SET_AVATAR', data.avatar)
          setLogined()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const data = response.data
          commit('SET_ID', data.id)
          commit('SET_NAME', data.username)
          commit('SET_AVATAR', data.avatar)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户菜单
    GetMenus({ commit }) {
      return new Promise((resolve, reject) => {
        getMenus().then(response => {
          const menus = initMenu(response.data)
          console.log(menus)
          commit('SET_MENUS', menus)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          removeLoginedState()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        removeLoginedState()
        resolve()
      })
    }
  }
}

export default user
