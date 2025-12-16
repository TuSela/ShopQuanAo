<template>
  <nav class="bg-gray-100 relative">
    <div class="max-w-7xl mx-auto flex items-center justify-center gap-20 px-4 py-3 text-lg font-medium text-black">

      <a href="#" class="hover:text-[#c92127] uppercase font-bold">Sale</a>
      <a href="#" class="hover:text-[#c92127]">Thời trang giữ ấm</a>

      <!-- NỮ (Mega dropdown) -->
      <div class="relative group">
        <a href="#" class="hover:text-[#c92127]">Nữ</a>

        <!-- MEGA MENU -->
        <div
          class="absolute left-1/2 top-full -translate-x-1/2 w-screen max-w-[1200px] 
                 bg-white shadow-lg border-t border-gray-200
                 opacity-0 invisible pointer-events-none
                 group-hover:opacity-100 group-hover:visible group-hover:pointer-events-auto
                 transition-all duration-200 z-50 py-6">
          
          <!-- GRID 4 CỘT -->
          <div class="grid grid-cols-4 gap-10 px-10">

            <!-- Cột 1 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Áo khoác</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác phao & lông vũ</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác lông cừu</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác gió</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác chống nắng</a></li>
              </ul>
            </div>

            <!-- Cột 2 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Áo</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo giữ nhiệt WarmMax</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác giữ ấm</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo thu đông</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo thun & polo</a></li>
              </ul>
            </div>

            <!-- Cột 3 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Quần & đầm váy</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Quần jeans & dài</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Quần shorts & chân váy</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Váy đầm</a></li>
              </ul>
            </div>

            <!-- Cột 4 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Đồ lót</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo lót & bra</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Quần lót</a></li>
              </ul>
            </div>

          </div>
        </div>
      </div>
      <div class="relative group">
      <router-link to="/Nam"><a href="#" class="hover:text-[#c92127]">Nam</a></router-link>

      
        <!-- MEGA MENU -->
        <div
          class="absolute left-1/2 top-full -translate-x-1/2 w-screen max-w-[1200px] 
                 bg-white shadow-lg border-t border-gray-200
                 opacity-0 invisible pointer-events-none
                 group-hover:opacity-100 group-hover:visible group-hover:pointer-events-auto
                 transition-all duration-200 z-50 py-6">
          
          <!-- GRID 4 CỘT -->
          <div class="grid grid-cols-4 gap-10 px-10">

            <!-- Cột 1 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Áo khoác</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác phao & lông vũ</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác lông cừu</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác gió</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác chống nắng</a></li>
              </ul>
            </div>

            <!-- Cột 2 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Áo</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo giữ nhiệt WarmMax</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác giữ ấm</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo thu đông</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo thun & polo</a></li>
              </ul>
            </div>

            <!-- Cột 3 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Quần</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Quần jeans & dài</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Quần shorts</a></li>
              </ul>
            </div>

            <!-- Cột 4 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Đồ lót</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Quần lót</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Áo ba lỗ</a></li>
              </ul>
            </div>

          </div>
        </div>
       </div>
      <div class="relative group">
      <a href="#" class="hover:text-[#c92127]">Trẻ em</a>

    <!-- MEGA MENU -->
        <div
          class="absolute left-1/2 top-full -translate-x-1/2 w-screen max-w-[900px] 
                 bg-white shadow-lg border-t border-gray-200
                 opacity-0 invisible pointer-events-none
                 group-hover:opacity-100 group-hover:visible group-hover:pointer-events-auto
                 transition-all duration-200 z-50 py-6">
          
          <!-- GRID 4 CỘT -->
          <div class="grid grid-cols-3 gap-10 px-10">

            <!-- Cột 1 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Thời trang giữ ấm</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo khoác ngoài</a></li>
              </ul>
            </div>

            <!-- Cột 2 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Bé gái</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo </a></li>
                <li><a class="hover:text-[#c92127]" href="#">Quần & váy</a></li>
              </ul>
            </div>

            <!-- Cột 3 -->
            <div>
              <h3 class="font-semibold mb-3 hover:text-[#c92127]">Bé trai</h3>
              <ul class="space-y-1 text-sm">
                <li><a class="hover:text-[#c92127]" href="#">Áo</a></li>
                <li><a class="hover:text-[#c92127]" href="#">Quần</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <a href="#" class="hover:text-[#c92127]">Mua sỉ</a>
      <a href="#" class="hover:text-[#c92127]">Tin tức</a>
    </div>
  </nav>
</template>
