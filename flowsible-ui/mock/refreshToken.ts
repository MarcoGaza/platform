import { defineFakeRoute } from "vite-plugin-fake-server/client";

// Simulated refreshtokenInterface
export default defineFakeRoute([
  {
    url: "/refresh-token",
    method: "post",
    response: ({ body }) => {
      if (body.refreshToken) {
        return {
          success: true,
          data: {
            accessToken: "eyJhbGciOiJIUzUxMiJ9.newAdmin",
            refreshToken: "eyJhbGciOiJIUzUxMiJ9.newAdminRefresh",
            // `expires`This date format is chosen to facilitate debugging，It may be more convenient to set the timestamp directly on the back end（It should be incremented each time）。If the back end returns the timestamp format，Front-end developers please come to this directory`src/utils/auth.ts`，Replace the code of the first`38`line withexpires = data.expiresThat's it。
            expires: "2030/10/30 23:59:59"
          }
        };
      } else {
        return {
          success: false,
          data: {}
        };
      }
    }
  }
]);
