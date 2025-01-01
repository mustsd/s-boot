<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="账号">
              <a-input placeholder="请输入账号" v-model="queryParam.account"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.name"></a-input>
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
    <div class="table-operator">
      <a-row :gutter="24">
        <a-col :md="6" :sm="8">
          <a-textarea
            placeholder="此处填写消息内容。。。。勾选要发送的人员(不勾发全体)。。点击发送"
            v-model="msgTxt"
            :auto-size="{ minRows: 2, maxRows: 6 }"
          />
        </a-col>
        <a-col :md="6" :sm="8">
          <a-button type="primary" @click="sendNotify" icon="notification">发送</a-button>
          <a-button type="primary" @click="clearInput" icon="reload" style="margin-left: 1px">清除</a-button>
        </a-col>
      </a-row>
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
        rowKey="account"
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
          <a-popconfirm title="确定强制登出吗?" @confirm="() => forceLogout(record)">
            <a>登出</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { BaseQueryMixin } from '@/mixins/BaseQueryMixin'
import { getAction, postAction } from '@/api/manage'

export default {
  name: 'OnlineUserList',
  mixins: [BaseQueryMixin, mixinDevice],
  data() {
    return {
      description: 'm_user管理页面',
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
          title: '电话',
          align: 'center',
          dataIndex: 'phone',
        },
        {
          title: '邮箱',
          align: 'center',
          dataIndex: 'email',
        },
        {
          title: '登录IP',
          align: 'center',
          dataIndex: 'ip',
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
        list: '/sys/socket/listOnlineUser',
        delete: '/sys/user/delete',
        deleteBatch: '/sys/user/deleteBatch',
        exportXlsUrl: '/sys/user/exportXls',
        importExcelUrl: 'sys/user/importExcel',
        forceLogout: '/sys/socket/forceLogout',
        sendNotify: '/sys/socket/notify',
      },
      dictOptions: {},
      msgTxt: '',
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    sendNotify() {
      if (!this.msgTxt) {
        this.$message.error('消息不能为空')
        return
      }
      postAction(this.url.sendNotify, { accounts: this.selectedRowKeys, msg: this.msgTxt }).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    clearInput() {
      this.msgTxt = ''
    },
    forceLogout(record) {
      getAction(this.url.forceLogout, { account: record.account }).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.warning(res.message)
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