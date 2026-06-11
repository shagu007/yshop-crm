package co.yixiang.yshop.module.crm.dal.dataobject.douyinorder;

import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 抖音来客订单 DO
 *
 * @author yshop
 */
@TableName("yshop_douyin_order")
@KeySequence("yshop_douyin_order_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmDouyinOrderDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 核销状态 0-待核销 1-已核销 2-已过期
     */
    private Integer verificationStatus;

    /**
     * 核销时间
     */
    private LocalDateTime verificationTime;

    /**
     * 核销门店
     */
    private String verificationStore;

    /**
     * 负责人ID
     */
    private Long ownerUserId;

    /**
     * 备注
     */
    private String remark;

}