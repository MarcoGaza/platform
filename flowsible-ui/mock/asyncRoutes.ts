// Simulate the backend to dynamically generate routes
import { defineFakeRoute } from "vite-plugin-fake-server/client";

/**
 * roles：Page level permissions，Here simulates two types "admin"、"common"
 * admin：Administrator role
 * common：Ordinary role
 */
const permissionRouter = {
  path: "/permission",
  meta: {
    title: "Permission management",
    icon: "ep:lollipop",
    rank: 10
  },
  children: [
    {
      path: "/permission/page/index",
      name: "PermissionPage",
      meta: {
        title: "Page permissions",
        roles: ["admin", "common"]
      }
    },
    {
      path: "/permission/button/index",
      name: "PermissionButton",
      meta: {
        title: "Button permissions",
        roles: ["admin", "common"],
        auths: [
          "permission:btn:add",
          "permission:btn:edit",
          "permission:btn:delete"
        ]
      }
    }
  ]
};

export default defineFakeRoute([
  {
    url: "/get-async-routes",
    method: "get",
    response: () => {
      return {
        success: true,
        data: [permissionRouter]
      };
    }
  }
]);
