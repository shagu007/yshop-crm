<template>
  <ContentWrap title="抖音来客订单管理">
    <!-- 搜索栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单编号" clearable @keyup.enter="handleQuery" class="!w-200px" />
      </el-form-item>
      <el-form-item label="客户姓名" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入客户姓名" clearable @keyup.enter="handleQuery" class="!w-200px" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable @keyup.enter="handleQuery" class="!w-200px" />
      </el-form-item>
      <el-form-item label="核销状态" prop="verificationStatus">
        <el-select v-model="queryParams.verificationStatus" placeholder="请选择核销状态" clearable class="!w-200px">
          <el-option label="待核销" :value="0" />
          <el-option label="已核销" :value="1" />
          <el-option label="已过期" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px" />搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px" />重置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap style="margin-top: 15px">
    <el-button type="primary" @click="openForm('create')" v-hasPermi="['crm:douyin-order:create']">
      <Icon icon="ep:plus" class="mr-5px" />新增订单
    </el-button>
    <el-button
      type="success"
      plain
      @click="handleExport"
      v-hasPermi="['crm:douyin-order:export']"
    >
      <Icon icon="ep:download" class="mr-5px" />导出Excel
    </el-button>
    <el-table v-loading="loading" :data="list" border stripe style="margin-top: 10px">
      <el-table-column label="订单编号" prop="orderNo" min-width="160" />
      <el-table-column label="客户姓名" prop="customerName" min-width="100" />
      <el-table-column label="手机号" prop="phone" min-width="120" />
      <el-table-column label="商品名称" prop="productName" min-width="140" show-overflow-tooltip />
      <el-table-column label="订单金额" prop="amount" min-width="100">
        <template #default="scope">
          ¥{{ scope.row.amount }}
        </template>
      </el-table-column>
      <el-table-column label="下单时间" prop="orderTime" min-width="150">
        <template #default="scope">
          {{ dateFormatter(scope.row.orderTime) }}
        </template>
      </el-table-column>
      <el-table-column label="核销状态" prop="verificationStatus" min-width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.verificationStatus === 0" type="warning">待核销</el-tag>
          <el-tag v-else-if="scope.row.verificationStatus === 1" type="success">已核销</el-tag>
          <el-tag v-else-if="scope.row.verificationStatus === 2" type="info">已过期</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="核销时间" prop="verificationTime" min-width="150">
        <template #default="scope">
          {{ dateFormatter(scope.row.verificationTime) }}
        </template>
      </el-table-column>
      <el-table-column label="核销门店" prop="verificationStore" min-width="140" show-overflow-tooltip />
      <el-table-column label="负责人" prop="ownUserName" min-width="100" />
      <el-table-column label="备注" prop="remark" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作" min-width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['crm:douyin-order:update']">
            编辑
          </el-button>
          <el-button link type="primary" @click="handleVerification(scope.row)" v-if="scope.row.verificationStatus === 0">
            核销
          </el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['crm:douyin-order:delete']">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗 -->
  <DouyinOrderForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DouyinOrderApi } from '@/api/crm/douyinorder'
import { ElMessage, ElMessageBox } from 'element-plus'
import DouyinOrderForm from './DouyinOrderForm.vue'
import { dateFormatter } from '@/utils/formatTime'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const queryFormRef = ref()
const formRef = ref()

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orderNo: '',
  customerName: '',
  phone: '',
  verificationStatus: undefined,
  ownerUserId: undefined
})

const getList = async () => {
  loading.value = true
  try {
    const res = await DouyinOrderApi.getPage(queryParams)
    list.value = res.list
    total.value = res.total
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = () => {
  queryFormRef.value?.resetFields()
  handleQuery()
}

const openForm = (type: string, id?: number) => {
  formRef.value?.open(type, id)
}

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确认删除该订单？', '提示', { type: 'warning' })
    await DouyinOrderApi.delete(id)
    ElMessage.success('删除成功')
    getList()
  } catch (e) {
    // 取消删除
  }
}

const handleVerification = async (row: any) => {
  try {
    await ElMessageBox.confirm(`确认将订单「${row.orderNo}」标记为已核销？`, '核销确认', { type: 'warning' })
    await DouyinOrderApi.update({
      ...row,
      verificationStatus: 1,
      verificationTime: new Date().toISOString()
    })
    ElMessage.success('核销成功')
    getList()
  } catch (e) {
    // 取消
  }
}

const handleExport = async () => {
  ElMessage.info('导出功能开发中...')
}


getList()
</script>
