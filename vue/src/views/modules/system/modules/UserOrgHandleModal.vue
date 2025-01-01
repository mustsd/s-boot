<template>
  <j-modal :title="title" width="30%" :destroyOnClose="true" :visible="visible" @ok="handleSubmit" @cancel="close">
    <a-card :bordered="false">
      <a-transfer
        :titles="['全部机构', '我的机构']"
        :data-source="dataSource"
        show-search
        :target-keys="targetKeys"
        :render="(item) => item.title"
        @change="handleChange"
        @search="handleSearch"
      />
    </a-card>
  </j-modal>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
export default {
  name: 'UserOrgHandleModal',
  components: {},
  data() {
    return {
      title: '',
      description: '用户机构维护',
      visible: false,
      dataSource: [],
      targetKeys: [],
      user: {},
      url: {
        listTransferByUser: '/system/sysOrg/listTransferByUser',
        editUserOrg: '/system/sysUserOrg/editUserOrg',
      },
    }
  },
  methods: {
    handleSubmit() {
      postAction(this.url.editUserOrg, { userId: this.user.id, orgIds: this.targetKeys }).then((res) => {
        if (res.success) {
          this.close()
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    initData(record) {
      this.user = record
      this.title = `用户机构维护 => ${this.user.name}`
      getAction(this.url.listTransferByUser, { userId: record.id }).then((res) => {
        if (res.success) {
          this.dataSource = res.result.allOrg.map((x) => {
            let k = new Object()
            k.key = x.id
            k.title = x.name
            k.description = x.region
            k.disabled = false
            return k
          })
          this.targetKeys = res.result.currentOrg.map((x) => {
            return x.id
          })
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleChange(targetKeys, direction, moveKeys) {
      this.targetKeys = targetKeys
      console.log(this.targetKeys)
    },
    handleSearch() {},
    close() {
      this.$emit('close')
      this.visible = false
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>