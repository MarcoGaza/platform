import "./index.scss";
import { isObject } from "@pureadmin/utils";
import type { Directive, DirectiveBinding } from "vue";

export interface RippleOptions {
  /** Custom`ripple`Color，Support`tailwindcss` */
  class?: string;
  /** Whether to spread from the center */
  center?: boolean;
  circle?: boolean;
}

export interface RippleDirectiveBinding
  extends Omit<DirectiveBinding, "modifiers" | "value"> {
  value?: boolean | { class: string };
  modifiers: {
    center?: boolean;
    circle?: boolean;
  };
}

function transform(el: HTMLElement, value: string) {
  el.style.transform = value;
  el.style.webkitTransform = value;
}

const calculate = (
  e: PointerEvent,
  el: HTMLElement,
  value: RippleOptions = {}
) => {
  const offset = el.getBoundingClientRect();

  // Get the click position distance el Vertical and horizontal distance
  let localX = e.clientX - offset.left;
  let localY = e.clientY - offset.top;

  let radius = 0;
  let scale = 0.3;
  // Calculate the click position to el The farthest distance from the vertex，That is, the maximum radius of the circle（Pythagorean Theorem）
  if (el._ripple?.circle) {
    scale = 0.15;
    radius = el.clientWidth / 2;
    radius = value.center
      ? radius
      : radius + Math.sqrt((localX - radius) ** 2 + (localY - radius) ** 2) / 4;
  } else {
    radius = Math.sqrt(el.clientWidth ** 2 + el.clientHeight ** 2) / 2;
  }

  // Center Point Coordinates
  const centerX = `${(el.clientWidth - radius * 2) / 2}px`;
  const centerY = `${(el.clientHeight - radius * 2) / 2}px`;

  // Click Position Coordinates
  const x = value.center ? centerX : `${localX - radius}px`;
  const y = value.center ? centerY : `${localY - radius}px`;

  return { radius, scale, x, y, centerX, centerY };
};

const ripples = {
  show(e: PointerEvent, el: HTMLElement, value: RippleOptions = {}) {
    if (!el?._ripple?.enabled) {
      return;
    }

    // Create ripple Elements and ripple Parent Element
    const container = document.createElement("span");
    const animation = document.createElement("span");

    container.appendChild(animation);
    container.className = "v-ripple__container";

    if (value.class) {
      container.className += ` ${value.class}`;
    }

    const { radius, scale, x, y, centerX, centerY } = calculate(e, el, value);

    // ripple Circle Size
    const size = `${radius * 2}px`;

    animation.className = "v-ripple__animation";
    animation.style.width = size;
    animation.style.height = size;

    el.appendChild(container);

    // Get Target Element Style Sheet
    const computed = window.getComputedStyle(el);
    // Prevent position Caused by Overwriting ripple Position Problem
    if (computed && computed.position === "static") {
      el.style.position = "relative";
      el.dataset.previousPosition = "static";
    }

    animation.classList.add("v-ripple__animation--enter");
    animation.classList.add("v-ripple__animation--visible");
    transform(
      animation,
      `translate(${x}, ${y}) scale3d(${scale},${scale},${scale})`
    );
    animation.dataset.activated = String(performance.now());

    setTimeout(() => {
      animation.classList.remove("v-ripple__animation--enter");
      animation.classList.add("v-ripple__animation--in");
      transform(animation, `translate(${centerX}, ${centerY}) scale3d(1,1,1)`);
    }, 0);
  },

  hide(el: HTMLElement | null) {
    if (!el?._ripple?.enabled) return;

    const ripples = el.getElementsByClassName("v-ripple__animation");

    if (ripples.length === 0) return;
    const animation = ripples[ripples.length - 1] as HTMLElement;

    if (animation.dataset.isHiding) return;
    else animation.dataset.isHiding = "true";

    const diff = performance.now() - Number(animation.dataset.activated);
    const delay = Math.max(250 - diff, 0);

    setTimeout(() => {
      animation.classList.remove("v-ripple__animation--in");
      animation.classList.add("v-ripple__animation--out");

      setTimeout(() => {
        const ripples = el.getElementsByClassName("v-ripple__animation");
        if (ripples.length === 1 && el.dataset.previousPosition) {
          el.style.position = el.dataset.previousPosition;
          delete el.dataset.previousPosition;
        }

        if (animation.parentNode?.parentNode === el)
          el.removeChild(animation.parentNode);
      }, 300);
    }, delay);
  }
};

function isRippleEnabled(value: any): value is true {
  return typeof value === "undefined" || !!value;
}

function rippleShow(e: PointerEvent) {
  const value: RippleOptions = {};
  const element = e.currentTarget as HTMLElement | undefined;

  if (!element?._ripple || element._ripple.touched) return;

  value.center = element._ripple.centered;
  if (element._ripple.class) {
    value.class = element._ripple.class;
  }

  ripples.show(e, element, value);
}

function rippleHide(e: Event) {
  const element = e.currentTarget as HTMLElement | null;
  if (!element?._ripple) return;

  window.setTimeout(() => {
    if (element._ripple) {
      element._ripple.touched = false;
    }
  });
  ripples.hide(element);
}

function updateRipple(
  el: HTMLElement,
  binding: RippleDirectiveBinding,
  wasEnabled: boolean
) {
  const { value, modifiers } = binding;
  const enabled = isRippleEnabled(value);
  if (!enabled) {
    ripples.hide(el);
  }

  el._ripple = el._ripple ?? {};
  el._ripple.enabled = enabled;
  el._ripple.centered = modifiers.center;
  el._ripple.circle = modifiers.circle;
  if (isObject(value) && value.class) {
    el._ripple.class = value.class;
  }

  if (enabled && !wasEnabled) {
    el.addEventListener("pointerdown", rippleShow);
    el.addEventListener("pointerup", rippleHide);
  } else if (!enabled && wasEnabled) {
    removeListeners(el);
  }
}

function removeListeners(el: HTMLElement) {
  el.removeEventListener("pointerdown", rippleShow);
  el.removeEventListener("pointerup", rippleHide);
}

function mounted(el: HTMLElement, binding: RippleDirectiveBinding) {
  updateRipple(el, binding, false);
}

function unmounted(el: HTMLElement) {
  delete el._ripple;
  removeListeners(el);
}

function updated(el: HTMLElement, binding: RippleDirectiveBinding) {
  if (binding.value === binding.oldValue) {
    return;
  }

  const wasEnabled = isRippleEnabled(binding.oldValue);
  updateRipple(el, binding, wasEnabled);
}

export const Ripple: Directive = {
  mounted,
  unmounted,
  updated
};
