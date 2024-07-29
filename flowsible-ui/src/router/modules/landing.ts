// src/router/modules/landing.ts
import LandingLayout from "@/layout/LandingLayout.vue";

export default {
  path: "/landing",
  component: LandingLayout,
  children: [
    {
      path: "",
      name: "Landing",
      component: () => import("@/views/landing/index.vue"),
      meta: {
        title: "Landing Page",
        showLink: false
      }
    },
    {
      path: "features",
      name: "Features",
      component: () => import("@/views/features/index.vue"),
      meta: {
        title: "Features"
      }
    },
    {
      path: "pricing",
      name: "Pricing",
      component: () => import("@/views/pricing/index.vue"),
      meta: {
        title: "Pricing"
      }
    },
    {
      path: "login",
      name: "Login",
      component: () => import("@/views/login/index.vue"),
      meta: {
        title: "Login"
      }
    }
  ]
};
