<template>
    <div>
        <div class="item-poster-container">
            <a :href="/film/ + nextFilm.id"><img class="item-poster" :src=nextFilm.poster></a>
        </div>
        <div class="item-content">
            <div class="item-title">
                <a :href="/film/ + nextFilm.id"><b>{{nextFilm.title}}</b></a>
            </div>
            <br><br>
            <div class="item-description">
                <div><b>Год:</b> {{nextFilm.year}}</div>
                <div><b>Страна:</b> {{nextFilm.country}}</div>
                <div><b>Жанр:</b> {{nextFilm.genre}}</div>
                <div><b>Режиссер: </b>
                    <span class="film-directors" v-for="(director, index) in nextFilm.directors" :key="director.id">
                        <span v-if="index !== nextFilm.directors.length - 1">
                            <a :href="/director/ + director.id">{{director.name}}</a>,
                        </span>
                        <span v-else>
                            <a :href="/director/ + director.id">{{director.name}}</a>
                        </span>
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "FilmContainer",
        props: ['nextFilm']
    }
</script>

<style scoped>

</style>