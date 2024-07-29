import { http } from "@/utils/http";

export type UserResult = {
  success: boolean;
  data: {
    /** Avatar */
    avatar: string;
    /** User name */
    username: string;
    /** Nickname */
    nickname: string;
    /** Role of the currently logged in user */
    roles: Array<string>;
    /** `token` */
    accessToken: string;
    /** Used to call refresh`accessToken`Required for the interface`token` */
    refreshToken: string;
    /** `accessToken`Expiration time（Format'xxxx/xx/xx xx:xx:xx'） */
    expires: Date;
  };
};

export type RefreshTokenResult = {
  success: boolean;
  data: {
    /** `token` */
    accessToken: string;
    /** Used to call refresh`accessToken`Required for the interface`token` */
    refreshToken: string;
    /** `accessToken`Expiration time（Format'xxxx/xx/xx xx:xx:xx'） */
    expires: Date;
  };
};

/** Login */
export const getLogin = (data?: object) => {
  return http.request<UserResult>("post", "/login", { data });
};

/** Refresh`token` */
export const refreshTokenApi = (data?: object) => {
  return http.request<RefreshTokenResult>("post", "/refresh-token", { data });
};
