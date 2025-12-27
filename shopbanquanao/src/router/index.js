
import { createRouter, createWebHistory } from "vue-router";

// Import các component
import AuthForm from "../components/header/AuthForm.vue";
import HomePage from "../pages/HomePage.vue"
import NamPage from "../pages/NamPage.vue";
import AccHome from "../pages/AccHome.vue";
import ProductDetails from "../components/product/ProductDetails.vue";
import CartPage from "../pages/CartPage.vue";
import Checkout from "../pages/Checkout.vue";
import NuPage from "../pages/NuPage.vue";
import Category from "../components/product/Category.vue";
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
    path: "/Nu",
    name: "Nupage",
    component: NuPage,
  },
  {
    path: "/Acc",
    name: "AccHome",
    component: AccHome,
  },
  {
  path: "/category",
  name: "CategoryPage",
  component: Category,
},
  {
  path: "/products/:id",
  name: "ProductDetail",
  component: ProductDetails
  },
  {
    path:"/carts",
    name:"Carts",
    component:CartPage,
    meta: { hideFooter: true }
  },
  {
    path:"/checkout",
    name:"Checkout",
    component:Checkout,
    meta: { hideFooter: true }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
