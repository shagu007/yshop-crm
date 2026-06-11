package co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 抖音订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmDouyinOrderRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "订单编号")
    @ExcelProperty("订单编号")
    private String orderNo;

    @Schema(description = "客户姓名")
    @ExcelProperty("客户姓名")
    private String customerName;

    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private String phone;

    @Schema(description = "商品名称")
    @ExcelProperty("商品名称")
    private String productName;

    @Schema(description = "订单金额")
    @ExcelProperty("订单金额")
    private BigDecimal amount;

    @Schema(description = "下单时间")
    @ExcelProperty("下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "核销状态 0-待核销 1-已核销 2-已过期")
    @ExcelProperty("核销状态")
    private Integer verificationStatus;

    @Schema(description = "核销时间")
    @ExcelProperty("核销时间")
    private LocalDateTime verificationTime;

    @Schema(description = "核销门店")
    @ExcelProperty("核销门店")
    private String verificationStore;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人ID")
    @ExcelProperty("负责人ID")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownUserName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}