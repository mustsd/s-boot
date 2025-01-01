<template>
  <j-modal
    :title="record.title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @cancel="handleCancel"
    cancelText="关闭"
    :hideFooter="true"
  >
    <a-spin :spinning="confirmLoading">
      <a-row>
        <div class="content" v-html="record.content" />
      </a-row>
      <a-row>
        <h4 style="float: right; font-style: italic">
          <font style="color: green"> by ： {{ record.sendBy }}</font>
          <font style="color: grey; margin-left: 10px">发布日期：{{ record.sendTime }}</font>
        </h4>
      </a-row>
    </a-spin>
  </j-modal>
</template>

<script>
import { postAction } from '@/api/manage'

export default {
  name: 'SysNoticeUserModal',
  components: {},
  data() {
    return {
      title: '系统通知',
      width: 800,
      visible: false,
      confirmLoading: false,
      url: {
        readNoticeUrl: '/notice/sysNotice/readNotice',
      },
      record: {},
      readFlag: 0,
    }
  },
  created() {},
  methods: {
    showNotify(record) {
      this.readFlag = record.readFlag
      this.visible = true
      this.confirmLoading = true
      this.result = {}
      postAction(this.url.readNoticeUrl, record).then((res) => {
        if (res.success) {
          this.record = res.result
        } else {
          this.$message.error(res.message)
        }
        this.confirmLoading = false
      })
    },
    close() {
      this.$emit('close', this.readFlag == 1)
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>
<style lang="less" scoped>
.content {
  border-style: outset;
  padding-left: 10px;
  padding-right: 10px;
  padding-bottom: 10px;
  padding-top: 10px;
}
</style>
