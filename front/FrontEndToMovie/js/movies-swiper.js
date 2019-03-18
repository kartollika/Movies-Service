//import Vue from 'vue'

//import BootstrapVue from 'bootstrap-vue'
//import 'bootstrap/dist/css/bootstrap.css'
//import 'bootstrap-vue/dist/bootstrap-vue.css'
//Vue.use(BootstrapVue);

//import BCarousel from 'bootstrap-vue/es/components/carousel/carousel'
//Vue.component('b-carousel', BCarousel)

//import ex from './slide.vue'

Vue.use(VueAwesomeSwiper)
new Vue({
    el: '#movies-swiper',
    components: {
      LocalSwiper: VueAwesomeSwiper.swiper,
      LocalSlide: VueAwesomeSwiper.swiperSlide,
    },
    data: {
      films: [],
      moviesSwiperOptions: {
        slidesPerView: 3,
        spaceBetween: 30,
        slidesPerGroup: 3,
        loop: true,
        loopFillGroupWithBlank: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      }
    },
    computed: {
      swiper() {
        return this.$refs.moviesSwiper.swiper
      }
    },
    mounted() {
      axios.get('http://localhost:8080/film').then(response => (this.films = response.data));
    },
    methods: {
      onSetTranslate() {
        console.log('onSetTranslate')
      },
      getUrl(url) {
        return "img/" + url + ".jpg"
      }
    }
  })

