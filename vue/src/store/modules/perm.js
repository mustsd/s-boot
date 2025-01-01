/**
 * 向后端请求用户的菜单，动态生成路由
 */
import { getDynamicRouter } from '@/router/dynamic-routers'

const permission = {
  state: {
    routers: [],
    allButtons: [],
    cachedViews: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.routers = routers
    },
    SET_ALL_BUTTONS: (state, allButtons) => {
      state.allButtons = allButtons
    },
    SET_CACHED_VIEWS: (state, cachedViews) => {
      state.cachedViews = cachedViews
    }
  },
  actions: {
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        getDynamicRouter().then(res => {
          const { routers, buttons, allButtons, keepAlives } = res
          commit('SET_ROUTERS', routers)
          commit('SET_BUTTONS', buttons)
          commit('SET_ALL_BUTTONS', allButtons)
          commit('SET_CACHED_VIEWS', keepAlives)
          resolve()
        })
      })
    }
  }
}

export default permission
