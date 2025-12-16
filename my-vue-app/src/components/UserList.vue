<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="bg-white border-b sticky top-0 z-40">
      <div
        class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between"
      >
        <h1 class="text-xl font-bold">Admin • Thêm / Chỉnh sửa Sản phẩm</h1>
        <div class="flex gap-2">
          <button class="btn-secondary">Xem trước</button>
          <button class="btn-primary">Lưu</button>
        </div>
      </div>
    </header>

    <!-- Content -->
    <main class="max-w-7xl mx-auto p-6 grid grid-cols-1 lg:grid-cols-12 gap-6">
      <!-- LEFT: Media -->
      <aside class="lg:col-span-4 space-y-6">
        <!-- Thumbnail -->
        <section class="card">
          <h3 class="card-title">Ảnh đại diện</h3>
          <div
            class="aspect-square rounded-xl border-2 border-dashed flex flex-col items-center justify-center text-gray-400 hover:border-blue-400 transition"
          >
            <span class="text-sm">Kéo & thả hoặc bấm để tải ảnh</span>
          </div>
        </section>

        <!-- Gallery -->
        <section class="card">
          <h3 class="card-title">Gallery</h3>
          <div class="grid grid-cols-3 gap-3">
            <div
              v-for="i in 6"
              :key="i"
              class="aspect-square bg-gray-100 rounded-lg"
            ></div>
            <div
              class="aspect-square border-2 border-dashed rounded-lg flex items-center justify-center text-gray-400"
            >
              +
            </div>
          </div>
        </section>
      </aside>

      <!-- RIGHT: Info -->
      <section class="lg:col-span-8 space-y-6">
        <!-- Basic info -->
        <div class="card">
          <h2 class="card-title">Thông tin sản phẩm</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="label">Tên sản phẩm</label>
              <input class="input" />
            </div>
            <div>
              <label class="label">Giá (VND)</label>
              <input class="input" />
            </div>
            <div>
              <label class="label">Danh mục</label>
              <select class="input"></select>
            </div>
            <div>
              <label class="label">Trạng thái</label>
              <select class="input"></select>
            </div>
          </div>
        </div>

        <!-- Description -->
        <div class="card">
          <h2 class="card-title">Mô tả sản phẩm</h2>
          <div class="h-48 bg-gray-50 rounded-lg"></div>
        </div>

        <!-- Variants -->
        <div class="card">
          <div class="flex justify-between items-center mb-4">
            <h2 class="card-title">Biến thể</h2>
            <button class="btn-secondary">+ Thêm màu</button>
          </div>
          <div class="border rounded-xl p-4 space-y-4">
            <div class="flex gap-4">
              <select class="input w-48"></select>
              <button class="btn-secondary">Thêm ảnh</button>
            </div>
            <div class="grid grid-cols-4 gap-3">
              <div class="aspect-square bg-gray-100 rounded"></div>
              <div
                class="aspect-square border border-dashed rounded flex items-center justify-center"
              >
                +
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.card {
  @apply bg-white rounded-2xl shadow-sm p-5;
}
.card-title {
  @apply font-semibold mb-4;
}
.label {
  @apply text-sm text-gray-600 mb-1 block;
}
.input {
  @apply w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500;
}
.btn-primary {
  @apply px-5 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700;
}
.btn-secondary {
  @apply px-4 py-2 rounded-lg border bg-white hover:bg-gray-50;
}
</style>
