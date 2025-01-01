import { getAction, putAction } from '@/api/manage'

// 改变密码
const changePassword = (params) => putAction('/sys/user/changePassword', params)

// 字典标签专用（通过code获取字典数组）
export const getDictItems = (dictKey, orgFilter) =>
  getAction('/sys/dict/getDictItem', { dictCode: dictKey, orgFilter: orgFilter })

export {
  changePassword
}
