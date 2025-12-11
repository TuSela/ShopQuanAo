<template>
  <div class="max-w-7xl mx-auto mt-10 grid grid-cols-12 gap-6">

    <aside class="col-span-3 bg-white p-6 rounded-lg shadow">

  <div class="flex items-center gap-4 mb-6">
  <!-- Ảnh avatar -->
  <div class="w-10 h-10 rounded-full overflow-hidden bg-gray-200 border border-gray-300 flex items-center justify-center">
    <img 
      v-if="profile.avatar" 
      :src="profile.avatar" 
      alt="Avatar"
      class="w-full h-full object-cover"
    />
    <svg 
    v-else xmlns="http://www.w3.org/2000/svg"
           class="w-6 h-6 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
        <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-7 8a7 7 0 1114 0H3z"/>
      </svg>
  </div>

  <!-- Tên người dùng -->
  <div>
    <p class="text-gray-600 text-sm">Xin chào,</p> 
    <h1 class="text-black text-xl font-semibold">{{ profile.hoten }}</h1>
  </div>
</div>

  <!-- MENU + SUBMENU -->
  <nav class="space-y-2">

    <!-- Loop menu -->
    <div v-for="item in menu" :key="item.key">

      <!-- Nút menu chính -->
      <button
        @click="toggleMenu(item.key)"
        class="w-full flex items-center gap-3 p-3 rounded-lg text-left transition"
        :class="activeTab === item.key
          ? 'bg-[#c92127] text-white'
          : 'hover:bg-gray-100 text-black'
        "
      >
        <component :is="item.icon" class="w-5 h-5" />
        <span>{{ item.label }}</span>
      </button>

      <!-- SUBMENU chỉ hiện khi click Tài khoản của tôi -->
      <div
        v-if="item.key === 'profile' && activeTab === 'profile'"
        class="ml-8 mt-2 space-y-2"
      >
        <button
          v-for="sub in subMenu"
          :key="sub.key"
          @click="activeSubTab = sub.key"
          class="block w-full text-left p-2 rounded transition"
          :class="activeSubTab === sub.key
            ? 'bg-[#c92127] text-white'
            : 'hover:bg-gray-100 text-black'
          "
        >
          {{ sub.label }}
        </button>
      </div>

    </div>
  </nav>

</aside>

    <!-- Content -->
    <main class="col-span-9 bg-white p-6 rounded-lg shadow">

      <!-- Đơn hàng -->
      <div v-if="activeTab === 'orders'">

        <h2 class="text-2xl font-bold mb-6">TẤT CẢ ĐƠN HÀNG</h2>

        <!-- Tabs trạng thái -->
        <div class="flex gap-6 border-b">
          <div
            v-for="tab in orderTabs"
            :key="tab.key"
            @click="orderStatus = tab.key"
            class="pb-2 cursor-pointer"
            :class="orderStatus === tab.key
              ? 'text-red-600 border-b-2 border-red-600'
              : 'text-gray-500'
            "
          >
            {{ tab.label }}
          </div>
        </div>

        <!-- Bảng đơn hàng -->
        <table class="w-full text-left mt-4">
          <thead>
            <tr class="text-sm text-black border-b">
              <th class="py-3">Mã đơn hàng</th>
              <th class="py-3">Sản phẩm</th>
              <th class="py-3">Ngày mua</th>
              <th class="py-3">Tổng tiền</th>
              <th class="py-3">Trạng thái</th>
              <th class="py-3">Hành động</th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="order in orders"
              :key="order.id"
              class="border-b text-sm"
            >
              <td class="py-4">{{ order.code }}</td>
              <td>{{ order.products }}</td>
              <td>{{ order.date }}</td>
              <td class="font-semibold text-red-600">{{ order.total }}</td>

              <td>
                <span
                  class="px-3 py-1 rounded text-xs"
                  :class="order.statusColor"
                >
                  {{ order.status }}
                </span>
              </td>

              <td>
                <button class="px-4 py-2 border rounded hover:bg-gray-50">
                  Xem chi tiết
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Các tab khác -->
      <div v-if="activeTab === 'profile'" class="text-gray-700">
        
    <!-- Nội dung -->
    <section class="col-span-9">

      <!-- Hồ sơ -->
      <div v-if="activeSubTab === 'info'">
      
  <h2 class="text-2xl font-bold mb-6 text-black">Hồ sơ của tôi</h2>
  <div class="grid grid-cols-12 gap-8">

    <!-- Ảnh đại diện -->
    <div class="col-span-4 flex flex-col items-center">
  <div class="w-40 h-40 rounded-full overflow-hidden bg-gray-200 border border-gray-300 flex items-center justify-center">
    <img
      v-if="previewAvatar || profile.avatar"
      :src="previewAvatar || profile.avatar "
      class="w-full h-full object-cover"
    />
    <svg
    v-else
    xmlns="http://www.w3.org/2000/svg"
    class="w-1/2 h-1/2 text-gray-600"
    fill="currentColor"
    viewBox="0 0 20 20"
  >
    <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-7 8a7 7 0 1114 0H3z"/>
  </svg>
  </div>

  <label
    for="fileUpload"
    class="mt-4 px-4 py-2 bg-gray-100 border rounded cursor-pointer hover:bg-gray-200"
  >
    Chọn ảnh
  </label>

  <input id="fileUpload" type="file" class="hidden" @change="onAvatarChange">

  <p class="text-sm text-gray-500 mt-2">
    Dung lượng tối đa 1MB. Định dạng JPEG, PNG
  </p>
</div>

    <!-- Form thông tin -->
    <div class="col-span-8 space-y-4">

      <!-- Họ tên -->
      <div>
        <label class="font-semibold text-black">Họ và tên</label>
        <input
          v-model="editprofile.hoten"
          type="text"
          class="w-full p-2 border rounded mt-1 text-black"
        />
      </div>

      <!-- Ngày sinh -->
      <div>
        <label class="font-semibold text-black">Ngày sinh</label>
        <input
          v-model="profile.ngaySinh"
          type="date"
          class="w-full p-2 border rounded mt-1 text-black"
        />
      </div>

      <!-- Giới tính -->
      <div>
        <label class="font-semibold block text-black">Giới tính</label>

        <div class="flex items-center gap-6 mt-2">
          <label class="flex items-center gap-2 text-black">
            <input type="radio" value="Nam" v-model="profile.gioiTinh" />
            Nam
          </label>
          <label class="flex items-center gap-2 text-black">
            <input type="radio" value="Nữ" v-model="profile.gioiTinh" />
            Nữ
          </label>
          <label class="flex items-center gap-2 text-black">
            <input type="radio" value="Khác" v-model="profile.gioiTinh" />
            Khác
          </label>
        </div>
      </div>

      <!-- Số điện thoại -->
      <div>
        <label class="font-semibold text-black">Số điện thoại</label>
        <input
          v-model="profile.sdt"
          type="text"
          class="w-full p-2 border rounded mt-1 text-black"
        />
      </div>

      <!-- Email -->
      <div>
        <label class="font-semibold text-black">Email</label>
        <input
          v-model="profile.email"
          type="email"
          class="w-full p-2 border rounded mt-1 text-black"
        />
      </div>

      <!-- Nút lưu -->
    <div class="flex justify-end">
      <button
        @click="saveprofile"
        class="mt-4 bg-[#c92127] text-white px-6 py-3 rounded hover:bg-red-800"
      >
        Lưu thay đổi
      </button>
    </div>

    </div>

  </div>
</div>

        <div v-if="activeSubTab === 'address'">

  <h2 class="text-xl font-bold mb-4 text-black">ĐỊA CHỈ GIAO HÀNG</h2>

  <div
    v-for="item in addressList"
    :key="item.maDiaChi"
    class="pb-4 border-b border-gray-200 p-4"
  >
    <div class="flex justify-between items-start">

      
      <div>
        <div class="flex items-center gap-2">
          <span class="font-semibold text-black">{{ item.hoten }}</span>
          <span class="text-sm bg-gray-200 px-2 py-1 rounded ">Mặc định</span>
        </div>

        <p class="text-gray-700 mt-1">
          Địa chỉ:
          <span class="font-semibold text-black">
          {{ item.diaChi }},
          {{ item.phuongXa }},
          {{ item.quanHuyen }},
          {{ item.tinhThanhPho }}
        </span>
        </p>

        <p class="text-gray-700">
          Điện thoại:
          <span class="font-semibold text-black">{{ item.sdt.trim() }}</span>
        </p>
      </div>

      
      <div class="text-right">
        <button @click="editAddress(item)" class="font-semibold text-black">Chỉnh sửa</button>
        <button @click="deleteAddress(item.maDiaChi)" class="block text-[#c92127] mt-1">Xóa</button>
      </div>
    </div>
  </div>
<div class="w-full flex justify-center py-6">
  <button
    @click="addNewAddress"
    class="w-full border border-dashed border-gray-400 py-2 text-center text-black font-semibold rounded hover:bg-gray-100"
  >
    THÊM ĐỊA CHỈ MỚI
  </button>
</div>
 <div
  v-if="editingAddress"
  class="fixed inset-0 backdrop-blur-xs bg-black/10 flex justify-center items-center z-50"
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
        <input
          v-model="editingAddress.hoten"
          class="w-full p-2 border rounded"
        />
      </div>

      <!-- SĐT -->
      <div>
        <label class="font-semibold">SĐT *</label>
        <input
          v-model="editingAddress.sdt"
          class="w-full p-2 border rounded"
        />
      </div>

      <!-- Email -->
      <div class="col-span-2">
        <label class="font-semibold">EMAIL *</label>
        <input
          v-model="editingAddress.email"
          class="w-full p-2 border rounded"
        />
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
          <option v-for="p in provinces" :key="p.code">{{ p.name }}</option>
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
          <option v-for="d in districts" :key="d.code">{{ d.name }}</option>
        </select>
      </div>

      <!-- Xã -->
      <div class="col-span-2">
        <label class="font-semibold">PHƯỜNG / XÃ *</label>
        <select
          v-model="editingAddress.phuongXa"
          class="w-full p-2 border rounded"
        >
          <option value="">-- Chọn xã --</option>
          <option v-for="w in wards" :key="w.code">{{ w.name }}</option>
        </select>
      </div>

      <!-- Địa chỉ -->
      <div class="col-span-2">
        <label class="font-semibold">ĐỊA CHỈ *</label>
        <input
          v-model="editingAddress.diaChi"
          class="w-full p-2 border rounded"
        />
      </div>

      <!-- Mặc định -->
      <div class="col-span-2 flex items-center mt-2">
        <input
          type="checkbox"
          v-model="editingAddress.macDinh"
          class="mr-2"
        />
        <label>Đặt làm địa chỉ mặc định</label>
      </div>
    </div>

    <!-- Nút lưu -->
    <button
      @click="saveAddress"
      class="mt-6 px-6 py-3 bg-[#c92127] text-white rounded hover:bg-red-800"
    >
      Lưu thông tin
    </button>

  </div>
</div>
</div>
<div v-if="activeSubTab === 'password'">
  <div class="bg-white p-6 rounded-lg shadow max-w-3xl">

  <h2 class="text-2xl font-bold mb-6">ĐỔI MẬT KHẨU</h2>

  <!-- Mật khẩu cũ -->
  <div class="mb-4">
    <label class="block font-semibold mb-1">Mật khẩu *</label>
    <div class="relative">
      <input
        :type="showoldPass ? 'text' : 'password'"
        v-model="passwordForm.oldPass"
        placeholder="Nhập mật khẩu"
        class="w-full border rounded p-3"
      />
      
      <button
              type="button"
              @click="showoldPass = !showoldPass"
              class="absolute right-3 top-4 text-gray-500"
            >
       <svg v-if="!showoldPass" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M3.28 2.22a.75.75 0 0 0-1.06 1.06l14.5 14.5a.75.75 0 1 0 1.06-1.06l-1.745-1.745a10.029 10.029 0 0 0 3.3-4.38 1.651 1.651 0 0 0 0-1.185A10.004 10.004 0 0 0 9.999 3a9.956 9.956 0 0 0-4.744 1.194L3.28 2.22ZM7.752 6.69l1.092 1.092a2.5 2.5 0 0 1 3.374 3.373l1.091 1.092a4 4 0 0 0-5.557-5.557Z" clip-rule="evenodd" />
  <path d="m10.748 13.93 2.523 2.523a9.987 9.987 0 0 1-3.27.547c-4.258 0-7.894-2.66-9.337-6.41a1.651 1.651 0 0 1 0-1.186A10.007 10.007 0 0 1 2.839 6.02L6.07 9.252a4 4 0 0 0 4.678 4.678Z" />
</svg>
<svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path d="M10 12.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z" />
  <path fill-rule="evenodd" d="M.664 10.59a1.651 1.651 0 0 1 0-1.186A10.004 10.004 0 0 1 10 3c4.257 0 7.893 2.66 9.336 6.41.147.381.146.804 0 1.186A10.004 10.004 0 0 1 10 17c-4.257 0-7.893-2.66-9.336-6.41ZM14 10a4 4 0 1 1-8 0 4 4 0 0 1 8 0Z" clip-rule="evenodd" />
</svg>
      </button>

    </div>
  </div>

  <!-- Mật khẩu mới -->
  <div class="mb-4">
    <label class="block font-semibold mb-1">Mật khẩu mới *</label>
    <div class="relative">
      <input
        :type="shownewPass1 ? 'text' : 'password'"
        v-model="passwordForm.newPass1"
        placeholder="Nhập mật khẩu"
        class="w-full border rounded p-3"
      />
      
      <button
              type="button"
              @click="shownewPass1 = !shownewPass1"
              class="absolute right-3 top-4 text-gray-500"
            >
        <svg v-if="!shownewPass1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M3.28 2.22a.75.75 0 0 0-1.06 1.06l14.5 14.5a.75.75 0 1 0 1.06-1.06l-1.745-1.745a10.029 10.029 0 0 0 3.3-4.38 1.651 1.651 0 0 0 0-1.185A10.004 10.004 0 0 0 9.999 3a9.956 9.956 0 0 0-4.744 1.194L3.28 2.22ZM7.752 6.69l1.092 1.092a2.5 2.5 0 0 1 3.374 3.373l1.091 1.092a4 4 0 0 0-5.557-5.557Z" clip-rule="evenodd" />
  <path d="m10.748 13.93 2.523 2.523a9.987 9.987 0 0 1-3.27.547c-4.258 0-7.894-2.66-9.337-6.41a1.651 1.651 0 0 1 0-1.186A10.007 10.007 0 0 1 2.839 6.02L6.07 9.252a4 4 0 0 0 4.678 4.678Z" />
</svg>
<svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path d="M10 12.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z" />
  <path fill-rule="evenodd" d="M.664 10.59a1.651 1.651 0 0 1 0-1.186A10.004 10.004 0 0 1 10 3c4.257 0 7.893 2.66 9.336 6.41.147.381.146.804 0 1.186A10.004 10.004 0 0 1 10 17c-4.257 0-7.893-2.66-9.336-6.41ZM14 10a4 4 0 1 1-8 0 4 4 0 0 1 8 0Z" clip-rule="evenodd" />
</svg>
      </button>

    </div>
  </div>

  <!-- Xác nhận mật khẩu -->
  <div class="mb-6">
    <label class="block font-semibold mb-1">Xác nhận mật khẩu *</label>
    <div class="relative">
      <input
        :type="shownewPass2 ? 'text' : 'password'"
        v-model="passwordForm.newPass2"
        placeholder="Nhập lại mật khẩu xác nhận"
        class="w-full border rounded p-3"
      />
      <button
              type="button"
              @click="shownewPass2 = !shownewPass2"
              class="absolute right-3 top-4 text-gray-500"
            >
        <svg v-if="!shownewPass2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M3.28 2.22a.75.75 0 0 0-1.06 1.06l14.5 14.5a.75.75 0 1 0 1.06-1.06l-1.745-1.745a10.029 10.029 0 0 0 3.3-4.38 1.651 1.651 0 0 0 0-1.185A10.004 10.004 0 0 0 9.999 3a9.956 9.956 0 0 0-4.744 1.194L3.28 2.22ZM7.752 6.69l1.092 1.092a2.5 2.5 0 0 1 3.374 3.373l1.091 1.092a4 4 0 0 0-5.557-5.557Z" clip-rule="evenodd" />
  <path d="m10.748 13.93 2.523 2.523a9.987 9.987 0 0 1-3.27.547c-4.258 0-7.894-2.66-9.337-6.41a1.651 1.651 0 0 1 0-1.186A10.007 10.007 0 0 1 2.839 6.02L6.07 9.252a4 4 0 0 0 4.678 4.678Z" />
</svg>
<svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path d="M10 12.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z" />
  <path fill-rule="evenodd" d="M.664 10.59a1.651 1.651 0 0 1 0-1.186A10.004 10.004 0 0 1 10 3c4.257 0 7.893 2.66 9.336 6.41.147.381.146.804 0 1.186A10.004 10.004 0 0 1 10 17c-4.257 0-7.893-2.66-9.336-6.41ZM14 10a4 4 0 1 1-8 0 4 4 0 0 1 8 0Z" clip-rule="evenodd" />
</svg>
      </button>

    </div>
  </div>

  <button
    class="bg-[#c92127] hover:bg-red-800 text-white font-bold px-6 py-3 rounded"
    @click="submitChangePassword"
  >
    CẬP NHẬT
  </button>

</div>

</div>

    
    </section>
      </div>

      <div v-if="activeTab === 'voucher'" class="text-gray-600">
        Mã khuyến mại...
      </div>

      <div v-if="activeTab === 'reviews'" class="text-gray-600">
        Đánh giá của tôi...
      </div>

      <div v-if="activeTab === 'seen'" class="text-gray-600">
        Sản phẩm đã xem...
      </div>

    </main>

  </div>
</template>
<style>
    @keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

.animate-fadeIn {
  animation: fadeIn 0.25s ease-out;
}
  </style>
<script setup>
import { ref, onMounted,watch,reactive } from "vue";
import api from '@/api';


const profile = ref({
  avatar: "",
  hoten: "",
  ngaySinh: "",
  gioiTinh: "",
  sdt: "",
  email: "",
});
const editprofile = ref({
  hoten: ""
});
const toggleMenu = (key) => {
  activeTab.value = key;

  if (key === "profile") {
    activeSubTab.value = "info"; // mở tab Hồ sơ mặc định
  }
};
const subMenu = [
  { key: "info", label: "Hồ sơ" },
  { key: "address", label: "Địa chỉ" },
  { key: "password", label: "Đổi mật khẩu" },
];
const activeSubTab = ref("info");
const avatarFile = ref(null);     // file người dùng chọn
const previewAvatar = ref(null);  // ảnh preview

const onAvatarChange = (e) => {
  const file = e.target.files[0];
  if (!file) return;

  avatarFile.value = file;
  previewAvatar.value = URL.createObjectURL(file); // show preview
};

// Gọi API backend
const loadUserProfile = async () => {
  try {
    const res = await api.get("/myinfor");
    const data = res.data.result; // lấy result
    profile.value = {
      avatar: data.avatar, 
      hoten: data.hoten,
      ngaySinh: data.ngaySinh, 
      gioiTinh: data.gioiTinh,
      sdt: data.sdt,
      email: data.email,
    };
    editprofile.value.hoten = data.hoten;
    console.log("Profile loaded:", profile.value);
  } catch (err) {
    console.error("Lỗi load profile:", err);
  }
};
// Chạy khi component mount
onMounted(() => {
  loadUserProfile();
});



const saveprofile = async () => {
  try {

    let avatarUrl = profile.value.avatar; // giữ avatar cũ nếu không upload ảnh mới

    // 1️⃣ Nếu có ảnh mới → upload avatar trước
    if (avatarFile.value) {
      const formData = new FormData();
      formData.append("avatar", avatarFile.value);

      const uploadRes = await api.put("/myinfor/avatar", formData, {
        headers: { "Content-Type": "multipart/form-data" }
      });

      if (uploadRes.data.code !== 1000) {
        alert("Upload ảnh thất bại!");
        return;
      }

      avatarUrl = uploadRes.data.result; // backend trả về URL ảnh
    }

    // 2️⃣ Cập nhật thông tin profile
    const payload = {
      ...profile.value,
      hoten: editprofile.value.hoten,
      avatar: avatarUrl,  // dùng avatar mới nếu vừa upload
    };

    const res = await api.put("/myinfor", payload);

    if (res.data.code === 1000) {
      alert("Cập nhật thông tin thành công!");
      profile.value.hoten = editprofile.value.hoten;
      // cập nhật avatar mới vào profile
      profile.value.avatar = avatarUrl;
      localStorage.setItem("user", JSON.stringify(profile.value));
      window.dispatchEvent(new Event('user-updated'));

      // xóa file và preview
      avatarFile.value = null;
      previewAvatar.value = null;
    } else {
      alert("Cập nhật thất bại!");
    }

  } catch (err) {
    console.error("Lỗi cập nhật thông tin:", err);
    alert("Không thể cập nhật thông tin!");
  }
};





//DIACHI

const addressList = ref([]);

const loadAddress = async () => {
  try {
    const res = await api.get("/myinfor/address"); 
    if (res.data.code === 1000) {
      addressList.value = res.data.result;
    }
    console.log("Address loaded:", addressList.value);
  } catch (err) {
    console.error("Lỗi load address:", err);
  }
};
 watch(activeSubTab, (newVal) => {
  if (newVal === "address") {
    loadAddress();
  }
});




const deleteAddress = async (id) => {
  if (!confirm("Bạn có chắc muốn xóa địa chỉ này không?")) return;

  try {
    const res = await api.delete(`/myinfor/address/${id}`);

    if (res.data.code === 1000) {
      // Xóa trên giao diện mà không cần reload API
      addressList.value = addressList.value.filter(a => a.maDiaChi !== id);

      alert("Xóa địa chỉ thành công!");
    } else {
      alert("Xóa thất bại!");
    }
  } catch (err) {
    console.error("Lỗi xóa địa chỉ:", err);
    alert("Không thể xóa địa chỉ!");
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
  editingAddress.value = { ...item }; // copy dữ liệu để chỉnh sửa
  await loadProvinces();
};

const saveAddress = async () => {
  try {
    const data = editingAddress.value;
    if (!data) return;

    let res;

    // UPDATE
    if (data.maDiaChi) {
      const id = data.maDiaChi;
      res = await api.put(`/myinfor/address/${id}`, data);

      console.log("Response update:", res.data);

      if (res.data.code === 1000) {
        // Prefer server result if backend returns updated object
        const updated = (res.data.result && typeof res.data.result === "object")
          ? res.data.result
          : { ...data };

        const index = addressList.value.findIndex(a => a.maDiaChi === id);
        if (index !== -1) {
          addressList.value[index] = { ...updated };
        } else {
          // nếu không tìm thấy thì thêm vào đầu để đồng bộ UI
          addressList.value.unshift(updated);
        }

        // đóng popup sau khi cập nhật UI
        editingAddress.value = null;
        alert("Cập nhật địa chỉ thành công!");
        return;
      } else {
        console.warn("Update failed, server response:", res.data);
        alert(res.data.message || "Cập nhật thất bại!");
        return;
      }
    }

    // CREATE
    res = await api.post(`/myinfor/address`, data);
    console.log("Response create:", res.data);

    if (res.data.code === 1000) {
      // Nếu backend trả object mới: dùng luôn
      if (res.data.result && typeof res.data.result === "object") {
        addressList.value.unshift(res.data.result);
      } else {
       
        await loadAddress();
      }

      // đóng popup sau khi đã đảm bảo UI đồng bộ
      editingAddress.value = null;
      alert("Thêm địa chỉ mới thành công!");
      return;
    } else {
      console.warn("Create failed, server response:", res.data);
      alert(res.data.message || "Thêm địa chỉ thất bại!");
      return;
    }

  } catch (err) {
    console.error("Lỗi lưu/cập nhật địa chỉ:", err);
    console.error("Server response (if any):", err.response?.data);
    alert("Không thể lưu địa chỉ!");
  }
};



const addNewAddress = async () => {
  // Reset dữ liệu form
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






const passwordForm = ref({
  oldPass: "",
  newPass1: "",
  newPass2: ""
});

const showoldPass = ref(false);
const  shownewPass1 = ref(false);
const  shownewPass2 = ref(false);


const submitChangePassword = async () => {

  if (passwordForm.value.newPass1 !== passwordForm.value.newPass2) {
    return alert("Mật khẩu xác nhận không khớp");
  }

  // Gọi API
  await api.put("/myinfor/change-password", {
    oldPass: passwordForm.value.oldPass,
    newPass1: passwordForm.value.newPass1,
    newPass2: passwordForm.value.newPass2
  });

  alert("Đổi mật khẩu thành công!");
};





import {
  User,
  TicketPercent,
  Star,
  Eye,
  Package
} from "lucide-vue-next";
const activeTab = ref("orders"); // mặc định tab Đơn hàng
const menu = [
  { key: "orders", label: "Đơn hàng của tôi", icon: Package },
  { key: "profile", label: "Tài khoản của tôi", icon: User },
  { key: "voucher", label: "Mã khuyến mại", icon: TicketPercent },
  { key: "reviews", label: "Đánh giá của tôi", icon: Star },
  { key: "seen", label: "Sản phẩm đã xem", icon: Eye },
];

const orderTabs = [
  { key: "all", label: "Tất cả đơn hàng" },
  { key: "pending", label: "Chờ thanh toán" },
  { key: "processing", label: "Đang xử lý" },
  { key: "shipping", label: "Đang giao" },
  { key: "done", label: "Đã giao" },
  { key: "cancel", label: "Đã hủy" },
  { key: "return", label: "Hoàn hàng" },
];

const orderStatus = ref("all");

const orders = ref([
  {
    id: 1,
    code: "SA8TPI928W",
    products: "2 sản phẩm",
    date: "26/11/2025",
    total: "518.300đ",
    status: "Lỗi Thanh toán",
    statusColor: "bg-red-100 text-red-600",
  },
  {
    id: 2,
    code: "SA8TPI928W",
    products: "2 sản phẩm",
    date: "26/11/2025",
    total: "518.300đ",
    status: "Chờ thanh toán VNPAY",
    statusColor: "bg-orange-100 text-orange-600",
  },
]);
</script>

