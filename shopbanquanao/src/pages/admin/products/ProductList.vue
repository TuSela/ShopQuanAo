
<template>
  <div class="p-6 max-w-7xl mx-auto space-y-6">

    <!-- HEADER -->
    <div class="flex justify-between items-center">
      <h1 class="text-2xl font-bold flex items-center gap-2">
        <Package class="w-6 h-6" /> Quản lý sản phẩm
      </h1>
      <router-link
        to="/admin/products/create"
        class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-lg transition flex items-center gap-2"
      >
        <PlusCircle class="w-5 h-5" /> Thêm sản phẩm
      </router-link>
    </div>

    <!-- FILTER PANEL -->
    <div class="bg-white rounded-xl shadow p-4 space-y-4">
      <div class="relative">
        <input
          type="text"
          placeholder="Tìm theo tên sản phẩm..."
          class="w-full border rounded-lg px-10 py-2 focus:ring focus:ring-blue-200"
          v-model="keyword"
        />
        <Search class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />
      </div>

      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <select v-model="filterDoiTuong" class="border rounded-lg px-3 py-2">
          <option value="">-- Đối tượng --</option>
          <option v-for="dt in doiTuongList" :key="dt" :value="dt">
            {{ dt }}
          </option>
        </select>

        <select v-model="filterLoai" class="border rounded-lg px-3 py-2">
          <option value="">-- Loại sản phẩm --</option>
          <option v-for="loai in loaiList" :key="loai" :value="loai">
            {{ loai }}
          </option>
        </select>

        <select v-model="sortField" class="border rounded-lg px-3 py-2">
          <option value="">-- Sắp xếp theo --</option>
          <option value="maSp">Mã SP</option>
          <option value="tenSp">Tên sản phẩm</option>
          <option value="tenLoai">Tên loại</option>
          <option value="gia">Giá</option>
          <option value="soLuong">Số lượng</option>
        </select>

        <select v-model="sortOrder" class="border rounded-lg px-3 py-2">
          <option value="asc"> Tăng dần</option>
          <option value="desc"> Giảm dần</option>
        </select>
      </div>
    </div>

    <!-- TABLE -->
    <div class="bg-white rounded-xl shadow overflow-hidden">
      <div class="max-h-[500px] overflow-y-auto">
        <table class="w-full text-sm">
          <thead class="bg-gray-50 sticky top-0 z-10">
            <tr>
              <th class="px-4 py-3 text-left">Mã</th>
              <th class="px-4 py-3 text-left">Ảnh</th>
              <th class="px-4 py-3 text-left">Tên</th>
              <th class="px-4 py-3 text-left">Đối tượng</th>
              <th class="px-4 py-3 text-left">Loại</th>
              <th class="px-4 py-3 text-right">Giá</th>
              <th class="px-4 py-3 text-center">Trạng thái</th>
              <th class="px-4 py-3 text-center">SL</th>
              <th class="px-4 py-3 text-center">Hành động</th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="p in visibleProducts"
              :key="p.maSp"
              class="border-t hover:bg-gray-50 transition"
            >
              <td class="px-4 py-3 font-semibold">{{ p.maSp }}</td>
              <td class="px-4 py-3">
                <img
                  :src="p.anhDaiDien"
                  class="w-12 h-12 object-cover rounded-lg border"
                />
              </td>
              <td class="px-4 py-3 font-medium">{{ p.tenSp }}</td>
              <td class="px-4 py-3">{{ p.types.doiTuong }}</td>
              <td class="px-4 py-3">{{ p.types.tenLoai }}</td>
              <td class="px-4 py-3 text-right font-semibold text-red-600">
                {{ p.gia.toLocaleString() }} ₫
              </td>
              <td class="px-4 py-3 text-center">
                <span
                  class="px-3 py-1 rounded-full text-xs font-semibold"
                  :class="p.trangThai
                    ? 'bg-green-100 text-green-600'
                    : 'bg-red-100 text-red-600'">
                  {{ p.trangThai ? "Đang bán" : "Ngừng bán" }}
                </span>
              </td>
              <td class="px-4 py-3 text-center">{{ p.soLuong }}</td>
              <td class="px-4 py-3 text-center">
                <router-link
                  :to="`/admin/products/${p.maSp}`"
                  class="text-blue-600 hover:underline flex items-center gap-1 justify-center"
                >
                  <Edit2 class="w-4 h-4" /> Sửa
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, computed, onMounted, onUnmounted, watch } from "vue";
import api from "@/api"; 
import { Package, Search, PlusCircle, Edit2 } from 'lucide-vue-next';

/* ================= STATE ================= */
const products = ref([]);
const keyword = ref("");

const filterLoai = ref("");
const filterDoiTuong = ref("");
const sortField = ref("");
const sortOrder = ref("asc");

const visibleCount = ref(200);
const STEP = 30;

/* ================= API ================= */
const fetchProducts = async () => {
  try {
    const res = await api.get("/products/product-list");
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
