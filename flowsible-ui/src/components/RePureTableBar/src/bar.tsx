import Sortable from "sortablejs";
import { useEpThemeStoreHook } from "@/store/modules/epTheme";
import {
  type PropType,
  ref,
  unref,
  computed,
  nextTick,
  defineComponent,
  getCurrentInstance
} from "vue";
import {
  delay,
  cloneDeep,
  isBoolean,
  isFunction,
  getKeyList
} from "@pureadmin/utils";

import DragIcon from "@/assets/table-bar/drag.svg?component";
import ExpandIcon from "@/assets/table-bar/expand.svg?component";
import RefreshIcon from "@/assets/table-bar/refresh.svg?component";
import SettingIcon from "@/assets/table-bar/settings.svg?component";
import CollapseIcon from "@/assets/table-bar/collapse.svg?component";

const props = {
  /** The leftmost title of the head */
  title: {
    type: String,
    default: "List"
  },
  /** For tree tables，If you want to enable the expansion and collapse function，Pass in the current tablerefThat's it */
  tableRef: {
    type: Object as PropType<any>
  },
  /** Columns to be displayed */
  columns: {
    type: Array as PropType<TableColumnList>,
    default: () => []
  },
  isExpandAll: {
    type: Boolean,
    default: true
  },
  tableKey: {
    type: [String, Number] as PropType<string | number>,
    default: "0"
  }
};

export default defineComponent({
  name: "PureTableBar",
  props,
  emits: ["refresh"],
  setup(props, { emit, slots, attrs }) {
    const size = ref("default");
    const loading = ref(false);
    const checkAll = ref(true);
    const isIndeterminate = ref(false);
    const instance = getCurrentInstance()!;
    const isExpandAll = ref(props.isExpandAll);
    const filterColumns = cloneDeep(props?.columns).filter(column =>
      isBoolean(column?.hide)
        ? !column.hide
        : !(isFunction(column?.hide) && column?.hide())
    );
    let checkColumnList = getKeyList(cloneDeep(props?.columns), "label");
    const checkedColumns = ref(getKeyList(cloneDeep(filterColumns), "label"));
    const dynamicColumns = ref(cloneDeep(props?.columns));

    const getDropdownItemStyle = computed(() => {
      return s => {
        return {
          background:
            s === size.value ? useEpThemeStoreHook().epThemeColor : "",
          color: s === size.value ? "#fff" : "var(--el-text-color-primary)"
        };
      };
    });

    const iconClass = computed(() => {
      return [
        "text-black",
        "dark:text-white",
        "duration-100",
        "hover:!text-primary",
        "cursor-pointer",
        "outline-none"
      ];
    });

    const topClass = computed(() => {
      return [
        "flex",
        "justify-between",
        "pt-[3px]",
        "px-[11px]",
        "border-b-[1px]",
        "border-solid",
        "border-[#dcdfe6]",
        "dark:border-[#303030]"
      ];
    });

    function onReFresh() {
      loading.value = true;
      emit("refresh");
      delay(500).then(() => (loading.value = false));
    }

    function onExpand() {
      isExpandAll.value = !isExpandAll.value;
      toggleRowExpansionAll(props.tableRef.data, isExpandAll.value);
    }

    function toggleRowExpansionAll(data, isExpansion) {
      data.forEach(item => {
        props.tableRef.toggleRowExpansion(item, isExpansion);
        if (item.children !== undefined && item.children !== null) {
          toggleRowExpansionAll(item.children, isExpansion);
        }
      });
    }

    function handleCheckAllChange(val: boolean) {
      checkedColumns.value = val ? checkColumnList : [];
      isIndeterminate.value = false;
      dynamicColumns.value.map(column =>
        val ? (column.hide = false) : (column.hide = true)
      );
    }

    function handleCheckedColumnsChange(value: string[]) {
      checkedColumns.value = value;
      const checkedCount = value.length;
      checkAll.value = checkedCount === checkColumnList.length;
      isIndeterminate.value =
        checkedCount > 0 && checkedCount < checkColumnList.length;
    }

    function handleCheckColumnListChange(val: boolean, label: string) {
      dynamicColumns.value.filter(item => item.label === label)[0].hide = !val;
    }

    async function onReset() {
      checkAll.value = true;
      isIndeterminate.value = false;
      dynamicColumns.value = cloneDeep(props?.columns);
      checkColumnList = [];
      checkColumnList = await getKeyList(cloneDeep(props?.columns), "label");
      checkedColumns.value = getKeyList(cloneDeep(filterColumns), "label");
    }

    const dropdown = {
      dropdown: () => (
        <el-dropdown-menu class="translation">
          <el-dropdown-item
            style={getDropdownItemStyle.value("large")}
            onClick={() => (size.value = "large")}
          >
            Loose
          </el-dropdown-item>
          <el-dropdown-item
            style={getDropdownItemStyle.value("default")}
            onClick={() => (size.value = "default")}
          >
            Default
          </el-dropdown-item>
          <el-dropdown-item
            style={getDropdownItemStyle.value("small")}
            onClick={() => (size.value = "small")}
          >
            Compact
          </el-dropdown-item>
        </el-dropdown-menu>
      )
    };

    /** Column display drag and drop sort */
    const rowDrop = (event: { preventDefault: () => void }) => {
      event.preventDefault();
      nextTick(() => {
        const wrapper: HTMLElement = (
          instance?.proxy?.$refs[`GroupRef${unref(props.tableKey)}`] as any
        ).$el.firstElementChild;
        Sortable.create(wrapper, {
          animation: 300,
          handle: ".drag-btn",
          onEnd: ({ newIndex, oldIndex, item }) => {
            const targetThElem = item;
            const wrapperElem = targetThElem.parentNode as HTMLElement;
            const oldColumn = dynamicColumns.value[oldIndex];
            const newColumn = dynamicColumns.value[newIndex];
            if (oldColumn?.fixed || newColumn?.fixed) {
              // Current column existsfixedProperties Then drag and drop is not allowed
              const oldThElem = wrapperElem.children[oldIndex] as HTMLElement;
              if (newIndex > oldIndex) {
                wrapperElem.insertBefore(targetThElem, oldThElem);
              } else {
                wrapperElem.insertBefore(
                  targetThElem,
                  oldThElem ? oldThElem.nextElementSibling : oldThElem
                );
              }
              return;
            }
            const currentRow = dynamicColumns.value.splice(oldIndex, 1)[0];
            dynamicColumns.value.splice(newIndex, 0, currentRow);
          }
        });
      });
    };

    const isFixedColumn = (label: string) => {
      return dynamicColumns.value.filter(item => item.label === label)[0].fixed
        ? true
        : false;
    };

    const rendTippyProps = (content: string) => {
      // https://vue-tippy.netlify.app/props
      return {
        content,
        offset: [0, 18],
        duration: [300, 0],
        followCursor: true,
        hideOnClick: "toggle"
      };
    };

    const reference = {
      reference: () => (
        <SettingIcon
          class={["w-[16px]", iconClass.value]}
          v-tippy={rendTippyProps("Column settings")}
        />
      )
    };

    return () => (
      <>
        <div {...attrs} class="w-[99/100] mt-2 px-2 pb-2 bg-bg_color">
          <div class="flex justify-between w-full h-[60px] p-4">
            {slots?.title ? (
              slots.title()
            ) : (
              <p class="font-bold truncate">{props.title}</p>
            )}
            <div class="flex items-center justify-around">
              {slots?.buttons ? (
                <div class="flex mr-4">{slots.buttons()}</div>
              ) : null}
              {props.tableRef?.size ? (
                <>
                  <ExpandIcon
                    class={["w-[16px]", iconClass.value]}
                    style={{
                      transform: isExpandAll.value ? "none" : "rotate(-90deg)"
                    }}
                    v-tippy={rendTippyProps(
                      isExpandAll.value ? "Fold" : "Expand"
                    )}
                    onClick={() => onExpand()}
                  />
                  <el-divider direction="vertical" />
                </>
              ) : null}
              <RefreshIcon
                class={[
                  "w-[16px]",
                  iconClass.value,
                  loading.value ? "animate-spin" : ""
                ]}
                v-tippy={rendTippyProps("Refresh")}
                onClick={() => onReFresh()}
              />
              <el-divider direction="vertical" />
              <el-dropdown
                v-slots={dropdown}
                trigger="click"
                v-tippy={rendTippyProps("Density")}
              >
                <CollapseIcon class={["w-[16px]", iconClass.value]} />
              </el-dropdown>
              <el-divider direction="vertical" />

              <el-popover
                v-slots={reference}
                placement="bottom-start"
                popper-style={{ padding: 0 }}
                width="200"
                trigger="click"
              >
                <div class={[topClass.value]}>
                  <el-checkbox
                    class="!-mr-1"
                    label="Column display"
                    v-model={checkAll.value}
                    indeterminate={isIndeterminate.value}
                    onChange={value => handleCheckAllChange(value)}
                  />
                  <el-button type="primary" link onClick={() => onReset()}>
                    Reset
                  </el-button>
                </div>

                <div class="pt-[6px] pl-[11px]">
                  <el-scrollbar max-height="36vh">
                    <el-checkbox-group
                      ref={`GroupRef${unref(props.tableKey)}`}
                      modelValue={checkedColumns.value}
                      onChange={value => handleCheckedColumnsChange(value)}
                    >
                      <el-space
                        direction="vertical"
                        alignment="flex-start"
                        size={0}
                      >
                        {checkColumnList.map((item, index) => {
                          return (
                            <div class="flex items-center">
                              <DragIcon
                                class={[
                                  "drag-btn w-[16px] mr-2",
                                  isFixedColumn(item)
                                    ? "!cursor-no-drop"
                                    : "!cursor-grab"
                                ]}
                                onMouseenter={(event: {
                                  preventDefault: () => void;
                                }) => rowDrop(event)}
                              />
                              <el-checkbox
                                key={index}
                                label={item}
                                value={item}
                                onChange={value =>
                                  handleCheckColumnListChange(value, item)
                                }
                              >
                                <span
                                  title={item}
                                  class="inline-block w-[120px] truncate hover:text-text_color_primary"
                                >
                                  {item}
                                </span>
                              </el-checkbox>
                            </div>
                          );
                        })}
                      </el-space>
                    </el-checkbox-group>
                  </el-scrollbar>
                </div>
              </el-popover>
            </div>
          </div>
          {slots.default({
            size: size.value,
            dynamicColumns: dynamicColumns.value
          })}
        </div>
      </>
    );
  }
});
