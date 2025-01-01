<template>
  <a-select
    :value="arrayValue"
    @change="onChange"
    :disabled="disabled"
    mode="multiple"
    :placeholder="placeholder"
    :getPopupContainer="(node) => node.parentNode"
    allowClear
  >
    <a-select-option v-for="(item, index) in dictOptions" :key="index" :value="item.value">
      <span style="display: inline-block; width: 100%" :title="item.title">
        {{ item.title }}
      </span>
    </a-select-option>
  </a-select>
</template>

<script>
import { getDictItems } from '@/api/api'
export default {
  name: 'JMultiSelectTag',
  props: {
    dictCode: String,
    placeholder: { type: String, default: '请选择项目..' },
    disabled: { type: Boolean, default: false },
    value: String,
    orgFilter: { type: Boolean, default: false },
  },
  data() {
    return {
      dictOptions: [],
      arrayValue: [],
    }
  },
  created() {
    //获取字典数据
    this.initDictData()
  },
  watch: {
    value(val) {
      if (!val) {
        this.arrayValue = []
      } else {
        this.arrayValue = this.value.split(',')
      }
    },
  },
  methods: {
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
    onChange(selectedValue) {
      this.$emit('change', selectedValue.join(','))
    },
  },
}
</script>
