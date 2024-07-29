import type { Directive } from "vue";
import type { CopyEl, OptimizeOptions, RippleOptions } from "@/directives";

declare module "vue" {
  export interface ComponentCustomProperties {
    /** `Loading` Animation loading command，See details：https://element-plus.org/zh-CN/component/loading.html#%E6%8C%87%E4%BB%A4 */
    vLoading: Directive<Element, boolean>;
    /** Button permission command */
    vAuth: Directive<HTMLElement, string | Array<string>>;
    /** Text copy command（Default double-click to copy） */
    vCopy: Directive<CopyEl, string>;
    /** Long press command */
    vLongpress: Directive<HTMLElement, Function>;
    /** Anti-shake、Throttling command */
    vOptimize: Directive<HTMLElement, OptimizeOptions>;
    /**
     * `v-ripple`Command，Usage is as follows：
     * 1. `v-ripple`Represents basic enablement`ripple`Function
     * 2. `v-ripple="{ class: 'text-red' }"`Represents customization`ripple`Color，Support`tailwindcss`，Effective style is`color`
     * 3. `v-ripple.center`Represents diffusion from the center
     */
    vRipple: Directive<HTMLElement, RippleOptions>;
  }
}

export {};
