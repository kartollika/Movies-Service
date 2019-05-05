<template>
  <base-dropdown>
    <a slot="title" class="dropdown-toggle nav-link nav-link-icon">Жанры</a>
    <a class="dropdown-item " href="/genre/action/">Экшн</a>
    <a class="dropdown-item" href="/genre/crime/">Криминал</a>
    <a class="dropdown-item" href="/genre/comedy/">Комедии</a>
    <a class="dropdown-item" href="/genre/horror/">Ужасы</a>
    <a class="dropdown-item" href="/genre/drama/">Драма</a>
    <a class="dropdown-item" href="/genre/fantasy/">Фантастика</a>
    <a class="dropdown-item" href="/genre/mystery/">Мистика</a>
    <a class="dropdown-item" href="/genre/thriller/">Триллер</a>
    <a class="dropdown-item" href="/genre/biography/">Биография</a>
    <a class="dropdown-item" href="/genre/history/">Историческое</a>
    <a class="dropdown-item" href="/genre/animation/">Мультфильмы</a>
  </base-dropdown>
</template>

<script>
  import BaseDropdown from "../base_components/BaseDropdown.vue"
  export default {
    components: {
      BaseDropdown
    }
  }
</script>

<style>
</style>
