<template>
  <div style="position: relative">
    <base-nav type="default" effect="dark" expand>
      <a class="navbar-brand" href="#">Movies Service</a>

      <ul class="navbar-nav ml-lg-auto">
        <li class="nav-item">
          <a class="nav-link nav-link-icon" href="#">Главая</a>
        </li>
        <li class="nav-item">
          <a class="nav-link nav-link-icon" href="#">Случайный фильм</a>
        </li>
        <li class="nav-item">
          <a class="nav-link nav-link-icon" href="#">Новинки</a>
        </li>
        <li><login-modal></login-modal></li>
      </ul>
    </base-nav>
  </div>
</template>

<script>
import BaseNav from "@/components/BaseNav";
import LoginModal from "@/components/LoginModal";

export default {
  components: {
    BaseNav,
    LoginModal
  }
};
</script>

<style>
</style>
