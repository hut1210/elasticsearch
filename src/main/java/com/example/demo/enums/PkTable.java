package com.example.demo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 业务类型枚举
 *
 * @author : zhucheng1
 * @version : 1.0
 * @date : 2019/3/14
 */
public enum PkTable {


    /**
     * 技术框架
     */
    Btp_Flow("flow", "流程", "", "", PkMixEnum.NO.getCode()),
    Btp_FlowNode("flow_node", "流程节点", "", "", PkMixEnum.NO.getCode()),

    /**
     * 系统级基础资料
     */
    System_Sp("sp", "销售平台表", "", "%08d", PkMixEnum.NO.getCode()),
    System_Dict("dict", "字典表", "", "%08d", PkMixEnum.NO.getCode()),
    System_Unit("unit", "业务单元表", "", "%08d", PkMixEnum.NO.getCode()),
    System_Tenant("tenant", "租户表", "SLES", "%06d", PkMixEnum.SHORT.getCode()),
    System_Logistics("logistics", "物流服务表", "SLGC", "%013d", PkMixEnum.NO.getCode()),
    System_Bizchannel("biz_channel", "下单通路", "", "%08d", PkMixEnum.NO.getCode()),
    System_Logistics_Biz_Config("logistics_biz_config", "物流公司快递业务配置", "", "%08d", PkMixEnum.NO.getCode()),
    System_MasterMapping("master_mapping", "基础数据映射", "", "", PkMixEnum.NO.getCode()),
    System_EnterpriseRoute("enterprise_route", "公共服务企业路由", "", "%08d", PkMixEnum.NO.getCode()),
    System_TenantRoute("tenant_route", "公共服务租户路由", "", "%08d", PkMixEnum.NO.getCode()),
    System_LogResourceRoute("logresource_route", "公共服务物流资源路由", "", "%08d", PkMixEnum.NO.getCode()),
    System_Component_Menu("component_menu", "组件-常用菜单", "", "%08d", PkMixEnum.NO.getCode()),
    System_Component_Row("component_row", "组件-常用自定义列", "", "%08d", PkMixEnum.NO.getCode()),
    System_Component_Search("component_search", "组件-常用自定查询条件", "", "%08d", PkMixEnum.NO.getCode()),
    System_TbBaseConfig("tb_base_config", "TB基础配置", "", "%08d", PkMixEnum.NO.getCode()),
    System_Component_Table("component_table", "组件-表格", "", "%08d", PkMixEnum.NO.getCode()),
    System_USER_TABLE_CONFIG("user_table_config", "用户表格配置", "", "%08d", PkMixEnum.NO.getCode()),
    System_Metadata_Table("metadata_table", "元数据-表", "", "%08d", PkMixEnum.NO.getCode()),
    System_Metadata_Field("metadata_field", "元数据-自有列", "", "%08d", PkMixEnum.NO.getCode()),
    System_Metadata_Fefine_Field("metadata_define_field", "元数据-自定义列", "", "%08d", PkMixEnum.NO.getCode()),
    /**
     * 租户级基础资料
     */
    Tenant_category("category", "商品分类表", "", "%s", PkMixEnum.NO.getCode()),
    Tenant_enterprise("enterprise", "企业主数据表", "SCPA", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_enterpriseISV("enterprise_isv", "企业主ISV关联", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_carrier("carrier", "承运商", "SCAR", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_supplier("supplier", "供应商", "SSUP", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_facilitatorOwner("facilitatorowner", "服务商货主", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_Owner("owner", "货主", "SOWE", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_OwnerISV("owner_isv", "货主ISV授权码", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_Brand("brand", "品牌", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_lgs("lgs", "企业物流服务", "", "", PkMixEnum.NO.getCode()),
    Tenant_lgsWarehouse("lgs_warehouse", "企业物流服务-仓储", "", "", PkMixEnum.NO.getCode()),
    Tenant_lgsCarrier("lgs_carrier", "企业物流服务-承运商", "", "", PkMixEnum.NO.getCode()),
    Tenant_customerCategory("customer_category", "客户分类", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_CustomerMain("customer_main", "客户主信息", "SCUS", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_CustomerMainExField("customer_main_exfield", "客户主信息自定义列", "", "%013d", PkMixEnum.NO.getCode()),
    Tenant_CustomerBank("customer_bank", "客户银行", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_CustomerBankExField("customer_bank_exfield", "客户银行自定义列", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_CustomerContact("customer_contact", "客户联系信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_CustomerContactExField("customer_contact_exfield", "客户联系信息自定义列", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_ORG_BINDING_MAP("org_binding_map", "机构绑定关系", "", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_ORG_BINDING_TC_MAP("org_binding_tc_map", "机构运力绑定关系", "", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_ContactsManage("contacts_manage","收寄件人管理","","%08d",PkMixEnum.NO.getCode()),

    Tenant_order_rule("order_rule", "单据规则", "", "%08d", PkMixEnum.LONG.getCode()),

    Tenant_DistributeSecurityType("distribute_security_type","分销保障类型","","",PkMixEnum.LONG.getCode()),
    Tenant_EpBusiRelations("ep_busi_relations","企业业务往来关系","","",PkMixEnum.LONG.getCode()),

    //承运商分单
    Tenant_CarrierSpit("carrier_spit", "承运商分单主表", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_CarrierSpitFactor("carrier_spit_factor", "分单因素表", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_CarrierSpitFactorCategory("carrier_spit_factor_category", "分单商品分类", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_CarrierSpitFactorLine("carrier_spit_factor_line", "分单线路", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_CarrierSpitFactorWeight("carrier_spit_factor_weight", "分单重量", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_CarrierSpitFactorSerial("carrier_spit_factor_serial", "分单序列", "", "%11d", PkMixEnum.LONG.getCode()),

    //订单分单
    Tenant_SplitFieldRegister("split_field_register", "分单条件注册表", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_SplitRuleMain("split_rule_main", "分单规则主档", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_SplitRuleItem("split_rule_item", "分单规则明细", "", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_SplitRuleItemScope("split_rule_item_scope", "分单规则明细自定义范围", "", "%08d", PkMixEnum.LONG.getCode()),

    //回传配置
    Tenant_UpstreamConfig("upstream_config", "回传上游系统配置数据", "", "%08d", PkMixEnum.LONG.getCode()),

    // 商品主数据资料
    Tenant_goodsBase("goodsBase", "商品基础信息", "SMCD", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_goodsStock("goodsStock", "商品货主库存信息", "SOMC", "%08d", PkMixEnum.LONG.getCode()),
    Tenant_goodsIndustryExtend("goodsIndustryExtend", "商品行业扩展", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_goodsAuxiliary("goodsAuxiliary", "商品辅助计量单位", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_goodsLevel("goodsLevel", "商品等级", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_goodsLevelMapping("goodsLevelMapping", "商品等级映射", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_channel("channel", "渠道管理", "", "%s", PkMixEnum.NO.getCode()),
    Tenant_Warehouse("warehouse", "仓库", "SWHS", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_WarehouseCover("warehouse_cover", "仓库覆盖", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_FsCover("fs_cover", "工厂库存地覆盖", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_customBillType("custom_billtype", "业务类型", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_customBillTypeAttribute("custom_billtype_attribute", "业务类型扩展属性", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_billtypeConfig("billtype_config", "单据类型配置", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_MappperScenarioTemple("mappper_scenario_temple", "场景映射模板表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_MappperScenarioParam("mappper_scenario_param", "场景映射参数表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_MappperScenarioData("mappper_scenario_data", "场景映射数据表", "", "%08d", PkMixEnum.NO.getCode()),
    //批次档案
    Tenant_goodsBatchConfig("goods_batch_config", "批次档案表", "", "%08d", PkMixEnum.NO.getCode()),
    // 包装策略
    Tenant_goodsPack("goods_pack", "包装策略表", "SGPU", "%08d", PkMixEnum.NO.getCode()),
    // 包装编码
    Tenant_goodsUom("goods_uom", "包装单位表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_GoodsAttribute("goods_attribute", "批属性表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_GoodsAttributeEnum("goods_attribute_enum", "批属性枚举表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_GoodsAttributeTemplate("goods_attribute_template", "批属性模板表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_GoodsAttributeTemplateRel("goods_attribute_template_rel", "批属性模板关联表", "", "%08d", PkMixEnum.NO.getCode()),
    // 租户商品
    Tenant_tenantGoodsBase("tenant_goods_base", "租户商品基础信息", "STMD", "%013d", PkMixEnum.LONG.getCode()),
    Tenant_tenantGoodsBarcode("tenant_goods_barcode_rel", "租户商品条码信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_tenantGoodsStockConfig("tenant_goods_stock_config", "租户商品库存配置信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_tenantGoodsPackRel("tenant_goods_pack_rel", "租户商品包装策略信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_tenantGoodsSalesInfo("tenant_goods_sales_info", "租户商品销售信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_tenantGoodsBindingRel("tenant_goods_binding_rel", "租户商品捆绑件信息", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_tenantGoodsAuxiliaryUnit("tenant_goods_auxiliary_unit", "租户商品辅助计量单位信息", "", "%08d", PkMixEnum.NO.getCode()),

    //库存
    Tenant_saleableWarehouseStock("saleable_warehouse_stock", "可销售库存", "", "", PkMixEnum.NO.getCode()),
    Tenant_channelStock("channel_stock", "渠道库存", "", "", PkMixEnum.NO.getCode()),
    Tenant_batchWarehouseStock("batch_warehouse_stock", "批次库存", "", "", PkMixEnum.NO.getCode()),
    //库存流水
    Tenant_warehouseStockFlow("warehouse_stock_flow", "可销售库存流水", "", "", PkMixEnum.NO.getCode()),
    Tenant_channelStockFlow("channel_stock_flow", "渠道库存流水", "", "", PkMixEnum.NO.getCode()),
    Tenant_stockAssist("stock_assist", "库存属性辅助表", "", "", PkMixEnum.NO.getCode()),
    //商流采购
    TENANT_PURCHASEMAIN("purchase_main", "商流采购单据", "SCPO", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_PURCHASEITEM("purchase_item", "商流采购单据明细", "", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_PURCHASESTATUS("purchase_status", "商流采购单据状态", "", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_PURCHASES_ITEM_WMS("purchase_item_wms", "商流采购单据回传明细", "", "%08d", PkMixEnum.LONG.getCode()),

    //商流退货入库
    TENANT_SALESRETURN("salesReturn", "商流退货入库单据", "SCSR", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_SALESRETURNITEM("salesReturn_item", "商流退货入库单据明细", "", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_SALESRETURNSTATUS("salesReturn_status", "商流退货入库单据状态", "", "%08d", PkMixEnum.LONG.getCode()),
    TENANT_SALESRETURN_ITEM_WMS("salesReturn_item_wms", "商流退货入库单据回传明细", "", "%08d", PkMixEnum.LONG.getCode()),
    //时效配置
    Tenant_prescriptionCalendar("prescription_calendar", "工作日历表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionCalendarEvent("prescription_calendar_event", "工作日历事件表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionSalerConfig("prescription_saler_config", "下传时效方案配置", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionTransportConfig("prescription_transport_config", "运输时效方案配置", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionTransportItem("prescription_transport_item", "运输时效路线明细表", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionWarehouseConfig("prescription_warehouse_config", "仓库时效方案配置", "", "%08d", PkMixEnum.NO.getCode()),
    Tenant_prescriptionWarehouseItem("prescription_warehouse_item", "仓库时效波次明细表", "", "%08d", PkMixEnum.NO.getCode()),

    //出库单资料
    Odo_Carrier("odo_carrier", "出库单物流信息", "", "", PkMixEnum.NO.getCode()),
    Odo_Contacts("odo_contacts", "出库单联系人", "", "", PkMixEnum.NO.getCode()),
    Odo_Extend("odo_extend", "出库单拓展", "", "", PkMixEnum.NO.getCode()),
    Odo_Finance("odo_finance", "出库单金融", "", "", PkMixEnum.NO.getCode()),
    Odo_Item("odo_item", "出库单明细", "", "%s", PkMixEnum.NO.getCode()),
    Odo_Item_Extend("odo_item_extend", "出库单明细拓展", "", "", PkMixEnum.NO.getCode()),
    Odo_Main("odo_main", "出库单主档", "SCOU", "%s", PkMixEnum.LONG.getCode()),
    Odo_Status("odo_status", "出库单状态", "", "", PkMixEnum.NO.getCode()),
    Odo_Pack("odo_pack", "出库单包裹", "", "", PkMixEnum.NO.getCode()),
    Odo_Pack_Item("odo_pack_item", "出库单包裹明细", "", "", PkMixEnum.NO.getCode()),
    Odo_Pack_Material("odo_pack_material", "出库单包裹耗材", "", "", PkMixEnum.NO.getCode()),
    Odo_Item_Batch("odo_item_batch", "出库单批次明细", "", "", PkMixEnum.NO.getCode()),
    Odo_Item_TraceCode("odo_item_tracecode", "出库单溯源码明细", "", "", PkMixEnum.NO.getCode()),

    //库存
    Stock_Channel_Ratio("channel_stock_ratio", "渠道库存比例", "", "%08d", PkMixEnum.LONG.getCode()),
    Stock_CheckStock("check_stock_main", "盘点单主档", "SCST", "%013d", PkMixEnum.LONG.getCode()),
    Stock_CheckStockItem("check_stock_detail", "盘点单明细", "", "", PkMixEnum.NO.getCode()),

    // 运单
    Wb_Main("wb_main", "运单主档", "SCTP", "%013d", PkMixEnum.LONG.getCode()),
    Wb_Item("wb_item", "运单明细", "", "%s", PkMixEnum.LONG.getCode()),
    Wb_StatusFlow("wb_statusflow", "运单状态", "", "%s", PkMixEnum.NO.getCode()),
    Wb_LogisticsTrackFlow("wb_logisticstrackflow", "运单物流轨迹", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Ext_Dispatch("wb_ext_dispatch", "运单扩展-调度信息", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Ext_Packtag("wb_ext_packtag", "运单扩展-包裹标签", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Ext_Payment("wb_ext_payment", "运单扩展-支付信息", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Ext_Rejection("wb_ext_rejection", "运单扩展-拒收信息", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Pack("wb_pack", "运单包裹信息", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Pack_Item("wb_pack_item", "运单包裹明细", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Pack_Status("wb_pack_status", "运单包裹流水", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Coldchain_Main("wb_coldchain_main", "运单-行业-冷链主表", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Box("wb_box", "运单-包车单", "", "%s", PkMixEnum.NO.getCode()),
    Wb_Box_item("wb_box_item", "运单-包车单明细", "", "%s", PkMixEnum.NO.getCode()),

    //销售订单资料
    Sales_Carrier("sales_carrier", "销售订单物流信息", "", "", PkMixEnum.NO.getCode()),
    Sales_Contacts("sales_contacts", "销售订单联系人", "", "", PkMixEnum.NO.getCode()),
    Sales_Extend("sales_extend", "销售订单拓展", "", "", PkMixEnum.NO.getCode()),
    Sales_Finance("sales_finance", "销售订单金融", "", "", PkMixEnum.NO.getCode()),
    Sales_Item("sales_item", "销售订单明细", "", "%s", PkMixEnum.NO.getCode()),
    Sales_Item_Batch("sales_item_batch", "销售订单批次明细", "", "%s", PkMixEnum.NO.getCode()),
    Sales_Item_Extend("sales_item_extend", "销售订单明细拓展", "", "", PkMixEnum.NO.getCode()),
    Sales_Main("sales_main", "销售订单主档", "SCSO", "%s", PkMixEnum.LONG.getCode()),
    Sales_Status("sales_status", "销售订单状态", "", "", PkMixEnum.NO.getCode()),
    Sales_Unique("sales_unique","防重","","",PkMixEnum.NO.getCode()),
    Sales_Item_TraceCode("sales_item_tracecode", "销售单溯源码明细", "", "", PkMixEnum.NO.getCode()),

    //采购退货资料
    Pro_Carrier("pro_carrier", "采购退货单物流信息", "", "", PkMixEnum.NO.getCode()),
    Pro_Contacts("pro_contacts", "采购退货单联系人", "", "", PkMixEnum.NO.getCode()),
    Pro_Extend("pro_extend", "采购退货单拓展", "", "", PkMixEnum.NO.getCode()),
    Pro_Finance("pro_finance", "采购退货单金融", "", "", PkMixEnum.NO.getCode()),
    Pro_Item("pro_item", "采购退货单明细", "", "%s", PkMixEnum.NO.getCode()),
    Pro_Item_Batch("pro_item_batch", "采购退货单批次明细", "", "%s", PkMixEnum.NO.getCode()),
    Pro_Item_Extend("pro_item_extend", "采购退货单明细拓展", "", "", PkMixEnum.NO.getCode()),
    Pro_Main("pro_main", "采购退货单主档", "SCPR", "%s", PkMixEnum.LONG.getCode()),
    Pro_Status("pro_status", "采购退货单状态", "", "", PkMixEnum.NO.getCode()),
    Pro_Item_TraceCode("pro_item_tracecode", "采购退货单溯源码明细", "", "", PkMixEnum.NO.getCode()),

    Receipts_perform("receipts_perform", "入库物流执行单据", "SCIN", "%08d", PkMixEnum.LONG.getCode()),
    Receipts_perform_item("receipts_perform_item", "入库物流执行单据明细", "", "%08d", PkMixEnum.LONG.getCode()),
    receipts_perform_status("receipts_perform_status", "入库物流执行单据状态", "", "%08d", PkMixEnum.LONG.getCode()),
    receipts_perform_item_pallet("receipts_perform_item_pallet", "入库物流执行单据状态", "", "%08d", PkMixEnum.LONG.getCode()),
    PERFORM_ITEM_WMS("perform_item_wms", "入库单回传明细表", "", "%08d", PkMixEnum.LONG.getCode()),
    PERFORM_PALLET("perform_pallet", "入库物板关系表", "", "%08d", PkMixEnum.LONG.getCode()),

    //等级变更单
    STOCK_LEVEL_CHANGE_MAIN("level_change_main", "等级变更单主档", "SLC", "%08d", PkMixEnum.LONG.getCode()),
    STOCK_LEVEL_CHANGE_ITEM("level_change_item", "等级变更单明细", "", "%08d", PkMixEnum.NO.getCode()),
    STOCK_LEVEL_CHANGE_ITEM_BATCH("level_change_item_batch", "等级变更单批次明细", "", "%08d", PkMixEnum.NO.getCode()),

    EXCEL_INPUT_TASK_MAIN("input_task", "导入任务表", "STASK", "%08d", PkMixEnum.LONG.getCode()),
    EXCEL_INPUT_TASK_ITEM("input_task_item", "导入明细", "", "%08d", PkMixEnum.NO.getCode()),

    EXCEL_EXPORT_TASK_MAIN("export_task", "导出任务表", "STASK", "%08d", PkMixEnum.LONG.getCode()),
   
    EXCEPTION_REGISTER("exception_register", "异常注册", "", "", PkMixEnum.NO.getCode()),
    BUSINESS_EXCEPTION("business_exception", "业务异常", "", "", PkMixEnum.NO.getCode()),

    //异步接单任务
    BBP_ASYNC_TASK_MAIN("async_task", "异步任务表", "AT", "%08d", PkMixEnum.LONG.getCode()),
    BBP_ASYNC_TASK_ITEM("async_task_log", "异步任务日志表", "", "%08d", PkMixEnum.NO.getCode()),


    //调拨单资料
    ALLOT_MAIN("allot_main", "调拨单主档", "DBD", "%13d", PkMixEnum.LONG.getCode()),
    ALLOT_ITEM("allot_item", "调拨单明细", "", "%", PkMixEnum.NO.getCode()),
    ALLOT_STATUS("allot_status", "调拨单状态", "", "", PkMixEnum.NO.getCode()),
    ALLOT_MAIN_EXTEND("allot_main_extend", "调拨单主档扩展", "", "", PkMixEnum.NO.getCode()),
    ALLOT_ITEM_EXTEND("allot_item_extend", "调拨单明细扩展", "", "", PkMixEnum.NO.getCode()),
    ALLOT_ITEM_OUT_BATCH("allot_item_out_batch", "调拨出库单批次明细", "", "", PkMixEnum.NO.getCode()),
    ALLOT_ITEM_IN_BATCH("allot_item_in_batch", "调拨入库单批次明细", "", "", PkMixEnum.NO.getCode()),
    //合同
    CONTRACT_TEMPLATE("template", "模板", "COT", "%08d", PkMixEnum.LONG.getCode()),
    CONTRACT_CONTRACT("contract", "合同", "CON", "%08d", PkMixEnum.LONG.getCode()),


    CARRIER_SERVICE_PRODUCT("carrier_service_product", "承运商服务产品", "", "", PkMixEnum.NO.getCode()),

    EXPRESS_USER("user", "快递对接平台注册用户", "", "", PkMixEnum.LONG.getCode()),
    EXPRESS_CONFIG("express_config", "快递对接平台配置表", "", "", PkMixEnum.NO.getCode()),

    STOCK_SNAPSHOT_OMS("stock_snapshot_oms","OMS库存快照","","",PkMixEnum.NO.getCode()),
    STOCK_SNAPSHOT_RECONCILIATION("stock_snapshot_reconciliation","OMS和SAP对账表","","",PkMixEnum.NO.getCode()),
    STOCK_SNAPSHOT_SAP("stock_snapshot_sap","SAP库存快照","","",PkMixEnum.NO.getCode()),

    //接收单
    Ro_Carrier("ro_carrier", "接收单物流信息", "", "", PkMixEnum.NO.getCode()),
    Ro_Contacts("ro_contacts", "接收单联系人", "", "", PkMixEnum.NO.getCode()),
    Ro_Finance("ro_finance", "接收单金融", "", "", PkMixEnum.NO.getCode()),
    Ro_Item("ro_item", "接收单明细", "", "%s", PkMixEnum.NO.getCode()),
    Ro_Main("ro_main", "接收单主档", "YDD", "%s", PkMixEnum.LONG.getCode()),
    Ro_Status("ro_status", "接收单状态", "", "", PkMixEnum.NO.getCode()),
    Ro_SALE_MAPPING("ro_sale_mapping", "接收单映射销售单", "", "", PkMixEnum.NO.getCode()),
    Ro_SALE_ITEM_MAPPING("ro_sale_item_mapping", "接收单映射销售单明细", "", "", PkMixEnum.NO.getCode()),
    Ro_PROCESS_FLOW("ro_process_flow", "接收单状态流水", "", "", PkMixEnum.NO.getCode()),
    Ro_PROCESS_ITEM_FLOW("ro_process_item_flow", "接收单状态明细流水", "", "", PkMixEnum.NO.getCode()),
    Ro_UNIQUE("ro_unique","防重","","",PkMixEnum.NO.getCode()),
    Ro_EXTEND("ro_extend","扩展","","",PkMixEnum.NO.getCode()),
    Ro_ITEM_EXTEND("ro_item_extend","明细扩展","","",PkMixEnum.NO.getCode()),
    //自定义字段
    CUSTOM_FIELD_SCENARIO("custom_field_scenario","自定义字段场景","","",PkMixEnum.NO.getCode()),
    CUSTOM_FIELD_CONFIG("custom_field_config","自定义字段配置","","",PkMixEnum.NO.getCode()),
    FETCH_DEFINITION("fetch_definition","拉取任务定义","","",PkMixEnum.NO.getCode()),
    FETCH_JOB("fetch_job","拉取任务","","",PkMixEnum.NO.getCode()),

    Query_Scheme_Main("query_scheme_main", "查询方案主表","" ,"" , PkMixEnum.NO.getCode()),
    Query_Scheme_Item("query_scheme_item", "查询方案明细表","" ,"" , PkMixEnum.NO.getCode()),
    SITE("site", "站点主表","ZD" ,"%11d" , PkMixEnum.NO.getCode()),
    Sorting_Center("sorting_center", "分拣中心","FJ" ,"%11d" , PkMixEnum.NO.getCode()),
    ;

    /**
     * id生成的key
     */
    String table;
    /**
     * 业务名称
     **/
    String name;
    /**
     * 业务单号前缀
     **/
    String prefix;

    /**
     * 业务单号格式化
     **/
    String format;
    /**
     * 是否混淆单号 开启混淆生成的单号最低11位
     */
    int mix;


    PkTable(String table, String name, String prefix, String format, Integer mix) {
        this.table = table;
        this.name = name;
        this.prefix = prefix;
        this.format = format;
        this.mix = mix;
    }

    public static final Map<String, PkTable> PkTableEnum_MAP = new HashMap();

    static {
        Iterator i$ = EnumSet.allOf(PkTable.class).iterator();

        while (i$.hasNext()) {
            PkTable e = (PkTable) i$.next();
            PkTableEnum_MAP.put(e.getTable(), e);
        }
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFormat() {
        return format;
    }

    public int getMix() {
        return mix;
    }

    public PkTable getPkTable(String table) {
        return PkTableEnum_MAP.get(table);
    }
}
