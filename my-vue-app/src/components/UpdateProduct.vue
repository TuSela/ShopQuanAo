<template>
  <div class="page">
    <h1>🛠 Cập nhật sản phẩm</h1>
    <!-- ===== BASIC INFO ===== -->
    <section class="card">
      <h2>Thông tin cơ bản</h2>
      <div class="grid">
        <div>
          <label>Mã sản phẩm</label>
          <input disabled :value="productForm.maSp" />
        </div>
        <div>
          <label>Tên sản phẩm</label> <input v-model="productForm.tenSp" />
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
        <div>
          <label>Giá</label> <input type="number" v-model="productForm.gia" />
        </div>
      </div>
    </section>
    <!-- ===== MÔ TẢ ===== -->
    <section class="card">
      <h2>Chi tiết sản phẩm</h2>

      <!-- Editor -->
      <textarea rows="6" v-model="productForm.moTa"></textarea>

      <!-- Preview -->
      <h3 style="margin-top: 12px">Xem trước</h3>
      <div class="preview" v-html="productForm.moTa"></div>
    </section>
    <!-- ===== VARIANT LIST ===== -->
    <section class="card">
      <div class="row-between">
        <h2>Danh sách biến thể</h2>
        <button @click="openCreate">➕ Thêm biến thể</button>
      </div>
      <div class="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Ảnh</th>
              <th>Màu</th>
              <th>Số size</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="c in variantsByColor"
              :key="c.mau"
              @click="openEditColor(c)"
            >
              <td><img :src="c.image" /></td>
              <td>{{ c.mau }}</td>
              <td>{{ c.sizes.length }}</td>
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
import { ref, reactive, computed, onMounted, watch } from "vue";
import axios from "axios";
/* ===== STATE ===== */

const allTypes = ref([]);
const selectedDoiTuong = ref("");
const selectedTenLoai = ref("");

const isInitializing = ref(true);

const currentColor = ref(null);

const openEditColor = (color) => {
  currentColor.value = JSON.parse(JSON.stringify(color));
  showModal.value = true;
};
/* ===== MOCK PRODUCT (SAU NÀY LẤY API) ===== */

const productForm = reactive({
  maSp: "1",
  tenSp: "Áo thun nam basic",
  gia: 199000,
  moTa: "Áo thun cotton 100%",
  maLoai: 2,
});
async function loadProduct(id) {
  const res = await axios.get(`http://localhost:8081/nhom19/products/${id}`);

  const p = res.data.result;

  // ===== BASIC INFO =====
  productForm.maSp = p.maSp;
  productForm.tenSp = p.tenSp;
  productForm.gia = p.gia;
  productForm.moTa = p.chiTiet; // HTML
  productForm.maLoai = p.maLoai;

  // ===== MAP VARIANTS =====
  variants.value = [];

  p.variants.forEach((color) => {
    color.sizes.forEach((s) => {
      variants.value.push({
        maPv: `${color.maMs}-${s.maKc}`,
        image: color.urlImages,
        mau: color.tenMs,
        size: s.tenKc,
        soLuong: s.soluong,
        trangThai: true,
      });
    });
  });
}
onMounted(async () => {
  await loadTypes(); // load danh sách loại
  await loadProduct(12); // load sản phẩm
  await mapTypeToSelect(); // map maLoai → select
  isInitializing.value = false;
});
/* ===== API ===== */
async function loadTypes() {
  const res = await axios.get("http://localhost:8081/nhom19/types");
  allTypes.value = res.data.result || [];
}
/* ===== MAP PRODUCT → SELECT ===== */
import { nextTick } from "vue";

async function mapTypeToSelect() {
  if (!productForm.maLoai || !allTypes.value.length) return;

  const found = allTypes.value.find((x) => x.maLoai === productForm.maLoai);
  if (!found) return;

  // 1. Set đối tượng
  selectedDoiTuong.value = found.doiTuong;
  await nextTick();

  // 2. Set tên loại
  selectedTenLoai.value = found.tenLoai;
  await nextTick();

  // 3. Set chi tiết loại (maLoai)
  productForm.maLoai = found.maLoai;
}
/* ===== COMPUTED OPTIONS ===== */
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
/* ===== WATCH (CHỈ RESET KHI USER ĐỔI) ===== */
watch(selectedDoiTuong, () => {
  if (isInitializing.value) return;
  selectedTenLoai.value = "";
  productForm.maLoai = "";
});
watch(selectedTenLoai, () => {
  if (isInitializing.value) return;
  productForm.maLoai = "";
});
/* ===== LIFECYCLE ===== */
onMounted(async () => {
  await loadTypes();
  mapTypeToSelect();
  isInitializing.value = false;
});
/* ===== MOCK VARIANTS ===== */
const variants = ref([
  {
    maPv: "PV01",
    image: "",
    mau: "Đen",
    size: "M",
    soLuong: 20,
    trangThai: true,
  },
  {
    maPv: "PV02",
    image: "",
    mau: "Trắng",
    size: "L",
    soLuong: 10,
    trangThai: false,
  },
]);
const variantsByColor = computed(() => {
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
      size: v.size,
      soLuong: v.soLuong,
    });
  });
  const saveColorSizes = () => {
    // Xoá variants cũ của màu này
    variants.value = variants.value.filter(
      (v) => v.mau !== currentColor.value.mau
    );

    // Add lại
    currentColor.value.sizes.forEach((s) => {
      variants.value.push({
        maPv: `${currentColor.value.mau}-${s.size}`,
        image: currentColor.value.image,
        mau: currentColor.value.mau,
        size: s.size,
        soLuong: s.soLuong,
        trangThai: true,
      });
    });

    closeModal();
  };
  return Object.values(map);
});

/* ===== MODAL STATE ===== */
const showModal = ref(false);
const modalMode = ref("create");
// create | edit;
const currentVariant = ref({});

/* ===== ACTIONS ===== */
const openEdit = (v) => {
  modalMode.value = "edit";
  currentVariant.value = { ...v };
  showModal.value = true;
};
const openCreate = () => {
  modalMode.value = "create";
  currentVariant.value = {
    maPv: "NEW",
    mau: "",
    size: "",
    soLuong: 0,
    trangThai: true,
  };
  showModal.value = true;
};
const closeModal = () => {
  showModal.value = false;
};
const saveVariant = () => {
  if (modalMode.value === "create") {
    variants.value.push({ ...currentVariant.value });
  } else {
    const index = variants.value.findIndex(
      (v) => v.maPv === currentVariant.value.maPv
    );
    variants.value[index] = { ...currentVariant.value };
  }
  closeModal();
};
const deleteVariant = () => {
  variants.value = variants.value.filter(
    (v) => v.maPv !== currentVariant.value.maPv
  );
  closeModal();
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
</style>
