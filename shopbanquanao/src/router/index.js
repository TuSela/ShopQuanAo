
import { createRouter, createWebHistory } from "vue-router";

// Import các component
import AuthForm from "../components/header/AuthForm.vue";
import HomePage from "../pages/HomePage.vue"
import NamPage from "../pages/NamPage.vue";
const routes = [
  {
    path: "/",
    name: "Home",
    component: HomePage,
  },
  {
    path: "/auth",
    name: "AuthForm",
    component: AuthForm,
  },
  {
    path: "/Nam",
    name: "Nampage",
    component: NamPage,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
