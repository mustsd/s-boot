import defaultAvatar from '@assets/default.svg'

const getters = {
  device: state => state.app.device,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar || defaultAvatar,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  roles: state => state.user.roles,
  userInfo: state => state.user.info,
  routers: state => state.permission.routers,
  multiTab: state => state.app.multiTab,
  lang: state => state.i18n.lang,
  serverName: state => state.app.serverName,
  serverNickName: state => state.app.serverNickName,
  buttons: state => state.user.buttons,
  allButtons: state => state.permission.allButtons,
  cachedViews: state => state.permission.cachedViews,
  unread: state => state.user.unread
}

export default getters
