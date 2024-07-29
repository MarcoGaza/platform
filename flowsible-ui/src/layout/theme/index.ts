/**
 * @description ⚠️：This file is only for theme plug-ins，Please do not export other tool functions in this file（Run only before the page is loaded）
 */

import type { multipleScopeVarsOptions } from "@pureadmin/theme";

/** Preset theme color */
const themeColors = {
  /* Bright white */
  light: {
    subMenuActiveText: "#000000d9",
    menuBg: "#fff",
    menuHover: "#f6f6f6",
    subMenuBg: "#fff",
    subMenuActiveBg: "#e0ebf6",
    menuText: "rgb(0 0 0 / 60%)",
    sidebarLogo: "#fff",
    menuTitleHover: "#000",
    menuActiveBefore: "#4091f7"
  },
  /* Dodge blue */
  default: {
    subMenuActiveText: "#fff",
    menuBg: "#001529",
    menuHover: "rgb(64 145 247 / 15%)",
    subMenuBg: "#0f0303",
    subMenuActiveBg: "#4091f7",
    menuText: "rgb(254 254 254 / 65%)",
    sidebarLogo: "#002140",
    menuTitleHover: "#fff",
    menuActiveBefore: "#4091f7"
  },
  /* Dark violet */
  saucePurple: {
    subMenuActiveText: "#fff",
    menuBg: "#130824",
    menuHover: "rgb(105 58 201 / 15%)",
    subMenuBg: "#000",
    subMenuActiveBg: "#693ac9",
    menuText: "#7a80b4",
    sidebarLogo: "#1f0c38",
    menuTitleHover: "#fff",
    menuActiveBefore: "#693ac9"
  },
  /* Dark pink */
  pink: {
    subMenuActiveText: "#fff",
    menuBg: "#28081a",
    menuHover: "rgb(216 68 147 / 15%)",
    subMenuBg: "#000",
    subMenuActiveBg: "#d84493",
    menuText: "#7a80b4",
    sidebarLogo: "#3f0d29",
    menuTitleHover: "#fff",
    menuActiveBefore: "#d84493"
  },
  /* Scarlet */
  dusk: {
    subMenuActiveText: "#fff",
    menuBg: "#2a0608",
    menuHover: "rgb(225 60 57 / 15%)",
    subMenuBg: "#000",
    subMenuActiveBg: "#e13c39",
    menuText: "rgb(254 254 254 / 65.1%)",
    sidebarLogo: "#42090c",
    menuTitleHover: "#fff",
    menuActiveBefore: "#e13c39"
  },
  /* Orange Red */
  volcano: {
    subMenuActiveText: "#fff",
    menuBg: "#2b0e05",
    menuHover: "rgb(232 95 51 / 15%)",
    subMenuBg: "#0f0603",
    subMenuActiveBg: "#e85f33",
    menuText: "rgb(254 254 254 / 65%)",
    sidebarLogo: "#441708",
    menuTitleHover: "#fff",
    menuActiveBefore: "#e85f33"
  },
  /* Emerald */
  mingQing: {
    subMenuActiveText: "#fff",
    menuBg: "#032121",
    menuHover: "rgb(89 191 193 / 15%)",
    subMenuBg: "#000",
    subMenuActiveBg: "#59bfc1",
    menuText: "#7a80b4",
    sidebarLogo: "#053434",
    menuTitleHover: "#fff",
    menuActiveBefore: "#59bfc1"
  },
  /* Lime Green */
  auroraGreen: {
    subMenuActiveText: "#fff",
    menuBg: "#0b1e15",
    menuHover: "rgb(96 172 128 / 15%)",
    subMenuBg: "#000",
    subMenuActiveBg: "#60ac80",
    menuText: "#7a80b4",
    sidebarLogo: "#112f21",
    menuTitleHover: "#fff",
    menuActiveBefore: "#60ac80"
  }
};

/**
 * @description Process the preset theme color into the format required by the theme plug-in
 */
export const genScssMultipleScopeVars = (): multipleScopeVarsOptions[] => {
  const result = [] as multipleScopeVarsOptions[];
  Object.keys(themeColors).forEach(key => {
    result.push({
      scopeName: `layout-theme-${key}`,
      varsContent: `
        $subMenuActiveText: ${themeColors[key].subMenuActiveText} !default;
        $menuBg: ${themeColors[key].menuBg} !default;
        $menuHover: ${themeColors[key].menuHover} !default;
        $subMenuBg: ${themeColors[key].subMenuBg} !default;
        $subMenuActiveBg: ${themeColors[key].subMenuActiveBg} !default;
        $menuText: ${themeColors[key].menuText} !default;
        $sidebarLogo: ${themeColors[key].sidebarLogo} !default;
        $menuTitleHover: ${themeColors[key].menuTitleHover} !default;
        $menuActiveBefore: ${themeColors[key].menuActiveBefore} !default;
      `
    } as multipleScopeVarsOptions);
  });
  return result;
};
