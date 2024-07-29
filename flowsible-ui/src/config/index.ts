import axios from "axios";
import type { App } from "vue";

let config: object = {};
const { VITE_PUBLIC_PATH } = import.meta.env;

const setConfig = (cfg?: unknown) => {
  config = Object.assign(config, cfg);
};

const getConfig = (key?: string): PlatformConfigs => {
  if (typeof key === "string") {
    const arr = key.split(".");
    if (arr && arr.length) {
      let data = config;
      arr.forEach(v => {
        if (data && typeof data[v] !== "undefined") {
          data = data[v];
        } else {
          data = null;
        }
      });
      return data;
    }
  }
  return config;
};

/** Get project dynamic global configuration */
export const getPlatformConfig = async (app: App): Promise<undefined> => {
  app.config.globalProperties.$config = getConfig();
  return axios({
    method: "get",
    url: `${VITE_PUBLIC_PATH}platform-config.json`
  })
    .then(({ data: config }) => {
      let $config = app.config.globalProperties.$config;
      // Automatically inject system configuration
      if (app && $config && typeof config === "object") {
        $config = Object.assign($config, config);
        app.config.globalProperties.$config = $config;
        // Set global configuration
        setConfig($config);
      }
      return $config;
    })
    .catch(() => {
      throw "PleasepublicAdd under the folderplatform-config.jsonConfiguration file";
    });
};

/** Local responsive storage namespace */
const responsiveStorageNameSpace = () => getConfig().ResponsiveStorageNameSpace;

export { getConfig, setConfig, responsiveStorageNameSpace };
