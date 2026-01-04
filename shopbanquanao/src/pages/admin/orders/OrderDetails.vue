<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import api from "@/api";
import { Loader2, Truck, CheckCircle } from "lucide-vue-next";

const route = useRoute();
const order = ref(null);
const loading = ref(false);

const displayStatus = (status) => {
  if (status === "DA_GIAO") return "Đã giao";
  return status;
};

/* ================= LOAD ORDER ================= */
const loadOrder = async () => {
  loading.value = true;
  try {
    const res = await api.get(`/orders/${route.params.id}`);
    order.value = res.data; // API trả về object trực tiếp
  } catch (err) {
    console.error("Lỗi load order detail:", err);
  } finally {
    loading.value = false;
  }
};

/* ================= UPDATE STATUS ================= */
const setDelivering = async () => {
  await api.put(`/orders/delivering/${route.params.id}`);
  await loadOrder();
};

const setDelivered = async () => {
  await api.put(`/orders/delivered/${route.params.id}`);
  await loadOrder();
};

/* ================= FORMAT ================= */
const formatMoney = (v) =>
  v?.toLocaleString("vi-VN", { style: "currency", currency: "VND" });

const formatDate = (v) =>
  v ? new Date(v).toLocaleString("vi-VN") : "—";

/* ================= STATUS ================= */
const isProcessing = computed(() => order.value?.orderStatus === "Đang xử lý");
const isDelivering = computed(() => order.value?.orderStatus === "Đang giao");

onMounted(loadOrder);
</script>

<template>
  <div class="p-6 max-w-6xl mx-auto">

    <!-- LOADING -->
    <div v-if="loading" class="text-center text-gray-500 py-20 flex justify-center items-center gap-2">
      <Loader2 class="w-6 h-6 animate-spin" />
      <span>Đang tải dữ liệu đơn hàng...</span>
    </div>

    <div v-else-if="order">
      <!-- HEADER -->
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-bold flex items-center gap-2">
          <Package class="w-6 h-6 text-gray-700" />
          Đơn hàng #{{ order.maDdh }}
        </h1>

        <span
          class="px-4 py-1.5 rounded-full text-sm font-semibold"
          :class="{
            'bg-yellow-100 text-yellow-700': order.orderStatus === 'Đang xử lý',
            'bg-blue-100 text-blue-700': order.orderStatus === 'Đang giao',
            'bg-green-100 text-green-700': order.orderStatus === 'DA_GIAO',
            'bg-red-100 text-red-700': order.orderStatus === 'Đã hủy'
          }"
        >
          {{ displayStatus(order.orderStatus) }}
        </span>
      </div>

      <!-- INFO -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
        <div class="bg-white rounded-xl shadow p-5 text-sm space-y-2">
          <h2 class="font-semibold text-base mb-2">Thông tin đơn hàng</h2>
          <p><b>Thanh toán:</b> {{ order.payment.tenPt }}</p>
          <p><b>Ngày thanh toán:</b> {{ formatDate(order.ngayThanhToan) }}</p>
          <p><b>Ngày giao:</b> {{ formatDate(order.shippedAt) }}</p>
        </div>

        <div class="bg-white rounded-xl shadow p-5 text-sm space-y-2">
          <h2 class="font-semibold text-base mb-2">Tổng tiền</h2>
          <p class="text-2xl font-bold text-red-600">
            {{ formatMoney(order.totalAmount) }}
          </p>
        </div>
      </div>

      <!-- ADDRESS -->
      <div class="bg-white rounded-xl shadow p-5 mb-6 text-sm">
        <h2 class="font-semibold text-base mb-2">Địa chỉ giao hàng</h2>
        <p class="font-medium">
          {{ order.address.hoten }} – {{ order.address.sdt }}
        </p>
        <p class="text-gray-600">
          {{ order.address.diaChi }},
          {{ order.address.phuongXa }},
          {{ order.address.quanHuyen }},
          {{ order.address.tinhThanhPho }}
        </p>
      </div>

      <!-- ITEMS -->
      <div class="bg-white rounded-xl shadow overflow-hidden mb-6">
        <table class="w-full text-sm">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-4 py-3 text-left">Sản phẩm</th>
              <th class="px-4 py-3 text-right">Giá</th>
              <th class="px-4 py-3 text-center">SL</th>
              <th class="px-4 py-3 text-right">Tổng</th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="i in order.items"
              :key="i.tenSp"
              class="border-t hover:bg-gray-50"
            >
              <td class="px-4 py-3 flex items-center gap-3">
                <img
                  :src="i.urlImage"
                  class="w-14 h-14 object-cover rounded-lg border"
                />
                <div>
                  <div class="font-medium">{{ i.tenSp }}</div>
                  <div class="text-gray-500 text-xs">
                    {{ i.tenMau }} – {{ i.tenKc }}
                  </div>
                </div>
              </td>

              <td class="px-4 py-3 text-right">
                {{ formatMoney(i.gia) }}
              </td>

              <td class="px-4 py-3 text-center">
                {{ i.soLuong }}
              </td>

              <td class="px-4 py-3 text-right font-semibold">
                {{ formatMoney(i.tongTien) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- ACTIONS -->
      <div class="flex gap-4">
        <button
          v-if="isProcessing"
          @click="setDelivering"
          class="px-5 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition flex items-center gap-2"
        >
          <Truck class="w-5 h-5" /> Chuyển sang Đang giao
        </button>

        <button
          v-if="isDelivering"
          @click="setDelivered"
          class="px-5 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition flex items-center gap-2"
        >
          <CheckCircle class="w-5 h-5" /> Xác nhận Đã giao
        </button>
      </div>
    </div>
  </div>
</template>
