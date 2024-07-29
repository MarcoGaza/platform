<script setup lang="ts">
import { hasAuth, getAuths } from "@/router/utils";

defineOptions({
  name: "PermissionButton"
});
</script>

<template>
  <div>
    <p class="mb-2">Currently ownedcodeList：{{ getAuths() }}</p>

    <el-card shadow="never" class="mb-2">
      <template #header>
        <div class="card-header">Component method to determine permissions</div>
      </template>
      <el-space wrap>
        <Auth value="permission:btn:add">
          <el-button plain type="warning">
            Owncode：'permission:btn:add' Permission visible
          </el-button>
        </Auth>
        <Auth :value="['permission:btn:edit']">
          <el-button plain type="primary">
            Owncode：['permission:btn:edit'] Permission visible
          </el-button>
        </Auth>
        <Auth
          :value="[
            'permission:btn:add',
            'permission:btn:edit',
            'permission:btn:delete'
          ]"
        >
          <el-button plain type="danger">
            Owncode：['permission:btn:add', 'permission:btn:edit',
            'permission:btn:delete'] Permission visible
          </el-button>
        </Auth>
      </el-space>
    </el-card>

    <el-card shadow="never" class="mb-2">
      <template #header>
        <div class="card-header">Function method to determine permission</div>
      </template>
      <el-space wrap>
        <el-button v-if="hasAuth('permission:btn:add')" plain type="warning">
          Owncode：'permission:btn:add' Permission visible
        </el-button>
        <el-button v-if="hasAuth(['permission:btn:edit'])" plain type="primary">
          Owncode：['permission:btn:edit'] Permission visible
        </el-button>
        <el-button
          v-if="
            hasAuth([
              'permission:btn:add',
              'permission:btn:edit',
              'permission:btn:delete'
            ])
          "
          plain
          type="danger"
        >
          Owncode：['permission:btn:add', 'permission:btn:edit',
          'permission:btn:delete'] Permission visible
        </el-button>
      </el-space>
    </el-card>

    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          Instruction method to determine permission（This method cannot
          dynamically modify permissions）
        </div>
      </template>
      <el-space wrap>
        <el-button v-auth="'permission:btn:add'" plain type="warning">
          Owncode：'permission:btn:add' Permission visible
        </el-button>
        <el-button v-auth="['permission:btn:edit']" plain type="primary">
          Owncode：['permission:btn:edit'] Permission visible
        </el-button>
        <el-button
          v-auth="[
            'permission:btn:add',
            'permission:btn:edit',
            'permission:btn:delete'
          ]"
          plain
          type="danger"
        >
          Owncode：['permission:btn:add', 'permission:btn:edit',
          'permission:btn:delete'] Permission visible
        </el-button>
      </el-space>
    </el-card>
  </div>
</template>
