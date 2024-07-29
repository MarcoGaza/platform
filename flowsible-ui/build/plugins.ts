import { cdn } from "./cdn";
import vue from "@vitejs/plugin-vue";
import { pathResolve } from "./utils";
import { viteBuildInfo } from "./info";
import svgLoader from "vite-svg-loader";
import type { PluginOption } from "vite";
import checker from "vite-plugin-checker";
import vueJsx from "@vitejs/plugin-vue-jsx";
import Inspector from "vite-plugin-vue-inspector";
import { configCompressPlugin } from "./compress";
import removeNoMatch from "vite-plugin-router-warn";
import { visualizer } from "rollup-plugin-visualizer";
import removeConsole from "vite-plugin-remove-console";
import { themePreprocessorPlugin } from "@pureadmin/theme";
import { genScssMultipleScopeVars } from "../src/layout/theme";
import { vitePluginFakeServer } from "vite-plugin-fake-server";

export function getPluginsList(
  VITE_CDN: boolean,
  VITE_COMPRESSION: ViteCompression
): PluginOption[] {
  const lifecycle = process.env.npm_lifecycle_event;
  return [
    vue(),
    // jsx、tsxSyntax support
    vueJsx(),
    checker({
      typescript: true,
      vueTsc: true,
      eslint: {
        lintCommand: `eslint ${pathResolve("../{src,mock,build}/**/*.{vue,js,ts,tsx}")}`,
        useFlatConfig: true
      },
      terminal: false,
      enableBuild: false
    }),
    // PressCommand(⌘)+Shift(⇧)，Then click the page element to automatically open the localIDEAnd jump to the corresponding code location
    Inspector(),
    viteBuildInfo(),
    /**
     * Remove unnecessary in the development environmentvue-routerDynamic routing warningNo match found for location with path
     * See if it is necessary https://github.com/vuejs/router/issues/521 And https://github.com/vuejs/router/issues/359
     * vite-plugin-router-warnOnly enable in the development environment，Only processvue-routerFiles and only run once when the service starts or restarts，Performance consumption is negligible
     */
    removeNoMatch(),
    // mockSupport
    vitePluginFakeServer({
      logger: false,
      include: "mock",
      infixName: false,
      enableProd: true
    }),
    // Custom theme
    themePreprocessorPlugin({
      scss: {
        multipleScopeVars: genScssMultipleScopeVars(),
        extract: true
      }
    }),
    // svgComponentization support
    svgLoader(),
    VITE_CDN ? cdn : null,
    configCompressPlugin(VITE_COMPRESSION),
    // Online environment deletionconsole
    removeConsole({ external: ["src/assets/iconfont/iconfont.js"] }),
    // Package analysis
    lifecycle === "report"
      ? visualizer({ open: true, brotliSize: true, filename: "report.html" })
      : (null as any)
  ];
}
