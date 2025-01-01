import Vue from 'vue'
import VueStorage from 'vue-ls'
import config from '@/config/defaultSettings'

// base library
import '@/core/lazy_lib/components_use'
// import Viser from 'viser-vue'

// ext library
import VueClipboard from 'vue-clipboard2'
import VueCropper from 'vue-cropper'
import MultiTab from '@/components/MultiTab'
import PageLoading from '@/components/PageLoading'
import PermissionHelper from '@/utils/helper/permission'
import Jcom from '@/components/Jcom/index'
import buttonAuth from './directives/buttonAuth'

//https://github.com/hinesboy/mavonEditor/blob/master/README.md
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import VueHighlightJS from 'vue-highlightjs'
import 'highlight.js/styles/idea.css' // or other highlight.js theme

VueClipboard.config.autoSetContainer = true

// Vue.use(Viser)
Vue.use(MultiTab)
Vue.use(PageLoading)
Vue.use(VueStorage, config.storageOptions)
Vue.use(VueClipboard)
Vue.use(PermissionHelper)
Vue.use(VueCropper)
Vue.use(Jcom)
Vue.use(buttonAuth)
Vue.use(mavonEditor)
Vue.use(VueHighlightJS)

process.env.NODE_ENV !== 'production' && console.warn('[antd-pro] NOTICE: Antd use lazy-load.')
