import "./index.css";
import type { OptionsType } from "./type";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import {
  useDark,
  isNumber,
  isFunction,
  useResizeObserver
} from "@pureadmin/utils";
import {
  type PropType,
  h,
  ref,
  toRef,
  watch,
  nextTick,
  defineComponent,
  getCurrentInstance
} from "vue";

const props = {
  options: {
    type: Array<OptionsType>,
    default: () => []
  },
  /** Default selection，According to the first index `0` Mode，Optional（`modelValue`Only pass`number`Type is only responsive） */
  modelValue: {
    type: undefined,
    require: false,
    default: "0"
  },
  /** Adjust the width to the parent element width	 */
  block: {
    type: Boolean,
    default: false
  },
  /** Control size */
  size: {
    type: String as PropType<"small" | "default" | "large">
  },
  /** Whether to disable globally，Default `false` */
  disabled: {
    type: Boolean,
    default: false
  },
  /** When the content changes，Set `resize` Can make it adapt to the container position */
  resize: {
    type: Boolean,
    default: false
  }
};

export default defineComponent({
  name: "ReSegmented",
  props,
  emits: ["change", "update:modelValue"],
  setup(props, { emit }) {
    const width = ref(0);
    const translateX = ref(0);
    const { isDark } = useDark();
    const initStatus = ref(false);
    const curMouseActive = ref(-1);
    const segmentedItembg = ref("");
    const instance = getCurrentInstance()!;
    const curIndex = isNumber(props.modelValue)
      ? toRef(props, "modelValue")
      : ref(0);

    function handleChange({ option, index }, event: Event) {
      if (props.disabled || option.disabled) return;
      event.preventDefault();
      isNumber(props.modelValue)
        ? emit("update:modelValue", index)
        : (curIndex.value = index);
      segmentedItembg.value = "";
      emit("change", { index, option });
    }

    function handleMouseenter({ option, index }, event: Event) {
      if (props.disabled) return;
      event.preventDefault();
      curMouseActive.value = index;
      if (option.disabled || curIndex.value === index) {
        segmentedItembg.value = "";
      } else {
        segmentedItembg.value = isDark.value
          ? "#1f1f1f"
          : "rgba(0, 0, 0, 0.06)";
      }
    }

    function handleMouseleave(_, event: Event) {
      if (props.disabled) return;
      event.preventDefault();
      curMouseActive.value = -1;
    }

    function handleInit(index = curIndex.value) {
      nextTick(() => {
        const curLabelRef = instance?.proxy?.$refs[`labelRef${index}`] as ElRef;
        if (!curLabelRef) return;
        width.value = curLabelRef.clientWidth;
        translateX.value = curLabelRef.offsetLeft;
        initStatus.value = true;
      });
    }

    function handleResizeInit() {
      useResizeObserver(".pure-segmented", () => {
        nextTick(() => {
          handleInit(curIndex.value);
        });
      });
    }

    (props.block || props.resize) && handleResizeInit();

    watch(
      () => curIndex.value,
      index => {
        nextTick(() => {
          handleInit(index);
        });
      },
      {
        immediate: true
      }
    );

    watch(() => props.size, handleResizeInit, {
      immediate: true
    });

    const rendLabel = () => {
      return props.options.map((option, index) => {
        return (
          <label
            ref={`labelRef${index}`}
            class={[
              "pure-segmented-item",
              (props.disabled || option?.disabled) &&
                "pure-segmented-item-disabled"
            ]}
            style={{
              background:
                curMouseActive.value === index ? segmentedItembg.value : "",
              color: props.disabled
                ? null
                : !option.disabled &&
                    (curIndex.value === index || curMouseActive.value === index)
                  ? isDark.value
                    ? "rgba(255, 255, 255, 0.85)"
                    : "rgba(0,0,0,.88)"
                  : ""
            }}
            onMouseenter={event => handleMouseenter({ option, index }, event)}
            onMouseleave={event => handleMouseleave({ option, index }, event)}
            onClick={event => handleChange({ option, index }, event)}
          >
            <input type="radio" name="segmented" />
            <div
              class="pure-segmented-item-label"
              v-tippy={{
                content: option?.tip,
                zIndex: 41000
              }}
            >
              {option.icon && !isFunction(option.label) ? (
                <span
                  class="pure-segmented-item-icon"
                  style={{ marginRight: option.label ? "6px" : 0 }}
                >
                  {h(
                    useRenderIcon(option.icon, {
                      ...option?.iconAttrs
                    })
                  )}
                </span>
              ) : null}
              {option.label ? (
                isFunction(option.label) ? (
                  h(option.label)
                ) : (
                  <span>{option.label}</span>
                )
              ) : null}
            </div>
          </label>
        );
      });
    };

    return () => (
      <div
        class={{
          "pure-segmented": true,
          "pure-segmented-block": props.block,
          "pure-segmented--large": props.size === "large",
          "pure-segmented--small": props.size === "small"
        }}
      >
        <div class="pure-segmented-group">
          <div
            class="pure-segmented-item-selected"
            style={{
              width: `${width.value}px`,
              transform: `translateX(${translateX.value}px)`,
              display: initStatus.value ? "block" : "none"
            }}
          ></div>
          {rendLabel()}
        </div>
      </div>
    );
  }
});
