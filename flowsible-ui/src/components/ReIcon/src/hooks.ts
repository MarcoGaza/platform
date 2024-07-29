import type { iconType } from "./types";
import { h, defineComponent, type Component } from "vue";
import { IconifyIconOnline, IconifyIconOffline, FontIcon } from "../index";

/**
 * Support `iconfont`、Custom `svg` And `iconify` All icons in
 * @see Click to view document icon chapter {@link https://pure-admin.github.io/pure-admin-doc/pages/icon/}
 * @param icon Must be passed Icon
 * @param attrs Optional iconType Property
 * @returns Component
 */
export function useRenderIcon(icon: any, attrs?: iconType): Component {
  // iconfont
  const ifReg = /^IF-/;
  // typeof icon === "function" Belongs toSVG
  if (ifReg.test(icon)) {
    // iconfont
    const name = icon.split(ifReg)[1];
    const iconName = name.slice(
      0,
      name.indexOf(" ") == -1 ? name.length : name.indexOf(" ")
    );
    const iconType = name.slice(name.indexOf(" ") + 1, name.length);
    return defineComponent({
      name: "FontIcon",
      render() {
        return h(FontIcon, {
          icon: iconName,
          iconType,
          ...attrs
        });
      }
    });
  } else if (typeof icon === "function" || typeof icon?.render === "function") {
    // svg
    return attrs ? h(icon, { ...attrs }) : icon;
  } else if (typeof icon === "object") {
    return defineComponent({
      name: "OfflineIcon",
      render() {
        return h(IconifyIconOffline, {
          icon: icon,
          ...attrs
        });
      }
    });
  } else {
    // By existence : Symbol to determine whether it is online or local icon，Existence means online icon，Otherwise
    return defineComponent({
      name: "Icon",
      render() {
        const IconifyIcon =
          icon && icon.includes(":") ? IconifyIconOnline : IconifyIconOffline;
        return h(IconifyIcon, {
          icon: icon,
          ...attrs
        });
      }
    });
  }
}
