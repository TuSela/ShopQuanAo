<template>
  <div class="page">
    <!-- ===== PAGE HEADER ===== -->
    <h1>🛠 Cập nhật sản phẩm</h1>

    <!-- ================================================= -->
    <!-- =============== THÔNG TIN CƠ BẢN ================ -->
    <!-- ================================================= -->
    <section class="card">
      <h2>Thông tin cơ bản</h2>

      <div class="grid">
        <!-- MÃ SẢN PHẨM -->
        <div>
          <label>Mã sản phẩm</label>
          <input disabled :value="productForm.maSp" />
        </div>

        <!-- TÊN SẢN PHẨM -->
        <div>
          <label>Tên sản phẩm</label>
          <input v-model="productForm.tenSp" />
        </div>

        <!-- ĐỐI TƯỢNG -->
        <div>
          <label>Đối tượng</label>
          <select v-model="selectedDoiTuong">
            <option disabled value="">-- Chọn đối tượng --</option>
            <option v-for="dt in doiTuongOptions" :key="dt" :value="dt">
              {{ dt }}
            </option>
          </select>
        </div>

        <!-- TÊN LOẠI -->
        <div>
          <label>Tên loại</label>
          <select v-model="selectedTenLoai" :disabled="!selectedDoiTuong">
            <option disabled value="">-- Chọn tên loại --</option>
            <option v-for="t in tenLoaiOptions" :key="t" :value="t">
              {{ t }}
            </option>
          </select>
        </div>

        <!-- CHI TIẾT LOẠI -->
        <div>
          <label>Chi tiết loại</label>
          <select v-model="productForm.maLoai" :disabled="!selectedTenLoai">
            <option disabled value="">-- Chọn chi tiết --</option>
            <option
              v-for="t in chiTietLoaiOptions"
              :key="t.maLoai"
              :value="t.maLoai"
            >
              {{ t.chiTietLoai }}
            </option>
          </select>
        </div>

        <!-- GIÁ -->
        <div>
          <label>Giá</label>
          <input type="number" v-model="productForm.gia" />
        </div>
      </div>
    </section>

    <!-- ================================================= -->
    <!-- ============== CHI TIẾT SẢN PHẨM ================ -->
    <!-- ================================================= -->
    <section class="card">
      <h2>Chi tiết sản phẩm</h2>

      <div class="editor-wrapper">
        <div
          class="editor"
          contenteditable="true"
          ref="editorRef"
          @input="onEditorInput"
        ></div>
      </div>

      <p class="hint">
        ✍️ Bạn có thể click trực tiếp vào nội dung để chỉnh sửa (giống Word).
      </p>
    </section>

    <!-- ================================================= -->
    <!-- =================== ACTION ====================== -->
    <!-- ================================================= -->
    <section class="card">
      <div class="action-right">
        <button class="btn-update" @click="updateBasicInfo">
          💾 Cập nhật thông tin cơ bản
        </button>
      </div>
    </section>
    <!-- ===== VARIANTS ===== -->
    <section class="card">
      <h2>Biến thể sản phẩm</h2>

      <div
        v-for="color in variantsByColor"
        :key="color.mau"
        class="variant-color"
      >
        <!-- ===== COLOR HEADER ===== -->
        <div class="color-header">
          <img :src="color.image" class="color-image" alt="Ảnh màu" />

          <div class="color-info">
            <h3>Màu: {{ color.mau }}</h3>
            <button class="btn-edit" @click="openEditColor(color)">
              ✏️ Chỉnh sửa
            </button>
          </div>
        </div>

        <!-- ===== SIZE TABLE ===== -->
        <table class="variant-table">
          <thead>
            <tr>
              <th>Mã biến thể</th>
              <th>Size</th>
              <th>Số lượng</th>
              <th>Trạng thái</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="size in color.sizes" :key="size.maBienThe">
              <!-- ❌ KHÔNG HIỂN THỊ maBienThe, maKc, maMs -->
              <td>{{ size.maBienThe }}</td>
              <td>{{ size.size }}</td>

              <td>
                <span
                  :class="{
                    'text-danger': size.soLuong === 0,
                    'text-success': size.soLuong > 0,
                  }"
                >
                  {{ size.soLuong }}
                </span>
              </td>

              <td>
                <span class="status" :class="size.soLuong > 0 ? 'on' : 'off'">
                  {{ size.trangThai > 0 ? "Hoạt động" : "Ngưng bán" }}
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- ===== VARIANT OVERLAY ===== -->
    <div v-if="showModal" class="overlay">
      <div v-if="showModal" class="overlay">
        <div class="modal">
          <h3>Quản lý size - {{ currentColor.mau }}</h3>

          <div v-for="(s, i) in currentColor.sizes" :key="i">
            <label>Size</label>
            <input v-model="s.size" />

            <label>Số lượng</label>
            <input type="number" v-model="s.soLuong" />
            <hr />
          </div>

          <button @click="currentColor.sizes.push({ size: '', soLuong: 0 })">
            ➕ Thêm size
          </button>

          <div class="actions">
            <button @click="closeModal">Huỷ</button>
            <button class="primary" @click="saveColorSizes">Lưu</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive, computed, onMounted, watch, nextTick } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

const route = useRoute();

const maSp = computed(() => route.params.id);
/* ================== STATE ================== */

const variants = ref([]); // danh sách biến thể (phẳng)
const allTypes = ref([]); // danh sách loại

const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");

const isInitializing = ref(true);

/* ===== Modal ===== */
const showModal = ref(false);
const currentColor = ref(null);

/* ===== Editor ===== */
const editorRef = ref(null);

/* ================== PRODUCT FORM ================== */

const productForm = reactive({
  maSp: null,
  tenSp: "",
  gia: 0,
  moTa: "", // HTML chiTiet từ BE
  maLoai: null,
});

/* ================== LOAD PRODUCT ================== */

async function loadProduct(id) {
  const res = await axios.get(`http://localhost:8081/nhom19/products/${id}`);

  const p = res.data.result;

  productForm.maSp = p.maSp;
  productForm.tenSp = p.tenSp;
  productForm.gia = p.gia;
  productForm.maLoai = p.maLoai;
  productForm.moTa = p.chiTiet; // HTML
}

/* ================== LOAD VARIANTS ================== */

async function loadVariants(maSp) {
  const res = await axios.get(`http://localhost:8081/nhom19/variants/${maSp}`);

  const list = res.data.result || [];

  variants.value = list.map((v) => ({
    maBienThe: v.maBienThe,
    mau: v.tenMs,
    image: v.urlImage,
    size: v.tenKc,
    soLuong: v.soluong,
    trangThai: v.trangThai,
    maMs: v.maMs,
    maKc: v.maKc,
  }));
}

/* ================== LOAD TYPES ================== */

async function loadTypes() {
  const res = await axios.get("http://localhost:8081/nhom19/types");
  allTypes.value = res.data.result || [];
}

/* ================== MAP TYPE TO SELECT ================== */

async function mapTypeToSelect() {
  if (!productForm.maLoai || !allTypes.value.length) return;

  const found = allTypes.value.find((t) => t.maLoai === productForm.maLoai);
  if (!found) return;

  selectedDoiTuong.value = found.doiTuong;
  await nextTick();

  selectedTenLoai.value = found.tenLoai;
  await nextTick();

  productForm.maLoai = found.maLoai;
}

/* ================== COMPUTED ================== */

const doiTuongOptions = computed(() => [
  ...new Set(allTypes.value.map((x) => x.doiTuong)),
]);

const tenLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value) return [];
  return [
    ...new Set(
      allTypes.value
        .filter((x) => x.doiTuong === selectedDoiTuong.value)
        .map((x) => x.tenLoai)
    ),
  ];
});

const chiTietLoaiOptions = computed(() => {
  if (!selectedDoiTuong.value || !selectedTenLoai.value) return [];
  return allTypes.value.filter(
    (x) =>
      x.doiTuong === selectedDoiTuong.value &&
      x.tenLoai === selectedTenLoai.value
  );
});

/* ================== VARIANTS GROUP BY COLOR ================== */

const variantsByColor = computed(() => {
  if (!variants.value.length) return [];

  const map = {};

  variants.value.forEach((v) => {
    if (!map[v.mau]) {
      map[v.mau] = {
        mau: v.mau,
        image: v.image,
        sizes: [],
      };
    }

    map[v.mau].sizes.push({
      maBienThe: v.maBienThe,
      size: v.size,
      soLuong: v.soLuong,
      maKc: v.maKc,
    });
  });

  return Object.values(map);
});

/* ================== WATCH ================== */

watch(selectedDoiTuong, () => {
  if (isInitializing.value) return;
  selectedTenLoai.value = "";
  productForm.maLoai = null;
});

watch(selectedTenLoai, () => {
  if (isInitializing.value) return;
  productForm.maLoai = null;
});

/* ================== EDITOR WATCH ================== */

watch(
  () => productForm.moTa,
  (html) => {
    if (editorRef.value && editorRef.value.innerHTML !== html) {
      editorRef.value.innerHTML = html || "";
    }
  },
  { immediate: true }
);

const onEditorInput = () => {
  productForm.moTa = editorRef.value.innerHTML;
};

/* ================== MODAL ================== */

const openEditColor = (color) => {
  currentColor.value = JSON.parse(JSON.stringify(color));
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const saveColorSizes = () => {
  // xoá biến thể cũ của màu
  variants.value = variants.value.filter(
    (v) => v.mau !== currentColor.value.mau
  );

  // add lại
  currentColor.value.sizes.forEach((s) => {
    variants.value.push({
      maBienThe: s.maBienThe,
      mau: currentColor.value.mau,
      image: currentColor.value.image,
      size: s.size,
      soLuong: s.soLuong,
      trangThai: true,
    });
  });

  closeModal();
};

/* ================== LIFECYCLE ================== */

onMounted(async () => {
  await loadTypes();

  if (!maSp.value) {
    console.error("❌ Không tìm thấy mã sản phẩm");
    return;
  }

  await loadProduct(maSp.value);
  await loadVariants(maSp.value);

  await mapTypeToSelect();
  isInitializing.value = false;
});

const updateBasicInfo = async () => {
  try {
    if (!productForm.maSp) {
      alert("❌ Không tìm thấy mã sản phẩm");
      return;
    }

    if (!productForm.maLoai) {
      alert("❌ Vui lòng chọn đầy đủ loại sản phẩm");
      return;
    }

    const payload = {
      tenSp: productForm.tenSp,
      maLoai: productForm.maLoai,
      gia: productForm.gia,
      chiTiet: productForm.moTa, // HTML editor
    };
    /* ================== API UPDATE ================== */
    const res = await axios.put(
      `http://localhost:8081/nhom19/products/${productForm.maSp}`,
      payload
    );

    if (res.data.code === 1000 && res.data.result === true) {
      alert("✅ Cập nhật sản phẩm thành công");
    } else {
      alert("❌ Cập nhật thất bại");
    }
  } catch (err) {
    console.error(err);
    alert("❌ Có lỗi xảy ra khi cập nhật");
  }
};
</script>

<style scoped>
.page {
  max-width: 1000px;
  margin: auto;
  padding: 24px;
  font-family: Arial, sans-serif;
}
h1 {
  margin-bottom: 20px;
}
.card {
  background: #fff;
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 20px;
}
.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}
label {
  font-size: 13px;
  color: #555;
}
input,
textarea,
select {
  width: 100%;
  padding: 6px;
  margin-top: 4px;
}
.row-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.table-wrapper {
  max-height: 300px;
  overflow: auto;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  border: 1px solid #ddd;
  padding: 6px;
  text-align: center;
}
tbody tr:hover {
  background: #f5f5f5;
  cursor: pointer;
}
img {
  width: 40px;
  height: 40px;
}
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 20px;
  width: 320px;
  border-radius: 8px;
}
.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
}
.primary {
  background: #2563eb;
  color: white;
}
.danger {
  background: #dc2626;
  color: white;
}
.preview {
  border: 1px solid #ddd;
  padding: 12px;
  border-radius: 6px;
  max-height: 400px;
  overflow: auto;
}
.preview img {
  max-width: 100%;
}
.variant-color {
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 16px;
  margin-bottom: 20px;
}

.color-header {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 12px;
}

.color-image {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.color-info {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.variant-table {
  width: 100%;
  border-collapse: collapse;
}

.variant-table th,
.variant-table td {
  padding: 10px;
  border-bottom: 1px solid #eee;
  text-align: center;
}

.status.on {
  color: #16a34a;
  font-weight: 600;
}

.status.off {
  color: #dc2626;
  font-weight: 600;
}

.btn-edit {
  background: #2563eb;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-update {
  background: #16a34a;
  color: white;
  border: none;
  padding: 10px 18px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
}

.btn-update:hover {
  background: #15803d;
}
</style>
