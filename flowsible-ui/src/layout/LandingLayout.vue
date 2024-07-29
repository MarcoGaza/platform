<!-- @format -->

<template>
  <div>
    <NavBar />
    <main>
      <router-view />
    </main>
    <AppFooter />
  </div>
</template>

<script>
import AppFooter from "@/components/AppFooter.vue";
import NavBar from "@/components/NavBar.vue";

export default {
  components: {
    NavBar,
    AppFooter
  }
};
</script>

<style scoped>
/* Add your styles here */
</style>
