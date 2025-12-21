import { createRouter, createWebHistory } from "vue-router";

import ProductList from "@/components/ProductList.vue";
import CreateProduct from "@/components/CreateProduct.vue";
import UpdateProduct from "@/components/UpdateProduct.vue";

const routes = [
  {
    path: "/",
    redirect: "/admin/products",
  },
  {
    path: "/admin/products",
    component: ProductList,
  },
  {
    path: "/admin/products/create",
    component: CreateProduct,
  },
  {
    path: "/admin/products/:id",
    component: UpdateProduct,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
