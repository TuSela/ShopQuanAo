<script setup>
import { ref, onMounted,computed } from "vue";
import api from "@/api";
import { useRouter } from "vue-router";
import { Package, Loader2 } from "lucide-vue-next";

const router = useRouter();
const orders = ref([]);
const loading = ref(false);
const filterStatus = ref("Đang xử lý");

const tabs = [
  { label: "Tất cả đơn hàng", value: "" },
  { label: "Đang xử lý", value: "Đang xử lý" },
  { label: "Đang giao", value: "Đang giao" },
  { label: "Đã giao", value: "DA_GIAO" },
  { label: "Đã hủy", value: "Đã hủy" },
];

const filteredOrders = computed(() => {
  if (!filterStatus.value) return orders.value;
  return orders.value.filter(o => o.orderStatus === filterStatus.value);
});
const displayStatus = (status) => {
  if (status === "DA_GIAO") return "Đã giao";
  return status;
};

const loadOrders = async () => {
  loading.value = true;
  try {
    const res = await api.get("/orders/list");
    orders.value = res.data;
  } catch (err) {
    console.error("Lỗi load orders", err);
  } finally {
    loading.value = false;
  }
};

const goDetail = (id) => {
  router.push(`/admin/orders/${id}`);
};

onMounted(loadOrders);
</script>

<template>
  <div class="p-6">
    <!-- PAGE TITLE -->
    <h1 class="text-2xl font-bold mb-6 flex items-center gap-2">
      <Package class="w-6 h-6 text-gray-700" />
      Quản lý đơn hàng
    </h1>

    <div class="bg-white rounded-xl shadow overflow-hidden">
<div class="border-b mb-6">
  <div class="flex justify-center gap-8">
    <button
      v-for="tab in tabs"
      :key="tab.value"
      @click="filterStatus = tab.value"
      class="relative px-4 py-3 text-base font-medium transition"
      :class="filterStatus === tab.value
        ? 'text-red-600'
        : 'text-gray-500 hover:text-gray-700'"
    >
      {{ tab.label }}

      <!-- gạch chân active -->
      <span
        v-if="filterStatus === tab.value"
        class="absolute left-0 right-0 -bottom-px h-0.5 bg-red-500 rounded"
      ></span>
    </button>
  </div>
</div>


      <table class="w-full text-sm">
        <thead class="bg-gray-50 text-gray-700">
          <tr>
            <th class="px-4 py-3 text-left">Mã</th>
            <th class="px-4 py-3 text-left">Khách hàng</th>
            <th class="px-4 py-3 text-center">Ngày đặt</th>
            <th class="px-4 py-3 text-center">Thanh toán</th>
            <th class="px-4 py-3 text-center">Trạng thái</th>
            <th class="px-4 py-3 text-right">Tổng tiền</th>
            <th class="px-4 py-3 text-center">Hành động</th>
          </tr>
        </thead>

        <!-- Orders -->
<tbody v-if="!loading && filteredOrders.length">
  <tr
    v-for="o in filteredOrders"
    :key="o.maDdh"
    class="border-t hover:bg-gray-50 transition"
  >
            <td class="px-4 py-3 font-semibold">#{{ o.maDdh }}</td>
            <td class="px-4 py-3">{{ o.hoten }}</td>
            <td class="px-4 py-3 text-center">{{ o.ngayThanhToan }}</td>
            <td class="px-4 py-3 text-center">{{ o.tenPt }}</td>

            <td class="px-4 py-3 text-center">
              <span
                class="px-3 py-1 rounded-full text-xs font-semibold"
                :class="{
                  'bg-yellow-100 text-yellow-700': o.orderStatus === 'Đang xử lý',
                  'bg-blue-100 text-blue-700': o.orderStatus === 'Đang giao',
                  'bg-green-100 text-green-700': o.orderStatus === 'DA_GIAO',
                  'bg-red-100 text-red-700': o.orderStatus === 'Đã hủy'
                }"
              >
                {{ displayStatus(o.orderStatus) }}
              </span>
            </td>

            <td class="px-4 py-3 text-right font-semibold text-red-600">
              {{ o.tongTien.toLocaleString() }} ₫
            </td>

            <td class="px-4 py-3 text-center">
              <button
                @click="goDetail(o.maDdh)"
                class="px-3 py-1.5 bg-blue-500 hover:bg-blue-600
                       text-white rounded-lg text-xs transition flex items-center gap-1"
              >
                Chi tiết
              </button>
            </td>
          </tr>
        </tbody>

        <!-- Loading -->
        <tbody v-else-if="loading">
          <tr>
            <td colspan="7" class="py-10 text-center text-gray-500 flex justify-center items-center gap-2">
              <Loader2 class="w-5 h-5 animate-spin" />
              Đang tải dữ liệu...
            </td>
          </tr>
        </tbody>

        <!-- Empty -->
        <tbody v-else>
          <tr>
            <td colspan="7" class="py-10 text-center text-gray-500">
              Không có đơn hàng nào
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
