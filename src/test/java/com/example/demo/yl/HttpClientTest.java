package com.example.demo.yl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import okhttp3.Response;

public class HttpClientTest {

    public IbMainParam getIbOrder() {
        List<IbItemParam> ibItemParamList = new ArrayList<IbItemParam>();
        IbItemParam param = new IbItemParam();
        param.setApplyOutstoreQty(new BigDecimal(111));
        param.setGoodsId(4398046511190L);
        param.setGoodsLevel("100");
        param.setGoodsName("tangwei005");
        param.setGoodsNo("CMG4398046511190");
        param.setIbId(560L);
        param.setIsvGoodsNo("1233311A2");
        param.setId(788L);
        param.setOrderLine("1");
        ibItemParamList.add(param);
        IbMainParam mainParam = new IbMainParam();
        mainParam.setIbItemParamList(ibItemParamList);
        mainParam.setCreateTime(1630310344000L);
        mainParam.setCreateUser("yili");
        mainParam.setDeptId(4398046511123L);
        mainParam.setDeptName("deptSellerTangWei");
        mainParam.setDeptNo("CBU30806325591884");
        mainParam.setIbCancleStatus(900);
        mainParam.setIbNo("CIB0000000000560");
        mainParam.setIbSource((byte)2);
        mainParam.setIbStatus(9000);
        mainParam.setId(560L);
        mainParam.setIsvIbNo("TEST330823ian600002");
        mainParam.setOriginWarehouseId(375L);
        mainParam.setOriginWarehouseName("经济仓测试3.0二期仓库测试");
        mainParam.setOriginWarehouseNo("800000375");
        mainParam.setSellerId(29L);
        mainParam.setSellerName("sellerTangWei");
        mainParam.setSellerNo("CCP0000000000029");
        mainParam.setShipperId(4418046511145L);
        mainParam.setShipperName("京东配送");
        mainParam.setShipperNo("CYF4418046511145");
        mainParam.setSpecialWarehouse(false);
        mainParam.setTargetWarehouseId(60L);
        mainParam.setTargetWarehouseName("J-WMS-TW03");
        mainParam.setTargetWarehouseNo("800000060");
        mainParam.setUpdateTime(1630310344000L);
        return mainParam;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TransportHttpWLNServiceImpl impl = new TransportHttpWLNServiceImpl();
		HttpUtils util = new HttpUtils();
        HttpClientUtil instance = HttpClientUtil.getInstance();
        try {
            IbMainParam ibMainParam = new HttpClientTest().getIbOrder();

            String content = "123";
            //调用Oracle中转代理接口
            JSONObject jsonObject = JSONObject.fromObject(ibMainParam);
            String requestUrl = "http://localhost:8080/yl/transfer";
            Response dataStr = util.post(requestUrl,jsonObject.toString());
            System.out.println(dataStr);
            //String s1 = instance.sendHttpPost(requestUrl, jsonObject.toString());
            //System.out.println(s1);
            
            //调用Oracle中转代理接口
            String ibNo = ibMainParam.getIbNo();
            StringBuilder requestStr = new StringBuilder();
            requestStr.append("content=").append(content);
            requestStr.append("&ibNo=").append(ibNo);
            String url = "http://localhost:8080/yl/transferTest?" + requestStr.toString();
            //String str = impl.transport(url);
            //System.out.println(str);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

	}

}
