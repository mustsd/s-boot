<template>
  <a-modal :title="title" width="60%" :destroyOnClose="true" :visible="visible" @cancel="close" :footer="null">
    <a-table
      ref="table"
      size="small"
      bordered
      rowKey="ordinalPosition"
      :columns="columns"
      :pagination="{
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30', '50', '200'],
        showSizeChanger: true
      }"
      :dataSource="dataSource"
      :loading="loading"
    >
      <template slot="htmlSlot" slot-scope="text">
        <div v-html="text"></div>
      </template>
      <template slot="imgSlot" slot-scope="text">
        <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
        <img v-else :src="text" height="25px" alt style="max-width: 80px; font-size: 12px; font-style: italic" />
      </template>
    </a-table>
  </a-modal>
</template>

<script>
import { getAction, deleteAction } from '@/api/manage'
import moment from 'moment'

export default {
  name: 'TableColumnListModal',
  components: {},
  data() {
    return {
      description: '字段列表',
      title: '',
      visible: false,
      loading: false,
      /* 数据源 */
      dataSource: [],
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '字段',
          align: 'center',
          dataIndex: 'columnName'
        },
        {
          title: '排序',
          align: 'center',
          dataIndex: 'ordinalPosition'
        },
        {
          title: 'nullable',
          align: 'center',
          dataIndex: 'isNullable'
        },
        {
          title: '数据类型',
          align: 'center',
          dataIndex: 'dataType'
        },
        {
          title: '字段类型',
          align: 'center',
          dataIndex: 'columnType'
        },
        {
          title: '主键',
          align: 'center',
          dataIndex: 'columnKey'
        },
        {
          title: '描述',
          align: 'center',
          dataIndex: 'columnComment'
        }
      ],
      url: {
        list: '/code/listColumn'
      }
    }
  },
  created() {},
  methods: {
    loadData(record) {
      this.loading = true
      getAction(this.url.list, record).then(res => {
        if (res.success) {
          this.dataSource = res.result
        } else {
          this.$message.error(res.message)
        }
        this.loading = false
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>

<style scoped>
@import '~@assets/less/common.less';
</style>
