import { defineStore } from "pinia";
import {
  type multiType,
  type positionType,
  store,
  isUrl,
  isEqual,
  isNumber,
  isBoolean,
  getConfig,
  routerArrays,
  storageLocal,
  responsiveStorageNameSpace
} from "../utils";
import { usePermissionStoreHook } from "./permission";

export const useMultiTagsStore = defineStore({
  id: "pure-multiTags",
  state: () => ({
    // Store tab information（Routing information）
    multiTags: storageLocal().getItem<StorageConfigs>(
      `${responsiveStorageNameSpace()}configure`
    )?.multiTagsCache
      ? storageLocal().getItem<StorageConfigs>(
          `${responsiveStorageNameSpace()}tags`
        )
      : [
          ...routerArrays,
          ...usePermissionStoreHook().flatteningRoutes.filter(
            v => v?.meta?.fixedTag
          )
        ],
    multiTagsCache: storageLocal().getItem<StorageConfigs>(
      `${responsiveStorageNameSpace()}configure`
    )?.multiTagsCache
  }),
  getters: {
    getMultiTagsCache(state) {
      return state.multiTagsCache;
    }
  },
  actions: {
    multiTagsCacheChange(multiTagsCache: boolean) {
      this.multiTagsCache = multiTagsCache;
      if (multiTagsCache) {
        storageLocal().setItem(
          `${responsiveStorageNameSpace()}tags`,
          this.multiTags
        );
      } else {
        storageLocal().removeItem(`${responsiveStorageNameSpace()}tags`);
      }
    },
    tagsCache(multiTags) {
      this.getMultiTagsCache &&
        storageLocal().setItem(
          `${responsiveStorageNameSpace()}tags`,
          multiTags
        );
    },
    handleTags<T>(
      mode: string,
      value?: T | multiType,
      position?: positionType
    ): T {
      switch (mode) {
        case "equal":
          this.multiTags = value;
          this.tagsCache(this.multiTags);
          break;
        case "push":
          {
            const tagVal = value as multiType;
            // Do not add to tab
            if (tagVal?.meta?.hiddenTag) return;
            // If it is an external link, no information needs to be added to the tab
            if (isUrl(tagVal?.name)) return;
            // IftitleIf it is empty, refuse to add empty information to the tab
            if (tagVal?.meta?.title.length === 0) return;
            // showLink:false Do not add to tab
            if (isBoolean(tagVal?.meta?.showLink) && !tagVal?.meta?.showLink)
              return;
            const tagPath = tagVal.path;
            // JudgetagWhether it already exists
            const tagHasExits = this.multiTags.some(tag => {
              return tag.path === tagPath;
            });

            // JudgetagInqueryAre the key values ​​equal?
            const tagQueryHasExits = this.multiTags.some(tag => {
              return isEqual(tag?.query, tagVal?.query);
            });

            // JudgementtagInparamsAre the key values ​​equal?
            const tagParamsHasExits = this.multiTags.some(tag => {
              return isEqual(tag?.params, tagVal?.params);
            });

            if (tagHasExits && tagQueryHasExits && tagParamsHasExits) return;

            // The maximum number of dynamic routes that can be opened
            const dynamicLevel = tagVal?.meta?.dynamicLevel ?? -1;
            if (dynamicLevel > 0) {
              if (
                this.multiTags.filter(e => e?.path === tagPath).length >=
                dynamicLevel
              ) {
                // If the number of currently opened dynamic routes is greater thandynamicLevel，Replace the first dynamic route tag
                const index = this.multiTags.findIndex(
                  item => item?.path === tagPath
                );
                index !== -1 && this.multiTags.splice(index, 1);
              }
            }
            this.multiTags.push(value);
            this.tagsCache(this.multiTags);
            if (
              getConfig()?.MaxTagsLevel &&
              isNumber(getConfig().MaxTagsLevel)
            ) {
              if (this.multiTags.length > getConfig().MaxTagsLevel) {
                this.multiTags.splice(1, 1);
              }
            }
          }
          break;
        case "splice":
          if (!position) {
            const index = this.multiTags.findIndex(v => v.path === value);
            if (index === -1) return;
            this.multiTags.splice(index, 1);
          } else {
            this.multiTags.splice(position?.startIndex, position?.length);
          }
          this.tagsCache(this.multiTags);
          return this.multiTags;
        case "slice":
          return this.multiTags.slice(-1);
      }
    }
  }
});

export function useMultiTagsStoreHook() {
  return useMultiTagsStore(store);
}
