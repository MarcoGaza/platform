import { useEventListener } from "@vueuse/core";

/** Is it`img`Tag */
function isImgElement(element) {
  return typeof HTMLImageElement !== "undefined"
    ? element instanceof HTMLImageElement
    : element.tagName.toLowerCase() === "img";
}

// In src/main.ts Just import and call import { addPreventDefault } from "@/utils/preventDefault"; addPreventDefault();
export const addPreventDefault = () => {
  // Prevent keyboard accessF12Open the browser developer tool panel with shortcut keys
  useEventListener(
    window.document,
    "keydown",
    ev => ev.key === "F12" && ev.preventDefault()
  );
  // Prevent the browser's default right-click menu from popping up（Will not affect custom right-click events）
  useEventListener(window.document, "contextmenu", ev => ev.preventDefault());
  // Prevent page elements from being selected
  useEventListener(window.document, "selectstart", ev => ev.preventDefault());
  // Images in browsers are usually draggable by default，And can be opened in a new tab or window，Or drag it to another application，Disable it here，Make it non-draggable by default
  useEventListener(
    window.document,
    "dragstart",
    ev => isImgElement(ev?.target) && ev.preventDefault()
  );
};
