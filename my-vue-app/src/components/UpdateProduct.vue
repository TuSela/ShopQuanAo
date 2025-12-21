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
      <textarea rows="5" v-model="productForm.moTa"></textarea>
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
              <th>Mã</th>
              <th>Ảnh</th>
              <th>Màu</th>
              <th>Size</th>
              <th>Số lượng</th>
              <th>Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="v in variants" :key="v.maPv" @click="openEdit(v)">
              <td>{{ v.maPv }}</td>
              <td><img :src="v.image" /></td>
              <td>{{ v.mau }}</td>
              <td>{{ v.size }}</td>
              <td>{{ v.soLuong }}</td>
              <td>{{ v.trangThai ? "Hoạt động" : "Ngừng" }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- ===== VARIANT OVERLAY ===== -->
    <div v-if="showModal" class="overlay">
      <div class="modal">
        <h3>{{ modalMode === "edit" ? "Sửa biến thể" : "Thêm biến thể" }}</h3>
        <label>Màu sắc</label> <input v-model="currentVariant.mau" />
        <label>Kích cỡ</label> <input v-model="currentVariant.size" />
        <label>Số lượng</label>
        <input type="number" v-model="currentVariant.soLuong" />
        <label>Trạng thái</label>
        <select v-model="currentVariant.trangThai">
          <option :value="true">Hoạt động</option>
          <option :value="false">Ngừng</option>
        </select>
        <div class="actions">
          <button @click="closeModal">Huỷ</button>
          <button
            v-if="modalMode === 'edit'"
            @click="deleteVariant"
            class="danger"
          >
            Xoá
          </button>
          <button @click="saveVariant" class="primary">Lưu</button>
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

/* ===== MOCK PRODUCT (SAU NÀY LẤY API) ===== */

const productForm = reactive({
  maSp: "1",
  tenSp: "Áo thun nam basic",
  gia: 199000,
  moTa: "Áo thun cotton 100%",
  maLoai: 2,
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
</style>
