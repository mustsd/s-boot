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
import { getDictItems } from '@/api/api'

export default {
  name: 'JDictSelectTag',
  props: {
    dictCode: String,
    placeholder: String,
    triggerChange: Boolean,
    disabled: Boolean,
    showSearch: { type: Boolean, default: true },
    value: [String, Number],
    orgFilter: { type: Boolean, default: false },
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
        getDictItems(this.dictCode, this.orgFilter).then((res) => {
          if (res.success) {
            this.dictOptions = res.result
            this.$store.commit('SET_SYSTEM_DICT', { dictKey: this.dictCode, dictItems: this.dictOptions })
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
