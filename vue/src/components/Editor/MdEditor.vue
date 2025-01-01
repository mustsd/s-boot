<template>
  <mavon-editor
    ref="md"
    v-model="text"
    :placeholder="placeholder"
    :fontSize="fontSize + 'px'"
    @save="handleSave"
    @imgAdd="addImage"
    @change="handleChange"
    style="z-index: 1"
  ></mavon-editor>
</template>
<script>
import { uploadAction } from '@/api/manage'

export default {
  name: 'MdEditor',
  components: {},
  props: {
    mdText: {
      type: String,
      required: false,
      default: ''
    },
    placeholder: {
      type: String,
      required: false,
      default: '不积跬步无以至千里...'
    },
    fontSize: {
      type: Number,
      required: false,
      default: 12
    }
  },
  data() {
    return {
      url: {
        uploadFile: '/sys/file/uploadImage'
      },
      text: ''
    }
  },
  watch: {
    mdText(val) {
      this.text = val
    }
  },
  methods: {
    addImage(pos, $file) {
      console.log($file)
      let formdata = new FormData()
      formdata.append('file', $file)
      uploadAction(this.url.uploadFile, formdata).then(res => {
        if (res.success) {
          this.$refs.md.$img2Url(pos, res.result)
        }
      })
    },
    handleChange(value, render) {
      const obj = { text: value, html: render }
      this.$emit('change', obj)
    },
    handleSave(value, render) {
      const obj = { text: value, html: render }
      this.$emit('save', obj)
    }
  }
}
</script>
<style lang="less" scoped>
.mavonEditor {
  width: 100%;
  height: 600px;
}
</style>
