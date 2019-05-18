<template>
    <div class="container col-lg-10 mb-lg-auto align-items-center">
        <swiper :options="swiperOption" class="swiper-container">
            <swiper-slide v-for="film in films" :key="film.id"><a :href="/film/ + film.id"><img :src=film.poster></a></swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
            <div class="swiper-button-prev" slot="button-prev"></div>
            <div class="swiper-button-next" slot="button-next"></div>
        </swiper>
    </div>
</template>

<script>
    export default {
        props: {
            films: Array
        },
        data() {
            return {
                authorization: localStorage.getItem("Authorization"),
                swiperOption: {
                    slidesPerView: 6,
                    spaceBetween: 10,
                    slidesPerGroup: 6,
                    loop: true,
                    loopFillGroupWithBlank: true,
                    pagination: {
                        el: ".swiper-pagination",
                        clickable: true
                    },
                    navigation: {
                        nextEl: ".swiper-button-next",
                        prevEl: ".swiper-button-prev"
                    }
                }
            }
        }
    };
</script>

<style>
    .container.col-lg-10.mb-lg-auto.align-items-center {
        margin-left: 17px;
    }

    .swiper-container {
        width: 985px;
    }
    .swiper-container img {
        width: 157px;
        height: 230px;
        border-radius: 5px;
    }
</style>
