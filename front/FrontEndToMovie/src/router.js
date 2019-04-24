import Vue from "vue";
import Router from "vue-router";

import Home from "@/pages/Home.vue"
import Admin from "@/pages/Admin.vue"

Vue.use(Router);

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin
    }
  ]
})