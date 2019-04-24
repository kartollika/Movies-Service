<template>
  <div>
    <div id="sidebar-container" class="sidebar-expanded d-none d-md-block">
      <ul class="list-group">
        <li
          class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed sidebar-section"
        >ГЛАВНАЯ СТРАНИЦА</li>
        <div class="sidebar-menu">
          <a href="#" class="bg-dark list-group-item list-group-item-action">
            <div class="d-flex w-100 justify-content-start align-items-center">
              <span class="fa fa-tasks fa-fw mr-3"></span>
              <span>История просмотров</span>
            </div>
          </a>
          <a href="#" class="bg-dark list-group-item list-group-item-action">
            <div class="d-flex w-100 justify-content-start align-items-center">
              <span class="fa fa-tasks fa-fw mr-3"></span>
              <span>Список желаний</span>
            </div>
          </a>
        </div>
        <li
          class="list-group-item sidebar-separator-title text-muted d-flex align-items-center menu-collapsed sidebar-section"
        >
          <span>ЖАНРЫ</span>
        </li>
        <div class="sidebar-submenu">
          <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
            <span>Экшн</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
            <span>Аниме</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action bg-dark text-white">
            <span>Комедии</span>
          </a>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
</script>

<style>
#sidebar-container {
  min-height: 100vh;
  background-color: #172b4d;
  padding: 0;
}

.sidebar-expanded {
  width: 230px;
}
/* Menu item*/
#sidebar-container .list-group a {
  height: 50px;
  color: white;
}
/* Submenu item*/
#sidebar-container .list-group .sidebar-submenu a {
  height: 45px;
  padding-left: 30px;
}
#sidebar-container .list-group .list-group-item {
  border: none !important;
}
.sidebar-submenu {
  font-size: 0.9rem;
}
.sidebar-submenu a {
  background-color: #0c192e !important;
}
.sidebar-menu a {
  background-color: #0c192e !important;
}
/* Separators */
.sidebar-separator-title {
  background-color: #0c192e !important;
  border: none !important;
  height: 35px;
}
.sidebar-separator {
  background-color: #0c192e;
  height: 25px;
}
.logo-separator {
  background-color: #0c192e;
  height: 60px;
}
</style>
