import store from '../store/'
import VueSocketIO from 'vue-socket.io'

export default (token) =>
  new VueSocketIO({
    debug: true,
    connection: process.env.VUE_APP_SOCKET_URL,
    vuex: {
      store,
      actionPrefix: 'SOCKET_',
      mutationPrefix: 'SOCKET_',
    },
    options: {
      path: process.env.VUE_APP_SOCKET_PATH || '',
      query: { token: token },
      autoConnect: false,
      timeout: 10000,
    },
  })
