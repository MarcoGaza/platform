import { defineStore } from "pinia";
import {
  type userType,
  store,
  router,
  resetRouter,
  routerArrays,
  storageLocal
} from "../utils";
import {
  type UserResult,
  type RefreshTokenResult,
  getLogin,
  refreshTokenApi
} from "@/api/user";
import { useMultiTagsStoreHook } from "./multiTags";
import { type DataInfo, setToken, removeToken, userKey } from "@/utils/auth";

export const useUserStore = defineStore({
  id: "pure-user",
  state: (): userType => ({
    // Avatar
    avatar: storageLocal().getItem<DataInfo<number>>(userKey)?.avatar ?? "",
    // Username
    username: storageLocal().getItem<DataInfo<number>>(userKey)?.username ?? "",
    // Nickname
    nickname: storageLocal().getItem<DataInfo<number>>(userKey)?.nickname ?? "",
    // Page level permissions
    roles: storageLocal().getItem<DataInfo<number>>(userKey)?.roles ?? [],
    // Is the login page free login selected?
    isRemembered: false,
    // How many days is the login page free login stored?，Default7Days
    loginDay: 7
  }),
  actions: {
    /** Store avatar */
    SET_AVATAR(avatar: string) {
      this.avatar = avatar;
    },
    /** Store username */
    SET_USERNAME(username: string) {
      this.username = username;
    },
    /** Store nickname */
    SET_NICKNAME(nickname: string) {
      this.nickname = nickname;
    },
    /** Store role */
    SET_ROLES(roles: Array<string>) {
      this.roles = roles;
    },
    /** Store whether the login page free login is selected */
    SET_ISREMEMBERED(bool: boolean) {
      this.isRemembered = bool;
    },
    /** Set the login page free login storage for a few days */
    SET_LOGINDAY(value: number) {
      this.loginDay = Number(value);
    },
    /** Login */
    async loginByUsername(data) {
      return new Promise<UserResult>((resolve, reject) => {
        getLogin(data)
          .then(data => {
            if (data?.success) setToken(data.data);
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    /** Front-end logout（No API call） */
    logOut() {
      this.username = "";
      this.roles = [];
      removeToken();
      useMultiTagsStoreHook().handleTags("equal", [...routerArrays]);
      resetRouter();
      router.push("/login");
    },
    /** Refresh`token` */
    async handRefreshToken(data) {
      return new Promise<RefreshTokenResult>((resolve, reject) => {
        refreshTokenApi(data)
          .then(data => {
            if (data) {
              setToken(data.data);
              resolve(data);
            }
          })
          .catch(error => {
            reject(error);
          });
      });
    }
  }
});

export function useUserStoreHook() {
  return useUserStore(store);
}
