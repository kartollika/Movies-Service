<template>
  <div class="col-lg-10 mb-lg-auto align-items-center container">
    <md-card>
      <md-card-media>
        <!-- swiper -->
        <swiper :options="swiperOption">
          <swiper-slide><img src="img/posters/Джон Уик.jpg"></swiper-slide>
          <swiper-slide><img src="img/posters/Леон.jpg"></swiper-slide>
          <swiper-slide><img src="img/posters/Терминатор.jpg"></swiper-slide>
          <swiper-slide><img src="img/posters/Марсианин.jpg"></swiper-slide>
          <swiper-slide><img src="img/posters/Матрица.jpg"></swiper-slide>
          <swiper-slide><img src="img/posters/Неуязвимые.jpg"></swiper-slide>
          <div class="swiper-pagination" slot="pagination"></div>
          <div class="swiper-button-prev" slot="button-prev"></div>
          <div class="swiper-button-next" slot="button-next"></div>
        </swiper>
      </md-card-media>
    </md-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      swiperOption: {
        slidesPerView: 3,
        spaceBetween: 30,
        slidesPerGroup: 3,
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
    };
  }
};
</script>

<style>
.container {
  width: 100%
}
img {
  width: inherit
}
</style>
