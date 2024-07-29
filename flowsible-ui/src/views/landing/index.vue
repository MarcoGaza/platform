<!-- @format -->

<template>
  <div class="landing-page">
    <header class="bg-gray-100 py-8">
      <div class="container mx-auto flex items-center justify-between">
        <a href="/" class="text-2xl font-bold text-gray-800">Flowsible</a>
        <nav>
          <ul class="flex space-x-6">
            <li>
              <a href="#features" class="hover:text-gray-600">Features</a>
            </li>
            <li><a href="#pricing" class="hover:text-gray-600">Pricing</a></li>
            <li>
              <a href="#testimonials" class="hover:text-gray-600"
                >Testimonials</a
              >
            </li>
            <li>
              <router-link
                to="/landing/login"
                class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-md"
                >Login</router-link
              >
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <section class="bg-white py-24">
      <div class="container mx-auto text-center">
        <h1 class="text-4xl font-bold text-gray-800 mb-4">
          The Future of Workflow Automation
        </h1>
        <p class="text-lg text-gray-600 mb-8">
          Flowsible is a powerful and intuitive workflow automation platform
          that helps you streamline your business processes and save time.
        </p>
        <router-link
          to="/landing/signup"
          class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-md"
          >Get Started</router-link
        >
      </div>
    </section>

    <section id="features" class="bg-gray-100 py-24">
      <!-- Features content -->
    </section>

    <section id="pricing" class="bg-white py-24">
      <!-- Pricing content -->
    </section>

    <section id="testimonials" class="bg-gray-100 py-24">
      <!-- Testimonials content -->
    </section>

    <footer class="bg-gray-800 py-8 text-white text-center">
      <div class="container mx-auto">
        <p>&copy; 2023 Flowsible. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup></script>

<style scoped></style>
