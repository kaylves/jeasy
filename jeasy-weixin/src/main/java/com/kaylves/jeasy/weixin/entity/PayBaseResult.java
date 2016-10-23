package com.kaylves.jeasy.weixin.entity;

/**
 * <支付基础结果集>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年8月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PayBaseResult
{
    private String return_code;
    
    private String return_msg;

    public String getReturn_code()
    {
        return return_code;
    }

    public void setReturn_code( String return_code )
    {
        this.return_code = return_code;
    }

    public String getReturn_msg()
    {
        return return_msg;
    }

    public void setReturn_msg( String return_msg )
    {
        this.return_msg = return_msg;
    }
}
