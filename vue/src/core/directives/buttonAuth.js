import store from '../../store'

const buttonAuth = {
  install(Vue, options) {
    Vue.directive('auth', {
      inserted: (el, binding) => {
        let action = binding.value
        if (isBan(action)) {
          if (2 === banType(action)) {
            el.parentNode.removeChild(el)
          } else if (1 === banType(action)) {
            el.setAttribute('disabled', true)
          } else {
            el.setAttribute('disabled', true)
          }
        }
      }
    })
  }
}

export function isBan(value) {
  let userAuths = store.getters.buttons
  if (!userAuths) {
    return true
  }
  return !userAuths.some(a => a.btnCode === value)
}

export function banType(value) {
  let allAuths = store.getters.allButtons
  if (!allAuths) {
    return '0'
  }
  let filteredArr = allAuths.filter(a => a.btnCode === value)
  if (filteredArr.length < 1) {
    return '0'
  }
  return filteredArr[0].btnAction
}
export default buttonAuth
