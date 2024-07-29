import Cookies from "js-cookie";
import { storageLocal } from "@pureadmin/utils";
import { useUserStoreHook } from "@/store/modules/user";

export interface DataInfo<T> {
  /** token */
  accessToken: string;
  /** `accessToken`Expiration time（Timestamp） */
  expires: T;
  /** Used to call refreshaccessTokenRequired when the interfacetoken */
  refreshToken: string;
  /** Avatar */
  avatar?: string;
  /** Username */
  username?: string;
  /** Nickname */
  nickname?: string;
  /** Role of the currently logged-in user */
  roles?: Array<string>;
}

export const userKey = "user-info";
export const TokenKey = "authorized-token";
/**
 * Through`multiple-tabs`Is it in`cookie`In，Judge whether the user has logged in to the system，
 * Thereby supporting multiple tabs to open the logged-in system without logging in again。
 * After the browser is completely closed`multiple-tabs`Will be automatically from`cookie`Destroyed in，
 * Open the browser again and need to log in to the system again
 * */
export const multipleTabsKey = "multiple-tabs";

/** Get`token` */
export function getToken(): DataInfo<number> {
  // Here and`TokenKey`Same，This writing method solves the initialization`Cookies`Does not exist in`TokenKey`Error
  return Cookies.get(TokenKey)
    ? JSON.parse(Cookies.get(TokenKey))
    : storageLocal().getItem(userKey);
}

/**
 * @description Settings`token`And some necessary information and use non-sensitive refresh`token`Solution
 * Non-sensitive refresh：Backend return`accessToken`（Access interface used`token`）、`refreshToken`（Used to call refresh`accessToken`Interface required`token`，`refreshToken`Expiration time（For example30Days）Should be greater than`accessToken`Expiration time（For example2Hours））、`expires`（`accessToken`Expiration time）
 * Will`accessToken`、`expires`、`refreshToken`Put these three pieces of information inkeyThe value isauthorized-tokenOfcookieIn（Automatically destroyed upon expiration）
 * Put`avatar`、`username`、`nickname`、`roles`、`refreshToken`、`expires`Put these six pieces of information inkeyThe value is`user-info`OflocalStorageIn（Use`multipleTabsKey`Automatically destroyed when the browser is completely closed）
 */
export function setToken(data: DataInfo<Date>) {
  let expires = 0;
  const { accessToken, refreshToken } = data;
  const { isRemembered, loginDay } = useUserStoreHook();
  expires = new Date(data.expires).getTime(); // If the backend sets the timestamp directly，Change the code here toexpires = data.expires，Then change the aboveDataInfo<Date>ToDataInfo<number>That's it
  const cookieString = JSON.stringify({ accessToken, expires, refreshToken });

  expires > 0
    ? Cookies.set(TokenKey, cookieString, {
        expires: (expires - Date.now()) / 86400000
      })
    : Cookies.set(TokenKey, cookieString);

  Cookies.set(
    multipleTabsKey,
    "true",
    isRemembered
      ? {
          expires: loginDay
        }
      : {}
  );

  function setUserKey({ avatar, username, nickname, roles }) {
    useUserStoreHook().SET_AVATAR(avatar);
    useUserStoreHook().SET_USERNAME(username);
    useUserStoreHook().SET_NICKNAME(nickname);
    useUserStoreHook().SET_ROLES(roles);
    storageLocal().setItem(userKey, {
      refreshToken,
      expires,
      avatar,
      username,
      nickname,
      roles
    });
  }

  if (data.username && data.roles) {
    const { username, roles } = data;
    setUserKey({
      avatar: data?.avatar ?? "",
      username,
      nickname: data?.nickname ?? "",
      roles
    });
  } else {
    const avatar =
      storageLocal().getItem<DataInfo<number>>(userKey)?.avatar ?? "";
    const username =
      storageLocal().getItem<DataInfo<number>>(userKey)?.username ?? "";
    const nickname =
      storageLocal().getItem<DataInfo<number>>(userKey)?.nickname ?? "";
    const roles =
      storageLocal().getItem<DataInfo<number>>(userKey)?.roles ?? [];
    setUserKey({
      avatar,
      username,
      nickname,
      roles
    });
  }
}

/** Delete`token`andkeyvalue`user-info`oflocalStorageinformation */
export function removeToken() {
  Cookies.remove(TokenKey);
  Cookies.remove(multipleTabsKey);
  storageLocal().removeItem(userKey);
}

/** formattoken（jwtformat） */
export const formatToken = (token: string): string => {
  return "Bearer " + token;
};
