import { axios } from '@/utils/request'

/**
 * @param parameter
 * @returns {*}
 */
export function login(parameter) {
  return axios({
    url: '/auth/login',
    method: 'post',
    data: parameter
  })
}

export function getInfo() {
  return axios({
    url: '/auth/info',
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurrentUserNav() {
  return axios({
    url: '/auth/nav',
    method: 'get'
  })
}

export function logout() {
  return axios({
    url: '/auth/logout',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * @param key {*}
 */
export function getCaptcha(key) {
  return axios({
    url: `/auth/captcha/${key}`,
    method: 'get'
  })
}
