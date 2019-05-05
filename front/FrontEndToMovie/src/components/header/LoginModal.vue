<template>
  <div>
    <div class="auth-buttons">
      <base-button type="primary" size="sm" class="mb-3" @click="login = true">Вход</base-button>
      <base-button type="primary" size="sm" class="mb-3" @click="registration = true">Регистрация</base-button>
    </div>
    <modal :show.sync="login" body-classes="p-0" modal-classes="modal-dialog-centered modal-sm">
      <card
        type="secondary"
        shadow
        header-classes="bg-white pb-5"
        body-classes="px-lg-5 py-lg-5"
        class="border-0"
      >
        <template>
          <div class="text-center text-muted mb-4">
            <small>Вход</small>
          </div>
          <form role="form" @submit.prevent="loginForm">
            <base-input
              alternative
              class="mb-3"
              placeholder="Логин"
              v-model="username"
              addon-left-icon="ni ni-email-83"
            ></base-input>
            <base-input
              alternative
              type="password"
              placeholder="Пароль"
              addon-left-icon="ni ni-lock-circle-open"
              v-model="password"
            ></base-input>
            <base-alert type="warning" v-show="error">
              <strong>Error!</strong> Неверное имя пользователя или пароль
            </base-alert>
            <div class="text-center">
              <base-button @click="loginForm" native-type="submit" type="primary" size="lg" class="my-4">Войти</base-button>
            </div>
          </form>
        </template>
      </card>
    </modal>
    <modal
      :show.sync="registration"
      body-classes="p-0"
      modal-classes="modal-dialog-centered modal-sm"
    >
      <card
        type="secondary"
        shadow
        header-classes="bg-white pb-5"
        body-classes="px-lg-5 py-lg-5"
        class="border-0"
      >
        <template>
          <div class="text-muted text-center mb-3">
            <small>Регистрация</small>
          </div>
        </template>
        <template>
          <form role="form" @submit.prevent="registrationForm">
            <base-input alternative class="mb-3" placeholder="Имя" v-model="newUser.name"></base-input>
            <base-alert type="warning" v-show="nameError">
              <strong>Ошибка!</strong> Недопустимое имя
            </base-alert>
            <base-input alternative class="mb-3" placeholder="Логин" v-model="newUser.username"></base-input>
            <base-alert type="warning" v-show="usedUsernameError">
              <strong>Ошибка!</strong> Логин занят
            </base-alert>
            <base-alert type="warning" v-show="usernameError">
              <strong>Ошибка!</strong> Недопустимый логин
            </base-alert>
            <base-input alternative type="password" placeholder="Пароль" v-model="newUser.password"></base-input>
            <base-alert type="warning" v-show="passwordError">
              <strong>Ошибка!</strong> Пароль должен быть не короче 8 символов
            </base-alert>
            <base-input
              alternative
              type="password"
              placeholder="Повторите пароль"
              v-model="checkPassword"
            ></base-input>
            <base-alert type="warning" v-show="checkPasswordError">
              <strong>Ошибка!</strong> Пароли не совпадают
            </base-alert>
            <div class="text-center">
              <base-button
                @click="registrationForm"
                native-type="submit"
                type="primary"
                size="lg"
                class="my-4"
              >Зарегистрироваться</base-button>
            </div>
          </form>
        </template>
      </card>
    </modal>
  </div>
</template>

<script>
import Modal from "../base_components/Modal.vue";
import axios from "axios";

export default {
  components: {
    Modal
  },
  data() {
    return {
      login: false,
      registration: false,
      username: "",
      password: "",
      info: "",
      error: false,
      newUser: {
        name: "",
        username: "",
        password: "",
        permissionsLevel: "USER"
      },
      checkPassword: "",
      checkPasswordError: false,
      passwordError: false,
      usernameError: false,
      emptyUsernameError: false,
      nameError: false
    };
  },
  methods: {
    loginForm() {
      this.error = false;
      axios
        .get(this.url + "/login", {
          params: {
            username: this.username,
            password: this.password
          }
        })
        .then(response => {
          localStorage.setItem("Authorization", response.headers.authorization);
          location.reload();
        })
        .catch(() => {
          this.error = true;
        });
    },
    registrationForm() {
      axios.defaults.headers = {
        "Content-Type": "application/json",
        accept: "*/*"
      };
      this.checkPasswordError = false;
      this.passwordError = false;
      this.usedUsernameError = false;
      this.usernameError = false;
      this.nameError = false;

      if (this.newUser.name === "") {
        this.nameError = true;
      } else if (
        this.newUser.username === "" ||
        !!/[^a-zA-Z0-9]/.test(this.newUser.username)
      ) {
        this.usernameError = true;
      } else if (this.newUser.password.length < 8) {
        this.passwordError = true;
      } else if (this.newUser.password !== this.checkPassword) {
        this.checkPasswordError = true;
        this.checkPassword = "";
      } else {
        axios.post(this.url + "/api/account", this.newUser).then(response => {
          this.username = this.newUser.username;
          this.password = this.newUser.password;
          if (response.status === 201) {
            this.loginForm();
          } else {
            this.usedUsernameError = true;
          }
        });
      }
    }
  }
};
</script>

<style>
.auth-buttons {
  position: absolute;
  float: right;
  right: 50px;
  bottom: 5px;
}
</style>