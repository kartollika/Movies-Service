<template>
  <div>
    <Header></Header>
    <div class="content-container">
      <div class="content">
        <h4>Случайный фильм:</h4>
        <div>
          <div class="item-poster-container">
            <a :href="/film/ + film.id">
              <img class="item-poster" src="../../public/img/posters/Марсианин.jpg">
            </a>
          </div>
          <div class="item-content">
            <div class="item-title">
              <a :href="/film/ + film.id">
                <b>{{film.title}}</b>
              </a>
            </div>
            <br>
            <br>
            <div class="item-description">
              <div>
                <b>Год:</b>
                {{film.year}}
              </div>
              <div>
                <b>Страна:</b>
                {{film.country}}
              </div>
              <div>
                <b>Жанр:</b>
                {{film.genre}}
              </div>
              <div>
                <b>Режиссер:</b>
                <span
                  class="film-directors"
                  v-for="(director, index) in film.directors"
                  :key="director.id"
                >
                  <span v-if="index !== film.directors.length - 1">
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
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Random",
  data() {
    return {
      randomEmpty: true,
      authorization: localStorage.getItem("Authorization"),
      film: [],
    };
  },
  mounted() {
    axios.defaults.headers = {
      "Content-Type": "application/json",
      Authorization: this.authorization
    };

    this.getRandom();
  },
  methods: {
    getRandom() {
      axios.get(this.url + "/random").then(response => {
        console.log(response.data)
        this.film = response.data;
        if (this.film.length !== 0) {
          this.randomEmpty = false;
        }
      });
    }
  }
};
</script>

<style scoped>
</style>