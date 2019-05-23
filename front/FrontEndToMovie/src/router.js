import Vue from "vue";
import Router from "vue-router";

import Home from "./pages/Home.vue"
import AdminRequests from "./pages/admin_panel/AdminRequestsPage.vue"
import AdminChangeLevel from "./pages/admin_panel/AdminChangePermissionsLevelPage"
import Search from "./pages/Search.vue"
import request from "./pages/FilmPage"
import nextDirector from "./pages/DirectorPage"
import Genre from "./pages/Genre"
import WishList from "./pages/WishList";
import History from "./pages/History";
import Random from "./pages/Random";
import EditorPage from "./pages/EditorPanelPage";

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
            path: '/admin/requests',
            name: 'admin-requests',
            component: AdminRequests
        },
        {
            path: '/admin/change',
            name: 'admin-change',
            component: AdminChangeLevel
        },
        {
            path: '/search/:query',
            component: Search
        },
        {
            path: '/film/:id',
            component: request
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
        },
        {
            path: '/editor/requests',
            component: EditorPage
        }
        
    ]
})