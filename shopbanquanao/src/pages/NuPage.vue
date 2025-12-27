<script setup>
import { ref, onMounted } from "vue"
import api from "@/api"
import CategorySlider from '@/components/home/CategorySlider.vue';
import Homeview from '@/components/home/Homeview.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import ProductList from '@/components/product/ProductList.vue';
const imgList=[
"/src/assets/image/Bannernu.png"
];
const products = ref([])
const categories=[
{
    name:"Áo Khoác",image:"/src/assets/image/nu1.png"},
    {name:"Quần dài & short",image:"/src/assets/image/nu5.png"},
    {name:"Áo chống nắng",image:"/src/assets/image/nu3.png"},
    {name:"Đầm & chân váy",image:"/src/assets/image/nu6.png"},
    {name:"Áo giữ nhiệt",image:"/src/assets/image/nu2.png"},
    {name:"Đồ lót",image:"/src/assets/image/nu7.png"},
    {name:"Áo polo & thun",image:"/src/assets/image/nu4.png"
}

];
onMounted(async () => {
  try {
    const res = await api.get("/products/by-doi-tuong?doiTuong=Nữ")
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
 <Homeview image="/src/assets/image/b1nu.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b2nu.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b4nu.png"
    to="/product/1"/>
<Homeview image="/src/assets/image/b3nu.png"
    to="/product/1"/>
    </div>
</template>