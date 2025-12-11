<template>
  <div v-if="product" class="max-w-[1200px] mx-auto mt-6 grid grid-cols-12 gap-8">

    <!-- ========== CỘT ẢNH NHỎ (Gallery Left) ========== -->
    <div class="col-span-1 space-y-3 overflow-y-auto max-h-[80vh] pr-2">
      <div
        v-for="(img, index) in galleryImages"
        :key="index"
        class="border rounded cursor-pointer overflow-hidden"
        :class="selectedImage === img ? 'border-red-600' : ''"
        @click="selectedImage = img"
      >
        <img :src="img" class="w-full object-cover" />
      </div>
    </div>

    <!-- ========== ẢNH LỚN ========== -->
    <div class="col-span-6 relative">
      <img :src="selectedImage" class=" w-full rounded-lg shadow" />

      <!-- chuyển ảnh -->
      <button
        class="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 p-2 rounded-full shadow"
        @click="prevImage"
      >
        ‹
      </button>
      <button
        class="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 p-2 rounded-full shadow"
        @click="nextImage"
      >
        ›
      </button>
    </div>

    <!-- ========== CỘT THÔNG TIN ========== -->
    <div class="col-span-5 space-y-4 ">
      <h1 class="text-2xl font-semibold leading-tight">
        {{ product.tenSp }}
      </h1>
      <p class="text-gray-500 text-sm">SKU: {{ product.maSp }}</p>

      <!-- Rating -->
      <div class="flex items-center gap-1 text-yellow-500">
        ⭐⭐⭐⭐⭐
        <span class="text-gray-700 ml-2">
          {{ product.danhGia }} sao | {{ product.soLuongDanhGia }} đánh giá
        </span>
      </div>

      <!-- Price -->
      <div class="text-red-600 font-bold text-3xl">
        {{ formatPrice(product.gia) }}đ
      </div>

      <!-- ========== CHỌN MÀU ========== -->
      <div>
        <p class="font-semibold mb-2">MÀU SẮC</p>
        <div class="flex gap-3">
          <button
            v-for="mau in danhSachMau"
            :key="mau"
            @click="chonMau(mau)"
            class="px-3 py-2 rounded-full border"
            :class="mauDangChon === mau ? 'border-red-600' : ''"
          >
            {{ mau }}
          </button>
        </div>
      </div>

      <!-- ========== CHỌN SIZE ========== -->
      <div>
        <p class="font-semibold mb-2">KÍCH THƯỚC</p>
        <div class="flex gap-3 flex-wrap">
          <button
            v-for="size in danhSachSizeTheoMau"
            :key="size"
            @click="sizeDangChon = size"
            class="px-4 py-2 border rounded-lg"
            :class="sizeDangChon === size ? 'bg-black text-white' : ''"
          >
            {{ size }}
          </button>
        </div>
      </div>

      <!-- ========== SỐ LƯỢNG ========== -->
      <div class="flex items-center gap-4 mt-3">
        <button @click="soLuong > 1 ? soLuong-- : 1" class="px-3 py-2 border">-</button>
        <span class="text-lg">{{ soLuong }}</span>
        <button @click="soLuong++" class="px-3 py-2 border">+</button>
      </div>

      <!-- Actions -->
      <div class="flex gap-4 mt-5">
        <button
          class="flex-1 border border-red-600 text-red-600 py-3 rounded-lg font-semibold"
        >
          🛒 Thêm giỏ hàng
        </button>
        <button class="flex-1 bg-red-600 text-white py-3 rounded-lg font-semibold">
          ⚡ Mua ngay
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import api from "@/api"; 
import { useRoute } from "vue-router";

const product = ref(null);
const selectedImage = ref("");
const galleryImages = ref([]);

const mauDangChon = ref("");
const sizeDangChon = ref("");

const soLuong = ref(1);

const route = useRoute();

onMounted(async () => {
  const id = route.params.id;
  const res = await api.get(`/products/1`);
  product.value = res.data.result;

  // Lấy ảnh đại diện
  const daiDien = product.value.productVariants.find(v => v.daiDien);

  selectedImage.value = daiDien?.urlImage || "/no-image.png";

  // Tạo gallery ảnh (chỉ lấy những ảnh có url hợp lệ)
  galleryImages.value = product.value.productVariants
    .filter(v => v.urlImage && v.urlImage !== "Chưa có ảnh sp")
    .map(v => v.urlImage);

  if (galleryImages.value.length === 0) {
    galleryImages.value = ["/no-image.png"];
  }

  mauDangChon.value = daiDien?.maMs.tenMs;
  sizeDangChon.value = daiDien?.maKc.tenKc;
});

const danhSachMau = computed(() => {
  const set = new Set();
  product.value.productVariants.forEach(v => set.add(v.maMs.tenMs));
  return [...set];
});

const danhSachSizeTheoMau = computed(() => {
  if (!mauDangChon.value) return [];
  const sizes = new Set();
  product.value.productVariants.forEach(v => {
    if (v.maMs.tenMs === mauDangChon.value) {
      sizes.add(v.maKc.tenKc);
    }
  });
  return [...sizes];
});

function chonMau(mau) {
  mauDangChon.value = mau;

  // auto chọn size đầu tiên theo màu
  sizeDangChon.value = danhSachSizeTheoMau.value[0];

  // đổi ảnh theo màu nếu có
  const bienThe = product.value.productVariants.find(
    v => v.maMs.tenMs === mau && v.urlImage !== "Chưa có ảnh sp"
  );

  if (bienThe) selectedImage.value = bienThe.urlImage;
}

const prevImage = () => {
  const idx = galleryImages.value.indexOf(selectedImage.value);
  selectedImage.value =
    galleryImages.value[(idx - 1 + galleryImages.value.length) % galleryImages.value.length];
};

const nextImage = () => {
  const idx = galleryImages.value.indexOf(selectedImage.value);
  selectedImage.value =
    galleryImages.value[(idx + 1) % galleryImages.value.length];
};

function formatPrice(p) {
  return Number(p).toLocaleString("vi-VN");
}
</script>
