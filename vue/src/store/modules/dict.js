import Vue from 'vue'

import { SYSTEM_DICT } from '@/store/mutation-types'

const dict = {
  state: {
    dict: {},
  },

  mutations: {
    SET_SYSTEM_DICT: (state, dict) => {
      state.dict[dict.dictKey] = dict.dictItems
      // Vue.ls.set(SYSTEM_DICT, state.dict, 7 * 24 * 60 * 60 * 1000)
    },
  },
  getters: {
    getDictItems: (state) => (dictKey) => {
      // state.dict = Vue.ls.get(SYSTEM_DICT) || {}
      return state.dict[dictKey]
    },
  },
}

export default dict
