<template>
    <div>
        <Header/>
        <div class="content-container">
            <div class="content">
                <h3 class="home-h3">Новинки</h3>
                <Swiper></Swiper>
            </div>
        </div>
    </div>
</template>

<script>
    import Swiper from '../components/swiper/Swiper.vue'

    export default {
        components: {
            Swiper
        },
        mounted() {
            document.title = "Главная";
        }
    }
</script>

<style>
    .home-h3 {
        margin-left: 30px;
    }
</style>