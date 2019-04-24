<template>
  <div>
    <Header></Header>
    <div class="content">
      <div v-if="!historyEmpty">
        <h4>Просмотренные фильмы:</h4>
        <div class="search-result">
          <div v-for="film in films" :key="film.id">
            <card class="film-card">
              <div>
                <div>
                  <img class="poster-sm" src="../../public/img/posters/Марсианин.jpg">
                </div>
                <div class="search-item-title">
                  <a :href="/film/ + film.id">
                    <b>{{film.title}}</b>
                  </a>
                </div>
                <div class="search-description">
                  <div class="description-item">
                    <b>Год:</b>
                    {{film.year}}
                  </div>
                  <div class="description-item">
                    <b>Страна:</b>
                    {{film.country}}
                  </div>
                  <div class="description-item">
                    <b>Жанр:</b>
                    {{film.genre}}
                  </div>
                  <div class="description-item">
                    <b>Режиссер:</b>
                  </div>
                </div>
              </div>
            </card>
          </div>
        </div>
      </div>
      <div v-else>
        <h4>Список пуст</h4>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "History",
  data() {
    return {
      historyEmpty: true,
      authorization: localStorage.getItem("Authorization"),
      films: [],
      pagination: {
        default: 1
      }
    };
  },
  mounted() {
    axios.defaults.headers = {
      "Content-Type": "application/json",
      Authorization: this.authorization
    };

    this.getHistory();
  },
  methods: {
    delEntry(id) {
      axios
        .delete(this.url + "/api/account/history", {
          params: {
            filmId: id
          }
        })
        .then(() => {
          this.getHistory();
        });
    },

    getHistory() {
      axios.get(this.url + "/api/account/history").then(response => {
        this.films = response.data;
        if (this.films.length !== 0) {
          this.historyEmpty = false;
        }
        this.films.forEach(function(film) {
          film.year = film.year.substring(0, 4);
          film.release = film.release.substring(0, 10);
        });
      });
    }
  }
};
</script>

<style scoped>
.delete-entry {
  float: right;
}
</style>