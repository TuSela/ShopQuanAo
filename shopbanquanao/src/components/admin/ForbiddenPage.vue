<template>
  <div class="h-screen flex items-center justify-center bg-gray-100">
    <div class="bg-white shadow-lg rounded-xl p-10 text-center max-w-sm animate-fade-in">
      <div class="text-6xl mb-4">🚫</div>
      <h1 class="text-2xl font-bold text-red-600 mb-2">Không có quyền truy cập</h1>
      <p class="text-gray-500 mb-6">
        Bạn không có quyền truy cập chức năng này.
      </p>
    </div>
  </div>
</template>

<script setup>
// Không cần script gì cả
</script>

<style scoped>
@keyframes fade-in {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}
.animate-fade-in {
  animation: fade-in 0.5s ease-out;
}
</style>
