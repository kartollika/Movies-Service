import Vue from "vue";
import Router from "vue-router";

import Home from "@/pages/Home.vue"
import Admin from "@/pages/Admin.vue"
import SearchPage from "@/pages/Search.vue"
import Film from "../src/pages/Film"
import Director from "../src/pages/Director"
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
            component: Home,
            meta: { title: 'Главная страница' }
        },
        {
            path: '/admin',
            name: 'admin',
            component: Admin,
            meta: { title: 'Панель админа' }
        },
        {
            path: '/search/:query',
            component: SearchPage,
            meta: { title: 'Поиск' }
        },
        {
            path: '/film/:id',
            component: Film,
            meta: { title: 'Фильм' }
        },
        {
            path: '/director/:id',
            component: Director,
            meta: { title: 'Режиссер' }
        },
        {
            path: '/genre/:name',
            component: Genre,
            meta: { title: 'Жанр' }
        },
        {
            path: '/wishlist',
            component: WishList,
            meta: { title: 'Список желаний' }
        },
        {
            path: '/history',
            component: History,
            meta: { title: 'Главная страница' }
        },
        {
            path: '/random',
            component: Random,
            meta: { title: 'Случайный фильм' }
        }
        
    ]
})