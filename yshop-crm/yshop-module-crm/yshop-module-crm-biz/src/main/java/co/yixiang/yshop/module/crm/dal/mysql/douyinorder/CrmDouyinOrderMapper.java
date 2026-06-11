package co.yixiang.yshop.module.crm.dal.mysql.douyinorder;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.CrmDouyinOrderPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.CrmDouyinOrderRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.douyinorder.CrmDouyinOrderDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 抖音订单 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmDouyinOrderMapper extends BaseMapperX<CrmDouyinOrderDO> {

    default PageResult<CrmDouyinOrderRespVO> selectPage(CrmDouyinOrderPageReqVO reqVO, List<Long> userIds) {
        return selectJoinPage(reqVO, CrmDouyinOrderRespVO.class, new MPJLambdaWrapper<CrmDouyinOrderDO>()
                .selectAll(CrmDouyinOrderDO.class)
                .selectAs(AdminUserDO::getNickname, CrmDouyinOrderRespVO::getOwnUserName)
                .leftJoin(AdminUserDO.class, AdminUserDO::getId, CrmDouyinOrderDO::getOwnerUserId)
                .likeIfExists(CrmDouyinOrderDO::getOrderNo, reqVO.getOrderNo())
                .likeIfExists(CrmDouyinOrderDO::getCustomerName, reqVO.getCustomerName())
                .eqIfExists(CrmDouyinOrderDO::getPhone, reqVO.getPhone())
                .likeIfExists(CrmDouyinOrderDO::getProductName, reqVO.getProductName())
                .eqIfExists(CrmDouyinOrderDO::getVerificationStatus, reqVO.getVerificationStatus())
                .eqIfExists(CrmDouyinOrderDO::getOwnerUserId, reqVO.getOwnerUserId())
                .likeIfExists(AdminUserDO::getNickname, reqVO.getOwnerUserName())
                .in(!userIds.isEmpty(), CrmDouyinOrderDO::getOwnerUserId, userIds)
                .orderByDesc(CrmDouyinOrderDO::getId)
        );
    }

}