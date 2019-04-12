import Vue from "vue";
import Router from "vue-router";

import Home from "@/pages/Home.vue"
import Admin from "@/pages/Admin.vue"
import SearchPage from "@/pages/SearchPage.vue"
import Film from "../src/pages/Film"
import Director from "../src/pages/Director"
import Genre from "../src/pages/Genre"

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
        },
        {
            path: '/search/:query',
            component: SearchPage
        },
        {
            path: '/film/:id',
            component: Film
        },
        {
            path: '/director/:id',
            component: Director
        },
        {
            path: '/genre/:name',
            component: Genre
        }
    ]
})