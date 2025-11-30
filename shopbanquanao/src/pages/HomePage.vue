<script setup>
import Navmenu from '@/components/header/Navmenu.vue';
import Mainheader from '@/components/header/Mainheader.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import CategorySlider from '@/components/home/Category Slider.vue';
import ProductList from '@/components/product/ProductList.vue';
import Homeview from '@/components/home/Homeview.vue';
import Mainfooter from '@/components/footer/Mainfooter.vue';

const categories = [
  { name: "Áo khoác giữ ấm", image: "/src/assets/image/1.png" }, 
  { name: "Áo khoác giữ ấm", image: "/src/assets/image/1a.png" }, 
  { name: "Áo giữ nhiệt", image: "/src/assets/image/2.png" }, 
  { name: "Áo giữ nhiệt", image: "/src/assets/image/1b.png" }, 
  { name: "Áo chống nắng", image: "/src/assets/image/3.png" }, 
  { name: "Áo chống nắng", image: "/src/assets/image/1c.png" }, 
  { name: "Áo polo & thun", image: "/src/assets/image/4.png" }, 
  { name: "Áo polo & thun", image: "/src/assets/image/1d.png" }, 
  { name: "Đồ lót", image: "/src/assets/image/5.png" }, 
  { name: "Đồ lót", image: "/src/assets/image/1e.png" }, 
  { name: "Quần dài", image: "/src/assets/image/6.png" }, 
  { name: "Quần dài", image: "/src/assets/image/1g.png" }, 
  { name: "Quần short & chân váy", image: "/src/assets/image/7.png" }, 
  { name: "Quần short", image: "/src/assets/image/1f.png" },
];
const imgList = [
  "/src/assets/image/anh1.jpg",
  "/src/assets/image/anh2.png",
  "/src/assets/image/anh3.jpg"
];
</script>

<template>
<router-view />
 <Mainheader></Mainheader>
 <Navmenu></Navmenu>
 <Homebanner :images="imgList"></Homebanner>
 <CategorySlider :categories="categories" title="HÔM NAY MUA GÌ?" />
 <ProductList></ProductList>
<div class="space-y-12">
 <Homeview image="/src/assets/image/ab.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/bc.png"
    to="/product/1"/>
<Homeview image="/src/assets//image/cd.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/de.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/ef.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/gh.png"
    to="/product/1"/>
    </div>
<Mainfooter></Mainfooter>
</template>

