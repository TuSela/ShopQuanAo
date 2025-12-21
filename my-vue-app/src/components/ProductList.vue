<template>
  <div class="p-6 max-w-7xl mx-auto space-y-6">
    <!-- HEADER -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold">Quản lý sản phẩm</h1>
      <router-link
        to="/admin/products/create"
        class="px-4 py-2 bg-blue-600 text-white rounded"
      >
        Thêm sản phẩm
      </router-link>
    </div>

    <!-- SEARCH -->
    <input
      type="text"
      placeholder="🔍 Tìm theo tên sản phẩm..."
      class="w-full border rounded-lg px-4 py-2"
      v-model="keyword"
    />
    <!-- FILTER & SORT -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
      <!-- LỌC ĐỐI TƯỢNG -->
      <select v-model="filterDoiTuong" class="border rounded px-3 py-2">
        <option value="">-- Đối tượng --</option>
        <option v-for="dt in doiTuongList" :key="dt" :value="dt">
          {{ dt }}
        </option>
      </select>

      <!-- LỌC LOẠI -->
      <select v-model="filterLoai" class="border rounded px-3 py-2">
        <option value="">-- Loại sản phẩm --</option>
        <option v-for="loai in loaiList" :key="loai" :value="loai">
          {{ loai }}
        </option>
      </select>

      <!-- SẮP XẾP THEO -->
      <select v-model="sortField" class="border rounded px-3 py-2">
        <option value="">-- Sắp xếp theo --</option>
        <option value="maSp">Mã SP</option>
        <option value="tenSp">Tên sản phẩm</option>
        <option value="tenLoai">Tên loại</option>
        <option value="gia">Giá</option>
        <option value="soLuong">Số lượng</option>
      </select>

      <!-- THỨ TỰ -->
      <select v-model="sortOrder" class="border rounded px-3 py-2">
        <option value="asc">Tăng dần</option>
        <option value="desc">Giảm dần</option>
      </select>
    </div>
    <div class="border rounded-lg overflow-y-auto max-h-[490px]">
      <table class="w-full text-sm">
        <thead class="bg-gray-100 sticky top-0 z-10">
          <tr>
            <th class="px-3 py-2 text-left">Mã SP</th>
            <th class="px-3 py-2 text-left">Ảnh</th>
            <th class="px-3 py-2 text-left">Tên</th>
            <th class="px-3 py-2 text-left">Đối tượng</th>
            <th class="px-3 py-2 text-left">Loại</th>
            <th class="px-3 py-2 text-left">Giá</th>
            <th class="px-3 py-2 text-left">Trạng thái</th>
            <th class="px-3 py-2 text-left">Số lượng</th>
            <th class="px-3 py-2 text-left">Hành động</th>
          </tr>
        </thead>

        <tbody>
          <tr
            v-for="p in visibleProducts"
            :key="p.maSp"
            class="border-t hover:bg-gray-50"
          >
            <td class="px-3 py-2 font-medium">{{ p.maSp }}</td>
            <td class="px-3 py-2">
              <img :src="p.anhDaiDien" class="w-12 h-12 object-cover rounded" />
            </td>

            <td class="px-3 py-2 font-medium">{{ p.tenSp }}</td>
            <td class="px-3 py-2">{{ p.types.doiTuong }}</td>
            <td class="px-3 py-2">{{ p.types.tenLoai }}</td>
            <td class="px-3 py-2">{{ p.gia }}</td>
            <td class="px-3 py-2">
              <span :class="p.trangThai ? 'text-green-600' : 'text-red-500'">
                {{ p.trangThai ? "Ngừng bán" : "Đang bán" }}
              </span>
            </td>
            <td class="px-3 py-2">{{ p.soLuong }}</td>
            <td class="px-3 py-2">
              <router-link
                :to="`/admin/products/${p.maSp}`"
                class="text-blue-600"
              >
                ✏ Sửa
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watch } from "vue";
import axios from "@/api/productApi";

/* ================= STATE ================= */
const products = ref([]);
const keyword = ref("");

const filterLoai = ref("");
const filterDoiTuong = ref("");
const sortField = ref("");
const sortOrder = ref("asc");

const visibleCount = ref(30);
const STEP = 30;

/* ================= API ================= */
const fetchProducts = async () => {
  try {
    const res = await axios.get("/products/product-list");
    products.value = res.data.result;
  } catch (e) {
    products.value = [];
  }
};

onMounted(fetchProducts);

/* ================= FILTER LIST ================= */
const loaiList = computed(() => {
  return [
    ...new Set(products.value.map((p) => p.types?.tenLoai).filter(Boolean)),
  ];
});

const doiTuongList = computed(() => {
  return [
    ...new Set(products.value.map((p) => p.types?.doiTuong).filter(Boolean)),
  ];
});

/* ================= SEARCH + FILTER + SORT ================= */
const filteredProducts = computed(() => {
  let list = [...products.value];

  // 🔍 SEARCH
  if (keyword.value) {
    list = list.filter((p) =>
      p.tenSp.toLowerCase().includes(keyword.value.toLowerCase())
    );
  }

  // 🎯 FILTER ĐỐI TƯỢNG
  if (filterDoiTuong.value) {
    list = list.filter((p) => p.types?.doiTuong === filterDoiTuong.value);
  }

  // 🎯 FILTER LOẠI
  if (filterLoai.value) {
    list = list.filter((p) => p.types?.tenLoai === filterLoai.value);
  }

  // 🔃 SORT
  if (sortField.value) {
    list.sort((a, b) => {
      let valA, valB;

      switch (sortField.value) {
        case "tenLoai":
          valA = a.types?.tenLoai || "";
          valB = b.types?.tenLoai || "";
          break;
        default:
          valA = a[sortField.value];
          valB = b[sortField.value];
      }

      if (typeof valA === "string") {
        return sortOrder.value === "asc"
          ? valA.localeCompare(valB)
          : valB.localeCompare(valA);
      }

      return sortOrder.value === "asc" ? valA - valB : valB - valA;
    });
  }

  return list;
});

/* ================= INFINITE SCROLL ================= */

const visibleProducts = computed(() => {
  return filteredProducts.value.slice(0, visibleCount.value);
});

const handleScroll = () => {
  const nearBottom =
    window.innerHeight + window.scrollY >=
    document.documentElement.scrollHeight - 100;

  if (nearBottom && visibleCount.value < filteredProducts.value.length) {
    visibleCount.value += STEP;
  }
};

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

watch([keyword, filterLoai, filterDoiTuong, sortField, sortOrder], () => {
  visibleCount.value = STEP;
});
/* ================= RESET WHEN FILTER CHANGE ================= */
watch([keyword, filterLoai, filterDoiTuong, sortField, sortOrder], () => {
  visibleCount.value = STEP;
});
</script>
