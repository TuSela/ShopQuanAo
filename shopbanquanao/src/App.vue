<script setup>
import { useAuthStore } from "@/stores/auth";
import { useRoute } from "vue-router";
import Mainheader from "@/components/header/Mainheader.vue";
import Userheader from "@/components/header/Userheader.vue";
import Mainfooter from "@/components/footer/Mainfooter.vue"
import Navmenu from "@/components/header/Navmenu.vue";
const auth = useAuthStore();
const route = useRoute();
</script>

<template >
  <div class="bg-[#faf9f8] min-h-screen">
    <template v-if="!route.meta.hideLayout">
    <Userheader v-if="auth.token"  />
    <Mainheader v-else />
    <Navmenu></Navmenu>
  </template>
    <router-view />
    <Mainfooter v-if="!route.meta.hideLayout && !route.meta.hideFooter" />
  </div>
</template>
