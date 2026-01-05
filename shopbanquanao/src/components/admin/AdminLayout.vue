<template>
  <div class="flex min-h-screen bg-[#f6f7fb]">
    <!-- SIDEBAR -->
    <aside
  class="fixed left-0 top-0 h-screen w-64
         bg-white shadow-sm flex flex-col z-40">
<div class="h-16 flex items-center px-6 text-lg font-bold">
  Xin chào, {{ adminName }}
</div>


      <nav class="flex-1 px-4 py-4 space-y-1 text-sm text-gray-600">
<SidebarLink :to="'/admin/dashboard'" :icon="ChartBar" label="Dashboard" />
<SidebarLink :to="'/admin/orders'" :icon="FileText" label="Đơn hàng" />
<SidebarLink :to="'/admin/products'" :icon="Package" label="Sản phẩm" />
<SidebarLink :to="'/admin/customers'" :icon="User" label="Khách hàng" />
<SidebarLink :to="'/admin/comment'" :icon="MessageCircle" label="Đánh giá" />
<SidebarLink :to="'/admin/colorsize'" :icon="Palette" label="Màu & Kích cỡ" />
<SidebarLink :to="'/admin/adminmanagement'" :icon="Users" label="Quản trị viên" />
      </nav>
    </aside>

    <!-- MAIN -->
    <div class="flex-1 flex flex-col ml-64">
      <!-- HEADER -->
<header class="h-16 bg-white shadow flex items-center justify-between px-6 sticky top-0 z-30">
  <!-- LEFT: TITLE + BREADCRUMB -->
  <div class="flex flex-col">
    <!-- PAGE TITLE -->
    <h2 class="text-xl font-semibold text-gray-800">
      {{ currentPageTitle }}
    </h2>

    <!-- BREADCRUMB -->
    <nav class="text-gray-400 text-sm flex items-center gap-1 mt-0.5">
      <template v-for="(crumb, index) in breadcrumbs" :key="index">
        <router-link
          v-if="crumb.to"
          :to="crumb.to"
          class="hover:text-gray-600"
        >
          {{ crumb.label }}
        </router-link>
        <span v-else class="text-gray-500">{{ crumb.label }}</span>
        <span v-if="index < breadcrumbs.length -1">/</span>
      </template>
    </nav>
  </div>

  <!-- RIGHT: SEARCH, NOTIFICATION, ADMIN -->
  <div class="flex items-center gap-4">
    <!-- SEARCH BAR -->
    <div class="relative">
      <input
        type="text"
        placeholder="Tìm kiếm..."
        class="pl-10 pr-4 py-1 rounded-lg border border-gray-200 focus:ring-1 focus:ring-blue-500 focus:outline-none text-sm"
      />
      <Search class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
    </div>

    <!-- NOTIFICATION -->
    <button class="relative p-2 rounded-lg hover:bg-gray-100 transition">
      <Bell class="w-5 h-5 text-gray-700" />
      <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs w-4 h-4 flex items-center justify-center rounded-full">3</span>
    </button>

    <!-- ADMIN DROPDOWN -->
    <div class="relative">
      <button
        @click.stop="toggleMenu"
        class="flex items-center gap-2 px-2 py-1 rounded-lg hover:bg-gray-100 transition"
      >
        <img src="/default-avatar.png" class="w-8 h-8 rounded-full border border-gray-200" />
        <span class="text-sm font-medium text-gray-800">{{ adminName }}</span>
        <svg class="w-3 h-3 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
          <path fill-rule="evenodd" d="M5.23 7.21a.75.75 0 011.06.02L10 10.939l3.71-3.71a.75.75 0 111.06 1.06l-4.24 4.24a.75.75 0 01-1.06 0L5.23 8.29a.75.75 0 01.02-1.08z" clip-rule="evenodd"/>
        </svg>
      </button>

      <div
        v-if="showMenu"
        class="absolute right-0 mt-2 w-44 bg-white rounded-xl shadow-lg z-50 overflow-hidden"
      >
<button
  @click="logout"
  class="w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50 text-left flex items-center gap-2"
>
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
    <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9" />
  </svg>
  Đăng xuất
</button>

      </div>
    </div>
  </div>
</header>


      <!-- CONTENT -->
      <main class="p-6 overflow-y-auto h-[calc(100vh-4rem)]" @click="showMenu = false">
        <router-view />
      </main>
    </div>
  </div>
</template>
<script setup>
import SidebarLink from "@/components/admin/SidebarLink.vue";
import { ChartBar, FileText, Package, User, MessageCircle, Palette, Users,Search, Bell } from 'lucide-vue-next';
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const showMenu = ref(false);
const toggleMenu = () => showMenu.value = !showMenu.value;
const logout = () => {
  localStorage.clear();
  location.href = "/";
};
function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // chuẩn Base64
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

const adminName = ref('Admin');

const token = localStorage.getItem('token');
if (token) {
  const payload = parseJwt(token);
  if (payload && payload.sub) {
    adminName.value = payload.sub;
  }
}

// Sidebar routes
const sidebarRoutes = [
  { path: '/admin/dashboard', label: 'Dashboard' },
  { path: '/admin/orders', label: 'Đơn hàng' },
  { path: '/admin/products', label: 'Sản phẩm' },
  { path: '/admin/customers', label: 'Khách hàng' },
  { path: '/admin/comment', label: 'Đánh giá' },
  { path: '/admin/colorsize', label: 'Màu & Kích cỡ' },
  { path: '/admin/adminmanagement', label: 'Quản trị viên' },
];

const route = useRoute();
const router = useRouter();

// Dynamic breadcrumb
const breadcrumbs = computed(() => {
  const pathSegments = route.path.split('/').filter(Boolean); // ["admin", "orders", "123"]
  const crumbList = [];

  // Module chính
  const moduleSegment = pathSegments[1]; // orders, products, etc
  const matchedRoute = sidebarRoutes.find(r => r.path.includes(moduleSegment));
  if (matchedRoute) {
    crumbList.push({ label: matchedRoute.label, to: matchedRoute.path });
  }

  // Chi tiết các segment còn lại
  if (pathSegments.length > 2) {
    for (let i = 2; i < pathSegments.length; i++) {
      crumbList.push({ label: pathSegments[i], to: null });
    }
  }

  return crumbList;
});

// Tiêu đề luôn là module chính
const currentPageTitle = computed(() => {
  return breadcrumbs.value[0]?.label || '';
});


</script>
