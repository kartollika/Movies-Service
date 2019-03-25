<template>
  <div>
    <base-button block type="default" class="mb-3" @click="login = true">Войти</base-button>
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
              addon-left-icon="ni ni-email-83"
            ></base-input>
            <base-input
              alternative
              type="password"
              placeholder="Пароль"
              addon-left-icon="ni ni-lock-circle-open"
            ></base-input>
            <base-checkbox>Запомнить меня</base-checkbox>
            <div class="text-center">
              <base-button type="primary" class="my-4">Войти</base-button>
            </div>
          </form>
        </template>
      </card>
    </modal>
  </div>
</template>

<script>
import BaseButton from "@/components/BaseButton.vue"
import CloseButton from "@/components/CloseButton.vue"
import BaseInput from "@/components/BaseInput.vue"
import BaseCheckbox from "@/components/BaseCheckbox.vue"
import Modal from "@/components/Modal.vue"
export default {
  components: {
    Modal
  },
  data() {
    return {
      login: false
    };
  }
};
</script>

<style>
</style>