<template>
  <DefaultLayout>
    <template v-slot:header>
      <Header/>
    </template>
    <template v-slot:sidebar>
      <Sidebar/>
    </template>
    <template v-slot:content>
      <Swiper/>
    </template>
  </DefaultLayout>
</template>


<script>
import DefaultLayout from '../components/DefaultLayout.vue'
import Header from '../components/Header.vue'
import Sidebar from '../components/Sidebar.vue'
import Swiper from '../components/Swiper.vue'

export default {
  components: {
    DefaultLayout,
    Header,
    Sidebar,
    Swiper
  }
}
</script>

<style>
</style>