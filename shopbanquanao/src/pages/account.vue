<template>
  <div class="max-w-7xl mx-auto mt-10 grid grid-cols-12 gap-6">

    <!-- Sidebar -->
    <aside class="col-span-3 bg-white p-6 rounded-lg shadow">
      <!-- Avatar + Name -->
      <div class="flex items-center gap-4 mb-6">
        <img
          src=""
          alt="avatar"
          class="w-14 h-14 rounded-full object-cover"
        />
        <div>
          <p class="text-gray-600 text-sm">Xin chào,</p>
          <p class="font-semibold">{{ user.name }}</p>
        </div>
      </div>

      <!-- Menu -->
      <nav class="space-y-2">
        <button
          v-for="item in menu"
          :key="item.key"
          @click="activeTab = item.key"
          class="w-full flex items-center gap-3 p-3 rounded-lg text-left transition"
          :class="activeTab === item.key
            ? 'bg-red-600 text-white'
            : 'hover:bg-gray-100 text-gray-700'
          "
        >
          <component :is="item.icon" class="w-5 h-5" />
          <span>{{ item.label }}</span>
        </button>
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
      <div v-if="activeTab === 'profile'" class="text-gray-600">
        Trang Tài khoản của tôi...
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

<script setup>
import { ref } from "vue";
import {
  User,
  TicketPercent,
  Star,
  Eye,
  Package
} from "lucide-vue-next";

const user = ref({
  name: "đặng tuấn"
});

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

