<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <a class="navbar-brand" href="#"><img src="assets/logo.png" width="170" alt="navbar"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01"
        aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#">Главная</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Случайный фильм</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Новинки</a>
          </li>
        </ul>
        <a href="#" class="nav-link border border-light rounded" id="show-login-modal" @click="showLoginModal = true"
          title="Войти">
          <i class="fa fa-sign-in mr-2"></i>Войти</a>
      </div>
    </nav>
  </header>
</template>