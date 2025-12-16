<template>
  <div v-if="product" class="max-w-[1200px] mx-auto mt-6 grid grid-cols-12 gap-8">

    <!-- ========== CỘT ẢNH NHỎ ========== -->
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
      <img :src="selectedImage" class="w-full rounded-lg shadow" />

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
    <div class="col-span-5 space-y-4">
      <h1 class="text-2xl font-semibold leading-tight">
        {{ product.tenSp }}
      </h1>
      <p class="text-gray-500 text-sm">SKU: {{ product.maSp }}</p>

      <div class="flex items-center gap-1 text-yellow-500">
        ⭐⭐⭐⭐⭐
        <span class="text-gray-700 ml-2">
          {{ product.danhGia }} sao | {{ product.soLuongDanhGia }} đánh giá
        </span>
      </div>

      <div class="text-red-600 font-bold text-3xl">
        {{ formatPrice(product.gia) }}đ
      </div>

      <!-- ========== CHỌN MÀU ========== -->
      <div>
        <p class="font-semibold mb-2">MÀU SẮC</p>
        <div class="flex gap-3">
          <button
            v-for="mau in danhSachMau"
            :key="mau.maMs"
            @click="chonMau(mau)"
            class="px-3 py-2 rounded-full border"
            :class="mauDangChon?.maMs === mau.maMs ? 'border-red-600' : ''"
          >
            {{ mau.tenMs }}
          </button>
        </div>
      </div>

      <!-- ========== CHỌN SIZE ========== -->
      <div>
        <p class="font-semibold mb-2">KÍCH THƯỚC</p>
        <div class="flex gap-3 flex-wrap">
          <button
            v-for="size in danhSachSizeTheoMau"
            :key="size.maKc"
            @click="sizeDangChon = size"
            class="px-4 py-2 border rounded-lg"
            :class="sizeDangChon?.maKc === size.maKc ? 'bg-black text-white' : ''"
          >
            {{ size.tenKc }}
          </button>
        </div>
      </div>

      <!-- ========== SỐ LƯỢNG ========== -->
      <p class="font-semibold mb-2">SỐ LƯỢNG</p>
      <div class="flex items-center gap-4 mt-3">
        <button @click="soLuong > 1 ? soLuong-- : 1" class="px-3 py-2 border">-</button>
        <span class="text-lg">{{ soLuong }}</span>
        <button @click="soLuong++" class="px-3 py-2 border">+</button>
      </div>

      <!-- NÚT HƯỚNG DẪN KÍCH THƯỚC -->
      <button
        @click="showSizeGuide = true"
        class="mt-4 text-blue-600 underline text-sm"
      >
        📏 Hướng dẫn chọn size
      </button>

      <!-- POPUP BẢNG SIZE -->
      <div
        v-if="showSizeGuide"
        class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      >
        <div class="bg-white rounded-lg p-5 max-w-[90%] max-h-[90%] overflow-auto shadow-xl">
          <button
            @click="showSizeGuide = false"
            class="float-right text-xl font-bold"
          >
            ✖
          </button>
          <img
            src="/size-guide.png"
            alt="Bảng size"
            class="w-full mt-4"
          />
        </div>
      </div>

      <div class="flex gap-4 mt-5">
        <button class="flex-1 border border-red-600 text-red-600 py-3 rounded-lg font-semibold"
          @click="addToCart">
          🛒 Thêm giỏ hàng
        </button>
        <button class="flex-1 bg-red-600 text-white py-3 rounded-lg font-semibold">
          ⚡ Mua ngay
        </button>
      </div>
    </div>

    <!-- ========== PHẦN MÔ TẢ (ĐÃ CHUYỂN VÀO GRID) ========== -->
    <!-- trên desktop: bắt đầu từ cột 2 và chiếm 10 cột → căn lề giống ảnh TokyoLife -->
    <div class="col-span-12 lg:col-start-2 lg:col-span-10">
      <div class="mo-ta-wrapper mt-12">
        <h2 class="title">MÔ TẢ SẢN PHẨM</h2>

        <div
          class="mo-ta-content"
          :class="{ expanded: showMore }"
          v-html="moTaHTML"
        ></div>

        <!-- Nút xem thêm -->
        <button class="xem-them-btn" @click="showMore = !showMore">
          {{ showMore ? "Thu gọn ▲" : "Xem thêm ▼" }}
        </button>
      </div>
    </div>

  </div>
</template>

<style>
/* --- WRAPPER CHÍNH --- */
.mo-ta-wrapper {
  width: 100%;
  text-align: left;
  margin-top: 20px;
}

/* --- TIÊU ĐỀ MÔ TẢ --- */
.mo-ta-wrapper .title {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 18px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e6e6e6;
  text-transform: uppercase;
  color: #111;
}

/* --- NỘI DUNG MÔ TẢ (theo layout của TokyoLife) --- */
.mo-ta-content {
  position: relative;
  font-size: 15px;
  line-height: 1.7;
  color: #333;

  max-height: 420px;
  overflow: hidden;
  transition: max-height 0.35s ease;

  /* ensure content flows with column width */
  width: 100%;
  box-sizing: border-box;
  padding-right: 8px;
}

/* Hiệu ứng mờ ở cuối để giống TokyoLife */
.mo-ta-content::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 90px;
  /* nếu nền trang là trắng, dùng white. Nếu nền khác, đổi rgba */
  background: linear-gradient(to bottom, rgba(255,255,255,0), #ffffff 70%);
  pointer-events: none;
  transition: opacity 0.3s ease;
}

/* Khi mở rộng thì tắt hiệu ứng mờ */
.mo-ta-content.expanded {
  max-height: 5000px;
}
.mo-ta-content.expanded::after {
  opacity: 0;
  height: 0;
}

/* Heading con */
.mo-ta-content h2,
.mo-ta-content h3 {
  font-size: 16px;
  font-weight: 700;
  margin: 18px 0 10px;
}

/* Text */
.mo-ta-content p {
  margin: 10px 0;
  color: #333;
}

/* Ảnh trong mô tả – block + full width column */
.mo-ta-content img {
  display: block;
  width: 100%;
  height: auto;
  margin: 18px 0;
  border-radius: 4px;
  object-fit: cover;
}

/* --- NÚT XEM THÊM --- */
.xem-them-btn {
  display: block;
  margin: 12px auto 0;
  background: none;
  border: none;
  color: #c91f1f;
  font-weight: 600;
  cursor: pointer;
  font-size: 15px;
}

/* --- Responsive nhỏ --- */
@media (max-width: 1024px) {
  .mo-ta-content::after {
    height: 60px;
  }
}
</style>

<script setup>
import { ref, computed, onMounted } from "vue";
import api from "@/api";
import { useRoute } from "vue-router";

const route = useRoute();

const moTaHTML = ref("");

const showSizeGuide = ref(false);
const showMore = ref(false);

const product = ref(null);
const selectedImage = ref("");
const galleryImages = ref([]);

const mauDangChon = ref(null);
const sizeDangChon = ref(null);

const soLuong = ref(1);

onMounted(async () => {
  const id = route.params.id;
  try {
    const res = await api.get(`/products/${id}`);
    product.value = res.data.result;

    // ========= GALLERY ẢNH TỪ listAnhSP =========
    galleryImages.value = (product.value.listAnhSP || []).filter(
      a => a && a !== "chưa có ảnh"
    );

    if (galleryImages.value.length === 0) {
      galleryImages.value = ["/no-image.png"];
    }

    selectedImage.value = galleryImages.value[0];

    //============CHI TIET==========
    if (product.value.chiTiet) {
      moTaHTML.value = product.value.chiTiet
    }

    // ========= CHỌN MÀU MẶC ĐỊNH =========
    mauDangChon.value = product.value.variants?.[0] || null;
    sizeDangChon.value = mauDangChon.value?.sizes?.[0] || null;

    // ========= ĐỔI ẢNH THEO MÀU =========
    if (mauDangChon.value && mauDangChon.value.urlImages && mauDangChon.value.urlImages !== "chưa có ảnh") {
      selectedImage.value = mauDangChon.value.urlImages;
    }
  } catch (e) {
    console.error("Lỗi lấy product:", e);
  }
});

const danhSachMau = computed(() => product.value?.variants || []);

const danhSachSizeTheoMau = computed(() => {
  if (!mauDangChon.value) return [];
  return mauDangChon.value.sizes || [];
});

function chonMau(mau) {
  mauDangChon.value = mau;
  sizeDangChon.value = mau.sizes?.[0] || null;

  if (mau.urlImages && mau.urlImages !== "chưa có ảnh") {
    selectedImage.value = mau.urlImages;
  }
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


const addToCart = async () => {
  // 1. Kiểm tra đăng nhập
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Vui lòng đăng nhập để thêm vào giỏ hàng");
    return;
  }

  // 2. Validate chọn đủ
  if (!mauDangChon.value || !sizeDangChon.value) {
    alert("Vui lòng chọn màu và size");
    return;
  }

  // 3. Payload
  const payload = {
    maSp: product.value.maSp,
    maMs: mauDangChon.value.maMs,
    maKc: sizeDangChon.value.maKc,
    soLuong: soLuong.value
  };

  try {
    // ✅ interceptor tự gắn token
    const res = await api.post("/carts", payload);

    // 4. Update token mới từ BE
    const newToken = res.data?.result?.token;
    if (newToken) {
      localStorage.setItem("token", newToken);
      window.dispatchEvent(new Event("user-updated"));
    }

    alert("Đã thêm sản phẩm vào giỏ hàng 🛒");

  } catch (err) {
    console.error("Add to cart error:", err);
    alert("Không thể thêm vào giỏ hàng");
  }
};

</script>
