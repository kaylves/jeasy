package com.kaylves.jeasy.weixin.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年8月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnifiedOrderReqData
{
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 设备号
     */
    private String device_info;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 货币类型
     */
    private String fee_type;
    /**
     * 总金额,单位（分）
     */
    private Integer total_fee;
    /**
     * 终端IP
     * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    private String spbill_create_ip;
    /**
     * 交易起始时间
     */
    private String time_start;
    /**
     * 交易结束时间
     */
    private String time_expire;
    /**
     * 商品标记
     */
    private String goods_tag;
    /**
     * 接收微信支付异步通知回调地址
     */
    private String notify_url;
    /**
     * 交易类型
     * JSAPI，NATIVE，APP，WAP
     */
    private String trade_type;
    /**
     * 商品ID
     */
    private String product_id;
    /**
     * 指定支付方式
     */
    private String limit_pay;
    /**
     * 用户标识
     */
    private String openid;
    
    /**
     * 签名
     */
    private String sign;
    public String getAppid()
    {
        return appid;
    }
    public String getAttach()
    {
        return attach;
    }
    public String getBody()
    {
        return body;
    }
    public String getDetail()
    {
        return detail;
    }
    public String getDevice_info()
    {
        return device_info;
    }
    public String getFee_type()
    {
        return fee_type;
    }
    public String getGoods_tag()
    {
        return goods_tag;
    }
    public String getLimit_pay()
    {
        return limit_pay;
    }
    public String getMch_id()
    {
        return mch_id;
    }
    public String getNonce_str()
    {
        return nonce_str;
    }
    public String getNotify_url()
    {
        return notify_url;
    }
    public String getOpenid()
    {
        return openid;
    }
    public String getOut_trade_no()
    {
        return out_trade_no;
    }
    public String getProduct_id()
    {
        return product_id;
    }
    public String getSign()
    {
        return sign;
    }
    public String getSpbill_create_ip()
    {
        return spbill_create_ip;
    }
    public String getTime_expire()
    {
        return time_expire;
    }
    public String getTime_start()
    {
        return time_start;
    }
    public Integer getTotal_fee()
    {
        return total_fee;
    }
    public String getTrade_type()
    {
        return trade_type;
    }
    public void setAppid( String appid )
    {
        this.appid = appid;
    }
    public void setAttach( String attach )
    {
        this.attach = attach;
    }
    public void setBody( String body )
    {
        this.body = body;
    }
    public void setDetail( String detail )
    {
        this.detail = detail;
    }
    public void setDevice_info( String device_info )
    {
        this.device_info = device_info;
    }
    public void setFee_type( String fee_type )
    {
        this.fee_type = fee_type;
    }
    public void setGoods_tag( String goods_tag )
    {
        this.goods_tag = goods_tag;
    }
    public void setLimit_pay( String limit_pay )
    {
        this.limit_pay = limit_pay;
    }
    
    public void setMch_id( String mch_id )
    {
        this.mch_id = mch_id;
    }
    public void setNonce_str( String nonce_str )
    {
        this.nonce_str = nonce_str;
    }
    public void setNotify_url( String notify_url )
    {
        this.notify_url = notify_url;
    }
    public void setOpenid( String openid )
    {
        this.openid = openid;
    }
    public void setOut_trade_no( String out_trade_no )
    {
        this.out_trade_no = out_trade_no;
    }
    public void setProduct_id( String product_id )
    {
        this.product_id = product_id;
    }
    public void setSign( String sign )
    {
        this.sign = sign;
    }
    public void setSpbill_create_ip( String spbill_create_ip )
    {
        this.spbill_create_ip = spbill_create_ip;
    }
    public void setTime_expire( String time_expire )
    {
        this.time_expire = time_expire;
    }
    public void setTime_start( String time_start )
    {
        this.time_start = time_start;
    }
    public void setTotal_fee( Integer total_fee )
    {
        this.total_fee = total_fee;
    }
    public void setTrade_type( String trade_type )
    {
        this.trade_type = trade_type;
    }
    
    /**
     * <Object 转Map>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年8月27日 下午3:14:51
     * @param o
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public  Map<String,Object> objectToMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
