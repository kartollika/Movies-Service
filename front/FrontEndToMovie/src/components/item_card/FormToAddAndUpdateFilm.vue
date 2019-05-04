<template>
    <div>
        <div class="film-poster-container">
            <img class="film-poster" :src=film.poster>
        </div>
        <div class="film-content">
            <form>
                <label>
                    Название фильма<br>
                    <input type="text" v-model="film.title" style="width: 395px">
                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label>
                    Жанр<br>
                    <input type="text" v-model="film.genre" style="width: 150px">
                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label>
                    Страна<br>
                    <input type="text" v-model="film.country" style="width: 150px">
                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label>
                    Год<br>
                    <input type="text" v-model="film.year" style="width: 60px">
                </label><br>
                <label>
                    Дата выхода<br>
                    <input type="Date" placeholder="Дата выхода"
                           v-model="film.release">
                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label>
                    Ссылка на постер<br>
                    <input type="text" v-model="film.poster" style="width: 659px">
                </label><br>
                <label>
                    Актеры<br>
                    <input type="text"  v-model="film.actors" style="width: 835px">
                </label><br>
                <label>
                    Режиссер<br>
                    <input type="text" size="50">
                </label><br>
                <label>
                    Описание<br>
                    <textarea v-model="film.description" style="width: 835px; height: 78px"></textarea>
                </label>
            </form>
            <div class="film-form-buttons">
                <base-button class="add-film-button" type="success" size="sm" @click="save">Сохранить</base-button>
                <base-button size="sm" type="info" @click="clear">Очистить</base-button>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "FormToAddAndUpdateFilm",
        props: {
            film: {
                type: Object,
                default() {
                    return {
                        title: '',
                        country: '',
                        description: '',
                        poster: '',
                        genre: '',
                        year: '',
                        release: '',
                        actors: '',
                        // directors: []
                    }
                }
            },
            type: {
                type: String,
                default: "add"
            }
        },

        methods: {
            save() {
                if (this.type === "add") {
                    this.film.year = this.film.year + "-01-01";
                    axios.post(this.url + "/api/films/", this.film).then(() => {
                        this.clear();
                    });
                } else if (this.type === "update") {
                    this.film.year = this.film.year + "-01-01";
                    axios.put(this.url + "/api/films/film/" + this.film.id, this.film).then(() => {
                        this.clear();
                        window.location.reload();
                    })
                }
            },

            clear() {
                this.film.poster = '';
                this.film.title = '';
                this.film.country = '';
                this.film.genre = '';
                this.film.release = '';
                this.film.year = '';
                this.film.description = '';
                this.film.actors = '';
                this.film.directors = ''
            },
        }
    }
</script>

<style scoped>
    .film-poster-container {
        float: left;
    }

    .film-poster {
        width: 270px;
        height: 389px;
        border-radius: 5px;
        margin-top: 10px;
    }

    .film-content {
        margin-top: 10px;
        width: 865px;
        height: 390px;
        background-color: white;
        float: left;
        border-radius: 5px;
        margin-left: 10px;
        padding-left: 15px;
        padding-top: 15px;
    }

    .film-form-buttons {
        float: right;
        margin-right: -9px;
        margin-top: 11px;
    }

    .add-film-button {
        float: right;
        margin-left: 5px;
    }

</style>