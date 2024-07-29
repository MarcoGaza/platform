<template>
  <nav class="bg-gray-100 py-4">
    <div class="container mx-auto flex justify-between items-center">
      <a href="/" class="text-2xl font-bold text-gray-800">Flowsible</a>
      <ul class="flex space-x-6">
        <li>
          <router-link to="/features" class="hover:text-gray-600"
            >Features</router-link
          >
        </li>
        <li>
          <router-link to="/pricing" class="hover:text-gray-600"
            >Pricing</router-link
          >
        </li>
        <li>
          <router-link
            to="/login"
            class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-md"
            >Login</router-link
          >
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar"
};
</script>

<style scoped>
/* Add your styles here */
</style>
