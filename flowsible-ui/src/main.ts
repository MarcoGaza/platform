import { setupStore } from "@/store";
import { MotionPlugin } from "@vueuse/motion";
import App from "./App.vue";
import { getPlatformConfig } from "./config";
import router from "./router";
// import { useEcharts } from "@/plugins/echarts";
import { useElementPlus } from "@/plugins/elementPlus";
import { injectResponsiveStorage } from "@/utils/responsive";
import { createApp, type Directive } from "vue";

import Table from "@pureadmin/table";
// import PureDescriptions from "@pureadmin/descriptions";

// Introduce reset style
import "./style/reset.scss";
// Import public style
import "./style/index.scss";
// Must be imported inmain.tsIntailwind.css，PreventviteEvery timehmrWill requestsrc/style/index.scssOverallcssFile causes slow hot update
import "element-plus/dist/index.css";
import "./style/tailwind.css";
// Import font icon
import "./assets/iconfont/iconfont.css";
import "./assets/iconfont/iconfont.js";

// Import the landing page component
import LandingPage from "@/views/landing/index.vue";

const app = createApp(App);

// Custom instruction
import * as directives from "@/directives";
Object.keys(directives).forEach(key => {
  app.directive(key, (directives as { [key: string]: Directive })[key]);
});

// Global registration@iconify/vueIcon library
import {
  FontIcon,
  IconifyIconOffline,
  IconifyIconOnline
} from "./components/ReIcon";
app.component("IconifyIconOffline", IconifyIconOffline);
app.component("IconifyIconOnline", IconifyIconOnline);
app.component("FontIcon", FontIcon);

// Global registration button level permission component
import { Auth } from "@/components/ReAuth";
app.component("Auth", Auth);

// Global registrationvue-tippy
import "tippy.js/dist/tippy.css";
import "tippy.js/themes/light.css";
import VueTippy from "vue-tippy";
app.use(VueTippy);

getPlatformConfig(app).then(async config => {
  setupStore(app);
  app.use(router);
  await router.isReady();
  injectResponsiveStorage(app, config);
  app.use(MotionPlugin).use(useElementPlus).use(Table);
  // .use(PureDescriptions)
  // .use(useEcharts);

  // Register the landing page component globally
  app.component("LandingPage", LandingPage);

  app.mount("#app");
});
