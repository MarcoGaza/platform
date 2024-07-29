// Local icons are stored here，In src/layout/index.vue Load in file，Avoid loading at first startup
import { addIcon } from "@iconify/vue/dist/offline";

// Local menu icon，Backend in the route icon Return the corresponding icon string in the front end and use it here addIcon Add to render menu icon
// @iconify-icons/ep
import Lollipop from "@iconify-icons/ep/lollipop";
import HomeFilled from "@iconify-icons/ep/home-filled";
addIcon("ep:lollipop", Lollipop);
addIcon("ep:home-filled", HomeFilled);
// @iconify-icons/ri
import Search from "@iconify-icons/ri/search-line";
import InformationLine from "@iconify-icons/ri/information-line";
addIcon("ri:search-line", Search);
addIcon("ri:information-line", InformationLine);
