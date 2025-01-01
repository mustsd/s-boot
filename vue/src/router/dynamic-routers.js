import { getCurrentUserNav } from '@/api/login'
import { RootRouter } from '@/config/router.config'
import store from '../store'
import { BasicLayout, BlankLayout, PageView, RouteView, IframeView } from '@/layouts'

const routeViewMap = {
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView,
  IframeView: IframeView,
}

export const getDynamicRouter = () => {
  return new Promise((resolve, reject) => {
    getCurrentUserNav()
      .then((res) => {
        const { menus, buttons, allButtons, keepAliveKeys } = res.result
        const dynamicRouters = []
        buildRouterTree(menus, dynamicRouters, 'default')
        RootRouter.children = dynamicRouters
        resolve({ routers: [RootRouter], buttons: buttons, allButtons: allButtons, keepAlives: keepAliveKeys })
      })
      .catch((err) => {
        reject(err)
      })
  })
}

function buildRouter(menu) {
  const { name, path, component, icon, hidden, external, key } = menu || {}
  const router = {
    //路由路径
    path: path,
    //命名路由名称
    name: key,
    //路由元信息
    hidden: hidden,
    hideChildrenInMenu: hidden,
    meta: {
      title: name,
      icon: icon,
      hiddenHeaderContent: true,
      external: external,
    },
    children: [],
  }
  //组件路径是这两种，增加target地址属性 eg: {{window._CONFIG.domianURL}}/doc.html https://www.baidu.com
  const reg = /^(http)(.)+|(.)*(window._CONFIG.domianURL)(.)*/
  if (component && reg.test(component)) {
    const token = store.getters.token
    // 外部跳转链接 eg :大屏  {{window._CONFIG.domianURL}}/demo/test?Access-Token={{token}} http://localhost:9990/manage/demo/test?Access-Token=xxx
    router.component = IframeView
    router.meta.target = (component || '').replace(/{{([^}}]+)?}}/g, (s1, s2) => eval(s2))
  } else {
    router.component = routeViewMap[component] || (() => import(`@/views/${component}`))
  }
  return router
}

function buildRouterTree(list, tree, parentId) {
  list.forEach((item) => {
    // 判断是否为父级菜单
    if (item.parentId === parentId) {
      const child = buildRouter(item)
      // 迭代 list， 找到当前菜单相符合的所有子菜单
      buildRouterTree(list, child.children, item.id)
      // 删掉不存在 children 值的属性
      if (child.children.length <= 0) {
        delete child.children
      }
      // 加入到树中
      tree.push(child)
    }
  })
}
