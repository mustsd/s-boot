import JModal from './JModal'
import JDictSelectTag from './JDictSelectTag.vue'
import JMultiSelectTag from './JMultiSelectTag.vue'
import JApiSelectTag from './JApiSelectTag.vue'
import JImageUploadTag from './JImageUpload.vue'
import JDate from './JDate.vue'
import JCron from './cron/JCron.vue'

export default {
  install(Vue) {
    Vue.component(JModal.name, JModal)
    Vue.component(JDictSelectTag.name, JDictSelectTag)
    Vue.component(JMultiSelectTag.name, JMultiSelectTag)
    Vue.component(JApiSelectTag.name, JApiSelectTag)
    Vue.component(JImageUploadTag.name, JImageUploadTag)
    Vue.component(JDate.name, JDate)
    Vue.component(JCron.name, JCron)
  },
}
