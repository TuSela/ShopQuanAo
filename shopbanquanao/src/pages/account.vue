<template>
  <div class="max-w-7xl mx-auto mt-10 grid grid-cols-12 gap-6">

    <aside class="col-span-3 bg-white p-6 rounded-lg shadow
         self-start top-6 h-fit">

  <div class="flex items-center gap-4 mb-6">
  <!-- Ảnh avatar -->
  <div class="w-10 h-10 rounded-full overflow-hidden bg-gray-200 border border-gray-300 flex items-center justify-center">
<img
  :src="previewAvatar || profile.avatar || '/default-avatar.png'"
  class="w-full h-full object-cover"
/>
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
        <div v-if="viewMode === 'list'">
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
  <template v-for="order in filteredOrders" :key="order.maDonHang">

    <!-- DÒNG ĐƠN HÀNG -->
    <tr class="border-b text-sm h-20 relative">
      <td class="py-4 font-medium">
        #{{ order.maDonHang }}
      </td>

      <td>
        {{ order.items.length }} sản phẩm
      </td>

      <td>
        {{ formatDate(order.ngayDat) }}
      </td>

      <td class="font-semibold text-red-600">
        {{ formatMoney(order.tongTien) }}
      </td>

      <td>
        <span
          class="px-3 py-1 rounded text-xs"
          :class="statusColor(order.trangThai)"
        >
          {{ order.trangThai }}
        </span>
      </td>

<td class="h-full">
  <div class="h-full flex items-center gap-4">

    <!-- NÚT HỦY ĐƠN (chỉ khi Đang xử lý) -->
    <button
      v-if="order.trangThai === 'Đang xử lý'"
      class="h-9 px-4 border border-gray-300 rounded
             text-gray-700 hover:bg-gray-100"
      @click="cancelOrder(order.maDonHang)"
    >
      Hủy đơn hàng
    </button>

    <!-- NÚT XEM CHI TIẾT (khi KHÔNG phải Đang xử lý) -->
    <button
      v-else
      class="h-9 px-6 border border-gray-300 rounded
             text-gray-700 hover:bg-gray-100"
      @click="viewOrderDetail(order.maDonHang)"
    >
      Xem chi tiết
    </button>

    <!-- NÚT ĐÁNH GIÁ (GIỮ NGUYÊN LOGIC CŨ) -->
    <button
      v-if="order.trangThai === 'Đã giao' && !isOrderFullyReviewed(order)"
      class="h-9 px-4 bg-red-500 text-white rounded hover:bg-red-600"
      @click="openReviewOrder(order)"
    >
      Đánh giá
    </button>

    <span
      v-else-if="order.trangThai === 'Đã giao'"
      class="text-green-600 font-semibold"
    >
      Đã đánh giá xong
    </span>

  </div>
</td>



      <!-- NÚT XỔ ĐÈ LÊN ĐƯỜNG KẺ -->
      <td
        colspan="6"
        class="absolute left-1/2 -bottom-6 -translate-x-1/2 -translate-y-1/2"
      >
        <button
          class="pointer-events-auto w-6 h-6 rounded-full bg-white flex items-center justify-center
                 text-gray-500 hover:bg-gray-100 shadow z-10"
          @click="toggleOrder(order.maDonHang)"
        >
          <span v-if="expandedOrderId === order.maDonHang"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M9.47 6.47a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 1 1-1.06 1.06L10 8.06l-3.72 3.72a.75.75 0 0 1-1.06-1.06l4.25-4.25Z" clip-rule="evenodd" />
</svg>
</span>
          <span v-else><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
</svg>
</span>
        </button>
      </td>
    </tr>

    <!-- PHẦN XỔ SẢN PHẨM -->
    <tr v-if="expandedOrderId === order.maDonHang">
      <td colspan="6" class="bg-gray-50 pt-6">
        <div
          v-for="(item, index) in order.items"
          :key="index"
          class="flex items-center gap-4 p-4 border-b last:border-b-0"
        >
          <img
            :src="item.anh"
            class="w-20 h-20 object-cover border rounded"
          />

          <div class="flex-1">
            <p class="font-medium">
              {{ item.tenSanPham }}
            </p>
            <p class="text-sm text-gray-500">
              Màu: {{ item.mau }} | Size: {{ item.size }}
            </p>
          </div>

          <div class="text-sm">
            x{{ item.soLuong }}
          </div>

          <div class="font-semibold text-red-600">
            {{ formatMoney(item.gia) }}
          </div>
<button
  v-if="reviewOrderId === order.maDonHang && !item.daDanhGia"
  class="px-3 py-1 bg-red-500 text-white rounded text-sm"
  @click="openReview(order, item)"
>
  Đánh giá
</button>

<span
  v-if="reviewOrderId === order.maDonHang && item.daDanhGia"
  class="text-green-600 text-sm font-semibold"
>
  Đã đánh giá
</span>

        </div>
      </td>
    </tr>

  </template>
</tbody>


        </table>
      </div>
       <div v-else>
<div class="mb-6">
  <!-- Dòng 1: Quay lại -->
  <button
    class="text-gray-500 hover:text-black mb-2"
    @click="viewMode = 'list'"
  >
    ← Quay lại
  </button>

  <!-- Dòng 2: Tiêu đề + trạng thái -->
  <div class="flex items-center gap-3">
    <h2 class="text-2xl font-bold">
      CHI TIẾT ĐƠN HÀNG #{{ orderDetail.maDdh }}
    </h2>

    <span
      class="px-3 py-1 text-sm rounded"
      :class="statusColor(orderDetail.orderStatus)"
    >
      {{ orderDetail.orderStatus }}
    </span>
  </div>
</div>



<div class="grid grid-cols-3 gap-6 mb-10">
  <div class="bg-gray-50 p-5 rounded min-h-[120px]">
    <p class="font-semibold mb-2">Địa chỉ người nhận</p>
    <p class="font-medium">{{ orderDetail.address.hoten }}</p>
    <p class="text-sm text-gray-600">
      {{ orderDetail.address.diaChi }},
      {{ orderDetail.address.phuongXa }},
      {{ orderDetail.address.quanHuyen }},
      {{ orderDetail.address.tinhThanhPho }}
    </p>
    <p class="text-sm mt-1">📞 {{ orderDetail.address.sdt }}</p>
  </div>

  <div class="bg-gray-50 p-5 rounded min-h-[120px]">
    <p class="font-semibold mb-2">Hình thức giao hàng</p>
    <p class="flex items-center gap-2">
      🚚 Giao hàng tại nhà
    </p>
  </div>

  <div class="bg-gray-50 p-5 rounded min-h-[120px]">
    <p class="font-semibold mb-2">Hình thức thanh toán</p>
    <p>{{ orderDetail.payment.tenPt }}</p>
  </div>
</div>
<h3 class="text-xl font-bold mb-4">
  GIỎ HÀNG
  <span class="text-sm text-red-500 font-normal">
    ({{ orderDetail.items.length }} sản phẩm)
  </span>
</h3>

<div class="grid grid-cols-12 text-sm font-semibold text-gray-500 border-b pb-3">
  <div class="col-span-6">Tên hàng</div>
  <div class="col-span-2 text-center">Giá</div>
  <div class="col-span-2 text-center">Số lượng</div>
  <div class="col-span-2 text-right">Tạm tính</div>
</div>
<div
  v-for="item in orderDetail.items"
  :key="item.tenSp + item.tenKc"
  class="grid grid-cols-12 gap-4 py-5 border-b items-center"
>
  <div class="col-span-6 flex gap-4">
    <img
      :src="item.urlImage"
      class="w-20 h-20 object-cover rounded border"
    />

    <div>
      <p class="font-medium">{{ item.tenSp }}</p>
      <p class="text-sm text-gray-500">
        Kích thước: <b>{{ item.tenKc }}</b><br />
        Màu sắc: <b>{{ item.tenMau }}</b>
      </p>
    </div>
  </div>

  <div class="col-span-2 text-center text-red-600 font-semibold">
    {{ formatMoney(item.gia) }}
  </div>

  <div class="col-span-2 text-center">
    {{ item.soLuong }}
  </div>

  <div class="col-span-2 text-right font-semibold">
    {{ formatMoney(item.tongTien) }}
  </div>
</div>
<div class="flex justify-end mt-8">
  <div class="w-96 text-sm">
    <div class="flex justify-between py-2">
      <span>Tạm tính</span>
      <span>{{ formatMoney(orderDetail.totalAmount) }}</span>
    </div>

    <div class="flex justify-between py-2">
      <span>Phí vận chuyển</span>
      <span>0 đ</span>
    </div>


    <div class="border-t mt-3 pt-3 flex justify-between text-lg font-bold">
      <span>Tổng cộng</span>
      <span class="text-red-600">
        {{ formatMoney(orderDetail.totalAmount) }}
      </span>
    </div>
  </div>
</div>


  </div>
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
  :src="previewAvatar || profile.avatar || '/default-avatar.png'"
  class="w-full h-full object-cover"
/>

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
      <div v-if="activeTab === 'reviews'" class="text-gray-600">
<div class="flex gap-6 border-b mb-6">
  <span class="text-red-500 border-b-2 border-red-500">
    Đã đánh giá
  </span>
</div>
<div>
  <div v-if="daDanhGia.length === 0" class="text-center text-gray-400">
    Bạn chưa có đánh giá nào
  </div>
  <div
    v-for="dg in daDanhGia"
    :key="dg.maDanhGia"
    class="flex gap-4 border p-4 mb-4"
  >
    <img :src="dg.anh" class="w-20 h-20 object-cover" />

    <div class="flex-1">
      <div class="font-semibold">{{ dg.tenSanPham }}</div>
      <div class="text-sm text-gray-500">
        {{ dg.mau }} / {{ dg.size }}
      </div>

      <div class="text-yellow-500">
        ⭐ {{ dg.diemDanhGia }}/5
      </div>

      <div class="mt-1">{{ dg.noiDung }}</div>

      <div class="text-xs text-gray-400">
        {{ new Date(dg.ngayTao).toLocaleString() }}
      </div>
    </div>
  </div>
</div>


      </div>


    </main>

  </div>
  <div
  v-if="showReviewModal"
  class="fixed inset-0 bg-black/40 flex items-center justify-center z-50"
>
   <!-- MODAL BOX -->
  <div class="bg-white w-[480px] rounded-xl p-6 shadow-lg">

    <h3 class="text-xl font-bold mb-4">Đánh giá sản phẩm</h3>
    <!-- THÔNG TIN SẢN PHẨM -->
<div class="flex gap-4 items-center border rounded-lg p-3 mb-4 bg-gray-50">
  <img
    :src="currentReviewItem?.anh"
    class="w-20 h-20 object-cover rounded border"
  />

  <div class="flex-1">
    <p class="font-semibold text-gray-800 line-clamp-2">
      {{ currentReviewItem?.tenSanPham }}
    </p>

    <p class="text-sm text-gray-500 mt-1">
      Phân loại:
      <span class="font-medium text-gray-700">
        {{ currentReviewItem?.mau }} / {{ currentReviewItem?.size }}
      </span>
    </p>
  </div>
</div>


    <!-- CHỌN SAO -->
    <div class="mb-4">
      <p class="font-semibold mb-2">Đánh giá</p>
      <div class="flex gap-2">
        <svg
          v-for="star in 5"
          :key="star"
          @click="reviewForm.diemDanhGia = star"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 20 20"
          fill="currentColor"
          class="w-8 h-8 cursor-pointer transition"
          :class="star <= reviewForm.diemDanhGia
            ? 'text-yellow-400'
            : 'text-gray-300 hover:text-yellow-300'"
        >
          <path
            d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.97a1 1 0 0 0 .95.69h4.174c.969 0 1.371 1.24.588 1.81l-3.378 2.455a1 1 0 0 0-.364 1.118l1.287 3.97c.3.921-.755 1.688-1.54 1.118l-3.378-2.455a1 1 0 0 0-1.176 0l-3.378 2.455c-.784.57-1.838-.197-1.539-1.118l1.286-3.97a1 1 0 0 0-.364-1.118L2.05 9.397c-.783-.57-.38-1.81.588-1.81h4.174a1 1 0 0 0 .95-.69l1.286-3.97Z"
          />
        </svg>
      </div>
    </div>

    <!-- NỘI DUNG -->
    <div class="mb-4">
      <p class="font-semibold mb-2">Nhận xét</p>
      <textarea
        v-model="reviewForm.noiDung"
        rows="4"
        class="w-full border rounded-lg p-3 focus:ring-2 focus:ring-red-400 focus:outline-none"
        placeholder="Hãy chia sẻ cảm nhận của bạn về sản phẩm..."
      ></textarea>
    </div>

    <!-- ACTION -->
    <div class="flex justify-end gap-3 mt-6">
      <button
        @click="showReviewModal = false"
        class="px-4 py-2 border rounded hover:bg-gray-100"
      >
        Hủy
      </button>

      <button
        class="bg-red-500 hover:bg-red-600 text-white px-6 py-2 rounded font-semibold"
        @click="submitReview"
      >
        Gửi đánh giá
      </button>
    </div>

  </div>
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
import { ref, onMounted,watch,reactive,computed } from "vue";
import api from '@/api';



const cancelOrder = async (maDonHang) => {
  if (!confirm("Bạn có chắc chắn muốn hủy đơn hàng này không?")) return;

  try {
    const res = await api.put(
      `/orders/cancel/${maDonHang}`
    );

    if (res.data.code === 1000) {
      alert("Hủy đơn hàng thành công!");

      // Cập nhật trạng thái ngay trên UI
      const order = orders.value.find(o => o.maDonHang === maDonHang);
      if (order) {
        order.trangThai = "Đã hủy";
      }
    } else {
      alert(res.data.message || "Hủy đơn hàng thất bại!");
    }
  } catch (err) {
    console.error("Lỗi hủy đơn:", err);
    alert("Không thể hủy đơn hàng!");
  }
};


const daDanhGia = ref([]);
const openReviewFromMyReview = (item) => {
  reviewForm.value = {
    maBienThe: item.maBienThe,
    maDdh: item.maDonHang,   // đã có sẵn
    noiDung: "",
    diemDanhGia: 5
  };
  currentReviewItem.value = item;
  showReviewModal.value = true;
};

const loadDanhGia = async () => {
  try {
    const res = await api.get("/myinfor/comments");

    console.log("API comments:", res.data); // 👈 kiểm tra luôn

    if (res.data.code === 1000 && Array.isArray(res.data.result)) {
      daDanhGia.value = res.data.result;
    } else {
      daDanhGia.value = [];
    }
  } catch (err) {
    console.error("Lỗi load đánh giá:", err);
    daDanhGia.value = [];
  }
};
onMounted(() => {
  loadDanhGia();
});

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
  Star,
  Package
} from "lucide-vue-next";
const activeTab = ref("orders"); // mặc định tab Đơn hàng
watch(activeTab, (val) => {
  if (val === "reviews") {
    loadDanhGia();
  }
});
const menu = [
  { key: "orders", label: "Đơn hàng của tôi", icon: Package },
  { key: "profile", label: "Tài khoản của tôi", icon: User },
  { key: "reviews", label: "Đánh giá của tôi", icon: Star },
];


const expandedOrderId = ref(null);

const toggleOrder = (orderId) => {
  expandedOrderId.value =
    expandedOrderId.value === orderId ? null : orderId;
};

const filteredOrders = computed(() => {
  if (orderStatus.value === "all") return orders.value;

  const map = {

    processing: "Đang xử lý",
    shipping: "Đang giao",
    done: "Đã giao",
    cancel: "Đã hủy",

  };

  return orders.value.filter(
    o => o.trangThai === map[orderStatus.value]
  );
});


const orderTabs = [
  { key: "all", label: "Tất cả đơn hàng" },
  { key: "processing", label: "Đang xử lý" },
  { key: "shipping", label: "Đang giao" },
  { key: "done", label: "Đã giao" },
  { key: "cancel", label: "Đã hủy" },
];

const orderStatus = ref("all");

const orders = ref([]);

/* Gọi API */
const fetchOrders = async () => {
  try {
    const res = await api.get("/myinfor/orders"); // => /api/orders

    if (res.data.code === 1000) {
      orders.value = res.data.result;
    }
  } catch (err) {
    console.error("Lỗi lấy đơn hàng:", err);
  }
};

onMounted(fetchOrders);

/* Helpers */
const formatDate = (date) =>
  new Date(date).toLocaleDateString("vi-VN");

const formatMoney = (money) =>
  money.toLocaleString("vi-VN") + "đ";

const statusColor = (status) => {
  switch (status) {
    case "Chờ xác nhận":
      return "bg-orange-100 text-orange-600";
    case "Đã giao":
      return "bg-green-100 text-green-600";
    case "Đã hủy":
      return "bg-red-100 text-red-600";
    default:
      return "bg-gray-100 text-gray-600";
  }
};

/* Click xem chi tiết */
const viewMode = ref('list') // 'list' | 'detail'
const orderDetail = ref(null)

const viewOrderDetail = async (maDdh) => {
  const res = await api.get(
    `/orders/${maDdh}`
  )
  orderDetail.value = res.data
  viewMode.value = 'detail'
}



const isOrderFullyReviewed = (order) => {
  return order.items.every(item => item.daDanhGia);
};

const currentReviewItem = ref(null);
const showReviewModal = ref(false);
const openReviewOrder = (order) => {
  expandedOrderId.value = order.maDonHang;
  reviewOrderId.value = order.maDonHang;
};

const reviewOrderId = ref(null);

const reviewForm = ref({
  maBienThe: null,
  maDdh: null,
  noiDung: "",
  diemDanhGia: 5
});
const openReview = (order, item) => {
  reviewForm.value = {
    maBienThe: item.maBienThe,
    maDdh: order.maDonHang,
    noiDung: "",
    diemDanhGia: 5
  };
  currentReviewItem.value = item;
  showReviewModal.value = true;
};
const submitReview = async () => {
  if (!reviewForm.value.noiDung.trim()) {
    alert("Vui lòng nhập nội dung đánh giá");
    return;
  }

  try {
    const res = await api.post("/comments", reviewForm.value);

    if (res.data.code === 1000) {
      if (currentReviewItem.value) {
        currentReviewItem.value.daDanhGia = true;
      }
      alert("Đánh giá thành công!");
      showReviewModal.value = false;
    }
  } catch (err) {
    alert("Không thể gửi đánh giá");
  }
};



</script>

