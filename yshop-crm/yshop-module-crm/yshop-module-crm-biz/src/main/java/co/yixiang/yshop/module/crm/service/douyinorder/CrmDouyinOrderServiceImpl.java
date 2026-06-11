package co.yixiang.yshop.module.crm.service.douyinorder;

import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.douyinorder.CrmDouyinOrderDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.crm.dal.mysql.douyinorder.CrmDouyinOrderMapper;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.CLUES_NOT_EXISTS;

/**
 * 抖音订单 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmDouyinOrderServiceImpl implements CrmDouyinOrderService {

    @Resource
    private CrmDouyinOrderMapper douyinOrderMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createDouyinOrder(CrmDouyinOrderSaveReqVO createReqVO) {
        CrmDouyinOrderDO order = BeanUtils.toBean(createReqVO, CrmDouyinOrderDO.class);
        douyinOrderMapper.insert(order);
        return order.getId();
    }

    @Override
    public void updateDouyinOrder(CrmDouyinOrderSaveReqVO updateReqVO) {
        validateExists(updateReqVO.getId());
        CrmDouyinOrderDO updateObj = BeanUtils.toBean(updateReqVO, CrmDouyinOrderDO.class);
        douyinOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteDouyinOrder(Long id) {
        validateExists(id);
        douyinOrderMapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (douyinOrderMapper.selectById(id) == null) {
            throw exception(CLUES_NOT_EXISTS);
        }
    }

    @Override
    public CrmDouyinOrderDO getDouyinOrder(Long id) {
        return douyinOrderMapper.selectById(id);
    }

    @Override
    public PageResult<CrmDouyinOrderRespVO> getDouyinOrderPage(CrmDouyinOrderPageReqVO pageReqVO) {
        // 获取当前登录用户及下属ID（如果是管理员则能看到全部）
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        ids.add(adminId);
        // 也查下属的
        List<Long> subIds = adminUserApi.getUserListBySubordinateIds(adminId);
        if (subIds != null && !subIds.isEmpty()) {
            ids.addAll(subIds);
        }
        return douyinOrderMapper.selectPage(pageReqVO, ids);
    }

}