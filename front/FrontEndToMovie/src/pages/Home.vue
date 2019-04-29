<template>
    <div>
        <Header/>
        <div class="content-container">
            <div class="content">
            <h3>Новинки</h3>
            <Swiper class="swiper"></Swiper>
            </div>
        </div>
    </div>
</template>

<script>
    import Swiper from '../components/Swiper.vue'

    export default {
        components: {
            Swiper
        },
        data() {
            return {
                host: ''
            }
        }
    }
</script>

<style>
</style>