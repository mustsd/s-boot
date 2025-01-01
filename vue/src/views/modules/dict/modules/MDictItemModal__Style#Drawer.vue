<template>
  <a-drawer :title="dict.name" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-row>
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
    </a-row>
    <a-row>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </a-row>
  </a-drawer>
</template>

<script>
import { BaseQueryMixin } from '@/mixins/BaseQueryMixin'
import { getAction } from '@/api/manage'
export default {
  name: 'MDictItemModal',
  mixins: [BaseQueryMixin],
  components: {},
  data() {
    return {
      title: '操作',
      width: 600,
      visible: false,
      // 表头
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
          title: 'key',
          align: 'center',
          dataIndex: 'itemKey',
        },
        {
          title: '值',
          align: 'center',
          dataIndex: 'itemValue',
        },
        {
          title: '描述',
          align: 'center',
          dataIndex: 'description',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        add: '/sys/dictItem/add',
        edit: '/sys/dictItem/edit',
        list: '/sys/dictItem/list',
        delete: '/sys/dictItem/delete',
      },
      disableMixinCreated: true,
      dict: {},
    }
  },
  methods: {
    handleAdd() {
      this.$emit('showItemModal', { dictId: this.dict.id, dictCode: this.dict.code })
    },
    handleEdit(record) {
      record.dictCode = this.dict.code
      this.$emit('showItemModal', record)
    },
    loadData() {
      this.loading = true
      getAction(this.url.list, { dictId: this.dict.id, column: 'createTime', order: 'desc' }).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
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
.ant-btn {
  margin-left: 1px;
  margin-bottom: 10px;
  float: left;
}
</style>