// import "@/utils/sso";
import { getConfig } from "@/config";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";
import { usePermissionStoreHook } from "@/store/modules/permission";
import type { DataInfo } from "@/utils/auth";
import { multipleTabsKey, removeToken, userKey } from "@/utils/auth";
import NProgress from "@/utils/progress";
import { buildHierarchyTree } from "@/utils/tree";
import { isAllEmpty, isUrl, openLink, storageLocal } from "@pureadmin/utils";
import Cookies from "js-cookie";
import type { RouteComponent, Router, RouteRecordRaw } from "vue-router";
import { createRouter, getHistoryMode } from "vue-router";
import landingRouter from "./modules/landing"; // Import the new route
import remainingRouter from "./modules/remaining";
import {
  ascending,
  findRouteByPath,
  formatFlatteningRoutes,
  formatTwoStageRoutes,
  getTopMenu,
  handleAliveRoute,
  initRouter,
  isOneOfArray
} from "./utils";

const modules: Record<string, any> = import.meta.glob(
  ["./modules/**/*.ts", "!./modules/**/remaining.ts"],
  {
    eager: true
  }
);

const routes = [];

Object.keys(modules).forEach(key => {
  routes.push(modules[key].default);
});

export const constantRoutes: Array<RouteRecordRaw> = formatTwoStageRoutes(
  formatFlatteningRoutes(buildHierarchyTree(ascending(routes.flat(Infinity))))
);

export const constantMenus: Array<RouteComponent> = ascending(
  routes.flat(Infinity)
).concat(...remainingRouter);

export const remainingPaths = Object.keys(remainingRouter).map(v => {
  return remainingRouter[v].path;
});

export const router: Router = createRouter({
  history: getHistoryMode(import.meta.env.VITE_ROUTER_HISTORY),
  routes: [
    {
      path: "/",
      redirect: "/landing"
    },
    ...constantRoutes,
    landingRouter,
    ...(remainingRouter as any)
  ],
  strict: true,
  scrollBehavior(to, from, savedPosition) {
    return new Promise(resolve => {
      if (savedPosition) {
        return savedPosition;
      } else {
        if (from.meta.saveSrollTop) {
          const top: number =
            document.documentElement.scrollTop || document.body.scrollTop;
          resolve({ left: 0, top });
        }
      }
    });
  }
});

export function resetRouter() {
  router.getRoutes().forEach(route => {
    const { name, meta } = route;
    if (name && router.hasRoute(name) && meta?.backstage) {
      router.removeRoute(name);
      router.options.routes = formatTwoStageRoutes(
        formatFlatteningRoutes(
          buildHierarchyTree(ascending(routes.flat(Infinity)))
        )
      );
    }
  });
  usePermissionStoreHook().clearAllCachePage();
}

const whiteList = ["/login"];

const { VITE_HIDE_HOME } = import.meta.env;

router.beforeEach((to, _from, next) => {
  if (to.meta?.keepAlive) {
    handleAliveRoute(to, "add");
    if (_from.name === undefined || _from.name === "Redirect") {
      handleAliveRoute(to);
    }
  }
  const userInfo = storageLocal().getItem<DataInfo<number>>(userKey);
  NProgress.start();
  const externalLink = isUrl(to?.name as string);
  if (!externalLink) {
    to.matched.some(item => {
      if (!item.meta.title) return "";
      const Title = getConfig().Title;
      if (Title) document.title = `${item.meta.title} | ${Title}`;
      else document.title = item.meta.title as string;
    });
  }
  function toCorrectRoute() {
    whiteList.includes(to.fullPath) ? next(_from.fullPath) : next();
  }
  if (Cookies.get(multipleTabsKey) && userInfo) {
    if (to.meta?.roles && !isOneOfArray(to.meta?.roles, userInfo?.roles)) {
      next({ path: "/error/403" });
    }
    if (VITE_HIDE_HOME === "true" && to.fullPath === "/welcome") {
      next({ path: "/error/404" });
    }
    if (_from?.name) {
      if (externalLink) {
        openLink(to?.name as string);
        NProgress.done();
      } else {
        toCorrectRoute();
      }
    } else {
      if (
        usePermissionStoreHook().wholeMenus.length === 0 &&
        to.path !== "/login"
      ) {
        initRouter().then((router: Router) => {
          if (!useMultiTagsStoreHook().getMultiTagsCache) {
            const { path } = to;
            const route = findRouteByPath(
              path,
              router.options.routes[0].children
            );
            getTopMenu(true);
            if (route && route.meta?.title) {
              if (isAllEmpty(route.parentId) && route.meta?.backstage) {
                const { path, name, meta } = route.children[0];
                useMultiTagsStoreHook().handleTags("push", {
                  path,
                  name,
                  meta
                });
              } else {
                const { path, name, meta } = route;
                useMultiTagsStoreHook().handleTags("push", {
                  path,
                  name,
                  meta
                });
              }
            }
          }
          if (isAllEmpty(to.name)) router.push(to.fullPath);
        });
      }
      toCorrectRoute();
    }
  } else {
    if (to.path !== "/login") {
      if (whiteList.indexOf(to.path) !== -1) {
        next();
      } else {
        removeToken();
        next({ path: "/login" });
      }
    } else {
      next();
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
