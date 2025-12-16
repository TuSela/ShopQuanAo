<template>
  <div class="max-w-7xl mx-auto py-10 grid grid-cols-12 gap-8">

    <!-- LEFT -->
    <div class="col-span-8 space-y-6">

<div class="bg-white p-6 rounded shadow space-y-8">

  <!-- ================= ĐỊA CHỈ ================= -->
<div>
  <h3 class="font-bold text-lg mb-4 flex items-center gap-2">
    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
  <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z" />
</svg> ĐỊA CHỈ GIAO HÀNG
  </h3>

  <!-- ĐỊA CHỈ ĐANG CHỌN -->
<div
  v-if="currentAddress"
  class="relative bg-gray-50 p-4 rounded flex justify-between items-start"
>
  <!-- VIỀN ĐỎ NÉT ĐỨT -->
  <div
    class="absolute left-0 top-0 h-full border-l-4 border-dashed border-red-500"
  ></div>

  <!-- NỘI DUNG -->
  <div class="pl-4">
    <p class="font-semibold text-black">
      Họ và tên: {{ currentAddress.hoten }}
      &nbsp;&nbsp;&nbsp;
      SĐT: {{ currentAddress.sdt }}
    </p>

    <p class="text-sm text-gray-900 mt-1">
      <span class="font-semibold text-black">
        Địa chỉ giao hàng:
      </span>
      {{ currentAddress.diaChi }},
      {{ currentAddress.phuongXa }},
      {{ currentAddress.quanHuyen }},
      {{ currentAddress.tinhThanhPho }}
    </p>
  </div>

  <!-- NHÃN MẶC ĐỊNH -->
  <span
    v-if="currentAddress.macDinh"
    class="text-red-500 text-sm font-medium"
  >
    Địa chỉ mặc định
  </span>
</div>

  <!-- ACTION -->
  <div class="text-sm mt-3 flex gap-4 justify-end ">
    <a
      class="text-gray-700 cursor-pointer underline"
       @click="openAddressPopup"
    >
      Chọn địa chỉ giao hàng
    </a>

    <a
      class="text-gray-700 cursor-pointer justify-end underline"
      @click="addNewAddress"
    >
      Thêm địa chỉ giao hàng
    </a>
  </div>
</div>

  <!-- ================= THANH TOÁN ================= -->
  <div>
    <h3 class="font-bold text-lg mb-4 flex items-center gap-2">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 8.25h19.5M2.25 9h19.5m-16.5 5.25h6m-6 2.25h3m-3.75 3h15a2.25 2.25 0 0 0 2.25-2.25V6.75A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25v10.5A2.25 2.25 0 0 0 4.5 19.5Z" />
</svg>
 PHƯƠNG THỨC THANH TOÁN
    </h3>

    <label
      v-for="pt in data.payment"
      :key="pt.maPt"
      class="flex items-center gap-3 mb-3 p-3 bg-gray-50 rounded cursor-pointer"
    >
      <input
        type="radio"
        name="payment"
        :value="pt.maPt"
        v-model="selectedPayment"
      />

      <span class="font-medium">
        {{ pt.tenPt }}
      </span>
    </label>
  </div>

</div>



<!-- GIỎ HÀNG -->
<div
  v-if="data.myCartResponse"
  class="bg-white p-6 rounded shadow"
>
  <!-- Title -->
  <h3 class="text-xl font-bold mb-4">
    GIỎ HÀNG
    <span class="text-sm text-red-500 font-normal">
      ({{ data.myCartResponse.items.length }} sản phẩm)
    </span>
  </h3>

  <!-- Header -->
  <div class="grid grid-cols-12 text-sm font-semibold pb-3 ">
    <div class="col-span-2"></div>
    <div class="col-span-4">Tên hàng</div>
    <div class="col-span-2 text-center">Giá</div>
    <div class="col-span-2 text-center">Số lượng</div>
    <div class="col-span-2 text-right">Tổng tiền</div>
  </div>

  <!-- Item -->
  <div
    v-for="item in data.myCartResponse.items"
    :key="item.variant.maBienThe"
    class="grid grid-cols-12 gap-4 py-4 items-center border-b last:border-b-0"
  >
    <!-- Tên hàng -->
    <div class="col-span-6 flex gap-4">
      <img
        :src="item.product.urlImage"
        class="w-24 h-28 object-cover rounded border"
      />

      <div>
        <p class="font-semibold">
          {{ item.product.tenSp }}
        </p>

        <p class="text-sm text-black mt-1">
          Kích thước:
          <b>{{ item.variant.tenKc }}</b> |
          Màu:
          <b>{{ item.variant.tenMs }}</b>
        </p>
      </div>
    </div>

    <!-- Giá -->
    <div class="col-span-2 text-center text-red-600 font-bold">
      {{ formatPrice(item.product.gia) }}đ
    </div>

    <!-- Số lượng (HIỂN THỊ, CHƯA XỬ LÝ API) -->
    <div class="col-span-2 text-center">
      {{ item.variant.soLuongDat }}
    </div>

    <!-- Tổng tiền -->
    <div class="col-span-2 text-right font-bold">
      {{ formatPrice(item.variant.tongTien) }}đ
    </div>
  </div>
</div>

    </div>

    <!-- RIGHT -->
    <div class="col-span-4 bg-white p-6 rounded shadow h-fit sticky top-6">
      <h3 class="font-bold text-lg mb-4">ĐƠN HÀNG</h3>

      <div v-if="data.myCartResponse">
        <div class="flex justify-between mb-3">
          <span>Tạm tính</span>
          <span class="font-bold">
            {{ formatPrice(data.myCartResponse.tongTien) }}đ
          </span>
        </div>

        <div class="flex justify-between mb-3">
          <span>Phí vận chuyển</span>
          <span class="font-bold">0đ</span>
        </div>

        <div class="border-t border-dashed my-4"></div>

        <div class="flex justify-between text-lg font-bold text-red-600">
          <span>Tổng thanh toán</span>
          <span>
            {{ formatPrice(data.myCartResponse.tongTien) }}đ
          </span>
        </div>
      </div>

      <button
        class="w-full mt-6 bg-red-600 hover:bg-red-700
               text-white py-3 rounded font-semibold"
        @click="placeOrder"
      >
        ĐẶT HÀNG
      </button>
    </div>

  </div>
  <!-- Overlay -->
<div
  v-if="editingAddress"
  class="fixed inset-0 bg-black/40 flex items-center justify-center z-50"
>
  <!-- Popup -->
  <div class="bg-white w-[900px] p-8 rounded-lg shadow-lg relative animate-fadeIn">

    <!-- Nút X đóng -->
    <button
      @click="editingAddress = null"
      class="absolute top-4 right-4 text-gray-600 hover:text-black text-2xl"
    >
      ×
    </button>

    <h2 class="text-2xl font-bold mb-6">Địa chỉ giao hàng</h2>

    <!-- FORM -->
    <div class="grid grid-cols-2 gap-4">
      <!-- Họ tên -->
      <div>
        <label class="font-semibold">HỌ TÊN *</label>
        <input v-model="editingAddress.hoten" class="w-full p-2 border rounded" />
      </div>

      <!-- SĐT -->
      <div>
        <label class="font-semibold">SĐT *</label>
        <input v-model="editingAddress.sdt" class="w-full p-2 border rounded" />
      </div>

      <!-- Email -->
      <div class="col-span-2">
        <label class="font-semibold">EMAIL *</label>
        <input v-model="editingAddress.email" class="w-full p-2 border rounded" />
      </div>

      <!-- Tỉnh -->
      <div>
        <label class="font-semibold">TỈNH / THÀNH PHỐ *</label>
        <select
          v-model="editingAddress.tinhThanhPho"
          @change="handleProvinceChange"
          class="w-full p-2 border rounded"
        >
          <option value="">-- Chọn tỉnh --</option>
          <option v-for="p in provinces" :key="p.code">
            {{ p.name }}
          </option>
        </select>
      </div>

      <!-- Huyện -->
      <div>
        <label class="font-semibold">QUẬN / HUYỆN *</label>
        <select
          v-model="editingAddress.quanHuyen"
          @change="handleDistrictChange"
          class="w-full p-2 border rounded"
        >
          <option value="">-- Chọn huyện --</option>
          <option v-for="d in districts" :key="d.code">
            {{ d.name }}
          </option>
        </select>
      </div>

      <!-- Xã -->
      <div class="col-span-2">
        <label class="font-semibold">PHƯỜNG / XÃ *</label>
        <select v-model="editingAddress.phuongXa" class="w-full p-2 border rounded">
          <option value="">-- Chọn xã --</option>
          <option v-for="w in wards" :key="w.code">
            {{ w.name }}
          </option>
        </select>
      </div>

      <!-- Địa chỉ -->
      <div class="col-span-2">
        <label class="font-semibold">ĐỊA CHỈ *</label>
        <input v-model="editingAddress.diaChi" class="w-full p-2 border rounded" />
      </div>

    </div>

    <!-- Nút lưu -->
    <button
      @click="saveAddress"
      class="mt-6 px-6 py-3 bg-[#c92127] text-white rounded hover:bg-red-800 block ml-auto"
    >
      Thêm địa chỉ mới
    </button>

  </div>
</div>
<!-- ================= POPUP CHỌN ĐỊA CHỈ ================= -->
<div
  v-if="showAddressPopup"
  class="fixed inset-0 bg-black/40 flex items-center justify-center z-50"
>
  <div class="bg-white w-[800px] rounded-lg shadow-lg p-6 relative">

    <!-- Close -->
    <button
      class="absolute top-4 right-4 text-2xl text-gray-500"
      @click="showAddressPopup = false"
    >
      ×
    </button>

    <h2 class="text-xl font-bold mb-6">
      THAY ĐỔI ĐỊA CHỈ GIAO HÀNG
    </h2>

    <!-- LIST -->
    <div class="space-y-4 max-h-[400px] overflow-y-auto">

      <div
        v-for="addr in data.address"
        :key="addr.maDiaChi"
        class="flex justify-between gap-4 p-4 rounded border"
        :class="tempSelectedAddress === addr.maDiaChi
          ? 'border-red-500 bg-red-50'
          : 'border-gray-200'"
      >
        <label class="flex gap-3 cursor-pointer flex-1">
          <input
            type="radio"
            name="popupAddress"
            :value="addr.maDiaChi"
            v-model="tempSelectedAddress"
          />

          <div>
            <p class="font-semibold">
              {{ addr.hoten }} SĐT:{{ addr.sdt }}
            </p>
            <p class="text-sm text-gray-600 mt-1">
              {{ addr.diaChi }},
              {{ addr.phuongXa }},
              {{ addr.quanHuyen }},
              {{ addr.tinhThanhPho }}
            </p>
          </div>
        </label>

        <!-- ACTION -->
        <!-- ACTION -->
<div class="flex items-center gap-4 text-sm shrink-0">

  <!-- CHỈNH SỬA -->
  <button
    class="flex items-center gap-1 text-gray-600 cursor-pointer"
    @click.stop="editAddress(addr)"
  >
    <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M3.69332 13.0127C3.28666 13.0127 2.90666 12.8727 2.63332 12.6127C2.28666 12.286 2.11999 11.7927 2.17999 11.2594L2.42666 9.09938C2.47332 8.69272 2.71999 8.15272 3.00666 7.85938L7.56888 3.03043L7.57714 3.02168L8.47999 2.06605C9.84666 0.619382 11.2733 0.579382 12.72 1.94605C14.1667 3.31272 14.2067 4.73938 12.84 6.18605L7.36666 11.9794C7.08666 12.2794 
      6.56666 12.5594 6.15999 12.626L4.01332 12.9927C3.97263 12.9951 3.93366 12.9984 3.89548 13.0015C3.82732 13.0072 3.76169 13.0127 3.69332 13.0127ZM3.73332 8.53938L7.67886 4.35836C8.17186 5.67111 9.24364 6.68571 10.5804 7.10823L6.63332 11.286C6.49999 11.426 6.17999 11.5994 5.98666 11.6327L3.83999 11.9994C3.61999 12.0327 3.43999 11.9927 3.31999 11.8794C3.19999 11.766 3.14666 11.586 3.17332 11.366L3.41999 9.20605C3.44666 9.01272 3.59999 8.67938 3.73332 8.53938ZM12.1067 5.49271L11.3697 6.27275C9.93437 
      6.02918 8.79022 4.93696 8.47522 3.51448L9.20666 2.73938C9.65999 2.25938 10.1067 1.93938 10.62 1.93938C11.0333 1.93938 11.4933 2.15272 12.0267 2.66605C13.2333 3.79938 12.9333 4.61272 12.1067 5.49271ZM2 15.1663H14C14.2733 15.1663 14.5 14.9397 14.5 14.6663C14.5 14.393 14.2733 14.1663 14 14.1663H2C1.72667 14.1663 1.5 14.393 1.5 14.6663C1.5 14.9397 1.72667 15.1663 2 15.1663Z" fill="#525252"></path>
    </svg>
    <span>Chỉnh sửa</span>
  </button>

  <!-- XÓA -->
  <button
    class="flex items-center gap-1 text-gray-600 cursor-pointer hover:text-red-600"
    @click.stop="deleteAddress(addr.maDiaChi)"
  >
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="size-4">
  <path fill-rule="evenodd" d="M5 3.25V4H2.75a.75.75 0 0 0 0 1.5h.3l.815 8.15A1.5 1.5 0 0 0 5.357 15h5.285a1.5 1.5 0 0 0 1.493-1.35l.815-8.15h.3a.75.75 0 0 0 0-1.5H11v-.75A2.25 2.25 0 0 0 8.75 1h-1.5A2.25 2.25 0 0 0 5 3.25Zm2.25-.75a.75.75 0 0 0-.75.75V4h3v-.75a.75.75 0 0 0-.75-.75h-1.5ZM6.05 6a.75.75 0 0 1 .787.713l.275 5.5a.75.75 0 0 1-1.498.075l-.275-5.5A.75.75 0 0 1 6.05 6Zm3.9 0a.75.75 0 0 1 .712.787l-.275 5.5a.75.75 0 0 1-1.498-.075l.275-5.5a.75.75 0 0 1 .786-.711Z" clip-rule="evenodd" />
</svg>

    <span>Xóa</span>
  </button>

</div>
      </div>

    </div>

    <!-- FOOTER -->
    <div class="flex justify-center gap-4 mt-6">
      <button
        class="px-6 py-2 border rounded cursor-pointer"
        @click="addNewAddress"
      >
        Thêm địa chỉ mới
      </button>

      <button
        class="px-6 py-2 bg-red-600 text-white rounded cursor-pointer"
        @click="confirmAddress"
      >
        Chọn địa chỉ
      </button>
    </div>

  </div>
</div>


</template>

<script setup>
import { ref, onMounted, computed,watch,nextTick } from "vue";
import api from "@/api";

const data = ref({
  address: [],
  payment: [],
  myCartResponse: null
});

const selectedAddress = ref(null);
const selectedPayment = ref(null);
const showAddressPopup = ref(false);
const tempSelectedAddress = ref(null);


const openAddressPopup = () => {
  tempSelectedAddress.value = selectedAddress.value;
  showAddressPopup.value = true;
};


const confirmAddress = () => {
  if (!tempSelectedAddress.value) {
    alert("Vui lòng chọn địa chỉ");
    return;
  }

  selectedAddress.value = tempSelectedAddress.value;
  showAddressPopup.value = false;
};


const loadCheckoutData = async () => {
  const res = await api.get("/orders/payments");
  data.value = res.data.result;

  if (data.value.address.length > 0 && !selectedAddress.value) {
    selectedAddress.value = data.value.address[0].maDiaChi;
  }

  if (data.value.payment.length > 0 && !selectedPayment.value) {
    selectedPayment.value = data.value.payment[0].maPt;
  }
};

onMounted(() => {
  loadCheckoutData();
});


const currentAddress = computed(() =>
  data.value.address.find(
    a => a.maDiaChi === selectedAddress.value
  )
);

const formatPrice = (p) =>
  Number(p).toLocaleString("vi-VN");

const placeOrder = async () => {
  if (!selectedAddress.value) {
    alert("Vui lòng chọn địa chỉ giao hàng");
    return;
  }

  if (!selectedPayment.value) {
    alert("Vui lòng chọn phương thức thanh toán");
    return;
  }

  try {
    const payload = {
      maGh: data.value.myCartResponse.maGioHang,
      maPt: selectedPayment.value,
      maDiaChi: selectedAddress.value,
    };

    const res = await api.post("/orders", payload);

    if (res.data.code === 1000) {
      alert("🎉 Đặt hàng thành công!");

      // Nếu BE trả token mới → update lại
      if (res.data.result?.token) {
        localStorage.setItem("token", res.data.result.token);
      }

      // 👉 điều hướng sau khi đặt hàng
      // router.push("/order-success");
    } else {
      alert(res.data.message || "Đặt hàng thất bại!");
    }
  } catch (err) {
    console.error("Lỗi đặt hàng:", err);
    alert("Không thể đặt hàng!");
  }
};







const editingAddress = ref(null);

const provinces = ref([]);
const districts = ref([]);
const wards = ref([]);

// Lấy danh sách tỉnh khi load trang "Địa chỉ"
const loadProvinces = async () => {
  const res = await fetch("https://provinces.open-api.vn/api/?depth=1");
  provinces.value = await res.json();
};

// Khi chọn tỉnh → load huyện
const handleProvinceChange = async () => {
  const province = provinces.value.find(
    (p) => p.name === editingAddress.value.tinhThanhPho
  );
  if (!province) return;

  const res = await fetch(
    `https://provinces.open-api.vn/api/p/${province.code}?depth=2`
  );
  const data = await res.json();

  districts.value = data.districts;
  editingAddress.value.quanHuyen = "";
  editingAddress.value.phuongXa = "";
  wards.value = [];
};

// Khi chọn huyện → load xã
const handleDistrictChange = async () => {
  const district = districts.value.find(
    (d) => d.name === editingAddress.value.quanHuyen
  );
  if (!district) return;

  const res = await fetch(
    `https://provinces.open-api.vn/api/d/${district.code}?depth=2`
  );
  const data = await res.json();

  wards.value = data.wards;
  editingAddress.value.phuongXa = "";
};

// Khi bấm "Chỉnh sửa" → tự load sẵn huyện & xã đúng theo địa chỉ
watch(editingAddress, async (val) => {
  if (!val) return;

  // Load tỉnh trước
  if (provinces.value.length === 0) {
    await loadProvinces();
  }

  // Load huyện theo tỉnh hiện có
  const province = provinces.value.find((p) => p.name === val.tinhThanhPho);
  if (province) {
    const res = await fetch(
      `https://provinces.open-api.vn/api/p/${province.code}?depth=2`
    );
    const data = await res.json();
    districts.value = data.districts;
  }

  // Load xã theo huyện hiện có
  const district = districts.value.find((d) => d.name === val.quanHuyen);
  if (district) {
    const res = await fetch(
      `https://provinces.open-api.vn/api/d/${district.code}?depth=2`
    );
    const data = await res.json();
    wards.value = data.wards;
  }
});



const editAddress = async (item) => {
  showAddressPopup.value = false;
  await nextTick();
  editingAddress.value = { ...item };
  await loadProvinces();
};

const saveAddress = async () => {
  try {
    if (!editingAddress.value) return;

    const payload = { ...editingAddress.value };

    // ===== UPDATE =====
    if (payload.maDiaChi) {
      await api.put(`/myinfor/address/${payload.maDiaChi}`, payload);

      // 🔥 reload toàn bộ checkout
      await loadCheckoutData();

      selectedAddress.value = payload.maDiaChi;
      editingAddress.value = null;

      alert("Cập nhật địa chỉ thành công!");
      return;
    }

    // ===== CREATE =====
    await api.post(`/myinfor/address`, payload);

    // 🔥 reload lại checkout
    await loadCheckoutData();

    editingAddress.value = null;
    alert("Thêm địa chỉ mới thành công!");

  } catch (err) {
    console.error(err);
    alert("Không thể lưu địa chỉ!");
  }
};


const addNewAddress = async () => {
  // Reset dữ liệu form
  showAddressPopup.value=false;
  editingAddress.value = {
    maDiaChi: null,        // địa chỉ mới nên chưa có id
    hoten: "",
    sdt: "",
    email: "",
    tinhThanhPho: "",
    quanHuyen: "",
    phuongXa: "",
    diaChi: "",
    macDinh: false,
  };

  districts.value = [];
  wards.value = [];

  // Load lại tỉnh/thành nếu chưa có
  if (provinces.value.length === 0) {
    await loadProvinces();
  }
  
};


const deleteAddress = async (id) => {
  if (!confirm("Bạn có chắc muốn xóa địa chỉ này không?")) return;

  try {
    const res = await api.delete(`/myinfor/address/${id}`);

    if (res.data.code === 1000) {

      // 🔥 Xóa khỏi danh sách địa chỉ
      data.value.address = data.value.address.filter(
        a => a.maDiaChi !== id
      );

      // 🔥 Nếu đang chọn đúng địa chỉ bị xóa → chọn địa chỉ khác
      if (selectedAddress.value === id) {
        selectedAddress.value =
          data.value.address.length > 0
            ? data.value.address[0].maDiaChi
            : null;
      }

      alert("Xóa địa chỉ thành công!");
    } else {
      alert(res.data.message || "Xóa thất bại!");
    }
  } catch (err) {
    console.error("Lỗi xóa địa chỉ:", err);
    alert("Không thể xóa địa chỉ!");
  }
};


</script>

