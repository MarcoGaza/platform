// Dynamically generate routes based on roles
import { defineFakeRoute } from "vite-plugin-fake-server/client";

export default defineFakeRoute([
  {
    url: "/login",
    method: "post",
    response: ({ body }) => {
      if (body.username === "admin") {
        return {
          success: true,
          data: {
            avatar: "https://avatars.githubusercontent.com/u/44761321",
            username: "admin",
            nickname: "Xiaoming",
            // A user may have multiple roles
            roles: ["admin"],
            accessToken: "eyJhbGciOiJIUzUxMiJ9.admin",
            refreshToken: "eyJhbGciOiJIUzUxMiJ9.adminRefresh",
            expires: "2030/10/30 00:00:00"
          }
        };
      } else {
        return {
          success: true,
          data: {
            avatar: "https://avatars.githubusercontent.com/u/52823142",
            username: "common",
            nickname: "Xiaolin",
            roles: ["common"],
            accessToken: "eyJhbGciOiJIUzUxMiJ9.common",
            refreshToken: "eyJhbGciOiJIUzUxMiJ9.commonRefresh",
            expires: "2030/10/30 00:00:00"
          }
        };
      }
    }
  }
]);
