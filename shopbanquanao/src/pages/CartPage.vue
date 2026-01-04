<template>
  <div class="max-w-7xl mx-auto py-10 ">
<div
      v-if="cart.items.length > 0"
      class="grid grid-cols-12 gap-8"
    >
    <!-- LEFT: GIỎ HÀNG -->
<div class="col-span-8 bg-white p-6 rounded shadow">

  <!-- Title -->
<h3 class="text-xl font-bold mb-4">
  GIỎ HÀNG
  <span class="text-sm text-[#c92127] font-normal">
    ({{ cart.items.length }} sản phẩm)
  </span>
</h3>

  <!-- Header cột -->
  <div class="grid grid-cols-12 text-sm font-semibold text-black pb-3">
    <div class="col-span-2"></div>
    <div class="col-span-4">Tên hàng</div>
    <div class="col-span-2 text-center">Giá</div>
    <div class="col-span-2 text-center">Số lượng</div>
    <div class="col-span-2 text-right">Tổng tiền</div>
  </div>

  <!-- Item -->
  <div
    v-for="item in cart.items"
    :key="item.variant.maBienThe"
    class="grid grid-cols-12 gap-4 py-4 items-center"
  >
    <!-- Tên hàng -->
    <div class="col-span-6 flex gap-4">
      <img
        :src="item.product.urlImage"
        class="w-24 h-30 object-cover rounded border"
      />

      <div>
        <p class="font-semibold">
          {{ item.product.tenSp }}
        </p>

        <p class="text-sm text-gray-500 mt-1">
          Kích thước: <b>{{ item.variant.tenKc }}</b> |
          Màu: <b>{{ item.variant.tenMs }}</b>
        </p>
      </div>
    </div>

    <!-- Giá -->
    <div class="col-span-2 text-center text-[#c92127] font-bold">
      {{ formatPrice(item.product.gia) }}đ
    </div>

    <!-- Số lượng -->
<div class="col-span-2 flex justify-center mt-1">
  <div class="flex items-center border rounded">
    <button
      class="px-3 py-1 hover:bg-gray-100"
      @click="item.variant.soLuongDat === 1
        ? removeItem(item)
        : updateQuantity(item, item.variant.soLuongDat - 1)"
    >
      −
    </button>

    <span class="px-4">
      {{ item.variant.soLuongDat }}
    </span>

    <button
      class="px-3 py-1 hover:bg-gray-100"
      @click="updateQuantity(item, item.variant.soLuongDat + 1)"
    >
      +
    </button>
  </div>
</div>


<div class="col-span-2 text-right mt-9">
    <div class="font-bold">
      {{ formatPrice(item.product.gia * item.variant.soLuongDat) }}đ
    </div>

    <button
      class="text-gray-400 hover:text-[#c92127] mt-4"
      @click="removeItem(item)"
    >
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="size-5">
  <path fill-rule="evenodd" d="M8.75 1A2.75 2.75 0 0 0 6 3.75v.443c-.795.077-1.584.176-2.365.298a.75.75 0 1 0 .23 1.482l.149-.022.841 10.518A2.75 2.75 0 0 0 7.596 19h4.807a2.75 2.75 0 0 0 2.742-2.53l.841-10.52.149.023a.75.75 0 0 0 .23-1.482A41.03 41.03 0 0 0 14 4.193V3.75A2.75 2.75 0 0 0 11.25 1h-2.5ZM10 4c.84 0 1.673.025 2.5.075V3.75c0-.69-.56-1.25-1.25-1.25h-2.5c-.69 0-1.25.56-1.25 1.25v.325C8.327 4.025 9.16 4 10 4ZM8.58 7.72a.75.75 0 0 0-1.5.06l.3 7.5a.75.75 0 1 0 1.5-.06l-.3-7.5Zm4.34.06a.75.75 0 1 0-1.5-.06l-.3 7.5a.75.75 0 1 0 1.5.06l.3-7.5Z" clip-rule="evenodd" />
</svg>
    </button>
  </div>

  </div>

</div>


    <!-- RIGHT: ĐƠN HÀNG -->
<div class="col-span-4 bg-white p-6 rounded shadow h-fit sticky top-6">
  <h2 class="text-lg font-bold mb-5">
    ĐƠN HÀNG
  </h2>

  <!-- Tổng tiền -->
  <div class="flex justify-between items-end mb-4">
    <span class="text-sm text-gray-600">
      Tổng giá trị đơn hàng
    </span>

    <span class="text-xl font-bold text-[#c92127]">
      {{ formatPrice(cart.tongTien) }}đ
    </span>
  </div>

  <!-- Đường gạch nét đứt -->
  <div class="border-t border-dashed border-gray-300 mb-6"></div>

  <!-- Button -->
  <button
    class="w-full bg-[#c92127] hover:bg-red-800
           text-white py-3 rounded font-semibold flex items-center justify-center gap-2"
           @click="router.push('/checkout')"
  >
    TIẾP TỤC THANH TOÁN →
  </button>
</div>
</div>
<div
      v-else
      class="max-w-7xl mx-auto py-20 bg-white rounded shadow
           flex flex-col items-center justify-center"
    >
      <img
        src="/src/assets/icon/oops.svg"
        class="w-40 mb-6"
        alt="Empty cart"
      />

      <p class="text-gray-600 mb-6">
        Bạn chưa có sản phẩm nào trong giỏ hàng.
      </p>

      <button
        class="bg-[#c92127] hover:bg-red-800 text-white px-10 py-3 rounded font-semibold"
        @click="$router.push('/')"
      >
        TIẾP TỤC MUA SẮM
      </button>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import api from "@/api";
import { useRouter } from "vue-router";

const router = useRouter();


const cart = ref({
  items: [],
  tongTien: 0
});

onMounted(async () => {
  try {
    const res = await api.get("/carts");
    cart.value = res.data.result;
  } catch (e) {
    console.error("Lỗi lấy giỏ hàng", e);
  }
});

const formatPrice = (p) =>
  Number(p).toLocaleString("vi-VN");


const updateQuantity = async (item, newQty) => {
  if (newQty < 1) return;

  try {
    await api.put(`/carts/${item.variant.maBienThe}`, {
      soLuong: newQty
    });

    await fetchCart(); // reload giỏ hàng
  } catch (e) {
    console.error("Update quantity error", e);
    alert("Không thể cập nhật số lượng");
  }
};


const removeItem = async (item) => {
  if (!confirm("Xoá sản phẩm khỏi giỏ hàng?")) return;

  try {
    const res = await api.delete(`/carts/${item.variant.maBienThe}`);

    // 1️⃣ update token (header)
    if (res.data.result?.token) {
      localStorage.setItem("token", res.data.result.token);
      window.dispatchEvent(new Event("user-updated"));
    }

    // 2️⃣ XOÁ NGAY TRONG STATE (UI biến mất liền)
    cart.value.items = cart.value.items.filter(
      i => i.variant.maBienThe !== item.variant.maBienThe
    );

    // 3️⃣ cập nhật tổng tiền
    cart.value.tongTien = cart.value.items.reduce(
      (sum, i) => sum + i.product.gia * i.variant.soLuongDat,
      0
    );

    // 4️⃣ sync lại BE (optional – để chắc)
    // await fetchCart();

  } catch (e) {
    console.error("Remove item error", e);
  }
};



const fetchCart = async () => {
  const res = await api.get("/carts");

  cart.value = {
    items: res.data.result?.items || [],
    tongTien: res.data.result?.tongTien || 0
  };
};



const isCheckout = ref(false);

const checkoutData = ref({
  address: [],
  payment: [],
  myCartResponse: null
});


const goCheckout = async () => {
  try {
    const res = await api.get("/orders/payments");

    checkoutData.value = res.data.result;
    isCheckout.value = true;
  } catch (e) {
    console.error("Checkout error", e);
    alert("Không thể vào trang thanh toán");
  }
};

</script>
