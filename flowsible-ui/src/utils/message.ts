import type { VNode } from "vue";
import { isFunction } from "@pureadmin/utils";
import { type MessageHandler, ElMessage } from "element-plus";

type messageStyle = "el" | "antd";
type messageTypes = "info" | "success" | "warning" | "error";

interface MessageParams {
  /** message type，optional `info` 、`success` 、`warning` 、`error` ，default `info` */
  type?: messageTypes;
  /** custom icon，this property will override `type` of icon */
  icon?: any;
  /** Whether to `message` Attribute as `HTML` Fragment processing，Default `false` */
  dangerouslyUseHTMLString?: boolean;
  /** Message style，Optional `el` 、`antd` ，Default `antd` */
  customClass?: messageStyle;
  /** Display time，Unit is milliseconds。Set to `0` Then it will not be closed automatically，`element-plus` Default is `3000` ，Platform changes to default `2000` */
  duration?: number;
  /** Whether to display the close button，Default value `false` */
  showClose?: boolean;
  /** Whether the text is centered，Default value `false` */
  center?: boolean;
  /** `Message` Offset from the top of the window，Default `20` */
  offset?: number;
  /** Set the root element of the component，Default `document.body` */
  appendTo?: string | HTMLElement;
  /** Merge messages with the same content，Not supported `VNode` Message of type，Default value `false` */
  grouping?: boolean;
  /** Callback function when closed, Parameter is closed `message` Example */
  onClose?: Function | null;
}

/** Usage is very simple，Reference src/views/components/message/index.vue File */

/**
 * `Message` Message prompt function
 */
const message = (
  message: string | VNode | (() => VNode),
  params?: MessageParams
): MessageHandler => {
  if (!params) {
    return ElMessage({
      message,
      customClass: "pure-message"
    });
  } else {
    const {
      icon,
      type = "info",
      dangerouslyUseHTMLString = false,
      customClass = "antd",
      duration = 2000,
      showClose = false,
      center = false,
      offset = 20,
      appendTo = document.body,
      grouping = false,
      onClose
    } = params;

    return ElMessage({
      message,
      type,
      icon,
      dangerouslyUseHTMLString,
      duration,
      showClose,
      center,
      offset,
      appendTo,
      grouping,
      // Global search pure-message You can know the style location of this class
      customClass: customClass === "antd" ? "pure-message" : "",
      onClose: () => (isFunction(onClose) ? onClose() : null)
    });
  }
};

/**
 * Close all `Message` Message prompt function
 */
const closeAllMessage = (): void => ElMessage.closeAll();

export { message, closeAllMessage };
