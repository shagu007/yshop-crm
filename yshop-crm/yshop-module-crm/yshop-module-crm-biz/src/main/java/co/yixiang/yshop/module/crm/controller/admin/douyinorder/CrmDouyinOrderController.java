package co.yixiang.yshop.module.crm.controller.admin.douyinorder;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.CrmDouyinOrderPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.CrmDouyinOrderRespVO;
import co.yixiang.yshop.module.crm.controller.admin.douyinorder.vo.CrmDouyinOrderSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.douyinorder.CrmDouyinOrderDO;
import co.yixiang.yshop.module.crm.service.douyinorder.CrmDouyinOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 抖音订单")
@RestController
@RequestMapping("/crm/douyin-order")
@Validated
public class CrmDouyinOrderController {

    @Resource
    private CrmDouyinOrderService douyinOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建抖音订单")
    @PreAuthorize("@ss.hasPermission('crm:douyin-order:create')")
    public CommonResult<Long> createDouyinOrder(@Valid @RequestBody CrmDouyinOrderSaveReqVO createReqVO) {
        return success(douyinOrderService.createDouyinOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新抖音订单")
    @PreAuthorize("@ss.hasPermission('crm:douyin-order:update')")
    public CommonResult<Boolean> updateDouyinOrder(@Valid @RequestBody CrmDouyinOrderSaveReqVO updateReqVO) {
        douyinOrderService.updateDouyinOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除抖音订单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:douyin-order:delete')")
    public CommonResult<Boolean> deleteDouyinOrder(@RequestParam("id") Long id) {
        douyinOrderService.deleteDouyinOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得抖音订单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:douyin-order:query')")
    public CommonResult<CrmDouyinOrderRespVO> getDouyinOrder(@RequestParam("id") Long id) {
        CrmDouyinOrderDO order = douyinOrderService.getDouyinOrder(id);
        return success(BeanUtils.toBean(order, CrmDouyinOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得抖音订单分页")
    @PreAuthorize("@ss.hasPermission('crm:douyin-order:query')")
    public CommonResult<PageResult<CrmDouyinOrderRespVO>> getDouyinOrderPage(@Valid CrmDouyinOrderPageReqVO pageReqVO) {
        return success(douyinOrderService.getDouyinOrderPage(pageReqVO));
    }

}