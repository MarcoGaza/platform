/**
 * This file acts on `vite.config.ts` of `optimizeDeps.include` Depends on pre-built configuration items
 * Depends on pre-built，`vite` When starting, the following include Modules in，Compile esm Format and cache to node_modules/.vite Folder，When the page is loaded into the corresponding module, if the browser has cache, read the browser cache，If not, read the local cache and load on demand
 * Especially when you disable the browser cache（This situation should only occur during the debugging phase）The corresponding module must be added to includein，Otherwise, you will encounter the problem of page freeze when switching between development environments（vite It will think it is a new dependency package and will reload and force refresh the page），Because it can neither use the browser cache，nor is it locally node_modules/.vite in cache
 * Warm reminder：If the third-party library you use is globally imported，that is, imported into src/main.ts file，you don't need to add it to include in，because vite will automatically cache them to node_modules/.vite
 */
const include = [
  "qs",
  "mitt",
  "dayjs",
  "axios",
  "pinia",
  "vue-types",
  "js-cookie",
  "vue-tippy",
  "pinyin-pro",
  "sortablejs",
  "@vueuse/core",
  "@pureadmin/utils",
  "responsive-storage"
];

/**
 * dependencies that are forced to be excluded in pre-build
 * Tips：all local icon modules imported starting with `@iconify-icons/` should be added to the following，*#*225*#* `exclude` in，because the platform recommends using it wherever it is needed Import and import individually，No need for pre-building，Just let the browser load
 */
const exclude = [
  "@iconify-icons/ep",
  "@iconify-icons/ri",
  "@pureadmin/theme/dist/browser-utils"
];

export { include, exclude };
