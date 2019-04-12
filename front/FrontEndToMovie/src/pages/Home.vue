<template>
    <div>
        <Header/>
        <div class="content">
            <h3>Новинки</h3>
            <Swiper class="swiper"></Swiper>
        </div>

    </div>
</template>

<script>
    import DefaultLayout from '../components/DefaultLayout.vue'
    import Swiper from '../components/Swiper.vue'
    import Sidebar from '../components/Sidebar.vue'

    export default {
        components: {
            Swiper,
            Sidebar,
            DefaultLayout
        }
    }
</script>

<style>
    .swiper {
    }
    .sidebar {
        float: left;
    }
</style>