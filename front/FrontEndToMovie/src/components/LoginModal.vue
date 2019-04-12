<template>
    <div>
        <div class="auth-buttons">
            <base-button type="primary" size="sm" class="mb-3" @click="login = true">Вход</base-button>
            <base-button type="primary" size="sm" class="mb-3" @click="registration = true">Регистрация</base-button>
        </div>
        <modal
                :show.sync="login"
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
                        <small>Войти через</small>
                    </div>
                    <div class="btn-wrapper text-center">
                        <base-button type="neutral">
                            <img slot="icon" src="img/icons/common/github.svg">
                            Github
                        </base-button>

                        <base-button type="neutral">
                            <img slot="icon" src="img/icons/common/google.svg">
                            Google
                        </base-button>
                    </div>
                </template>
                <template>
                    <div class="text-center text-muted mb-4">
                        <small>Или с помощью почты</small>
                    </div>
                    <form role="form">
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
                            <base-button @click="loginForm" type="primary" size="lg" class="my-4">Войти</base-button>
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
                    <form role="form">
                        <base-input
                                alternative
                                class="mb-3"
                                placeholder="Имя"
                                v-model="newUser.name"
                        ></base-input>
                        <base-input
                                alternative
                                class="mb-3"
                                placeholder="Логин"
                                v-model="newUser.username"
                        ></base-input>
                        <base-alert type="warning" v-show="usernameError">
                            <strong>Ошибка!</strong> Логин занят
                        </base-alert>
                        <base-input
                                alternative
                                type="password"
                                placeholder="Пароль"
                                v-model="newUser.password"
                        ></base-input>
                        <base-alert type="warning" v-show="passwordError">
                            <strong>Ошибка!</strong> Недопустимый пароль
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
                            <base-button @click="registrationForm" type="primary" size="lg" class="my-4">
                                Зарегистрироваться
                            </base-button>
                        </div>
                    </form>
                </template>
            </card>
        </modal>
    </div>
</template>

<script>
    import Modal from "@/components/Modal.vue"
    import axios from 'axios'

    export default {
        components: {
            Modal
        },
        data() {
            return {
                login: false,
                registration: false,
                username: '',
                password: '',
                info: '',
                error: false,
                newUser: {
                    name: '',
                    username: '',
                    password: '',
                    permissionsLevel: 'USER',
                },
                checkPassword: '',
                checkPasswordError: false,
                passwordError: false,
                usernameError: false

            };
        },
        methods: {
            loginForm() {
                this.error = false;
                axios.get("http://localhost:8080/login", {
                    params: {
                        username: this.username,
                        password: this.password
                    }
                }).then((response) => {
                    localStorage.setItem("Authorization", response.headers.authorization);
                    location.reload();
                }).catch((e) => {
                    this.error = true
                })
            },

            registrationForm() {
                axios.defaults.headers = {
                    'Content-Type': 'application/json',
                    'accept' : '*/*'
                };
                this.checkPasswordError = false;
                this.passwordError = false;
                this.usernameError = false;

                if (this.newUser.password !== this.checkPassword) {
                    this.checkPasswordError = true;
                    this.newUser.password = '';
                    this.checkPassword = '';
                } else if (this.newUser.password === '') {
                    this.passwordError = true;
                } else {
                    axios.post("http://localhost:8080/api/account", this.newUser).then((response) => {
                        this.username = this.newUser.username;
                        this.password = this.newUser.password;
                        if (response.status === 201) {
                            this.loginForm();
                        } else {
                            this.usernameError = true;
                        }
                    }).catch((e) => {
                    })
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