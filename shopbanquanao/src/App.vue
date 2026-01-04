<script setup>
import { useAuthStore } from "@/stores/auth";
import { useRoute } from "vue-router";

import Mainheader from "@/components/header/Mainheader.vue";
import Userheader from "@/components/header/Userheader.vue";
import Mainfooter from "@/components/footer/Mainfooter.vue";
import Navmenu from "@/components/header/Navmenu.vue";

const auth = useAuthStore();
const route = useRoute();
</script>

<template>
  <div class="bg-[#faf9f8] min-h-screen">

    <!-- ===== USER LAYOUT ===== -->
    <template v-if="!route.meta.hideLayout && !route.meta.requiresAdmin">
      <Userheader v-if="auth.token" />
      <Mainheader v-else />
      <Navmenu />
    </template>

    <!-- ===== PAGE CONTENT ===== -->
    <router-view />

    <!-- ===== FOOTER (KHÔNG HIỆN ADMIN) ===== -->
    <Mainfooter
      v-if="!route.meta.hideLayout && !route.meta.hideFooter && !route.meta.requiresAdmin"
    />
  </div>
</template>
