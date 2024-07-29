<script setup lang="ts">
import { initRouter } from "@/router/utils";
import { storageLocal } from "@pureadmin/utils";
import { type CSSProperties, ref, computed } from "vue";
import { useUserStoreHook } from "@/store/modules/user";
import { usePermissionStoreHook } from "@/store/modules/permission";

defineOptions({
  name: "PermissionPage"
});

const elStyle = computed((): CSSProperties => {
  return {
    width: "85vw",
    justifyContent: "start"
  };
});

const username = ref(useUserStoreHook()?.username);

const options = [
  {
    value: "admin",
    label: "Administrator role"
  },
  {
    value: "common",
    label: "Normal role"
  }
];

function onChange() {
  useUserStoreHook()
    .loginByUsername({ username: username.value, password: "admin123" })
    .then(res => {
      if (res.success) {
        storageLocal().removeItem("async-routes");
        usePermissionStoreHook().clearAllCachePage();
        initRouter();
      }
    });
}
</script>

<template>
  <div>
    <p class="mb-2">
      Simulate the background to return the corresponding route according to
      different roles，Observe the changes in the left menu（Administrator role
      can view the system management menu、Normal role cannot view the system
      management menu）
    </p>
    <el-card shadow="never" :style="elStyle">
      <template #header>
        <div class="card-header">
          <span>Current role：{{ username }}</span>
        </div>
      </template>
      <el-select v-model="username" class="!w-[160px]" @change="onChange">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-card>
  </div>
</template>
