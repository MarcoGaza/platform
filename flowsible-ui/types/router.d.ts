// Global route type declaration

import type { RouteComponent, RouteLocationNormalized } from "vue-router";
import type { FunctionalComponent } from "vue";

declare global {
  interface ToRouteType extends RouteLocationNormalized {
    meta: CustomizeRouteMeta;
  }

  /**
   * @description Complete sub-route`meta`Configuration table
   */
  interface CustomizeRouteMeta {
    /** Menu name（Compatible with internationalization、Non-internationalization，How to use internationalization must be in the root directory`locales`Add correspondingly under the folder） `Required` */
    title: string;
    /** Menu icon `Optional` */
    icon?: string | FunctionalComponent | IconifyIcon;
    /** Extra icon on the right side of the menu name */
    extraIcon?: string | FunctionalComponent | IconifyIcon;
    /** Whether to display in the menu（Default`true`）`Optional` */
    showLink?: boolean;
    /** Whether to display the parent menu `Optional` */
    showParent?: boolean;
    /** Page level permission settings `Optional` */
    roles?: Array<string>;
    /** Button level permission settings `Optional` */
    auths?: Array<string>;
    /** Route component cache（Enable `true`、Close `false`）`Optional` */
    keepAlive?: boolean;
    /** Embedded`iframe`Link `Optional` */
    frameSrc?: string;
    /** `iframe`Whether to enable the first load animation for the page（Default`true`）`Optional` */
    frameLoading?: boolean;
    /** Page loading animation（Two modes，The second one has a higher weight，The first one is directly adopted`vue`Built-in`transitions`Animation，The second one is to use`animate.css`Write in、Leaving animation，The platform recommends using the second mode，Already built-in`animate.css`，Just write the corresponding animation name）`Optional` */
    transition?: {
      /**
       * @description Current route animation effect
       * @see {@link https://next.router.vuejs.org/guide/advanced/transitions.html#transitions}
       * @see animate.css {@link https://animate.style}
       */
      name?: string;
      /** Entering animation */
      enterTransition?: string;
      /** Leaving animation */
      leaveTransition?: string;
    };
    /** The current menu name or custom information is prohibited from being added to the tab page（Default`false`） */
    hiddenTag?: boolean;
    /** Whether the current menu name is fixedly displayed on the tab page and cannot be closed（Default`false`） */
    fixedTag?: boolean;
    /** The maximum number of dynamic routes that can be opened `Optional` */
    dynamicLevel?: number;
    /** Activate a menu
     * （Mainly used through`query`Or`params`Parameter-passing routes，When they are configured`showLink: false`After that, they are not displayed in the menu，There will be no menu highlighting，
     * And by setting`activePath`Specify the activation menu to get highlighting，`activePath`For the specified activation menu`path`）
     */
    activePath?: string;
  }

  /**
   * @description Complete sub-route configuration table
   */
  interface RouteChildrenConfigsTable {
    /** Sub-route address `Required` */
    path: string;
    /** Route name（Do not repeat the corresponding，Same as the current component`name`Keep consistent）`Required` */
    name?: string;
    /** Route redirection `Optional` */
    redirect?: string;
    /** Load components on demand `Optional` */
    component?: RouteComponent;
    meta?: CustomizeRouteMeta;
    /** Sub-route configuration items */
    children?: Array<RouteChildrenConfigsTable>;
  }

  /**
   * @description Overall route configuration table（Include complete sub-routes）
   */
  interface RouteConfigsTable {
    /** Route address `Required` */
    path: string;
    /** Route name（Keep unique）`Optional` */
    name?: string;
    /** `Layout`Component `Optional` */
    component?: RouteComponent;
    /** Route redirection `Optional` */
    redirect?: string;
    meta?: {
      /** Menu name（Compatible with internationalization、Non-internationalization，How to use internationalization must be in the root directory`locales`Add to the corresponding folder）`Required` */
      title: string;
      /** Menu icon `Optional` */
      icon?: string | FunctionalComponent | IconifyIcon;
      /** Whether to display in the menu（Default`true`）`Optional` */
      showLink?: boolean;
      /** Menu ascending order，The higher the value, the later the ranking（Only for top-level routes）`Optional` */
      rank?: number;
    };
    /** Sub-route configuration items */
    children?: Array<RouteChildrenConfigsTable>;
  }
}

// https://router.vuejs.org/zh/guide/advanced/meta.html#typescript
declare module "vue-router" {
  interface RouteMeta extends CustomizeRouteMeta {}
}
