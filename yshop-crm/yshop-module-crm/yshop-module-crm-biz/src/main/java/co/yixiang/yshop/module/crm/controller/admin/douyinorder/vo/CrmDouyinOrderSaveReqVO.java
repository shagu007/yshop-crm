package co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 抖音订单新增/修改 Request VO")
@Data
public class CrmDouyinOrderSaveReqVO {

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "订单编号")
    @NotEmpty(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "客户姓名")
    @NotEmpty(message = "客户姓名不能为空")
    private String customerName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "订单金额")
    private BigDecimal amount;

    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "核销状态 0-待核销 1-已核销 2-已过期")
    private Integer verificationStatus;

    @Schema(description = "核销时间")
    private LocalDateTime verificationTime;

    @Schema(description = "核销门店")
    private String verificationStore;

    @Schema(description = "负责人ID")
    private Long ownerUserId;

    @Schema(description = "备注")
    private String remark;

}