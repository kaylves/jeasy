
package com.kaylves.jeasy.weixin.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaylves.jeasy.weixin.common.RandomStringGenerator;
import com.kaylves.jeasy.weixin.common.Signature;
import com.kaylves.jeasy.weixin.common.Util;
import com.kaylves.jeasy.weixin.context.WeiXinApplicationContext;
import com.kaylves.jeasy.weixin.entity.UnifiedOrderReqData;
import com.kaylves.jeasy.weixin.entity.UnifiedOrderResData;
import com.kaylves.jeasy.weixin.utils.httpclient.HttpsRequest;
/**
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年8月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WeiXinPayApi
{
  
    private static Logger log = LoggerFactory.getLogger( WeiXinPayApi.class );

    /**
     * <统一支付接口>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年8月27日 下午4:45:32
     * @param order
     * @param request
     * @param key
     * @return [参数说明]
     * 
     * @return UnifiedOrderResData [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static UnifiedOrderResData unifiedorder(UnifiedOrderReqData order,HttpsRequest request,String key)
    {
        order.setNonce_str( RandomStringGenerator.getRandomStringByLength( 20 ) );
        String sign = Signature.getSign(order.objectToMap(  ),key);
        order.setSign( sign );
        
        String payServiceResponseString = request.sendPost( WeiXinApplicationContext.UNIFIEDORDER_API, order );
        

        //将从API返回的XML数据映射到Java对象
        UnifiedOrderResData scanPayResData = (UnifiedOrderResData) Util.getObjectFromXML(payServiceResponseString, UnifiedOrderResData.class);
        
        if (scanPayResData == null || scanPayResData.getReturn_code() == null) {
            log.error("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            return null;
        }

        if (scanPayResData.getReturn_code().equals("FAIL")) {
            log.error(scanPayResData.getReturn_msg());
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            return null;
        } else {
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString, key)) {
                log.error("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                return null;
            }


            if (scanPayResData.getResult_code().equals("SUCCESS")) {
                return scanPayResData;
            }
            return null;
        } 
    }

}
