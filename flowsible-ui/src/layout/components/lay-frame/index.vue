<script setup lang="ts">
import { getConfig } from "@/config";
import { useMultiFrame } from "@/layout/hooks/useMultiFrame";
import { useMultiTagsStoreHook } from "@/store/modules/multiTags";
import { type Component, shallowRef, watch, computed } from "vue";
import { type RouteRecordRaw, RouteLocationNormalizedLoaded } from "vue-router";

const props = defineProps<{
  currRoute: RouteLocationNormalizedLoaded;
  currComp: Component;
}>();

const compList = shallowRef([]);
const { setMap, getMap, MAP, delMap } = useMultiFrame();

const keep = computed(() => {
  return (
    getConfig().KeepAlive &&
    props.currRoute.meta?.keepAlive &&
    !!props.currRoute.meta?.frameSrc
  );
});
// Avoid re-rendering LayFrame
const normalComp = computed(() => !keep.value && props.currComp);

watch(useMultiTagsStoreHook().multiTags, (tags: any) => {
  if (!Array.isArray(tags) || !keep.value) {
    return;
  }
  const iframeTags = tags.filter(i => i.meta?.frameSrc);
  // tagsMust be less thanMAP，Only then the closing action is performed，BecauseMAPThe order of insertion istagsOccurs after the change
  if (iframeTags.length < MAP.size) {
    for (const i of MAP.keys()) {
      if (!tags.some(s => s.path === i)) {
        delMap(i);
        compList.value = getMap();
      }
    }
  }
});

watch(
  () => props.currRoute.fullPath,
  path => {
    const multiTags = useMultiTagsStoreHook().multiTags as RouteRecordRaw[];
    const iframeTags = multiTags.filter(i => i.meta?.frameSrc);
    if (keep.value) {
      if (iframeTags.length !== MAP.size) {
        const sameKey = [...MAP.keys()].find(i => path === i);
        if (!sameKey) {
          // Add cache
          setMap(path, props.currComp);
        }
      }
    }

    if (MAP.size > 0) {
      compList.value = getMap();
    }
  },
  {
    immediate: true
  }
);
</script>
<template>
  <template v-for="[fullPath, Comp] in compList" :key="fullPath">
    <div v-show="fullPath === currRoute.fullPath" class="w-full h-full">
      <slot
        :fullPath="fullPath"
        :Comp="Comp"
        :frameInfo="{ frameSrc: currRoute.meta?.frameSrc, fullPath }"
      />
    </div>
  </template>
  <div v-show="!keep" class="w-full h-full">
    <slot :Comp="normalComp" :fullPath="currRoute.fullPath" frameInfo />
  </div>
</template>
