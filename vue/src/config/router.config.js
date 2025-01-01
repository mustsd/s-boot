// eslint-disable-next-line
import { UserLayout, BasicLayout } from '@/layouts'

const BasicRouter = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        meta: { title: '登录' },
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },
  {
    path: '/404',
    hidden: true,
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
]
const NFRouter = {
  path: '*',
  hidden: true,
  redirect: '/404'
}

// 根级菜单
const RootRouter = {
  name: 'index',
  path: '/',
  component: BasicLayout,
  redirect: '/dashboard/workplace',
  children: []
}
export { BasicRouter, NFRouter, RootRouter }
