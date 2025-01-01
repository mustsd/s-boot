<template>
  <a-card :bordered="false" style="height: 100%">
    <a-spin :spinning="loading">
      <!-- <a-input-search @search="handleSearch" style="width:100%;margin-top: 10px" placeholder="输入组织机构名称进行查询..."/> -->

      <a-tree
        :show-line="true"
        v-if="treeData.length > 0"
        :treeData="treeData"
        :defaultExpandAll="true"
        @select="onSelect"
      >
      </a-tree>
    </a-spin>
  </a-card>
</template>

<script>
import { getAction } from '@/api/manage'

export default {
  name: 'ArticleLeftTree',
  data() {
    return {
      loading: false,
      treeData: [],
      url: {
        loadTree: '/sys/article/tree'
      }
    }
  },
  created() {
    this.loadTree()
  },
  methods: {
    onSelect(selectedKeys, info) {
      console.log('selected', selectedKeys, info)
    },
    loadTree() {
      this.loading = true
      getAction(this.url.loadTree, '').then(res => {
        this.loading = false
        this.treeData = res.result
        console.log(res)
      })
    }
  }
}
</script>

<style scoped></style>
