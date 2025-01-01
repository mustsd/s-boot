<template>
  <div :class="prefixCls">
    <div ref="editor" class="editor-wrapper"></div>
  </div>
</template>

<script>
import WEditor from 'wangeditor'
import { uploadAction } from '@/api/manage'
import hljs from 'highlight.js'
import 'highlight.js/styles/idea.css'

export default {
  name: 'WangEditor',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-editor-wang',
    },
    // eslint-disable-next-line
    value: {
      type: String,
    },
  },
  data() {
    return {
      editor: null,
      editorContent: undefined,
      url: {
        uploadFile: '/sys/file/uploadImage',
      },
    }
  },
  watch: {
    value(val) {
      if (val != undefined && val != this.editorContent) {
        this.editor.txt.html(val)
      }
    },
  },
  mounted() {
    this.initEditor()
  },
  methods: {
    clear() {
      this.editor.txt.clear()
    },
    initEditor() {
      this.editor = new WEditor(this.$refs.editor)
      this.editor.config.onchange = (html) => {
        this.editorContent = html
        this.$emit('change', html)
      }
      this.editor.config.onchangeTimeout = 200 // 修改为 500ms
      this.editor.config.customUploadImg = (files, insertImgFn) => {
        let formdata = new FormData()
        formdata.append('file', files[0])
        uploadAction(this.url.uploadFile, formdata).then((res) => {
          if (res.success) {
            insertImgFn(res.result)
          }
        })
      }
      this.editor.config.focus = false
      this.editor.highlight = hljs
      this.editor.create()
    },
  },
}
</script>

<style lang="less" scoped>
.ant-editor-wang {
  .editor-wrapper {
    text-align: left;
  }
}
</style>
