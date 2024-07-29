import type { CSSProperties, VNode, Component } from "vue";

type DoneFn = (cancel?: boolean) => void;
type EventType =
  | "open"
  | "close"
  | "openAutoFocus"
  | "closeAutoFocus"
  | "fullscreenCallBack";
type ArgsType = {
  /** `cancel` Click the cancel button、`sure` Click the confirm button、`close` Click the close button in the upper right corner or a blank page or pressescKey */
  command: "cancel" | "sure" | "close";
};
type ButtonType =
  | "primary"
  | "success"
  | "warning"
  | "danger"
  | "info"
  | "text";

/** https://element-plus.org/zh-CN/component/dialog.html#attributes */
type DialogProps = {
  /** `Dialog` Show and hide */
  visible?: boolean;
  /** `Dialog` Title */
  title?: string;
  /** `Dialog` Width，Default `50%` */
  width?: string | number;
  /** Is it full screen `Dialog`（Will always be in full screen state，Unless the pop-up box is closed），Default `false`，`fullscreen` And `fullscreenIcon` Only when both are transferred `fullscreen` Will take effect */
  fullscreen?: boolean;
  /** Whether to display the full screen operation icon，Default `false`，`fullscreen` And `fullscreenIcon` Only when both are transferred `fullscreen` Will take effect */
  fullscreenIcon?: boolean;
  /** `Dialog CSS` In `margin-top` Value，Default `15vh` */
  top?: string;
  /** Whether a mask layer is required，Default `true` */
  modal?: boolean;
  /** `Dialog` Whether to insert itself into `body` On the element。Nested `Dialog` This property must be specified and assigned a value `true`，Default `false` */
  appendToBody?: boolean;
  /** Whether `Dialog` When it appears `body` Scroll lock，Default `true` */
  lockScroll?: boolean;
  /** `Dialog` Custom class name */
  class?: string;
  /** `Dialog` Custom style */
  style?: CSSProperties;
  /** `Dialog` Open delay time，Unit milliseconds，Default `0` */
  openDelay?: number;
  /** `Dialog` Close delay time，Unit milliseconds，Default `0` */
  closeDelay?: number;
  /** Can it be clicked `modal` Close `Dialog`，Default `true` */
  closeOnClickModal?: boolean;
  /** Can it be pressed `ESC` Close `Dialog`，Default `true` */
  closeOnPressEscape?: boolean;
  /** Whether to display the close button，Default `true` */
  showClose?: boolean;
  /** Callback before closing，Will pause `Dialog` Close. Execute in callback function `done` Parameter method is the time to actually close the dialog box */
  beforeClose?: (done: DoneFn) => void;
  /** For `Dialog` Enable draggable function，Default `false` */
  draggable?: boolean;
  /** Whether to let `Dialog` Of `header` And `footer` Partially centered，Default `false` */
  center?: boolean;
  /** Whether to align the dialog box horizontally and vertically，Default `false` */
  alignCenter?: boolean;
  /** When closed `Dialog` When，Destroy the elements，Default `false` */
  destroyOnClose?: boolean;
};

//element-plus.org/zh-CN/component/popconfirm.html#attributes
type Popconfirm = {
  /** Title */
  title?: string;
  /** Confirm button text */
  confirmButtonText?: string;
  /** Cancel button text */
  cancelButtonText?: string;
  /** Confirm button type，Default `primary` */
  confirmButtonType?: ButtonType;
  /** Cancel button type，Default `text` */
  cancelButtonType?: ButtonType;
  /** Custom icon，Default `QuestionFilled` */
  icon?: string | Component;
  /** `Icon` Color，Default `#f90` */
  iconColor?: string;
  /** Is it hidden `Icon`，Default `false` */
  hideIcon?: boolean;
  /** Delay when closing，Default `200` */
  hideAfter?: number;
  /** Whether to insert the drop-down list of `popover` *#*433*#* `body` element，default `true` */
  teleported?: boolean;
  /** when `popover` the component is not triggered for a long time and `persistent` the property is set to `false` when, `popover` will be deleted，default `false` */
  persistent?: boolean;
  /** pop-up width，minimum width `150px`，default `150` */
  width?: string | number;
};

type BtnClickDialog = {
  options?: DialogOptions;
  index?: number;
};
type BtnClickButton = {
  btn?: ButtonProps;
  index?: number;
};
/** https://element-plus.org/zh-CN/component/button.html#button-attributes */
type ButtonProps = {
  /** button text */
  label: string;
  /** button size */
  size?: "large" | "default" | "small";
  /** button type */
  type?: "primary" | "success" | "warning" | "danger" | "info";
  /** Is it a plain button，default `false` */
  plain?: boolean;
  /** Is it a text button，default `false` */
  text?: boolean;
  /** Whether to display the background color of the text button，Default `false` */
  bg?: boolean;
  /** Is it a link button，Default `false` */
  link?: boolean;
  /** Is it a rounded button，Default `false` */
  round?: boolean;
  /** Is it a round button，Default `false` */
  circle?: boolean;
  /** Confirmation button `Popconfirm` Bubble confirmation box related configuration */
  popconfirm?: Popconfirm;
  /** Is it loading，Default `false` */
  loading?: boolean;
  /** Custom loading status icon component */
  loadingIcon?: string | Component;
  /** Is the button disabled，Default `false` */
  disabled?: boolean;
  /** Is the icon component */
  icon?: string | Component;
  /** Is it native? `autofocus` Properties，Default `false` */
  autofocus?: boolean;
  /** Native `type` Properties，Default `button` */
  nativeType?: "button" | "submit" | "reset";
  /** Automatically insert spaces between two Chinese characters */
  autoInsertSpace?: boolean;
  /** Customize button color, And automatically calculate `hover` And `active` Color after triggering */
  color?: string;
  /** `dark` Mode, Means automatic setting `color` For `dark` Color of mode，Default `false` */
  dark?: boolean;
  /** Customize element tag */
  tag?: string | Component;
  /** Callback triggered after clicking the button */
  btnClick?: ({
    dialog,
    button
  }: {
    /** Current `Dialog` Information */
    dialog: BtnClickDialog;
    /** Current `button` Information */
    button: BtnClickButton;
  }) => void;
};

interface DialogOptions extends DialogProps {
  /** Content area component `props`，Can be passed `defineProps` Receive */
  props?: any;
  /** Whether to hide `Dialog` Contents of button operation area */
  hideFooter?: boolean;
  /** Confirmation button `Popconfirm` Bubble confirmation box related configuration */
  popconfirm?: Popconfirm;
  /**
   * @description Custom dialog title content renderer
   * @see {@link https://element-plus.org/zh-CN/component/dialog.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E5%A4%B4%E9%83%A8}
   */
  headerRenderer?: ({
    close,
    titleId,
    titleClass
  }: {
    close: Function;
    titleId: string;
    titleClass: string;
  }) => VNode | Component;
  /** Custom content renderer */
  contentRenderer?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => VNode | Component;
  /** Custom button operation area content renderer，Will overwrite`footerButtons`And default `Cancel` And `Confirm` Button */
  footerRenderer?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => VNode | Component;
  /** Custom bottom button operation */
  footerButtons?: Array<ButtonProps>;
  /** `Dialog` Callback after opening */
  open?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => void;
  /** `Dialog` Callback after closing（Only click the close button in the upper right corner or a blank page or pressescKey will be triggered only when the page is closed） */
  close?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => void;
  /** `Dialog` Callback after closing。 `args` Returned `command` Value parsing：`cancel` Click the cancel button、`sure` Click the OK button、`close` Click the close button in the upper right corner or a blank page or pressescKey  */
  closeCallBack?: ({
    options,
    index,
    args
  }: {
    options: DialogOptions;
    index: number;
    args: any;
  }) => void;
  /** Callback when the full screen button is clicked */
  fullscreenCallBack?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => void;
  /** Input focus is focused on `Dialog` Callback when content is in focus */
  openAutoFocus?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => void;
  /** Input focus from `Dialog` Callback when content loses focus */
  closeAutoFocus?: ({
    options,
    index
  }: {
    options: DialogOptions;
    index: number;
  }) => void;
  /** Callback when the cancel button at the bottom is clicked，Will pause `Dialog` Close. Execute in the callback function `done` When the parameter method is used, the dialog box is actually closed */
  beforeCancel?: (
    done: Function,
    {
      options,
      index
    }: {
      options: DialogOptions;
      index: number;
    }
  ) => void;
  /** Click the callback of the bottom confirmation button，Will pause `Dialog` Close. Execute in the callback function `done` When the parameter method is used, the dialog box is actually closed */
  beforeSure?: (
    done: Function,
    {
      options,
      index
    }: {
      options: DialogOptions;
      index: number;
    }
  ) => void;
}

export type { EventType, ArgsType, DialogProps, ButtonProps, DialogOptions };
