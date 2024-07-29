import {
  isArray,
  throttle,
  debounce,
  isObject,
  isFunction
} from "@pureadmin/utils";
import { useEventListener } from "@vueuse/core";
import type { Directive, DirectiveBinding } from "vue";

export interface OptimizeOptions {
  /** Event name */
  event: string;
  /** Event trigger method */
  fn: (...params: any) => any;
  /** Whether to execute immediately */
  immediate?: boolean;
  /** Anti-shake or throttling delay time（Anti-shake default：`200`Milliseconds、Throttling default：`1000`Milliseconds） */
  timeout?: number;
  /** Passed parameters */
  params?: any;
}

/** Anti-shake（v-optimizeOrv-optimize:debounce）、Throttling（v-optimize:throttle）Instructions */
export const optimize: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding<OptimizeOptions>) {
    const { value } = binding;
    const optimizeType = binding.arg ?? "debounce";
    const type = ["debounce", "throttle"].find(t => t === optimizeType);
    if (type) {
      if (value && value.event && isFunction(value.fn)) {
        let params = value?.params;
        if (params) {
          if (isArray(params) || isObject(params)) {
            params = isObject(params) ? Array.of(params) : params;
          } else {
            throw new Error(
              "[Directive: optimize]: `params` must be an array or object"
            );
          }
        }
        // Register using addEventListener on mounted, and removeEventListener automatically on unmounted
        useEventListener(
          el,
          value.event,
          type === "debounce"
            ? debounce(
                params ? () => value.fn(...params) : value.fn,
                value?.timeout ?? 200,
                value?.immediate ?? false
              )
            : throttle(
                params ? () => value.fn(...params) : value.fn,
                value?.timeout ?? 1000
              )
        );
      } else {
        throw new Error(
          "[Directive: optimize]: `event` and `fn` are required, and `fn` must be a function"
        );
      }
    } else {
      throw new Error(
        "[Directive: optimize]: only `debounce` and `throttle` are supported"
      );
    }
  }
};
