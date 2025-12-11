
import { createRouter, createWebHistory } from "vue-router";

// Import các component
import AuthForm from "../components/header/AuthForm.vue";
import HomePage from "../pages/HomePage.vue"
import NamPage from "../pages/NamPage.vue";
import AccHome from "../pages/AccHome.vue";
import ProductDetails from "../components/product/ProductDetails.vue";
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
     meta: { hideLayout: true }
  },
  {
    path: "/Nam",
    name: "Nampage",
    component: NamPage,
  },
  {
    path: "/Acc",
    name: "AccHome",
    component: AccHome,
  },
  {
  path: "/product",
  name: "ProductDetail",
  component: ProductDetails
}
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
