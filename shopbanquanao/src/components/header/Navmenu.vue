<template>
  <nav class="relative bg-white">
    <!-- MENU CẤP 1 -->
    <ul class="max-w-7xl mx-auto flex justify-center gap-30 py-3 text-lg font-medium">

      <li class="nav-item group relative">
        <router-link
      :to="{ path: '/category', query: { banChay: 'true' } }">
<a class="cursor-pointer hover:text-[#c92127] text-xl font-sans flex items-center gap-2">
  <img src="/src/assets/icon/sale.webp" alt="Sale" class="w-6 h-6">
  Hot
</a>
</router-link>
      </li>

      <!-- MENU CÓ MEGA -->
      <li
        v-for="menu in menus"
        :key="menu.doiTuong"
        class="nav-item group"
      >
        <router-link
          v-if="menu.doiTuong === 'Nam'"
          to="/Nam"
          class="hover:text-[#c92127] text-xl font-sans font-semibold"
        >
          Nam
        </router-link>

        <router-link
          v-else-if="menu.doiTuong === 'Nữ'"
          to="/Nu"
          class="hover:text-[#c92127] text-xl font-sans font-semibold"
        >
          Nữ
        </router-link>

        <router-link
          v-else="menu.doiTuong === 'Trẻ em'"
          :to="{
    path: '/category',
    query: {
      doiTuong: menu.doiTuong
    }
  }"
          class="hover:text-[#c92127] text-xl font-sans font-semibold"
        >
          Trẻ em
        </router-link>

        <!-- MEGA MENU -->
        <div
          class="absolute left-0 top-full w-screen bg-white group-hover:bg-[#faf9f8] shadow-xl
                 opacity-0 invisible
                 group-hover:opacity-100 group-hover:visible
                 transition-all duration-200 z-50"
        >
          <div class="absolute -top-4 left-0 w-full h-4 bg-transparent"></div>

          <div class="max-w-7xl mx-auto px-10 py-10">
            <div class="grid grid-cols-4 gap-8">

              <div
                v-for="cat in menu.categories"
                :key="cat.tenLoai"
              >
<router-link
  :to="{
    path: '/category',
    query: {
      tenLoai: cat.tenLoai,
      doiTuong: menu.doiTuong
    }
  }"
  class="font-semibold text-xl mb-3 hover:text-[#c92127] block"
>
  {{ cat.tenLoai }}
</router-link>
                <ul class="space-y-2">
                  <!-- ✅ SỬA Ở ĐÂY -->
<li
  v-for="item in cat.danhMuc"
  :key="item.maLoai"
>
<router-link
  :to="{
    path: '/category',
    query: {
      maLoai: item.maLoai,     // 4
      doiTuong: menu.doiTuong  // Nam
    }
  }"
  class=" text-base text-gray-900 hover:text-[#c92127] block"
>
  {{ item.chiTietLoai }}
</router-link>

</li>

                </ul>

              </div>

            </div>
          </div>
        </div>
      </li>

      <li class="nav-item">
        <a class="hover:text-[#c92127] text-xl cursor-pointer font-sans font-semibold">Mua sỉ</a>
      </li>

      <li class="nav-item">
        <a class="hover:text-[#c92127] text-xl cursor-pointer font-sans font-semibold">Tin tức</a>
      </li>

    </ul>
  </nav>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"

const menus = ref([])

onMounted(async () => {
  const res = await axios.get("/api/types/nav-menu")
  menus.value = res.data.result
})
</script>
