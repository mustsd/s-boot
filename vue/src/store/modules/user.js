import Vue from 'vue'
import { login, getInfo, logout } from '@/api/login'
import { ACCESS_TOKEN, SYSTEM_DICT } from '@/store/mutation-types'
import { welcome } from '@/utils/util'
import socketIo from '../../socket/'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {},
    buttons: [],
    unread: 0,
  },

  mutations: {
    SET_UNREAD: (state, unread) => {
      state.unread = unread
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_BUTTONS: (state, buttons) => {
      state.buttons = buttons
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo)
          .then((res) => {
            if (res.success) {
              const result = res.result
              Vue.ls.set(ACCESS_TOKEN, result.token, 7 * 24 * 60 * 60 * 1000)
              commit('SET_TOKEN', result.token)

              resolve()
            } else {
              reject(res)
            }
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo()
          .then((res) => {
            if (res.success) {
              const result = res.result
              commit('SET_INFO', result)
              commit('SET_NAME', { name: result.name, welcome: welcome() })
              commit('SET_AVATAR', result.avatar)
              commit('SET_UNREAD', result.unread)
              Vue.use(socketIo(state.token))
              resolve(res)
            } else {
              reject(new Error('获取用户信息失败'))
            }
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

    // 登出
    Logout({ commit, state }) {
      return new Promise((resolve) => {
        logout()
          .then(() => {
            resolve()
          })
          .catch(() => {
            resolve()
          })
          .finally(() => {
            commit('SET_TOKEN', '')
            commit('SET_NAME', {})
            commit('SET_INFO', {})
            commit('SET_AVATAR', '')
            Vue.ls.remove(ACCESS_TOKEN)
            // Vue.ls.remove(SYSTEM_DICT)
          })
      })
    },
    //清理登录痕迹
    ClearTrail({ commit, state }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '')
        commit('SET_NAME', {})
        commit('SET_INFO', {})
        commit('SET_AVATAR', '')
        Vue.ls.remove(ACCESS_TOKEN)
        // Vue.ls.remove(SYSTEM_DICT)
        resolve()
      })
    },
    RefrashToken({ commit, state }, token) {
      Vue.ls.set(ACCESS_TOKEN, token, 7 * 24 * 60 * 60 * 1000)
      commit('SET_TOKEN', token)
    },
  },
}

export default user
