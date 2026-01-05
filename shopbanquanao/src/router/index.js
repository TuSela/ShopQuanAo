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
import ForbiddenPage from "../components/admin/ForbiddenPage.vue";

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
        component: CustomerManager,
         meta: { permission: "USER_MANAGE" }
      },
      {
        path: "products",
        component: ProductList,
         meta: { permission: "PRODUCT_MANAGE" }
      },
      {
        path: "products/:id",
        component: UpdateProduct,
         meta: { permission: "PRODUCT_MANAGE" }
      },
      {
        path: "products/create",
        component: CreateProduct,
         meta: { permission: "PRODUCT_MANAGE" }
      },
      {
        path: "orders",
        component: OrderList,
         meta: { permission: "ORDER_MANAGE" }
      },
      {
        path: "orders/:id",
        component: OrderDetails,
         meta: { permission: "ORDER_MANAGE" }
      },
      {
        path: "comment",
        component: CommentsManagement,
         meta: { permission: "COMMENT_MANAGE" }
      },
      {
        path: "colorsize",
        component: ColorSizeManager,
         meta: { permission: "COLOR_MANAGE" }

      },
      {
        path: "dashboard",
        component: Dashboard,
         meta: { permission: "REPORT_VIEW" }
      },
      {
        path: "adminmanagement",
        component: AdminManagement,
         meta: { permission: "ADMIN_MANAGE" }
      },
        {
  path: "/403",
  component: ForbiddenPage
},
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
});
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  if (to.meta.permission) {
    if (!token) return next("/403");

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      const scope = payload.scope || "";

      if (!scope.includes(to.meta.permission)) {
        return next("/403");
      }
    } catch (e) {
      return next("/403");
    }
  }

  next();
});

export default router;
