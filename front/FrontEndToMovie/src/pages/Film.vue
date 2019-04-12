<template>
    <div>
        <Header></Header>
        <div class="content">
            <h3>{{film.title}}</h3>
            <img class="poster-lg" src="../../public/img/posters/Марсианин.jpg">
            <div class="description">
                <div class="description-item"><b>Год: </b> {{film.year}}</div><hr>
                <div class="description-item"><b>Страна: </b> {{film.country}}</div><hr>
                <div class="description-item"><b>Жанр: </b> {{film.genre}}</div><hr>
                <div><div class="description-item"><b>Режиссер: </b> <span class="directors" v-for="director in film.directors">{{director.name}}</span></div></div><hr>
                <div class="description-item"><b>Дата выхода: </b> {{film.release}}</div><hr>
                <div class="description-item"><b>Бюджет: </b> {{film.budget}}</div><hr>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "Film",
        data() {
            return {
                film: '',
                authorization: localStorage.getItem("Authorization")
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get("http://localhost:8080/api/films/film/" + this.$route.params.id).then((response) => {
                this.film = response.data;
                this.film.year = this.film.year.substring(0, 4);
                this.film.release = this.film.release.substring(0, 10);
            })
        }
    }
</script>
p
<style scoped>
    .description {
        margin-top: 25px;
        font-size: 15px;
    }
    .directors {
        position: relative;
        float: right;
        margin-right: 52%;
    }
</style>