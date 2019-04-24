<template>
  <DefaultLayout>
    <template v-slot:header>
      <Header/>
    </template>
    <template v-slot:sidebar>
      <Sidebar/>
    </template>
  </DefaultLayout>
</template>

<script>
import DefaultLayout from "../components/DefaultLayout.vue"
import Header from "../components/Header.vue";
import Sidebar from "../components/Sidebar.vue"

export default {
  components: {
    DefaultLayout,
    Header,
    Sidebar,
  }
};
</script>

<style>
</style>