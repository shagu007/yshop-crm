<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="60%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="订单编号" prop="orderNo">
            <el-input v-model="formData.orderNo" placeholder="请输入抖音订单编号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="客户姓名" prop="customerName">
            <el-input v-model="formData.customerName" placeholder="请输入客户姓名" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入手机号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="formData.productName" placeholder="请输入商品名称" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="订单金额" prop="amount">
            <el-input-number v-model="formData.amount" :min="0" :precision="2" placeholder="请输入订单金额" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下单时间" prop="orderTime">
            <el-date-picker
              v-model="formData.orderTime"
              type="datetime"
              placeholder="选择下单时间"
              value-format="YYYY-MM-DD HH:mm:ss"
              class="!w-full"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="核销状态" prop="verificationStatus">
            <el-select v-model="formData.verificationStatus" placeholder="请选择核销状态" class="!w-full">
              <el-option label="待核销" :value="0" />
              <el-option label="已核销" :value="1" />
              <el-option label="已过期" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="核销时间" prop="verificationTime">
            <el-date-picker
              v-model="formData.verificationTime"
              type="datetime"
              placeholder="选择核销时间"
              value-format="YYYY-MM-DD HH:mm:ss"
              class="!w-full"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="核销门店" prop="verificationStore">
            <el-input v-model="formData.verificationStore" placeholder="请输入核销门店" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人" prop="ownerUserId">
            <el-select v-model="formData.ownerUserId" placeholder="请选择负责人" class="!w-full">
              <el-option
                v-for="user in userList"
                :key="user.id"
                :label="user.nickname"
                :value="user.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark" type="textarea" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm" :loading="formLoading">确 定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { Dialog } from '@/components/Dialog'
import { DouyinOrderApi, DouyinOrderVO } from '@/api/crm/douyinorder'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { ElMessage } from 'element-plus'

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formRef = ref()
const userList = ref<UserVO[]>([])

// 加载用户列表
onMounted(async () => {
  try {
    userList.value = await getSimpleUserList()
  } catch (e) {
    console.error(e)
  }
})
const formData = reactive<DouyinOrderVO>({
  orderNo: '',
  customerName: '',
  phone: '',
  productName: '',
  amount: 0,
  orderTime: undefined,
  verificationStatus: 0,
  verificationTime: undefined,
  verificationStore: '',
  ownerUserId: undefined,
  remark: ''
})

const formRules = {
  orderNo: [{ required: true, message: '订单编号不能为空', trigger: 'blur' }],
  customerName: [{ required: true, message: '客户姓名不能为空', trigger: 'blur' }]
}

// 打开表单
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  formLoading.value = false
  if (type === 'create') {
    dialogTitle.value = '新增抖音订单'
    resetForm()
  } else if (type === 'update' && id) {
    dialogTitle.value = '编辑抖音订单'
    try {
      const res = await DouyinOrderApi.getDetail(id)
      Object.assign(formData, res)
    } catch (e) {
      console.error(e)
    }
  }
}

// 提交表单
const submitForm = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return
  formLoading.value = true
  try {
    if (formData.id) {
      await DouyinOrderApi.update(formData as DouyinOrderVO)
      ElMessage.success('更新成功')
    } else {
      await DouyinOrderApi.create(formData as DouyinOrderVO)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    emit('success')
  } catch (e) {
    console.error(e)
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  Object.assign(formData, {
    id: undefined,
    orderNo: '',
    customerName: '',
    phone: '',
    productName: '',
    amount: 0,
    orderTime: undefined,
    verificationStatus: 0,
    verificationTime: undefined,
    verificationStore: '',
    ownerUserId: undefined,
    remark: ''
  })
}

const emit = defineEmits(['success'])
defineExpose({ open })
</script>
