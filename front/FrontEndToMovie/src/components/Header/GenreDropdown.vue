<template>
  <base-dropdown>
    <a slot="title" class="dropdown-toggle nav-link nav-link-icon">Жанры</a>
    <a class="dropdown-item " href="/genre/экшн/">Экшн</a>
    <a class="dropdown-item" href="/genre/аниме/">Аниме</a>
    <a class="dropdown-item" href="/genre/comedy/">Комедии</a>
    <a class="dropdown-item" href="/genre/horror/">Ужасы</a>
    <a class="dropdown-item" href="/genre/фантастика/">Фантастика</a>
    <a class="dropdown-item" href="/genre/фентези/">Фэнтэзи</a>
  </base-dropdown>
</template>

<script>
import BaseDropdown from "../Base/BaseDropdown.vue"
export default {
  components: {
    BaseDropdown
  }
}
</script>

<style>

</style>
