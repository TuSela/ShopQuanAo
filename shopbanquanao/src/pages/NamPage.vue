<script setup>
import { ref, onMounted } from "vue"
import api from "@/api"
import CategorySlider from '@/components/home/CategorySlider.vue';
import Homeview from '@/components/home/Homeview.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import ProductList from '@/components/product/ProductList.vue';
const imgList=[
"/src/assets/image/Bannernam.png"
];
const products = ref([])
const categories=[
{
    name:"Áo Khoác",image:"/src/assets/image/nam1.png"},
    {name:"Áo giữ nhiệt",image:"/src/assets/image/nam2.png"},
    {name:"Áo chống nắng",image:"/src/assets/image/nam3.png"},
    {name:"Áo polo & thun",image:"/src/assets/image/nam4.png"},
    {name:"Áo điều hòa làm mát",image:"/src/assets/image/nam5.png"},
    {name:"Quần dài",image:"/src/assets/image/nam6.png"},
    {name:"Quần short",image:"/src/assets/image/nam7.png"},
    {name:"Đồ lót",image:"/src/assets/image/nam8.png"
}

];
onMounted(async () => {
  try {
    const res = await api.get("/products/by-doi-tuong?doiTuong=Nam")
    products.value = res.data.result
  } catch (e) {
    console.error("Load products failed", e)
  }
})
</script>
<template>
<Homebanner :images ="imgList"></Homebanner>
<CategorySlider :categories="categories" title="HOT NHẤT TẠI ĐÂY!"></CategorySlider>

 <ProductList :products="products" />
<div class="space-y-12">
 <Homeview image="/src/assets/image/b1nam.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b2nam.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b3nam.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b4nam.png"
    to="/product/1"/>
    </div>
</template>