package co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo;

import co.yixiang.yshop.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 抖音订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmDouyinOrderPageReqVO extends PageParam {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "客户姓名")
    private String customerName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "核销状态 0-待核销 1-已核销 2-已过期")
    private Integer verificationStatus;

    @Schema(description = "负责人ID")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownerUserName;

    @Schema(description = "下单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] orderTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}