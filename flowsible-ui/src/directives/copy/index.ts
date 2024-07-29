import { message } from "@/utils/message";
import { useEventListener } from "@vueuse/core";
import { copyTextToClipboard } from "@pureadmin/utils";
import type { Directive, DirectiveBinding } from "vue";

export interface CopyEl extends HTMLElement {
  copyValue: string;
}

/** Text copy instruction（Default double-click copy） */
export const copy: Directive = {
  mounted(el: CopyEl, binding: DirectiveBinding<string>) {
    const { value } = binding;
    if (value) {
      el.copyValue = value;
      const arg = binding.arg ?? "dblclick";
      // Register using addEventListener on mounted, and removeEventListener automatically on unmounted
      useEventListener(el, arg, () => {
        const success = copyTextToClipboard(el.copyValue);
        success
          ? message("Copy successfully", { type: "success" })
          : message("Copy failed", { type: "error" });
      });
    } else {
      throw new Error(
        '[Directive: copy]: need value! Like v-copy="modelValue"'
      );
    }
  },
  updated(el: CopyEl, binding: DirectiveBinding) {
    el.copyValue = binding.value;
  }
};
