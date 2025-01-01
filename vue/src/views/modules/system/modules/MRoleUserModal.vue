<template>
  <j-modal :title="title" :width="width" :visible="visible" @cancel="handleCancel" :footer="null">
    <a-table
      ref="table"
      size="small"
      bordered
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="false"
      class="j-table-force-nowrap"
    ></a-table>
  </j-modal>
</template>

<script>
import { getAction } from '@/api/manage'

export default {
  name: 'MRoleUserModal',
  data() {
    return {
      title: '角色关联用户',
      width: 800,
      visible: false,
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '账号',
          align: 'center',
          dataIndex: 'account',
        },
        {
          title: '姓名',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '性别',
          align: 'center',
          dataIndex: 'gender_dictText',
        },
        {
          title: '电话',
          align: 'center',
          dataIndex: 'phone',
        },
        {
          title: '邮箱',
          align: 'center',
          dataIndex: 'email',
        },
      ],
      url: {
        listByRoleId: '/sys/user/listByRoleId',
      },
      dataSource: [],
    }
  },
  created() {},
  methods: {
    loadData(record) {
      this.title = record.name
      getAction(this.url.listByRoleId, { roleId: record.id }).then((res) => {
        if (res.success) {
          this.dataSource = res.result
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>
