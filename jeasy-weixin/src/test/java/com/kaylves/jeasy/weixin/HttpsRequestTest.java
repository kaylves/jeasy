package com.kaylves.jeasy.weixin;

import org.junit.BeforeClass;
import org.junit.Test;

import com.kaylves.jeasy.weixin.api.WeiXinPayApi;
import com.kaylves.jeasy.weixin.entity.UnifiedOrderReqData;
import com.kaylves.jeasy.weixin.utils.httpclient.HttpsRequest;

public class HttpsRequestTest
{
    private static HttpsRequest request = null;
    
    @BeforeClass
    public static void before(){
        try
        {
            request = new  HttpsRequest("D:/cert/apiclient_cert.p12","1244719102");
        }  catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test() throws Exception{
        UnifiedOrderReqData order = new UnifiedOrderReqData();
        order.setTrade_type( "JSAPI" );
        order.setAppid( "wx8175da54abffb03d" );
        order.setBody( "kaylves_commodity" );
        order.setMch_id( "1244719102" );
        order.setOpenid( "oKeoWuHJitDA68X6IV_G1NkdvpJc" );
        order.setNotify_url( "http://erp.ycii.net/tgsyl/order.jhtm" );
        order.setOut_trade_no( "1234" );
        order.setTotal_fee( 100 );
        order.setSpbill_create_ip( "8.8.8.8" );
        
        WeiXinPayApi.unifiedorder( order, request, "F2455D52E56C41C79E17F5A3B34BDB22" );
    }
}
