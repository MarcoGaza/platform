import type { VNode, Component } from "vue";
import type { iconType } from "@/components/ReIcon/src/types.ts";

export interface OptionsType {
  /** Text */
  label?: string | (() => VNode | Component);
  /**
   * @description Icon，Use platform built-in `useRenderIcon` Function rendering
   * @see {@link Usage reference https://pure-admin.github.io/pure-admin-doc/pages/icon/#%E9%80%9A%E7%94%A8%E5%9B%BE%E6%A0%87-userendericon-hooks }
   */
  icon?: string | Component;
  /** Icon properties、Style configuration */
  iconAttrs?: iconType;
  /** Value */
  value?: any;
  /** Whether to disable */
  disabled?: boolean;
  /** `tooltip` Prompt */
  tip?: string;
}
