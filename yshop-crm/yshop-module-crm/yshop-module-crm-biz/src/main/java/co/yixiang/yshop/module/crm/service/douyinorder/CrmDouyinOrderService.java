package co.yixiang.yshop.module.crm.service.douyinorder;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.douyinorder.CrmDouyinOrderDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;

/**
 * 抖音订单 Service 接口
 *
 * @author yshop
 */
public interface CrmDouyinOrderService {

    /**
     * 创建抖音订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDouyinOrder(@Valid CrmDouyinOrderSaveReqVO createReqVO);

    /**
     * 更新抖音订单
     *
     * @param updateReqVO 更新信息
     */
    void updateDouyinOrder(@Valid CrmDouyinOrderSaveReqVO updateReqVO);

    /**
     * 删除抖音订单
     *
     * @param id 编号
     */
    void deleteDouyinOrder(Long id);

    /**
     * 获得抖音订单
     *
     * @param id 编号
     * @return 抖音订单
     */
    CrmDouyinOrderDO getDouyinOrder(Long id);

    /**
     * 获得抖音订单分页
     *
     * @param pageReqVO 分页查询
     * @return 抖音订单分页
     */
    PageResult<CrmDouyinOrderRespVO> getDouyinOrderPage(CrmDouyinOrderPageReqVO pageReqVO);

}