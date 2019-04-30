import Vue from "vue";
import Router from "vue-router";

import Home from "@/pages/Home.vue"
import Admin from "@/pages/AdminPage.vue"
import Search from "@/pages/Search.vue"
import nextFilm from "./pages/FilmPage"
import nextDirector from "./pages/DirectorPage"
import Genre from "../src/pages/Genre"
import WishList from "./pages/WishList";
import History from "./pages/History";
import Random from "./pages/Random";

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
            component: Search
        },
        {
            path: '/film/:id',
            component: nextFilm
        },
        {
            path: '/director/:id',
            component: nextDirector
        },
        {
            path: '/genre/:name',
            component: Genre
        },
        {
            path: '/wishlist',
            component: WishList
        },
        {
            path: '/history',
            component: History
        },
        {
            path: '/random',
            component: Random
        }
        
    ]
})