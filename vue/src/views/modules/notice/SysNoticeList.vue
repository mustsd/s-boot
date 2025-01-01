<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="消息状态">
              <j-dict-select-tag v-model="queryParam.status" placeholder="请选择状态" dictCode="noticeStatus" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('系统通知')">导出</a-button>
      <!-- <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="uploadFile(text)">
            下载
          </a-button>
        </template>
        <span slot="action" slot-scope="text, record">
          <span v-show="record.status == 1">
            <a @click="handleEdit(record)">编辑</a>
            <a-divider type="vertical" />
          </span>
          <span v-show="record.status == 1 || record.status == 2">
            <a-popconfirm title="确定发布吗?" @confirm="() => handlePublish(record.id)">
              <a v-show="record.status == 1">发布</a>
            </a-popconfirm>
            <a-popconfirm title="确定撤销吗?" @confirm="() => handleCancel(record.id)">
              <a v-show="record.status == 2">撤销</a>
            </a-popconfirm>
            <a-divider type="vertical" />
          </span>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <sysNotice-modal ref="modalForm" @ok="modalFormOk"></sysNotice-modal>
  </a-card>
</template>
<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { BaseQueryMixin } from '@/mixins/BaseQueryMixin'
import SysNoticeModal from './modules/SysNoticeModal'
import { getAction } from '@/api/manage'

export default {
  name: 'SysNoticeList',
  mixins: [BaseQueryMixin, mixinDevice],
  components: {
    SysNoticeModal,
  },
  data() {
    return {
      description: '系统通知管理页面',
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
          title: '标题',
          align: 'center',
          dataIndex: 'title',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'status_dictText',
        },
        {
          title: '发布人',
          align: 'center',
          dataIndex: 'sendBy',
        },
        {
          title: '发布时间',
          align: 'center',
          dataIndex: 'sendTime',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '撤销人',
          align: 'center',
          dataIndex: 'cancelBy',
        },
        {
          title: '撤销时间',
          align: 'center',
          dataIndex: 'cancelTime',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '创建人',
          align: 'center',
          dataIndex: 'createBy',
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '更新人',
          align: 'center',
          dataIndex: 'updateBy',
        },
        {
          title: '更新时间',
          align: 'center',
          dataIndex: 'updateTime',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/notice/sysNotice/list',
        delete: '/notice/sysNotice/delete',
        deleteBatch: '/notice/sysNotice/deleteBatch',
        exportXlsUrl: '/notice/sysNotice/exportXls',
        importExcelUrl: '/notice/sysNotice/importExcel',
        publishUrl: '/notice/sysNotice/publish',
        cancelUrl: '/notice/sysNotice/cancel',
      },
      dictOptions: {},
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    handleCancel(id) {
      getAction(this.url.cancelUrl, { id: id }).then((res) => {
        if (res.success) {
          this.loadData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handlePublish(id) {
      getAction(this.url.publishUrl, { id: id }).then((res) => {
        if (res.success) {
          this.loadData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    initDictConfig() {},
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
