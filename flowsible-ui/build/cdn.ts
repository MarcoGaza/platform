import { Plugin as importToCDN } from "vite-plugin-cdn-import";

/**
 * @description Use when packaging`cdn`Mode，Only for external use（Not used by default，If you need to usecdnMode，Please in .env.production File，Set VITE_CDN Set totrue）
 * The platform uses domesticcdn：https://www.bootcdn.cn，Of course you can also choose https://unpkg.com Or https://www.jsdelivr.com
 * Note：The above-mentioned use only for external use is not completely certain，If your company's intranet deployment has relevantjs、cssFile，You can also change the following configuration accordingly，A complete set of intranet versioncdn
 */
export const cdn = importToCDN({
  //（prodUrlExplanation： name: Corresponding to the followingmodulesOfname，version: Automatically read localpackage.jsonIndependenciesVersion number of the corresponding package in the dependency，path: Corresponding to the followingmodulesOfpath，Of course, you can also write the full path，Will be replacedprodUrl）
  prodUrl: "https://cdn.bootcdn.net/ajax/libs/{name}/{version}/{path}",
  modules: [
    {
      name: "vue",
      var: "Vue",
      path: "vue.global.prod.min.js"
    },
    {
      name: "vue-router",
      var: "VueRouter",
      path: "vue-router.global.min.js"
    },
    // Not directly installed in the projectvue-demi，ButpiniaUsed，So it needs to be importedpiniaIntroduced beforevue-demi（https://github.com/vuejs/pinia/blob/v2/packages/pinia/package.json#L77）
    {
      name: "vue-demi",
      var: "VueDemi",
      path: "index.iife.min.js"
    },
    {
      name: "pinia",
      var: "Pinia",
      path: "pinia.iife.min.js"
    },
    {
      name: "element-plus",
      var: "ElementPlus",
      path: "index.full.min.js",
      css: "index.min.css"
    },
    {
      name: "axios",
      var: "axios",
      path: "axios.min.js"
    },
    {
      name: "dayjs",
      var: "dayjs",
      path: "dayjs.min.js"
    },
    {
      name: "echarts",
      var: "echarts",
      path: "echarts.min.js"
    }
  ]
});
