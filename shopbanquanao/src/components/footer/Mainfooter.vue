<template>
  <footer class="bg-white border-t mt-10 text-sm text-gray-700">
    <div class="border-t py-8">
      <div class="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-5 gap-8 px-4">

        <!-- LEFT: 4 COLUMNS (giống phần trên) -->
        <div class="md:col-span-4 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-1">

          <div>
            <h3 class="font-bold mb-3">VỀ TOKYOLIFE</h3>
            <ul class="space-y-2">
              <li>Chúng tôi là ai</li>
              <li>Cam kết của chúng tôi</li>
              <li>Tin tuyển dụng</li>
              <li>Hệ thống cửa hàng</li>
            </ul>
          </div>

          <div>
            <h3 class="font-bold mb-3">HỖ TRỢ KHÁCH HÀNG</h3>
            <ul class="space-y-2">
              <li>Hướng dẫn đặt hàng</li>
              <li>Phương thức thanh toán</li>
              <li>Chính sách sinh nhật thành viên</li>
              <li>Chính sách tích - tiêu điểm</li>
              <li>Chính sách hoàn tiền</li>
            </ul>
          </div>

          <div>
            <h3 class="font-bold mb-3">CHÍNH SÁCH</h3>
            <ul class="space-y-2">
              <li>Chính sách vận chuyển</li>
              <li>Chính sách kiểm hàng</li>
              <li>Chính sách đổi trả</li>
              <li>Điều kiện & Điều khoản</li>
              <li>Chính sách bảo mật</li>
            </ul>
          </div>

          <div>
            <h3 class="font-bold mb-3">LIÊN HỆ</h3>
            <ul class="space-y-2">
              <li>Tư vấn mua online: 024 7308 2882</li>
              <li>Kiếu nại & bảo hành: 024 7300 6999</li>
              <li>Email: cskh@tokyolife.vn</li>
              <li>Giờ làm việc: 8:30 – 22:00 hằng ngày</li>
            </ul>
          </div>
        </div>

        <!-- RIGHT: 1 COLUMN (gộp ĐK + QR + Payment) -->
        <div class="space-y-6">

          <!-- Đăng ký -->
          <div>
            <h3 class="font-bold mb-2">ĐĂNG KÝ NHẬN TIN</h3>
            <div class="flex gap-2">
              <input type="email" placeholder="Nhập Email" class="border px-3 py-2 w-full rounded" />
              <button class="bg-black text-white px-4 rounded">Đăng ký</button>
            </div>
          </div>

          <!-- QR + APP -->
          <div class="flex gap-4 items-center">
            <img src="/src/assets/image/1.png" class="w-20">
            <div class="space-y-2">
              <img src="/src/assets/icon/app_store.svg" class="w-28">
              <img src="/src/assets/icon/google_play.svg" class="w-28">
            </div>
          </div>

          <!-- Payment -->
           <h3 >Chúng tôi kết nối thanh toán qua</h3>
          <div class="flex gap-4 items-center">
            <img src="/src/assets/icon/cod.svg" class="h-10">
            <img src="/src/assets/icon/vnpay.svg" class="h-10">
          </div>

        </div>

      </div>
    </div>

    <!-- PART 3: COMPANY INFO -->
    <div class=" text-xs text-gray-600 py-1 px-4">
      <div class="max-w-7xl mx-auto flex flex-col md:flex-row justify-between">

        <div>
          <p class="font-bold mb-1">CÔNG TY CỔ PHẦN STAAAR</p>
          <p>Địa chỉ: Tầng 6, số 96 Thái Hà, Phường Đống Đa, Thành phố Hà Nội, Việt Nam.</p>
          <p>Mã số thuế: 0109749326, ngày cấp ĐKKD 29/04/2021.</p>
          <p>Điện thoại: 024.7300.6999 — Email: cskh@tokyolife.vn</p>
        </div>

        <img src="/src/assets/icon/bct.png" class="h-11 mt-4 md:mt-5">
      </div>
    </div>

  </footer>
</template>
