import {
  type RouterHistory,
  type RouteRecordRaw,
  type RouteComponent,
  createWebHistory,
  createWebHashHistory
} from "vue-router";
import { router } from "./index";
import { isProxy, toRaw } from "vue";
import { useTimeoutFn } from "@vueuse/core";
import {
  isString,
  cloneDeep,
  isAllEmpty,
  intersection,
  storageLocal,
  isIncludeAllChildren
} from "@pureadmin/utils";
import { getConfig } from "@/config";
import { buildHierarchyTree } from "@/utils/tree";
import { userKey, type DataInfo } from "@/utils/auth";
import { type menuType, routerArrays } from "@/layout/types";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";
import { usePermissionStoreHook } from "@/store/modules/permission";
const IFrame = () => import("@/layout/frame.vue");
// https://cn.vitejs.dev/guide/features.html#glob-import
const modulesRoutes = import.meta.glob("/src/views/**/*.{vue,tsx}");

// Dynamic routes
import { getAsyncRoutes } from "@/api/routes";

function handRank(routeInfo: any) {
  const { name, path, parentId, meta } = routeInfo;
  return isAllEmpty(parentId)
    ? isAllEmpty(meta?.rank) ||
      (meta?.rank === 0 && name !== "Home" && path !== "/")
      ? true
      : false
    : false;
}

/** According to the routemetaNextrankSort the routes in ascending order */
function ascending(arr: any[]) {
  arr.forEach((v, index) => {
    // WhenrankWhen it does not exist，Automatically create according to the order，Home page route is always in the first place
    if (handRank(v)) v.meta.rank = index + 2;
  });
  return arr.sort(
    (a: { meta: { rank: number } }, b: { meta: { rank: number } }) => {
      return a?.meta.rank - b?.meta.rank;
    }
  );
}

/** FiltermetaMediumshowLinkForfalseMenu */
function filterTree(data: RouteComponent[]) {
  const newTree = cloneDeep(data).filter(
    (v: { meta: { showLink: boolean } }) => v.meta?.showLink !== false
  );
  newTree.forEach(
    (v: { children }) => v.children && (v.children = filterTree(v.children))
  );
  return newTree;
}

/** FilterchildrenLength0Directory，When there is no menu in the directory，This directory will be filtered，The directory is not grantedrolesPermission，When there is only one menu in the directory with display permission，Then this directory will be displayed */
function filterChildrenTree(data: RouteComponent[]) {
  const newTree = cloneDeep(data).filter((v: any) => v?.children?.length !== 0);
  newTree.forEach(
    (v: { children }) => v.children && (v.children = filterTree(v.children))
  );
  return newTree;
}

/** Judge whether there are the same values ​​in two arrays */
function isOneOfArray(a: Array<string>, b: Array<string>) {
  return Array.isArray(a) && Array.isArray(b)
    ? intersection(a, b).length > 0
      ? true
      : false
    : true;
}

/** FromlocalStorageGet the role of the currently logged in userroles，Filter menus without permission */
function filterNoPermissionTree(data: RouteComponent[]) {
  const currentRoles =
    storageLocal().getItem<DataInfo<number>>(userKey)?.roles ?? [];
  const newTree = cloneDeep(data).filter((v: any) =>
    isOneOfArray(v.meta?.roles, currentRoles)
  );
  newTree.forEach(
    (v: any) => v.children && (v.children = filterNoPermissionTree(v.children))
  );
  return filterChildrenTree(newTree);
}

/** By specifying `key` Get the parent path collection，Default `key` for `path` */
function getParentPaths(value: string, routes: RouteRecordRaw[], key = "path") {
  // deep traversal search
  function dfs(routes: RouteRecordRaw[], value: string, parents: string[]) {
    for (let i = 0; i < routes.length; i++) {
      const item = routes[i];
      // return to parentpath
      if (item[key] === value) return parents;
      // childrendo not recurse if it does not exist or is empty
      if (!item.children || !item.children.length) continue;
      // when searching down, put the currentpathpushing
      parents.push(item.path);

      if (dfs(item.children, value, parents).length) return parents;
      // when deep traversal search is not found, the currentpath pop
      parents.pop();
    }
    // return an empty array when not found
    return [];
  }

  return dfs(routes, value, []);
}

/** find the corresponding `path` routing information */
function findRouteByPath(path: string, routes: RouteRecordRaw[]) {
  let res = routes.find((item: { path: string }) => item.path == path);
  if (res) {
    return isProxy(res) ? toRaw(res) : res;
  } else {
    for (let i = 0; i < routes.length; i++) {
      if (
        routes[i].children instanceof Array &&
        routes[i].children.length > 0
      ) {
        res = findRouteByPath(path, routes[i].children);
        if (res) {
          return isProxy(res) ? toRaw(res) : res;
        }
      }
    }
    return null;
  }
}

function addPathMatch() {
  if (!router.hasRoute("pathMatch")) {
    router.addRoute({
      path: "/:pathMatch(.*)",
      name: "pathMatch",
      redirect: "/error/404"
    });
  }
}

/** processing dynamic routing（routing returned by the backend） */
function handleAsyncRoutes(routeList) {
  if (routeList.length === 0) {
    usePermissionStoreHook().handleWholeMenus(routeList);
  } else {
    formatFlatteningRoutes(addAsyncRoutes(routeList)).map(
      (v: RouteRecordRaw) => {
        // prevent duplicate addition of routing
        if (
          router.options.routes[0].children.findIndex(
            value => value.path === v.path
          ) !== -1
        ) {
          return;
        } else {
          // remember to put routingpushtoroutesYou still need to use it lateraddRoute，So that the route can jump normally
          router.options.routes[0].children.push(v);
          // Finally, the route is in ascending order
          ascending(router.options.routes[0].children);
          if (!router.hasRoute(v?.name)) router.addRoute(v);
          const flattenRouters: any = router
            .getRoutes()
            .find(n => n.path === "/");
          router.addRoute(flattenRouters);
        }
      }
    );
    usePermissionStoreHook().handleWholeMenus(routeList);
  }
  if (!useMultiTagsStoreHook().getMultiTagsCache) {
    useMultiTagsStoreHook().handleTags("equal", [
      ...routerArrays,
      ...usePermissionStoreHook().flatteningRoutes.filter(
        v => v?.meta?.fixedTag
      )
    ]);
  }
  addPathMatch();
}

/** Initialize the route（`new Promise` Writing method to prevent infinite loops in asynchronous requests）*/
function initRouter() {
  if (getConfig()?.CachingAsyncRoutes) {
    // Turn on dynamic route cache locallylocalStorage
    const key = "async-routes";
    const asyncRouteList = storageLocal().getItem(key) as any;
    if (asyncRouteList && asyncRouteList?.length > 0) {
      return new Promise(resolve => {
        handleAsyncRoutes(asyncRouteList);
        resolve(router);
      });
    } else {
      return new Promise(resolve => {
        getAsyncRoutes().then(({ data }) => {
          handleAsyncRoutes(cloneDeep(data));
          storageLocal().setItem(key, data);
          resolve(router);
        });
      });
    }
  } else {
    return new Promise(resolve => {
      getAsyncRoutes().then(({ data }) => {
        handleAsyncRoutes(cloneDeep(data));
        resolve(router);
      });
    });
  }
}

/**
 * Process multi-level nested routes into one-dimensional arrays
 * @param routesList Incoming routes
 * @returns Return processed one-dimensional routes
 */
function formatFlatteningRoutes(routesList: RouteRecordRaw[]) {
  if (routesList.length === 0) return routesList;
  let hierarchyList = buildHierarchyTree(routesList);
  for (let i = 0; i < hierarchyList.length; i++) {
    if (hierarchyList[i].children) {
      hierarchyList = hierarchyList
        .slice(0, i + 1)
        .concat(hierarchyList[i].children, hierarchyList.slice(i + 1));
    }
  }
  return hierarchyList;
}

/**
 * Process one-dimensional arrays into multi-level nested arrays（All routes at level three and above are converted to level two，keep-alive Only supports level two cache）
 * https://github.com/pure-admin/vue-pure-admin/issues/67
 * @param routesList Processed one-dimensional route menu array
 * @returns Return the format of reprocessing the one-dimensional array into the specified route
 */
function formatTwoStageRoutes(routesList: RouteRecordRaw[]) {
  if (routesList.length === 0) return routesList;
  const newRoutesList: RouteRecordRaw[] = [];
  routesList.forEach((v: RouteRecordRaw) => {
    if (v.path === "/") {
      newRoutesList.push({
        component: v.component,
        name: v.name,
        path: v.path,
        redirect: v.redirect,
        meta: v.meta,
        children: []
      });
    } else {
      newRoutesList[0]?.children.push({ ...v });
    }
  });
  return newRoutesList;
}

/** Process cached routes（Add、Delete、Refresh） */
function handleAliveRoute({ name }: ToRouteType, mode?: string) {
  switch (mode) {
    case "add":
      usePermissionStoreHook().cacheOperate({
        mode: "add",
        name
      });
      break;
    case "delete":
      usePermissionStoreHook().cacheOperate({
        mode: "delete",
        name
      });
      break;
    case "refresh":
      usePermissionStoreHook().cacheOperate({
        mode: "refresh",
        name
      });
      break;
    default:
      usePermissionStoreHook().cacheOperate({
        mode: "delete",
        name
      });
      useTimeoutFn(() => {
        usePermissionStoreHook().cacheOperate({
          mode: "add",
          name
        });
      }, 100);
  }
}

/** Filter dynamic routes from the backend Regenerate standard routes */
function addAsyncRoutes(arrRoutes: Array<RouteRecordRaw>) {
  if (!arrRoutes || !arrRoutes.length) return;
  const modulesRoutesKeys = Object.keys(modulesRoutes);
  arrRoutes.forEach((v: RouteRecordRaw) => {
    // AddbackstageAdd attributesmeta，Mark this route as a backend return route
    v.meta.backstage = true;
    // ParentredirectAttribute value：If the child exists and the parentredirectAttribute does not exist，Default to the first childpath；If the child exists and the parentredirectAttribute exists，Take the existing oneredirectProperties，Will overwrite default values
    if (v?.children && v.children.length && !v.redirect)
      v.redirect = v.children[0].path;
    // ParentnameProperty value：If the child exists and the parentnameProperty does not exist，Default is the first childname；If the child exists and the parentnameProperty exists，Take the existing onenameProperties，Will overwrite default values（Note：Parent was found in the testnameCannot be repeated with the childnameRepeat，If repeated, redirection will be invalid（Jump404），So here for the parentnameWhen naming, it will be automatically added`Parent`，Avoid duplication）
    if (v?.children && v.children.length && !v.name)
      v.name = (v.children[0].name as string) + "Parent";
    if (v.meta?.frameSrc) {
      v.component = IFrame;
    } else {
      // Pass to the backendcomponentCompatible with component path and non-pass（If the backend passescomponentComponent path，ThenpathYou can write it casually，If not passed，componentThe component path will followpathKeep consistent）
      const index = v?.component
        ? modulesRoutesKeys.findIndex(ev => ev.includes(v.component as any))
        : modulesRoutesKeys.findIndex(ev => ev.includes(v.path));
      v.component = modulesRoutes[modulesRoutesKeys[index]];
    }
    if (v?.children && v.children.length) {
      addAsyncRoutes(v.children);
    }
  });
  return arrRoutes;
}

/** Get route history mode https://next.router.vuejs.org/zh/guide/essentials/history-mode.html */
function getHistoryMode(routerHistory): RouterHistory {
  // lenFor1 Represents only history mode for2 represents the existence in the historical modebaseparameter https://next.router.vuejs.org/zh/api/#%E5%8F%82%E6%95%B0-1
  const historyMode = routerHistory.split(",");
  const leftMode = historyMode[0];
  const rightMode = historyMode[1];
  // no param
  if (historyMode.length === 1) {
    if (leftMode === "hash") {
      return createWebHashHistory("");
    } else if (leftMode === "h5") {
      return createWebHistory("");
    }
  } //has param
  else if (historyMode.length === 2) {
    if (leftMode === "hash") {
      return createWebHashHistory(rightMode);
    } else if (leftMode === "h5") {
      return createWebHistory(rightMode);
    }
  }
}

/** get the button-level permissions of the current page */
function getAuths(): Array<string> {
  return router.currentRoute.value.meta.auths as Array<string>;
}

/** whether there is button-level permissions */
function hasAuth(value: string | Array<string>): boolean {
  if (!value) return false;
  /** from the current route`meta`get all button-level customizations in the field`code`value */
  const metaAuths = getAuths();
  if (!metaAuths) return false;
  const isAuths = isString(value)
    ? metaAuths.includes(value)
    : isIncludeAllChildren(value, metaAuths);
  return isAuths ? true : false;
}

function handleTopMenu(route) {
  if (route?.children && route.children.length > 1) {
    if (route.redirect) {
      return route.children.filter(cur => cur.path === route.redirect)[0];
    } else {
      return route.children[0];
    }
  } else {
    return route;
  }
}

/** get the first menu in all menus（top menu）*/
function getTopMenu(tag = false): menuType {
  const topMenu = handleTopMenu(
    usePermissionStoreHook().wholeMenus[0]?.children[0]
  );
  tag && useMultiTagsStoreHook().handleTags("push", topMenu);
  return topMenu;
}

export {
  hasAuth,
  getAuths,
  ascending,
  filterTree,
  initRouter,
  getTopMenu,
  addPathMatch,
  isOneOfArray,
  getHistoryMode,
  addAsyncRoutes,
  getParentPaths,
  findRouteByPath,
  handleAliveRoute,
  formatTwoStageRoutes,
  formatFlatteningRoutes,
  filterNoPermissionTree
};
