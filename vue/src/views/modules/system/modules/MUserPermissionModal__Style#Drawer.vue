<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form>
        <a-form-item :label="lable">
          <a-tree
            show-icon
            checkable
            show-line
            v-if="treeData.length > 0"
            v-model="checkedKeys"
            :defaultExpandAll="true"
            :tree-data="treeData"
            @select="showPermRoles"
          >
            <a-icon slot="menu" type="profile" theme="twoTone" />
            <a-icon slot="action" type="tag" theme="twoTone" />
          </a-tree>
        </a-form-item>
      </a-form>
    </a-spin>
    <m-permission-role-modal ref="permRoleModal"></m-permission-role-modal>
  </a-drawer>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import MPermissionRoleModal from './MPermissionRoleModal'

export default {
  name: 'MUserPermissionModal',
  components: { MPermissionRoleModal },
  data() {
    return {
      title: '操作',
      width: 400,
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      url: {
        listTree: '/sys/permission/listUserPermissionTree',
      },
      record: {},
      treeData: [],
      checkedKeys: [],
      allPermKey: [],
    }
  },
  computed: {
    lable() {
      return `用户权限 - (${this.checkedKeys.length}/${this.allPermKey.length})`
    },
  },
  created() {},
  methods: {
    showPermRoles([permId]) {
      if (!permId) {
        return
      }
      this.$refs.permRoleModal.visible = true
      this.$refs.permRoleModal.loadData(permId)
    },
    loadTree() {
      getAction(this.url.listTree, { userId: this.record.id }).then((res) => {
        if (res.success) {
          this.treeData = res.result.treeData
          this.checkedKeys = res.result.checkedKey
          this.allPermKey = res.result.allPermKey
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
  },
}
</script>

<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 30px;
  margin-bottom: 30px;
  float: right;
}
</style>
