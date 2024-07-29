import { ref } from "vue";
import reDialog from "./index.vue";
import { useTimeoutFn } from "@vueuse/core";
import { withInstall } from "@pureadmin/utils";
import type {
  EventType,
  ArgsType,
  DialogProps,
  ButtonProps,
  DialogOptions
} from "./type";

const dialogStore = ref<Array<DialogOptions>>([]);

/** Open the pop-up box */
const addDialog = (options: DialogOptions) => {
  const open = () =>
    dialogStore.value.push(Object.assign(options, { visible: true }));
  if (options?.openDelay) {
    useTimeoutFn(() => {
      open();
    }, options.openDelay);
  } else {
    open();
  }
};

/** Close the pop-up box */
const closeDialog = (options: DialogOptions, index: number, args?: any) => {
  dialogStore.value[index].visible = false;
  options.closeCallBack && options.closeCallBack({ options, index, args });

  const closeDelay = options?.closeDelay ?? 200;
  useTimeoutFn(() => {
    dialogStore.value.splice(index, 1);
  }, closeDelay);
};

/**
 * @description Change the property value of the pop-up box itself
 * @param value Property value
 * @param key Property，Default`title`
 * @param index Pop-up box index（Default`0`，Represents only one pop-up box，For nested pop-ups, assign the pop-up box index to which property value you want to change`index`）
 */
const updateDialog = (value: any, key = "title", index = 0) => {
  dialogStore.value[index][key] = value;
};

/** Close all pop-ups */
const closeAllDialog = () => {
  dialogStore.value = [];
};

/** Don't forget to import and register in the following three places，Register with confidence，Not used`addDialog`The call will not be mounted
 * https://github.com/pure-admin/vue-pure-admin/blob/main/src/App.vue#L4
 * https://github.com/pure-admin/vue-pure-admin/blob/main/src/App.vue#L12
 * https://github.com/pure-admin/vue-pure-admin/blob/main/src/App.vue#L22
 */
const ReDialog = withInstall(reDialog);

export type { EventType, ArgsType, DialogProps, ButtonProps, DialogOptions };
export {
  ReDialog,
  dialogStore,
  addDialog,
  closeDialog,
  updateDialog,
  closeAllDialog
};
