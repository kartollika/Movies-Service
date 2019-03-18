
var app = new Vue({
        el: '#login',
        data: {
            showLoginModal: false,
        }
    })

Vue.component('modal', {
    template: '#login-modal'
})
