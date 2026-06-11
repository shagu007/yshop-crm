import request from '@/config/axios'

// 抖音订单 VO
export interface DouyinOrderVO {
  id?: number
  orderNo: string // 订单编号
  customerName: string // 客户姓名
  phone: string // 手机号
  productName: string // 商品名称
  amount: number // 订单金额
  orderTime: Date // 下单时间
  verificationStatus: number // 核销状态 0-待核销 1-已核销 2-已过期
  verificationTime: Date // 核销时间
  verificationStore: string // 核销门店
  ownerUserId: number // 负责人ID
  remark: string // 备注
}

// 抖音订单 API
export const DouyinOrderApi = {
  // 查询分页
  getPage: async (params: any) => {
    return await request.get({ url: `/crm/douyin-order/page`, params })
  },

  // 查询详情
  getDetail: async (id: number) => {
    return await request.get({ url: `/crm/douyin-order/get?id=` + id })
  },

  // 新增
  create: async (data: DouyinOrderVO) => {
    return await request.post({ url: `/crm/douyin-order/create`, data })
  },

  // 修改
  update: async (data: DouyinOrderVO) => {
    return await request.put({ url: `/crm/douyin-order/update`, data })
  },

  // 删除
  delete: async (id: number) => {
    return await request.delete({ url: `/crm/douyin-order/delete?id=` + id })
  }
}