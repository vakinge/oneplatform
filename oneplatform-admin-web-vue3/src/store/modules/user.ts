import { defineStore } from 'pinia';
import { createStorage } from '@/utils/Storage';
import { store } from '@/store';
import { ACCESS_TOKEN, CURRENT_USER, IS_LOCKSCREEN } from '@/store/mutation-types';
import { ResultEnum } from '@/enums/httpEnum';

const Storage = createStorage({ storage: localStorage });
import { getUserInfo, login, getUserPermssions } from '@/api/system/user';
import { storage } from '@/utils/Storage';


export const useUserStore = defineStore({
  id: 'app-user',
  state: (): any => ({
    token: Storage.get(ACCESS_TOKEN, ''),
    username: '',
    welcome: '',
    avatar: '',
    permissions: [],
    info: Storage.get(CURRENT_USER, {}),
  }),
  getters: {
    getToken(): string {
      return this.token;
    },
    getAvatar(): string {
      return this.avatar;
    },
    getNickname(): string {
      return this.username;
    },
    getPermissions(): [any][] {
      return this.permissions;
    },
    getUserInfo(): object {
      return this.info;
    },
    isAdmin(): boolean {
      return this.info && this.info.admin;
    },
  },
  actions: {
    setToken(token: string) {
      this.token = token;
    },
    setAvatar(avatar: string) {
      this.avatar = avatar;
    },
    setPermissions(permissions) {
      this.permissions = permissions;
    },
    setUserInfo(info) {
      this.info = info;
    },
    // 登录
    async login(userInfo) {
      const session = await login(userInfo);
      storage.set(ACCESS_TOKEN, session.sessionId, session.expiredAt);
      storage.set(CURRENT_USER, session.user, session.expiredAt);
      storage.set(IS_LOCKSCREEN, false);
      this.setToken(session.sessionId);
      this.setUserInfo(session.user);
      return session.user;
    },

    // 获取用户信息
    async GetInfo() {
      let that = this;
      const permissions = await getUserPermssions();
      this.setPermissions(permissions);
      //
      return new Promise((resolve, reject) => {
        getUserInfo()
          .then((res) => {
            that.setUserInfo(res);
            that.setAvatar(res.avatar);
            resolve(res);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 登出
    async logout() {
      this.setPermissions([]);
      this.setUserInfo('');
      storage.remove(ACCESS_TOKEN);
      storage.remove(CURRENT_USER);
      return Promise.resolve('');
    },
  },
});

// Need to be used outside the setup
export function useUserStoreWidthOut() {
  return useUserStore(store);
}
