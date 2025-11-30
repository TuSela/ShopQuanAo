<script setup>
import Mainfooter from '@/components/footer/Mainfooter.vue';
import Mainheader from '@/components/header/Mainheader.vue';
import Navmenu from '@/components/header/Navmenu.vue';
import CategorySlider from '@/components/home/Category Slider.vue';
import Homeview from '@/components/home/Homeview.vue';
import Homebanner from '@/components/home/Homebanner.vue';
import ProductList from '@/components/product/ProductList.vue';
const imgList=[
"/src/assets/image/Bannernam.png"
];
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

</script>
<template>
    <Mainheader></Mainheader>
 <Navmenu></Navmenu>
<Homebanner :images ="imgList"></Homebanner>
<CategorySlider :categories="categories" title="HOT NHẤT TẠI ĐÂY!"></CategorySlider>
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
<Mainfooter></Mainfooter>
</template>