<template>
  <j-modal :title="title" :width="width" :visible="visible" @cancel="handleCancel" :footer="null">
    <a-spin :spinning="loading">
      <a-tabs v-if="dataSource.length > 0" :defaultActiveKey="0" type="card">
        <a-tab-pane v-for="(t, index) in dataSource" :key="index" :tab="t.fileName">
          <a-button
            type="primary"
            ghost
            v-clipboard:copy="t.content"
            v-clipboard:success="clipboardSuccess"
            style="float: right; margin-right: 10px"
            >复制代码</a-button
          >
          <pre v-highlightjs="t.content"><code :class="t.fileExt==='vue'?'xml':t.ext"></code></pre>
        </a-tab-pane>
      </a-tabs>
    </a-spin>
  </j-modal>
</template>
<script>
import { postAction } from '@/api/manage'
import 'highlight.js/styles/idea.css'

export default {
  name: 'CodePreviewModal',
  data() {
    return {
      title: '代码预览',
      width: '60%',
      visible: false,
      loading: false,
      dataSource: [],
      url: {
        codePreview: '/code/preview'
      }
    }
  },
  created() {},
  methods: {
    clipboardSuccess() {
      this.$message.success('复制成功')
    },
    loadData(record) {
      this.loading = true
      postAction(this.url.codePreview, record).then(res => {
        if (res.success) {
          this.dataSource = res.result
        } else {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleCancel() {
      this.close()
    }
  }
}
</script>
<style lang="less"></style>
