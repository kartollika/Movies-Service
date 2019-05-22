<template>
  <div class="item">
    <div class="item-poster-container">
      <a :href="/film/ + nextFilm.id">
        <img class="item-poster" :src="nextFilm.poster">
      </a>
    </div>
    <div class="item-content">
      <div class="wishlist-icon">
        <i class="fa fa-star-o" aria-hidden="true" v-if="!inWishList" @click="addWish"></i>
        <i class="fa fa-star" aria-hidden="true" v-else @click="deleteWish"></i>
      </div>
      <div class="item-title">
        <a :href="/film/ + nextFilm.id">
          <b>{{title}}</b>
        </a>
      </div>
      <br>
      <br>
      <div class="item-description">
        <div>
          <b>Год:</b>
          {{nextFilm.year}}
        </div>
        <div>
          <b>Страна:</b>
          {{nextFilm.country}}
        </div>
        <div>
          <b>Жанр:</b>
          {{genres}}
        </div>
        <div>
          <b>Режиссер:</b>
          <span
            class="film-directors"
            v-for="(director, index) in nextFilm.directors"
            :key="director.id"
          >
            <span
              v-if="index < (maxDirectors < nextFilm.directors.length ? maxDirectors : nextFilm.directors.length) - 1"
            >&nbsp;
              <a :href="/director/ + director.id">{{director.name}}</a>,
            </span>
            <span
              v-else-if="index === (maxDirectors < nextFilm.directors.length ? maxDirectors : nextFilm.directors.length) - 1"
            >&nbsp;
              <a :href="/director/ + director.id">{{director.name}}</a>
              <span v-if="maxDirectors < nextFilm.directors.length">, ...</span>
            </span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "FilmContainer",
  props: ["nextFilm"],
  data() {
    return {
      inWishList: false,
      maxDirectors: 2,
      maxGenres: 2
    };
  },
  computed: {
    title: function() {
        if (this.nextFilm.title.length > 40) {
            return this.nextFilm.title.substr(0, 40) + '...';
        }
        return this.nextFilm.title;
    },
    genres: function() {
        var genres = this.nextFilm.genre.split(', ').slice(0, this.maxGenres).join(', ');
        if (this.nextFilm.genre.length > this.maxGenres) {
            genres += ', ...';
        }
        return genres;
    }
  },
  mounted() {
    axios.defaults.headers = {
      "Content-Type": "application/json",
      Authorization: this.authorization
    };
    axios
      .get(this.url + "/api/account/wishlist/contains", {
        params: {
          filmId: this.nextFilm.id
        }
      })
      .then(response => {
        this.inWishList = response.data;
      });
  },

  methods: {
    deleteWish() {
      axios
        .delete(this.url + "/api/account/wishlist", {
          params: {
            filmId: this.nextFilm.id
          }
        })
        .then(() => {
          this.inWishList = false;
          this.$emit("updateWishlist", true);
        });
    },

    addWish() {
      axios
        .put(this.url + "/api/account/wishlist?filmId=" + this.nextFilm.id)
        .then(() => {
          this.inWishList = true;
        });
    }
  }
};
</script>

<style scoped>
.wishlist-icon {
  float: right;
  font-size: 18px;
  color: #e378ca;
}
.wishlist-icon:hover {
  cursor: pointer;
}
</style>