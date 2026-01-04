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
  v-model="keyword"
  @keyup.enter="onSearch"
  type="text"
  placeholder="Tìm kiếm..."
  class="flex-1 px-4 outline-none text-[18px]"
/>
<button
  @click="onSearch"
  class="px-4 bg-[#c92127] hover:bg-red-800 text-white flex items-center justify-center h-full"
>

      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
      </svg>
    </button>

  </div>
</div>

<div class="ml-auto flex items-center gap-4 shrink-0">
<!-- Cart Icon -->
<button class="relative hover:text-[#c92127] transition">
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-7">
  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z" />
</svg>
</button>
<!-- User Icon + Login Popup -->
<div class="relative group hover:text-[#c92127] transition">
  <!-- User Icon -->
  <button class="p-2">
    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-7">
  <path stroke-linecap="round" stroke-linejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
</svg>
  </button>
  <!-- Popup Login -->
  <div class="absolute right-0 mt-3 w-80 bg-white shadow-xl rounded-lg p-6 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 z-50">
    
    <h3 class="text-center font-bold text-gray-800">CHÀO MỪNG QUÝ KHÁCH<br/>ĐẾN VỚI TOKYOLIFE</h3>

    <p class="text-center text-gray-600 mt-2 text-sm">
      Đăng nhập tài khoản của Quý Khách
    </p>

    <!-- Nút Đăng Nhập -->
    <button @click="goAuthForm" class="w-full mt-4 py-3 rounded-md bg-[#c92127] hover:bg-red-800 text-white font-semibold shadow">
      ĐĂNG NHẬP
    </button>

    <div class="my-4 border-t border-gray-400"></div>

    <p class="text-center text-gray-800 font-bold">ĐĂNG KÍ THÀNH VIÊN</p>

    <!-- Nút Đăng Ký -->
    <button @click="goAuthForm" class="w-full mt-3 py-3 rounded-md border font-semibold hover:text-white text-gray-700 hover:bg-[#c92127]">
      ĐĂNG KÝ
    </button>
  </div>
</div>
</div>
</div>
</header>
</template>
<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router";
const router = useRouter();
const keyword = ref("")

const onSearch = () => {
  if (!keyword.value.trim()) return

  router.push({
    path: "/category",
    query: {
      keyword: keyword.value.trim(),
      page: 0
    }
  })
}
function goAuthForm() {
  router.push({ name: "AuthForm" }); 
}
</script>
