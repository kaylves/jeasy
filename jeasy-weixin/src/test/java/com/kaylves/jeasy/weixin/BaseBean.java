package com.kaylves.jeasy.weixin;

import org.junit.BeforeClass;

/**
 * <一句话功能简述>基类
 * <p><功能详细描述>
 * @author  kaylves
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseBean
{
    //public static final String APP_ID= "wx8175da54abffb03d";
    //public static final String appsecret= "fa06af22bcdf88fc4cbb2f60aa81baf0";
    
    //kaylves test 共众号
    public static final String APP_ID= "wx6f3014ea424079b5";
    public static final String appsecret= "c252ad94582e94f73a481b04aee3c060";
    
    public static String token_key= "KFQZcOvHtTm0uuVk8yQuHGe2rPZHdOJPJzsPau7hLI2hxJsqWIhf18OBM0i1LKyLcvrCKphZvYrzV_-FBymNz7HbuRcoZ0CmKL4VfcGpOqmzNBTpMsGqP9Ul06mJoys4PLOdADAKGJ";
    
    @BeforeClass
    public static void beforeClass(){
        //token_key = WeiXinApi.getAccessToken( APP_ID, appsecret);
        System.out.println(token_key);
    }
    
    public static void main( String[] args )
    {
        System.out.println(System.currentTimeMillis());
    }
}
