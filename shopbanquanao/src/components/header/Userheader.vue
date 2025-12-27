<template>
  <div class="w-full bg-[#c92127] text-white text-sm">
    <div class="max-w-[1500px] mx-auto h-10 flex items-center justify-center font-sans font-semibold">
      ƯU ĐÃI ĐỘC QUYỀN &nbsp; &lt;&lt; MUA NGAY &gt;&gt;
    </div>
  </div>
<header class="w-full bg-[#faefda] shadow">
<div class="max-w-[1500px] mx-auto flex items-center py-2 px-6 relative">
<!-- Logo -->
<div class="flex items-center gap-2 shrink-0">
  <router-link to="/">
<img src="/src/assets/icon/logo.png" alt="TOKYOLIFE" class="h-6.5" />
</router-link>
</div>


<!-- Search Bar -->
<div class="absolute left-1/2 -translate-x-[45%] w-[1000px] max-w-full">
  <div class="flex items-center border-2 rounded-full overflow-hidden h-10 bg-white">

    <!-- Ô tìm kiếm -->
    <input
      type="text"
      placeholder="Tìm kiếm..."
      class="flex-1 px-4 outline-none text-[18px] "
    />
    <button class="px-4 bg-[#c92127] hover:bg-red-800 text-white flex items-center justify-center h-full">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
      </svg>
    </button>

  </div>
</div>

<div class="ml-auto flex items-center gap-4 shrink-0">
<!-- Cart Icon -->
<button class="relative " @click="goCart">
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z" />
</svg>
<span
    v-if="cartCount > 0"
    class="absolute -top-2 -right-2
           bg-red-600 text-white text-xs
           min-w-[18px] h-[18px]
           flex items-center justify-center
           rounded-full px-1"
  >
    {{ cartCount }}
  </span>
</button>
<div 
  class="relative mx-3"
  @mouseenter="handleEnter"
  @mouseleave="handleLeave"
>
    <!-- AVATAR -->
    <div 
      class="w-10 h-10 bg-gray-300 rounded-full flex items-center justify-center cursor-pointer"
      @click="goProfile"
    >
      <svg v-if="!isLoggedIn()" xmlns="http://www.w3.org/2000/svg"
           class="w-6 h-6 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
        <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-7 8a7 7 0 1114 0H3z"/>
      </svg>

      <img 
        v-if="isLoggedIn() && user.avatar"
        :src="user.avatar"
        class="w-full h-full rounded-full object-cover"
      />

      <svg v-else-if="isLoggedIn()" xmlns="http://www.w3.org/2000/svg"
           class="w-6 h-6 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
        <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-7 8a7 7 0 1114 0H3z"/>
      </svg>
    </div>
    <!-- DROPDOWN -->
    <div 
  v-show="open"
  class="absolute right-0 mt-5 w-64 bg-white shadow-lg rounded-lg p-4 z-50"
  
>
      <!-- USER INFO -->
      <div class="flex items-center mb-4">
        <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center">
          <img 
            v-if="user.avatar" 
            :src="user.avatar" 
            class="w-full h-full rounded-full object-cover"
          />
          <svg v-else xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-7 8a7 7 0 1114 0H3z"/>
          </svg>
        </div>
        <div class="ml-3">
          <p class="font-semibold ">
  {{ user.hoten }}
</p>
        </div>
      </div>

      <!-- MENU ITEM -->
      <ul class="space-y-2">
        <li class="flex items-center gap-2 cursor-pointer hover:text-red-600" @click="goAccHome">
          
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 0 0 2.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 0 0-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 0 0 .75-.75 2.25 2.25 0 0 0-.1-.664m-5.8 0A2.251 2.251 0 0 1 13.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25ZM6.75 12h.008v.008H6.75V12Zm0 3h.008v.008H6.75V15Zm0 3h.008v.008H6.75V18Z" />
</svg> Đơn hàng
        </li>

        <li class="flex items-center gap-2 cursor-pointer text-red-500 hover:text-red-700" @click="logout">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9" />
</svg> Đăng xuất
        </li>
      </ul>
    </div>
</div>
  </div>
</div>

</header>
</template>
<script setup>
import { useAuthStore } from "@/stores/auth";
const auth = useAuthStore();
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
const router = useRouter();
let closeTimeout;
const open = ref(false);


const cartCount = ref(0);



// user reactive
const user = ref({
  hoten: "",
  avatar: ""
});
const loadUser = () => {
  // load user
  const savedUser = localStorage.getItem("user");
  if (savedUser) {
    user.value = JSON.parse(savedUser);
  }

  // load cart count TỪ TOKEN (luôn luôn)
  const token = localStorage.getItem("token");
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    cartCount.value = payload.giohang || 0;

    console.log("Cart count:", cartCount.value);
  }
};

// Khi load trang → lấy user từ localStorage
onMounted(() => {
  loadUser(); // load lần đầu khi mount

  // Lắng nghe event user-updated để reload
  window.addEventListener("user-updated", () => {
    loadUser();
  });
});

// Check đã đăng nhập chưa
const isLoggedIn = () => {
  return localStorage.getItem("token") !== null;
};

const goCart = () => {
  router.push("/carts");
};


const goProfile = () => {
  if (!isLoggedIn()) {
    router.push("/login");
    return;
  }
  router.push("/Acc");
};

const goAccHome = () => {
  if (!isLoggedIn()) {
    router.push("/login");
    return;
  }
  router.push("/Acc");
};

const logout = () => {
  auth.logout(); 
  router.push("/");
};

const handleEnter = () => {
  clearTimeout(closeTimeout);
  open.value = true;
};

const handleLeave = () => {
  closeTimeout = setTimeout(() => {
    open.value = false;
  }, 250);
};
</script>
