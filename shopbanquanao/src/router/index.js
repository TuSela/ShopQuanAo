import { createRouter, createWebHistory } from "vue-router";

// ===== USER =====
import AuthForm from "../components/header/AuthForm.vue";
import HomePage from "../pages/HomePage.vue";
import NamPage from "../pages/NamPage.vue";
import AccHome from "../pages/AccHome.vue";
import ProductDetails from "../components/product/ProductDetails.vue";
import CartPage from "../pages/CartPage.vue";
import Checkout from "../pages/Checkout.vue";
import NuPage from "../pages/NuPage.vue";
import Category from "../components/product/Category.vue";

// ===== ADMIN =====
import AdminLayout from "../components/admin/AdminLayout.vue";
import CustomerManager from "../pages/admin/CustomerManager.vue";
import ProductList from "../pages/admin/products/ProductList.vue";
import UpdateProduct from "../pages/admin/products/UpdateProduct.vue";
import CreateProduct from "../pages/admin/products/CreateProduct.vue";
import OrderList from "../pages/admin/orders/OrderList.vue";
import OrderDetails from "../pages/admin/orders/OrderDetails.vue";
import CommentsManagement from "../pages/admin/CommentsManagement.vue";
import ColorSizeManager from "../pages/admin/ColorSizeManager.vue";
import Dashboard from "../pages/admin/Dashboard.vue";
import AdminManagement from "../pages/admin/AdminManagement.vue";

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
    path: "/carts",
    name: "Carts",
    component: CartPage,
    meta: { hideFooter: true }
  },
  {
    path: "/checkout",
    name: "Checkout",
    component: Checkout,
    meta: { hideFooter: true }
  },

  // ===== ADMIN (GIỮ NAME AdminLauout) =====
  {
    path: "/admin",
    name: "AdminLauout",
    component: AdminLayout,
    meta: { requiresAdmin: true },
    children: [
      {
        path: "customers",
        name: "AdminCustomers",
        component: CustomerManager
      },
      {
        path: "products",
        component: ProductList
      },
      {
        path: "products/:id",
        component: UpdateProduct
      },
      {
        path: "products/create",
        component: CreateProduct,
      },
      {
        path: "orders",
        component: OrderList,
      },
      {
        path: "orders/:id",
        component: OrderDetails,
      },
      {
        path: "comment",
        component: CommentsManagement,
      },
      {
        path: "colorsize",
        component: ColorSizeManager,
      },
      {
        path: "dashboard",
        component: Dashboard,
      },
      {
        path: "adminmanagement",
        component: AdminManagement,
      },
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
