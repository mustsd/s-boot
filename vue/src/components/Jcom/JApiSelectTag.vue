<template>
  <a-select
    :showSearch="showSearch"
    :getPopupContainer="(target) => target.parentNode"
    :placeholder="placeholder"
    :disabled="disabled"
    :value="getValueSting"
    @change="handleInput"
    optionFilterProp="children"
    :filterOption="filterOption"
  >
    <a-select-option :value="undefined">请选择</a-select-option>
    <a-select-option v-for="(item, key) in dictOptions" :key="key" :value="item.value">
      {{ item.title }}
    </a-select-option>
  </a-select>
</template>

<script>
import { getAction } from '@/api/manage'

export default {
  name: 'JApiSelectTag',
  props: {
    apiUrl: String,
    param: { type: Object, default: null },
    placeholder: String,
    triggerChange: Boolean,
    disabled: Boolean,
    showSearch: { type: Boolean, default: true },
    value: [String, Number],
  },
  data() {
    return {
      dictOptions: [],
    }
  },
  created() {
    //获取字典数据
    this.initDictData()
  },
  computed: {
    getValueSting() {
      return this.value != null ? this.value.toString() : null
    },
    dictCode() {
      return `${this.apiUrl}${this.param ? JSON.stringify(this.param) : ''}`
    },
  },
  methods: {
    filterOption(input, option) {
      let text = option.componentOptions.children[0].text
      if (text) {
        return text.indexOf(input) >= 0
      }
      return false
    },
    initDictData() {
      this.dictOptions = this.$store.getters.getDictItems(this.dictCode)
      if (!this.dictOptions) {
        getAction(this.apiUrl, this.param).then((res) => {
          if (res.success) {
            this.dictOptions = res.result
            this.$store.commit('SET_SYSTEM_DICT', { dictKey: this.dictCode, dictItems: this.dictOptions })
          } else {
            this.$message.warning(res.message)
          }
        })
      }
    },
    handleInput(e) {
      if (this.triggerChange) {
        this.$emit('change', e)
      } else {
        this.$emit('input', e)
      }
    },
  },
}
</script>

<style scoped></style>
